package lingmod.cards.verse;

import static lingmod.ModCore.makeID;

import lingmod.cards.AbstractVerseCard;

/**
 * 普通五言：床前明月光，疑是地上霜，举头望明月，低头思故乡
 */
public class JingYeSiCard extends AbstractVerseCard {
    public static final String ID = makeID(JingYeSiCard.class.getSimpleName());

    public JingYeSiCard() {
        super(ID, -2, CardType.ATTACK, CardRarity.BASIC);
    }
    @Override
    public void upp() {
    }
}