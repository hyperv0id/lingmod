package lingmod.cards.poetry;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractPoetryCard;
import lingmod.interfaces.CardConfig;

import static lingmod.ModCore.makeID;

@CardConfig(damage = 2, magic = 2)
public class PoZhenZiCard extends AbstractPoetryCard {
    public static final String ID = makeID(PoZhenZiCard.class.getSimpleName());

    public PoZhenZiCard() {
        super(ID, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
    }

    @Override
    public void upp() {
        upgradeDamage(1);
    }

    @Override
    public void use_p(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        abstractPlayer.hand.group.forEach(c -> dmg(abstractMonster, null));
        addToBot(new DrawCardAction(magicNumber));
    }
}
