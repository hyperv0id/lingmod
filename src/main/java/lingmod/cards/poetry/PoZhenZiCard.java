package lingmod.cards.poetry;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.cards.AbstractPoetryCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.util.Wiz;

@CardConfig(magic = 2)
@Credit(link = "https://zuodaoxing.lofter.com/post/30b9c9c3_2b48ec499", platform = Credit.LOFTER, username = "左刀行")
public class PoZhenZiCard extends AbstractPoetryCard {
    public static final String ID = makeID(PoZhenZiCard.class.getSimpleName());

    public PoZhenZiCard() {
        super(ID, CardType.ATTACK, CardRarity.RARE, CardTarget.SELF);
    }

    @Override
    public void use_p(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DiscardAction(Wiz.adp(), Wiz.adp(), Wiz.adp().hand.size(), true));
        addToBot(new DrawCardAction(magicNumber));
    }
}
