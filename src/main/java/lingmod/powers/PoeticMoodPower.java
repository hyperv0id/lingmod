package lingmod.powers;

import static lingmod.ModCore.makeID;
import static lingmod.powers.AbstractEasyPower.I18N.getName;

import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BufferPower;

import lingmod.ModCore;

/**
 * 诗兴
 * 单例
 * 打出不同类型牌时增加1点，叠满后根据等级获得对应Buff
 */
public class PoeticMoodPower extends AbstractEasyPower {

    public static final String CLASS_NAME = PoeticMoodPower.class.getSimpleName();
    public static final String ID = makeID(CLASS_NAME);
    public static int powerGained = 0;
    public static int BUFFER_NUM = 12;

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    public static final Logger logger = ModCore.logger;

    public PoeticMoodPower(AbstractCreature owner, int amount) {
        super(ID, getName(ID), TYPE, false, owner, amount);
    }


    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if(this.amount >= BUFFER_NUM) {
            addToBot(new ApplyPowerAction(owner, owner, new BufferPower(owner, 1)));
            this.amount -= BUFFER_NUM;
            this.flash();
        }
    }
}
