package lingmod.powers;

import basemod.BaseMod;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import lingmod.actions.MyApplyPower_Action;

import static lingmod.ModCore.makeID;

/**
 * 逍遥：回合结束后抽牌++
 */
public class XiaoYaoPower extends AbstractEasyPower {
    public static final String CLASS_NAME = XiaoYaoPower.class.getSimpleName();
    public static final String POWER_ID = makeID(CLASS_NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = true; // 是否回合后消失
    private int capaExpand;

    public XiaoYaoPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, TYPE, TURN_BASED, owner, amount);
        // 覆盖卡图
        this.updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        BaseMod.MAX_HAND_SIZE++;
        capaExpand++;
        if (owner != null) {
            amount++;
            addToBot(new MyApplyPower_Action(owner, owner,
                    new DrawCardNextTurnPower(owner, amount)));
            updateDescription();
        }
    }

    @Override
    public void onRemove() {
        super.onRemove();
        BaseMod.MAX_HAND_SIZE -= capaExpand;
        capaExpand = 0;
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount);
    }
}
