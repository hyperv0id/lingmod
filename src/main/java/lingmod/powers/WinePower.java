package lingmod.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * remove vigor only when wine >= 0
 */
public class WinePower extends AbstractEasyPower {
    public static final String CLASS_NAME = WinePower.class.getSimpleName();
    public static final String POWER_ID = makeID(CLASS_NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;

    public WinePower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, TYPE, false, owner, amount);
        this.amount = amount;
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        if (this.amount <= 0) addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                this));
    }

    public void dampLater() {
        if (Wiz.isStanceNell()) {
            addToBot(new ReducePowerAction(owner, owner, this, 1));
        } else {
            addToBot(new ReducePowerAction(owner, owner, this, (this.amount + 1) / 2));
        }
    }
}