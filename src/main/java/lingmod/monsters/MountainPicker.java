package lingmod.monsters;

import static lingmod.ModCore.makeID;
import static lingmod.ModCore.makeImagePath;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.abstracts.CustomMonster;
import lingmod.actions.MyApplyPower_Action;
import lingmod.powers.GiveGoldAsHP;
import lingmod.powers.InvincibleForPlayer;
import lingmod.powers.ShiftingPower2;
import lingmod.util.MonsterHelper;
import lingmod.util.Wiz;

/**
 * 挑山人大战掌柜的
 */
public class MountainPicker extends CustomMonster {
    public static final String ID = makeID(MountainPicker.class.getSimpleName());
    public static final int MAX_HP = 100;
    protected static final MonsterStrings ms = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = ms.NAME;
    protected static final String IMG_PATH = makeImagePath("monsters/Avg_avg_npc_302.png");
    protected final int DMG_1 = 10;
    protected final int DMG_2 = 30;

    public MountainPicker() {
        super(NAME, ID, MAX_HP, -10.0F, -30.0F, 476.0F, 410.0F, IMG_PATH,
                -250.0F, 30.0F);
        this.type = EnemyType.NORMAL;
        this.dialogX = -200.0F * Settings.scale;
        this.dialogY = 10.0F * Settings.scale;
        this.damage.add(new DamageInfo(this, DMG_1));
        this.damage.add(new DamageInfo(this, DMG_2));
    }

    @Override
    public void usePreBattleAction() {
        super.useUniversalPreBattleAction();
        addToBot(new MyApplyPower_Action(this, this, new InvincibleForPlayer(this)));
        addToBot(new MyApplyPower_Action(this, this, new GiveGoldAsHP(this, 1)));
        addToBot(new MyApplyPower_Action(this, this, new ShiftingPower2(this)));
    }

    public AbstractCreature getTarget() {
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (mo.id.equals(InnManager.ID)) return mo;
        }
        return null;
    }

    @Override
    public void takeTurn() {
        switch (nextMove) {
            case 1:
                turn_buff();
                setMove((byte) 2, Intent.ATTACK_BUFF, damage.get(0).base, 2, true);
                break;
            case 2:
                turn_atkb1();
                addToBot(new MyApplyPower_Action(this, this, new StrengthPower(this, 2)));
                setMove((byte) 3, Intent.ATTACK_BUFF, damage.get(1).base);
                break;
            case 3:
                turn_atkb2();
                addToBot(new MyApplyPower_Action(this, this, new StrengthPower(this, 2)));
                setMove((byte) 4, Intent.ATTACK_DEBUFF);
                break;
            case 4:
                turn_atkd();
                setMove((byte) 5, Intent.ESCAPE);
                break;
            default:
                turn_exit();
                break;

        }
    }

    public void turn_exit() {
        Wiz.atb(new TalkAction(this, ms.DIALOG[0], 2.0F, 2.0F));
        AbstractDungeon.actionManager.addToBottom(new EscapeAction(this));
        AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte) 99, Intent.ESCAPE));
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void turn_atkd() {
        addToBot(new RemoveAllBlockAction(getTarget(), this));
        addToBot(new DamageAction(getTarget(), damage.get(1)));
    }

    public void turn_buff() {
        addToBot(new MyApplyPower_Action(this, this, new StrengthPower(this, 5)));
    }

    private void turn_atkb1() {
        int multi = MonsterHelper.intentMultiAmt(this);
        for (int i = 0; i < Math.max(1, multi); i++) {
            addToBot(new DamageAction(getTarget(), damage.get(0)));
        }
    }

    private void turn_atkb2() {
        addToBot(new DamageAction(getTarget(), damage.get(1)));
    }


    @Override
    protected void getMove(int num) {
        this.setMove((byte) 1, Intent.BUFF);
        //        this.setMove(FRAIL_NAME, (byte) 4, Intent.ESCAPE);
        if (getTarget() == null) {
            setMove((byte) 99, Intent.ESCAPE);
        }
    }

}
