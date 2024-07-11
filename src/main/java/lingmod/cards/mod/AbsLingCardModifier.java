package lingmod.cards.mod;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static lingmod.ModCore.makeID;

public abstract class AbsLingCardModifier extends AbstractCardModifier {
    @Override
    public String identifier(AbstractCard card) {
        String name = this.getClass().getSimpleName();
        return makeID(name);
    }
}
