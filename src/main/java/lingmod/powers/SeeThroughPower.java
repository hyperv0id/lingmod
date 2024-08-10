package lingmod.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static lingmod.ModCore.makeID;

/**
 * 看破
 */
public class SeeThroughPower extends AbstractEasyPower {
    public static final String POWER_NAME = SeeThroughPower.class.getSimpleName();
    public static final String ID = makeID(POWER_NAME);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);

    public SeeThroughPower(AbstractCreature owner, int amount) {
        super(ID, powerStrings.NAME, PowerType.BUFF, true, owner, amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        if (!isPlayer) addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        if (this.amount <= 0) addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (this.amount >= 1) {
            this.flash();
            addToBot(new ReducePowerAction(owner, owner, this, 1));
        }
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0].replaceAll("!X!", String.valueOf(amount));
        super.updateDescription();
    }
}
