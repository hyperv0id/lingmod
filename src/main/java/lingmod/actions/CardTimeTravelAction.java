package lingmod.actions;

import java.util.Iterator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.unique.GamblingChipAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import lingmod.cards.attack.XunRiFeng;

/**
 * 选择任意张牌消耗，但是会将选择的牌记录下来
 */
public class CardTimeTravelAction extends AbstractGameAction {

    public XunRiFeng sourceCard;
    private AbstractPlayer p;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private boolean notchip;

    GamblingChipAction reference;

    public CardTimeTravelAction(XunRiFeng src) {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, -1);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.notchip = false;
        sourceCard = src;
    }

    public void update() {
        if (this.duration == 0.5F) {
            if (this.notchip) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[1], 99, true, true);
            } else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 99, true, true);
            }

            this.addToBot(new WaitAction(0.25F));
            this.tickDuration();
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                if (!AbstractDungeon.handCardSelectScreen.selectedCards.group.isEmpty()) {
                    this.addToTop(new DrawCardAction(this.p,
                            AbstractDungeon.handCardSelectScreen.selectedCards.group.size()));
                    Iterator<AbstractCard> var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

                    while (var1.hasNext()) {
                        AbstractCard c = (AbstractCard) var1.next();
                        AbstractDungeon.player.hand.moveToExhaustPile(c);
                        sourceCard.addCard(c);
                    }
                }

                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }

            this.tickDuration();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("GamblingChipAction");
        TEXT = uiStrings.TEXT;
    }
}
