package lingmod.actions;

import java.util.Iterator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class ExhaustToDiscardAction extends AbstractGameAction {
    public static final String[] TEXT;
    private static final UIStrings uiStrings;

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("DualWieldAction");
        TEXT = uiStrings.TEXT;
    }

    private final AbstractPlayer p;
    private final int dupeAmount;

    public ExhaustToDiscardAction(AbstractCreature source, int copyAmt) {
        this.setValues(AbstractDungeon.player, source, copyAmt);
        this.actionType = ActionType.DRAW;
        this.duration = 0.25F;
        this.p = AbstractDungeon.player;
        this.dupeAmount = copyAmt;
    }

    public void update() {
        Iterator<AbstractCard> var1;
        AbstractCard c;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false);
                this.tickDuration();
                return;
            }

            if (this.p.hand.group.size() == 1) {
                this.addToTop(new MakeTempCardInDiscardAction(this.p.hand.getTopCard().makeStatEquivalentCopy(),
                        dupeAmount));
                this.addToTop(new ExhaustSpecificCardAction(p.hand.getTopCard(), p.hand));
                this.returnCards();
                this.isDone = true;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

            while (var1.hasNext()) {
                c = var1.next();
                this.addToTop(new MakeTempCardInDiscardAction(c.makeStatEquivalentCopy(), dupeAmount));
                this.addToTop(new ExhaustSpecificCardAction(c, p.hand));
            }

            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }

        this.tickDuration();
    }

    private void returnCards() {
        this.p.hand.refreshHandLayout();
    }
}
