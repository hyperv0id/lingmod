package lingmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import lingmod.util.CustomTags;

import static lingmod.ModCore.logger;
import static lingmod.ModCore.makeID;
import static lingmod.util.Wiz.swapCardCost;

public class SwapCostAction extends AbstractGameAction {
    public static final String[] TEXT;
    private static final UIStrings uiStrings;

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(makeID("SwapCost"));
        TEXT = uiStrings.TEXT;
    }

    public boolean checkWine = false;
    private final AbstractPlayer p;

    public SwapCostAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public SwapCostAction(boolean checkWine) {
        this();
        this.checkWine = checkWine;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (1 == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }

            if (2 == this.p.hand.group.size()) {
                AbstractCard c1 = this.p.hand.group.get(0);
                AbstractCard c2 = this.p.hand.group.get(1);
                swapCardCost(c1, c2, false);
                this.isDone = true;
                return;
            }

            if (this.p.hand.group.size() > 2) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 2, false, false, false, false);
                this.tickDuration();
                return;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            if (AbstractDungeon.handCardSelectScreen.selectedCards.size() < 2) {
                logger.error("??? Why Size is not 2");
                isDone = true;
                return;
            }
            AbstractCard c1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.get(0);
            if (checkWine && c1.hasTag(CustomTags.WINE)) {
                c1.cost = 0;
                c1.isCostModified = true;
                c1.setCostForTurn(0);
            }
            AbstractCard c2 = AbstractDungeon.handCardSelectScreen.selectedCards.group.get(1);
            if (checkWine && c2.hasTag(CustomTags.WINE)) {
                c2.cost = 0;
                c2.isCostModified = true;
                c2.setCostForTurn(0);
            }
            swapCardCost(c1, c2, false);
            this.p.hand.addToTop(c1);
            this.p.hand.addToTop(c2);
            (c1).superFlash();
            (c1).applyPowers();
            (c2).superFlash();
            (c2).applyPowers();
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