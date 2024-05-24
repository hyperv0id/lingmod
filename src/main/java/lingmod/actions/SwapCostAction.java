package lingmod.actions;

import static lingmod.ModCore.logger;
import static lingmod.ModCore.makeID;

import java.util.Iterator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import lingmod.util.CustomTags;

public class SwapCostAction extends AbstractGameAction {

    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private boolean checkWine;
    public static int numDiscarded;
    private static final float DURATION;
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(makeID("SwapCost"));
        TEXT = uiStrings.TEXT;
        DURATION = Settings.ACTION_DUR_XFAST;
    }

    public SwapCostAction(AbstractCreature target, AbstractCreature source, boolean wine) {
        this.p = (AbstractPlayer) target;
        this.duration = DURATION;
        this.setValues(target, source);
        this.actionType = ActionType.SPECIAL;
        this.amount = 2;
        this.checkWine = wine;
    }

    @Override
    public void update() {
        if (p.hand.size() < 2 || // 手牌中没有两张牌，无效
                AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
            return;
        }
        if (this.duration == 0.5F) {
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], amount, false, true, false, false, true);
            this.addToBot(new WaitAction(0.25F));
            this.tickDuration();
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                AbstractCard c1 = null, c2 = null;
                Iterator<AbstractCard> var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();
                if (var1.hasNext()) {
                    c1 = (AbstractCard) var1.next();
                    if (checkWine && c1.hasTag(CustomTags.WINE)) {
                        c1.cost = 0;
                        c1.isCostModified = true;
                    }
                }
                if (var1.hasNext()) {
                    c2 = (AbstractCard) var1.next();
                    if (checkWine && c1.hasTag(CustomTags.WINE)) {
                        c1.cost = 0;
                        c1.isCostModified = true;
                    }
                }
                if (c1 != null && c2 != null) {
                    // 交换能量
                    int t = c1.cost;

                    c1.cost = c2.cost;
                    c1.costForTurn = c1.cost;
                    c1.isCostModified = true;

                    c2.cost = t;
                    c2.costForTurn = t;
                    c2.isCostModified = true;

                    AbstractDungeon.player.hand.addToTop(c1);
                    AbstractDungeon.player.hand.addToTop(c2);
                } else {
                    logger.warn(this + "SwapCost May Have Bugs");
                }

                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }

            this.tickDuration();
        }
    }

}
