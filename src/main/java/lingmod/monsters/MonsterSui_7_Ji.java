package lingmod.monsters;

import basemod.BaseMod;
import basemod.abstracts.CustomMonster;
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
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import lingmod.cards.status.Sui_7DealCard;
import lingmod.patch.OnSaveLoadPatch;
import lingmod.powers.Sui7DealPower;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;
import static lingmod.ModCore.makeImagePath;

/**
 * 绩老七：
 * 1. 攻击时给予等量缠绕
 * 2. 选择1-n张牌消耗，然后替换成随机卡牌，每交易一张，绩获得1力量
 */
public class MonsterSui_7_Ji extends CustomMonster {
    public static final String ID = makeID(MonsterSui_7_Ji.class.getSimpleName());
    public static final int MAX_HP = 200;
    protected static final MonsterStrings ms = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = ms.NAME;
    public static final String[] MOVES = ms.MOVES;
    public static final String[] DIALOGS = ms.DIALOG;
    protected static final String IMG_PATH = makeImagePath("monsters/MonsterSui_7_Ji.png");
    public static int STRENGTH_GROW = 1;
    protected boolean firstTurn = true;
    protected int baseDamage = 5;

    public MonsterSui_7_Ji() {
        super(NAME, ID, MAX_HP, -10.0F, -30.0F, 476.0F, 410.0F, null,
                -50.0F, 30.0F);
        this.img = ImageMaster.loadImage(IMG_PATH);
        this.type = EnemyType.ELITE;
        this.dialogX = -200.0F * Settings.scale;
        this.dialogY = 10.0F * Settings.scale;
        this.damage.add(new DamageInfo(this, baseDamage));
    }

    @Override
    public void usePreBattleAction() {
        super.useUniversalPreBattleAction();
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(this, this, new Sui7DealPower(this, this, 1)));
        if (Wiz.isPlayerLing()) {
            addToBot(new TalkAction(this, DIALOGS[0], 0.5F, 2.0F));
        }
        setMove((byte) 99, Intent.DEBUFF);
    }

    @Override
    protected void getMove(int moveID) {
        if (lastMove((byte) 1)) {
            this.setMove((byte) 2, Intent.ATTACK_DEBUFF, this.baseDamage);
        } else {
            this.setMove((byte) 1, Intent.ATTACK, this.baseDamage);
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
    public void takeTurn() {
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
}
