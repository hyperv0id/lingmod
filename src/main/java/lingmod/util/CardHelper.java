package lingmod.util;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lingmod.ModCore;
import lingmod.cards.mod.NellaFantasiaMod;
import lingmod.cards.mod.WineMod;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;

public class CardHelper {
    public static AbstractCard lastCard(boolean isOnUseCard) {
        int cardsPlayed = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();
        int onUseModi = isOnUseCard ? 1 : 0;
        if (cardsPlayed <= onUseModi)
            return null;

        AbstractCard lastCard = null;
        lastCard = AbstractDungeon.actionManager.cardsPlayedThisTurn.get(cardsPlayed - 1 - onUseModi);
        return lastCard;
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
    public static AbstractCard returnTrulyRandomWineInCombat() {
        List<AbstractCard> list = getAllCards();
        list =
                list.stream().filter(c -> !c.hasTag(AbstractCard.CardTags.HEALING)).filter(c -> c.hasTag(CustomTags.WINE) || CardModifierManager.hasModifier(c, WineMod.ID))
                        .collect(Collectors.toList());
        if (list.isEmpty()) {
            ModCore.logger.warn("Cannot find card with tag lingmod:wine");
            return null;
        }
        return (AbstractCard) list.get(cardRandomRng.random(list.size() - 1));
    }

    /**
     * 获得随机一张梦
     *
     * @return 随机的梦，稀有度平均过
     */
    public static AbstractCard returnTrulyRandomDreamInCombat() {
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
        ModCore.logger.info("Found Dream: " + list.stream().map(c -> c.name).toString());
        return (AbstractCard) list.get(cardRandomRng.random(list.size() - 1));
    }
}