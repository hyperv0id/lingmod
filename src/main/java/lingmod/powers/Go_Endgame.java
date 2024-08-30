package lingmod.powers;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.ThornsPower;

import lingmod.actions.MyApplyPower_Action;

/**
 * 收官：打出非攻击牌，就获得 1 荆棘
 */
public class Go_Endgame extends AbstractEasyPower {
    public static final String POWER_NAME = Go_Endgame.class.getSimpleName();
    public static final String ID = makeID(POWER_NAME);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
    protected boolean justApplied = true;

    public Go_Endgame(AbstractCreature owner) {
        super(ID, powerStrings.NAME, PowerType.BUFF, true, owner, 0);
        loadRegion("thousandCuts");
    }

    @Override
    public void atEndOfRound() {
        super.atEndOfRound();
        if (justApplied)
            justApplied = false;
        else
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        if (this.amount <= 0)
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (this.amount >= 1 && card.type != AbstractCard.CardType.ATTACK) {
            this.flash();
            addToBot(new MyApplyPower_Action(owner, owner, new ThornsPower(owner, 1)));
        }
    }
}
