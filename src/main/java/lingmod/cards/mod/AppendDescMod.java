package lingmod.cards.mod;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

import java.util.List;

public class AppendDescMod extends AbstractCardModifier {


    @Override
    public AbstractCardModifier makeCopy() {
        return new AppendDescMod();
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return super.modifyDescription(rawDescription, card);
    }

    @Override
    public void onOtherCardPlayed(AbstractCard card, AbstractCard otherCard, CardGroup group) {
        super.onOtherCardPlayed(card, otherCard, group);

    }
}
