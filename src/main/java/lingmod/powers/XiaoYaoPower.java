package lingmod.powers;

import static lingmod.ModCore.makeID;

import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

import lingmod.ModCore;

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
    public static final Logger logger = ModCore.logger;

    private static int id_postfix = 0;

    public XiaoYaoPower(AbstractCreature owner) {
        super(POWER_ID + id_postfix++, NAME, TYPE, TURN_BASED, owner, 0);
        this.updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        if (owner != null) {
            amount++;
            addToBot(new ApplyPowerAction(owner, owner,
                    new DrawCardNextTurnPower(owner, amount)));
            updateDescription();
        }
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        // 可以扩容
        if (amount > 5) {
            int amt = amount - 5;
            if (amt > 5)
                amt = 5; // 最多有 10+10 = 15张牌
            addToBot(new ApplyPowerAction(owner, owner,
                    new CapacityExpansionPower(owner, amt)));
        }
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount);
    }
}
