package lingmod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BufferPower;
import lingmod.ModCore;
import org.apache.logging.log4j.Logger;

import static lingmod.ModCore.makeID;
import static lingmod.powers.AbstractEasyPower.I18N.getName;

/**
 * 诗兴
 * 单例
 * 打出不同类型牌时增加1点，叠满后根据等级获得对应Buff
 */
public class PoeticMoodPower extends AbstractEasyPower {

    public static final String CLASS_NAME = PoeticMoodPower.class.getSimpleName();
    public static final String ID = makeID(CLASS_NAME);
    public static int powerGained = 0;
    public static int threshold = 12;

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    public static final Logger logger = ModCore.logger;

    public PoeticMoodPower(AbstractCreature owner, int amount) {
        super(ID, getName(ID), TYPE, false, owner, amount);
    }

    public void checkTrigger() {

        if (this.amount >= threshold) {
            this.flash();
            this.stackPower(-threshold);
            addToBot(new ApplyPowerAction(owner, owner, new BufferPower(owner, 1)));
        }
    }


    @Override
    public void stackPower(int stackAmount) {
        if (stackAmount > 0)
            powerGained += stackAmount;
        super.stackPower(stackAmount);
        this.checkTrigger();
    }
}
