package lingmod.cards.mod;

import basemod.cardmods.EtherealMod;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class EtheralThisTurnMod extends EtherealMod {

    public EtheralThisTurnMod() {
        super();
    }

    @Override
    public boolean removeAtEndOfTurn(AbstractCard card) {
        return true;
    }
}
