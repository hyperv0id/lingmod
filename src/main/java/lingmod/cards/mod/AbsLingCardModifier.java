package lingmod.cards.mod;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.abstracts.AbstractCardModifier;

public abstract class AbsLingCardModifier extends AbstractCardModifier {
    @Override
    public String identifier(AbstractCard card) {
        String name = this.getClass().getSimpleName();
        return makeID(name);
    }
}
