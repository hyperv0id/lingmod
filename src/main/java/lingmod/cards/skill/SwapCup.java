package lingmod.cards.skill;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import lingmod.actions.SwapCostAction;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.Credit;

/**
 * 换盏：交换两张牌的费用，如果是酒，那么先将其费用变为0
 */
@Credit(username = "枯荷倚梅cc", platform = "lofter", link = "https://anluochen955.lofter.com/post/1f2a08fc_2baf8eeb3")
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