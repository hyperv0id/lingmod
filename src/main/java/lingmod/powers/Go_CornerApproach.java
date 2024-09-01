package lingmod.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import lingmod.actions.MyApplyPower_Action;

import static lingmod.ModCore.makeID;

/**
 * Skill --> 虚弱
 */
public class Go_CornerApproach extends AbstractEasyPower {
    public static final String POWER_NAME = Go_CornerApproach.class.getSimpleName();
    public static final String ID = makeID(POWER_NAME);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);

    public Go_CornerApproach(AbstractCreature owner, int amount) {
        super(ID, powerStrings.NAME, PowerType.BUFF, true, owner, amount);
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        if (this.amount <= 0) addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        super.onAfterUseCard(card, action);
        if (card.type != AbstractCard.CardType.SKILL) return;
        AbstractCreature src = action.source == null ? AbstractDungeon.player : action.source;
        addToBot(new MyApplyPower_Action(src, owner, new WeakPower(src, 1,
                owner.getClass().isAssignableFrom(AbstractMonster.class))));
        addToBot(new ReducePowerAction(owner, owner, this, 1));
        super.onAfterUseCard(card, action);
    }
}
