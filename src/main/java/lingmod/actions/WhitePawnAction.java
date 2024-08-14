package lingmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class WhitePawnAction extends AbstractGameAction {
    AbstractPlayer p;
    AbstractCard whitePawn;
    String msg;

    public WhitePawnAction(AbstractCard src, String msg) {
        p = AbstractDungeon.player;
        whitePawn = src;
        this.msg = msg;
        duration = startDuration = DEFAULT_DURATION;
    }

    @Override
    public void update() {
        if (this.duration == startDuration) {
            if (p.drawPile.isEmpty()) {
                this.isDone = true;
                return;
            }
            CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : p.drawPile.group) {
                temp.addToTop(c);
            }
            temp.sortAlphabetically(true);
            temp.sortByRarityPlusStatusCardType(false);
            AbstractDungeon.gridSelectScreen.open(temp, 1, msg, false);
            this.tickDuration();
        } else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                addToTop(new ExhaustSpecificCardAction(c, p.drawPile));
                addToTop(new MakeTempCardInDrawPileAction(whitePawn.makeCopy(), 1, true, true));
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        this.tickDuration();
    }
}
