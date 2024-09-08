package lingmod.monsters;

import static lingmod.ModCore.makeID;
import static lingmod.ModCore.makeImagePath;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import basemod.abstracts.CustomMonster;
import lingmod.actions.FastApplyPower_Action;
import lingmod.powers.GiveGoldAsHP;
import lingmod.powers.InvincibleForPlayer;
import lingmod.powers.ShiftingPower2;
import lingmod.util.MonsterHelper;
import lingmod.util.Wiz;

/**
 * 挑山人大战掌柜的
 */
public class InnManager extends CustomMonster {
    public static final String ID = makeID(InnManager.class.getSimpleName());
    public static final int MAX_HP = 100;
    protected static final MonsterStrings ms = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = ms.NAME;
    public static final String[] MOVES = ms.MOVES;
    public static String FRAIL_NAME = MOVES[0];
    protected static final String IMG_PATH = makeImagePath("monsters/Avg_avg_npc_300.png");
    public static int STRENGTH_GROW = 1;
    protected boolean firstTurn = true;
    protected int DMG_1 = 5;
    protected int DMG_2 = 14;

    public InnManager() {
        super(NAME, ID, MAX_HP, -10.0F, -30.0F, 476.0F, 410.0F, IMG_PATH,
                150.0F, 30.0F);
        this.img = ImageMaster.loadImage(IMG_PATH);
        this.type = EnemyType.NORMAL;
        this.dialogX = -200.0F * Settings.scale;
        this.dialogY = 10.0F * Settings.scale;
        this.damage.add(new DamageInfo(this, DMG_1));
        this.damage.add(new DamageInfo(this, DMG_2));
    }

    @Override
    public void usePreBattleAction() {
        super.useUniversalPreBattleAction();
        addToBot(new FastApplyPower_Action(this, this, new InvincibleForPlayer(this)));
        addToBot(new FastApplyPower_Action(this, this, new GiveGoldAsHP(this, 2)));
        addToBot(new FastApplyPower_Action(this, this, new ShiftingPower2(this)));
    }


    public AbstractCreature getTarget() {
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (mo.id.equals(MountainPicker.ID)) return mo;
        }
        return null;
    }
    @Override
    public void takeTurn() {
        if (getTarget() == null) {
            turn_exit();
            return;
        }
        switch (nextMove) {
            case 1:
                turn_buff();
                setMove((byte) 2, Intent.ATTACK, damage.get(0).base, 2, true);
                break;
            case 2:
                turn_attack();
                setMove((byte) 3, Intent.ATTACK, damage.get(1).base);
                break;
            case 3:
                turn_attack_2();
                setMove((byte) 4, Intent.DEFEND);
                break;
            case 4:
                turn_defend();
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

    public void turn_defend() {
        addToBot(new GainBlockAction(this, 20));
    }

    public void turn_buff() {
        addToBot(new FastApplyPower_Action(this, this, new PlatedArmorPower(this, 16)));
        addToBot(new FastApplyPower_Action(this, this, new BarricadePower(this)));
    }

    private void turn_attack() {
        int mult = MonsterHelper.intentMultiAmt(this);
        for (int i = 0; i < mult; i++) {
            addToBot(new DamageAction(getTarget(), damage.get(0)));
        }
    }

    private void turn_attack_2() {
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
