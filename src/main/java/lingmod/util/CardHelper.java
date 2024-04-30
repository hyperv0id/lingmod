package lingmod.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CardHelper {
    public static AbstractCard lastCard(boolean isOnUseCard) {
        int cardsPlayed = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();
        int onUseModi = isOnUseCard ? 1 : 0;
        if(cardsPlayed <= onUseModi) return null;

        AbstractCard lastCard = null;
        lastCard = AbstractDungeon.actionManager.cardsPlayedThisTurn.get(cardsPlayed - 1 - onUseModi);
        return lastCard;
    }
}
