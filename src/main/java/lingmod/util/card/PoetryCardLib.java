package lingmod.util.card;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.random.Random;
import lingmod.ModCore;
import lingmod.cards.AbstractPoetryCard;

import java.util.ArrayList;

public class PoetryCardLib {
    public static ArrayList<AbstractCard> cardPool = new ArrayList<>();

    public static AbstractCard getCard(Random rng) {
        if (cardPool.isEmpty()) initCardPool();
        int r = rng.random(cardPool.size() - 1);
        AbstractCard card = cardPool.get(r).makeCopy();
        cardPool.remove(r);
        return card;
    }

    public static void removeCard(String id) {
        for (int i = 0; i < cardPool.size(); i++) {
            if (cardPool.get(i).cardID.equals(id)) {
                cardPool.remove(i);
                break;
            }
        }

    }

    public static void initCardPool() {
        new AutoAdd(ModCore.modID).packageFilter(AbstractPoetryCard.class).any(AbstractPoetryCard.class, (info, card) -> cardPool.add(card));
    }
}
