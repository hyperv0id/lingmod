package lingmod.cards.skill;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.actions.SwapCostAction;
import lingmod.cards.AbstractEasyCard;

import static lingmod.ModCore.makeID;

/**
 * 换盏：交换两张牌的费用，如果是酒，那么先将其费用变为0
 */
public class SwapCup extends AbstractEasyCard {
    public final static String ID = makeID(SwapCup.class.getSimpleName());

    public SwapCup() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = 0;
        CardModifierManager.addModifier(this, new ExhaustMod());
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return p.hand.group.size() >= 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SwapCostAction(upgraded));
    }

    @Override
    public void upp() {
    }
}