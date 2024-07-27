package lingmod.util;

import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class VerseCardManager {

    /**
     * 开局时选择一个词牌，来规定整场战斗的格调
     *
     * @param r
     */
    public static void onBattleStart(AbstractRoom r) {
        // TODO: 优化词牌逻辑，使用单独 TopPanel UI和CardGroup

        //        // 选择一个词牌
        //        ArrayList<AbstractCard> deckCards = AbstractDungeon.player.masterDeck.group;
        //
        //        List<AbstractCard> stanceChoices_ = deckCards.stream().filter(c -> c.hasTag(CustomTags.ARIA)).map(c -> c.makeCopy()).collect(Collectors.toList());
        //        ArrayList<AbstractCard> stanceChoices = new ArrayList<>(stanceChoices_);
        //        // 选择
        //        AbstractDungeon.actionManager.addToBottom(new ChooseOneAction(stanceChoices));
        //        for(AbstractCard c: deckCards.stream().filter(c -> c.hasTag(CustomTags.ARIA)).collect(Collectors.toList())) {
        //            logger.info("------EXHAUST------" + c.cardID);
        //            AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
        //        }
    }
}
