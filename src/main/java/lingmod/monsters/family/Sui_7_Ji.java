package lingmod.monsters.family;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import lingmod.cards.status.Sui_7DealCard;
import lingmod.patch.OnSaveLoadPatch;
import lingmod.powers.Sui7DealPower;
import lingmod.util.MonsterHelper;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;
import static lingmod.ModCore.makeImagePath;

/**
 * 绩老七：
 * 1. 攻击时给予等量缠绕
 * 2. 选择1-n张牌消耗，然后替换成随机卡牌，每交易一张，绩获得1力量
 */
public class Sui_7_Ji extends AbsFamily {
    public static final String ID = makeID(Sui_7_Ji.class.getSimpleName());
    public static final int MAX_HP = 200;
    protected static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOGS = monsterStrings.DIALOG;
    protected static final String IMG_PATH = makeImagePath("monsters/MonsterSui_7_Ji.png");
    public static int STRENGTH_GROW = 1;
    protected boolean firstTurn = true;
    protected int baseDamage = 14;

    public Sui_7_Ji() {
        super(NAME, ID, MAX_HP, 0F, 0F, 200.0F, 200.0F, IMG_PATH,
                0F, 0F);
        this.type = EnemyType.ELITE;
        this.dialogX = -200.0F * Settings.scale;
        this.dialogY = 10.0F * Settings.scale;
        this.damage.add(new DamageInfo(this, baseDamage));
        role = FamilyRole.ENEMY;
    }

    @Override
    public void usePreBattleAction() {
        super.useUniversalPreBattleAction();
    }

    @Override
    protected void getMove(int moveID) {
        if (role == FamilyRole.ENEMY) {
            if (lastMove((byte) 1)) {
                this.setMove((byte) 2, Intent.ATTACK_DEBUFF, this.baseDamage);
            } else {
                this.setMove((byte) 1, Intent.ATTACK, this.baseDamage);
            }
        }
        // this.setMove((byte) 1, Intent.MAGIC);
    }

    @Override
    public void die() {
        super.die();
        Sui7DealPower power = (Sui7DealPower) getPower(Sui7DealPower.POWER_ID);
        if (power != null)
            BaseMod.unsubscribeLater(power);
    }

    @Override
    protected void takeTurnAlly() {
        // 对所有敌人施加缠绕
        MonsterHelper.allMonstersNotSummon().forEach(mo -> addToBot(new ApplyPowerAction(this, mo, new ConstrictedPower(mo, mo, 3))));
    }

    @Override
    protected void takeTurnEnemy() {

        AbstractPlayer p = AbstractDungeon.player;
        DamageInfo info = this.damage.get(0);

        if (firstTurn) {
            addToBot(new MakeTempCardInDrawPileAction(new Sui_7DealCard().makeCopy(), Wiz.adp().masterDeck.size() / 2,
                    true, true));
            if (OnSaveLoadPatch.saveTimes > 0) {
                EventStrings es = CardCrawlGame.languagePack.getEventString(makeID("SL_Monster_Talk"));
                addToBot(new TalkAction(this, es.DESCRIPTIONS[0], 0.5f, 2.0f));
            }
            this.setMove((byte) 1, Intent.ATTACK, this.baseDamage);
        } else {
            if (this.nextMove == 1) {
                addToBot(new TalkAction(this, DIALOGS[2], 0.5F, 2.0F));
                addToBot(new DamageAction(p, info, AttackEffect.FIRE));
                this.setMove((byte) 2, Intent.ATTACK_DEBUFF, this.baseDamage);
            } else if (nextMove == 2) {
                addToBot(new TalkAction(this, DIALOGS[2], 0.5F, 2.0F));
                addToBot(new DamageAction(p, info, AttackEffect.FIRE));
                addToBot(new ApplyPowerAction(p, this, new ConstrictedPower(p, this, info.output)));
                this.setMove((byte) 1, Intent.ATTACK, this.baseDamage);
            }
        }

        firstTurn = false;
    }

    @Override
    protected void takeFirstTurn() {
        if (role == FamilyRole.ENEMY) {
            super.useUniversalPreBattleAction();
            AbstractPlayer p = AbstractDungeon.player;
            addToBot(new ApplyPowerAction(this, this, new Sui7DealPower(this, this, 1)));
            if (Wiz.isPlayerLing()) {
                addToBot(new TalkAction(this, DIALOGS[0], 0.5F, 2.0F));
            }
            setMove((byte) 99, Intent.DEBUFF);
        } else {
            setMove((byte) 2, Intent.DEBUFF);
        }
    }
}
