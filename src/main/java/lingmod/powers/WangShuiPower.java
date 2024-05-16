package lingmod.powers;

import static lingmod.ModCore.makeID;
import static lingmod.powers.AbstractEasyPower.I18N.getName;

import com.megacrit.cardcrawl.core.AbstractCreature;

import lingmod.actions.ExhaustAllAction;

/**
 * 忘水：回合结束时消耗所有手牌
 */
public class WangShuiPower extends AbstractEasyPower{
    public static final String CLASS_NAME = WangShuiPower.class.getSimpleName();
    public static final String ID = makeID(CLASS_NAME);

    public WangShuiPower(AbstractCreature owner) {
        super(ID, getName(ID), PowerType.DEBUFF, true, owner, 0);
    }

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        super.atEndOfTurnPreEndTurnCards(isPlayer);
        addToBot(new ExhaustAllAction());
        // addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

}
