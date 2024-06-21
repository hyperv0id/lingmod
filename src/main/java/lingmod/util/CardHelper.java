package lingmod.util;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.srcCommonCardPool;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.srcRareCardPool;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.srcUncommonCardPool;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Bite;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.helpers.CardModifierManager;
import lingmod.ModCore;
import lingmod.cards.mod.NellaFantasiaMod;
import lingmod.cards.mod.WineMod;

public class CardHelper {
    public static AbstractCard lastCard(boolean isOnUseCard) {
        int cardsPlayed = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();
        int onUseModi = isOnUseCard ? 1 : 0;
        if (cardsPlayed <= onUseModi)
            return null;
        return AbstractDungeon.actionManager.cardsPlayedThisTurn.get(cardsPlayed - 1 - onUseModi);
    }

    public static ArrayList<AbstractCard> getAllCards() {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        cards.addAll(srcCommonCardPool.group);
        cards.addAll(srcUncommonCardPool.group);
        cards.addAll(srcRareCardPool.group);
        return cards;
    }

    /**
     * 获得随机一张酒
     *
     * @return 随机的酒，稀有度平均过
     */
    public static AbstractCard getTrulyRandWineInCombat() {
        List<AbstractCard> list = getAllCards();
        list =
                list.stream().filter(c -> !c.hasTag(AbstractCard.CardTags.HEALING)).filter(c -> c.hasTag(CustomTags.WINE) || CardModifierManager.hasModifier(c, WineMod.ID))
                        .collect(Collectors.toList());
        if (list.isEmpty()) {
            ModCore.logger.warn("Cannot find card with tag lingmod:wine");
            return null;
        }
        return list.get(cardRandomRng.random(list.size() - 1));
    }

    /**
     * 获得随机一张梦
     *
     * @return 随机的梦，稀有度平均过
     */
    public static AbstractCard getTrulyRandDreamInCombat() {
        List<AbstractCard> list = getAllCards();
        list =
                list.stream().filter(c -> !c.hasTag(AbstractCard.CardTags.HEALING)).filter(c ->
                                (c.hasTag(CustomTags.DREAM)
                                        || CardModifierManager.hasModifier(c, NellaFantasiaMod.ID)))
                        .collect(Collectors.toList());
        if (list.isEmpty()) {
            ModCore.logger.warn("Cannot find card with tag lingmod:dream");
            return null;
        }
        // ModCore.logger.info("Found Dream: " + list.stream().map(c -> c.name).toString());
        return list.get(cardRandomRng.random(list.size() - 1));
    }

    public static boolean isStarterStrike(AbstractCard card) {
        return card.hasTag(AbstractCard.CardTags.STARTER_STRIKE) || card.cardID.equals(Bite.ID);
    }

    public static boolean isStarterDefend(AbstractCard card) {
        return card.hasTag(AbstractCard.CardTags.STARTER_DEFEND);
    }

    public static boolean isStarterSD(AbstractCard card) {
        return isStarterDefend(card) || isStarterStrike(card);
    }
}