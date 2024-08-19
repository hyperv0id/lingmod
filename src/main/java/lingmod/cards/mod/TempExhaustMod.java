package lingmod.cards.mod;

import basemod.cardmods.ExhaustMod;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class TempExhaustMod extends ExhaustMod {
    public TempExhaustMod() {
        super();
    }

    @Override
    public boolean removeAtEndOfTurn(AbstractCard card) {
        return true;
    }
}
