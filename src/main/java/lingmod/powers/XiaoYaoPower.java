package lingmod.powers;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import lingmod.actions.FastApplyPower_Action;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * 逍遥：
 * 能量上限++
 * 回合结束后抽牌++
 */
public class XiaoYaoPower extends AbstractEasyPower {
    public static final String CLASS_NAME = XiaoYaoPower.class.getSimpleName();
    public static final String POWER_ID = makeID(CLASS_NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = true; // 是否回合后消失

    public XiaoYaoPower(AbstractCreature owner, int adder) {
        super(POWER_ID, NAME, TYPE, TURN_BASED, owner, adder);
        isTwoAmount = true;
        this.amount2 = 0; // 增加的抽卡数
        this.amount = adder; // 回合结束后增加多少
        // 覆盖卡图
        this.updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        if (owner != null) {
            BaseMod.MAX_HAND_SIZE += amount;
            amount2 += amount;
            addToBot(new FastApplyPower_Action(owner, owner,
                    new DrawCardNextTurnPower(owner, amount2)));
            updateDescription();
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        Wiz.atb(new GainEnergyAction(amount));
    }

    @Override
    public void onRemove() {
        super.onRemove();
        BaseMod.MAX_HAND_SIZE -= amount2;
        amount2 = 0;
    }

    @Override
    public void onVictory() {
        super.onVictory();
        BaseMod.MAX_HAND_SIZE -= amount2;
        amount2 = 0;
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount, amount2, amount);
    }
}
