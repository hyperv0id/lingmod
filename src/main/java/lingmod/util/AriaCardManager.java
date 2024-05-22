package lingmod.util;

import static lingmod.ModCore.logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class AriaCardManager {

    /**
     * TODO: 开局时选择一个词牌，来规定整场战斗的格调
     * @param r
     */
    public static void onBattleStart(AbstractRoom r) {
        // 选择一个词牌
        ArrayList<AbstractCard> deckCards = AbstractDungeon.player.masterDeck.group;

        List<AbstractCard> stanceChoices_ = deckCards.stream().filter(c -> c.hasTag(CustomTags.ARIA)).map(c -> c.makeCopy()).collect(Collectors.toList());
        ArrayList<AbstractCard> stanceChoices = new ArrayList<>(stanceChoices_);
        // 选择
        AbstractDungeon.actionManager.addToBottom(new ChooseOneAction(stanceChoices));
        for(AbstractCard c: deckCards.stream().filter(c -> c.hasTag(CustomTags.ARIA)).collect(Collectors.toList())) {
            logger.info("------EXHAUST------" + c.cardID);
            AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
        }
    }
}
