package lingmod.cards.verse;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractVerseCard;
import lingmod.interfaces.CardConfig;

import static lingmod.ModCore.makeID;

/**
 * 普通五言：床前明月光，疑是地上霜，举头望明月，低头思故乡
 */
@CardConfig(damage = 5, block = 5)
public class JingYeSiCard extends AbstractVerseCard {
    public static final String ID = makeID(JingYeSiCard.class.getSimpleName());

    public JingYeSiCard() {
        super(ID, CardType.ATTACK, CardRarity.BASIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        dmg(m, null);
        blck();
    }

    @Override
    public void upp() {
    }
}