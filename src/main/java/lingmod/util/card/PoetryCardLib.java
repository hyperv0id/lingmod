package lingmod.util.card;

import java.util.ArrayList;

import com.megacrit.cardcrawl.random.Random;

import basemod.AutoAdd;
import lingmod.ModCore;
import lingmod.cards.AbstractPoetryCard;

public class PoetryCardLib {
    public static ArrayList<AbstractPoetryCard> cardPool = new ArrayList<>();

    public static AbstractPoetryCard getCard(Random rng) {
        if (cardPool.isEmpty()) initCardPool();
        int r = rng.random(cardPool.size() - 1);
        AbstractPoetryCard card = (AbstractPoetryCard) cardPool.get(r).makeCopy();
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
