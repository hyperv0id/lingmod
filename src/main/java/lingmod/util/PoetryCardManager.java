package lingmod.util;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import lingmod.cards.AbstractPoetryCard;
import lingmod.patch.PlayerFieldsPatch;
import lingmod.ui.PoetryOrb;
import lingmod.ui.PoetryTopPanel;

import java.util.ArrayList;
import java.util.List;

//诗词赋曲
public class PoetryCardManager {

    /**
     * 开局时选择一个诗词赋曲来规定整场战斗的格调
     */
    public static void onBattleStart(AbstractRoom r) {

        // 选择一个诗词赋曲
        CardGroup cg = PlayerFieldsPatch.poetryCardGroup.get(Wiz.adp());

        ArrayList<AbstractCard> stanceChoices = cg.group;

        //        AbstractCard chosenCard = null;
        //        if (stanceChoices.size() == 1) {
        //            selectCard(stanceChoices);
        //        } else {
        Wiz.atb(new SelectCardsAction(stanceChoices, PoetryTopPanel.TEXT[2], PoetryCardManager::selectCard));
        //        }
    }

    static void selectCard(List<AbstractCard> cards) {
        AbstractPoetryCard card = (AbstractPoetryCard) cards.get(0).makeStatEquivalentCopy();
        AbstractDungeon.player.channelOrb(new PoetryOrb(card));
    }
}