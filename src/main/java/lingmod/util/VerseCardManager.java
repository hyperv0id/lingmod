package lingmod.util;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import lingmod.patch.PlayerFieldsPatch;
import lingmod.ui.VerseTopPanel;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class VerseCardManager {

    /**
     * 开局时选择一个词牌，来规定整场战斗的格调
     */
    public static void onBattleStart(AbstractRoom r) {

        // 选择一个词牌
        CardGroup cg = PlayerFieldsPatch.verseCardGroup.get(Wiz.adp());

        ArrayList<AbstractCard> stanceChoices =
                cg.group.stream().map(AbstractCard::makeStatEquivalentCopy).collect(Collectors.toCollection(ArrayList::new));

        AbstractCard chosenCard = null;
        if (stanceChoices.size() == 1) {
            Wiz.att(new MakeTempCardInHandAction(stanceChoices.get(0))); // 加入手牌
        } else {
            Wiz.atb(new SelectCardsAction(stanceChoices, VerseTopPanel.TEXT[2], c -> {
                Wiz.att(new MakeTempCardInHandAction(c.get(0))); // 加入手牌
            }));
        }
    }
}
