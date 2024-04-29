package lingmod.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CardHelper {
    public static AbstractCard lastCard(boolean isOnUseCard) {
        int cardsPlayed = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();
        if (cardsPlayed <= 1) return null;

        AbstractCard lastCard = null;
        if(isOnUseCard)
             lastCard = AbstractDungeon.actionManager.cardsPlayedThisTurn.get(cardsPlayed - 2);
        else
            lastCard = AbstractDungeon.actionManager.cardsPlayedThisTurn.get(cardsPlayed - 1);
        return lastCard;
    }
}
