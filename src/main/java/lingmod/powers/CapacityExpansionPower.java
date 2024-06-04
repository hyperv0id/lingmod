package lingmod.powers;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static lingmod.ModCore.makeID;

// Referenced classes of package Goldenglow.powers:
//            AbstractGoldenglowPower
public class CapacityExpansionPower extends AbstractEasyPower {

    public CapacityExpansionPower(AbstractCreature owner, int amt)
    {
        super(ID, NAME, PowerType.BUFF, false, owner, amt);
    }

    public String getDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void onInitialApplication()
    {
        super.onInitialApplication();
        BaseMod.MAX_HAND_SIZE += amount;
    }

    public void onRemove()
    {
        super.onRemove();
        BaseMod.MAX_HAND_SIZE -= amount;
    }

    public void stackPower(int stackAmount)
    {
        super.stackPower(stackAmount);
        BaseMod.MAX_HAND_SIZE += stackAmount;
    }

    public void reducePower(int reduceAmount)
    {
        super.reducePower(reduceAmount);
        BaseMod.MAX_HAND_SIZE -= reduceAmount;
    }

    public void onVictory()
    {
        super.onVictory();
        BaseMod.MAX_HAND_SIZE -= amount;
    }

    public void atEndOfTurn(boolean isPlayer)
    {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    public static final String ID = makeID(CapacityExpansionPower.class.getSimpleName());
    private static final PowerStrings powerStrings;
    private static final String NAME;
    private static final String DESCRIPTIONS[];

    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}