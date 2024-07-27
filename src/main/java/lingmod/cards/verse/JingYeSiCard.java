package lingmod.cards.verse;

import lingmod.cards.AbstractVerseCard;

import static lingmod.ModCore.makeID;

/**
 * 普通五言：床前明月光，疑是地上霜，举头望明月，低头思故乡
 * "给予 !M! 虚弱",
 * "给予 !M! 易伤",
 * "NL 造成 !D! 点伤害",
 * "获得 !B! 格挡"
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