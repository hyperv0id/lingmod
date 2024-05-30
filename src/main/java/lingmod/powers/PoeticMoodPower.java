package lingmod.powers;

import static lingmod.ModCore.makeID;
import static lingmod.powers.AbstractEasyPower.I18N.getName;

import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import lingmod.ModCore;
import lingmod.stance.PoeticStance;

/**
 * 诗意
 * 单例
 * 打出不同类型牌时增加1点，叠满后根据等级获得对应Buff
 */
public class PoeticMoodPower extends AbstractEasyPower {

    public static final String CLASS_NAME = PoeticMoodPower.class.getSimpleName();
    public static final String ID = makeID(CLASS_NAME);
    public static int powerGained = 0;

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    public static final Logger logger = ModCore.logger;

    public PoeticMoodPower(AbstractCreature owner, int amount) {
        super(ID, getName(ID), TYPE, false, owner, amount);
    }


    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if(this.amount >= 12) {
            addToBot(new ChangeStanceAction(new PoeticStance()));
            this.amount -= 12;
            this.flash();
        }
    }
}
