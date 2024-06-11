package lingmod.cards.mod;

import com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.cardmods.EtherealMod;

public class EtheralThisTurnMod extends EtherealMod {

    public EtheralThisTurnMod() {
        super();
    }

    @Override
    public boolean removeAtEndOfTurn(AbstractCard card) {
        return true;
    }
}
