package lingmod.cards.mod;

import basemod.abstracts.AbstractCardModifier;
import basemod.cardmods.EtherealMod;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import lingmod.util.Wiz;

public class SummonMod extends AbstractCardModifier {
    public SummonMod() {
        super();
    }

    @Override
    public void onRetained(AbstractCard card) {
        super.onRetained(card);
        AbstractCard cp = card.makeStatEquivalentCopy();
        CardModifierManager.addModifier(cp, new ExhaustMod());
        CardModifierManager.addModifier(cp, new EtherealMod());
        Wiz.atb(new MakeTempCardInHandAction(cp, 1));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new SummonMod();
    }
}
