package lingmod.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lingmod.ModCore;

import java.util.ArrayList;
import java.util.Iterator;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;

public class CardHelper {
    public static AbstractCard lastCard(boolean isOnUseCard) {
        int cardsPlayed = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();
        int onUseModi = isOnUseCard ? 1 : 0;
        if (cardsPlayed <= onUseModi) return null;

        AbstractCard lastCard = null;
        lastCard = AbstractDungeon.actionManager.cardsPlayedThisTurn.get(cardsPlayed - 1 - onUseModi);
        return lastCard;
    }

    /**
     * 获得随机一张酒
     * @return 随机的酒，稀有度平均过
     */
    public static AbstractCard returnTrulyRandomWineInCombat() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        Iterator<AbstractCard> var2 = srcCommonCardPool.group.iterator();

        AbstractCard c;
        while (var2.hasNext()) {
            c = (AbstractCard) var2.next();
            if (c.hasTag(CustomTags.WINE) && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
            }
        }

        var2 = srcUncommonCardPool.group.iterator();

        while (var2.hasNext()) {
            c = (AbstractCard) var2.next();
            if (c.hasTag(CustomTags.WINE) && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
            }
        }

        var2 = srcRareCardPool.group.iterator();

        while (var2.hasNext()) {
            c = (AbstractCard) var2.next();
            if (c.hasTag(CustomTags.WINE) && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
            }
        }
        if(list.isEmpty())
        {
            ModCore.logger.warn("Cannot find card with tag lingmod:wine");
            return null;
        }
        return (AbstractCard) list.get(cardRandomRng.random(list.size() - 1));
    }

    /**
     * 获得随机一张梦
     * @return 随机的梦，稀有度平均过
     */
    public static AbstractCard returnTrulyRandomDreamInCombat() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        Iterator<AbstractCard> var2 = srcCommonCardPool.group.iterator();

        AbstractCard c;
        while (var2.hasNext()) {
            c = (AbstractCard) var2.next();
            if (c.hasTag(CustomTags.DREAM) && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
            }
        }

        var2 = srcUncommonCardPool.group.iterator();

        while (var2.hasNext()) {
            c = (AbstractCard) var2.next();
            if (c.hasTag(CustomTags.DREAM) && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
            }
        }

        var2 = srcRareCardPool.group.iterator();

        while (var2.hasNext()) {
            c = (AbstractCard) var2.next();
            if (c.hasTag(CustomTags.DREAM) && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
            }
        }
        if(list.isEmpty())
        {
            ModCore.logger.warn("Cannot find card with tag lingmod:wine");
            return null;
        }
        return (AbstractCard) list.get(cardRandomRng.random(list.size() - 1));
    }

}
