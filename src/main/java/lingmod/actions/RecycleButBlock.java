package lingmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.Iterator;

public class RecycleButBlock extends AbstractGameAction {
    public static final String[] TEXT;
    private static final UIStrings uiStrings;

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("RecycleAction");
        TEXT = uiStrings.TEXT;
    }

    private final AbstractPlayer p;
    private int magnification = 1;

    public RecycleButBlock(int magnification) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.magnification = magnification;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
            } else if (this.p.hand.size() == 1) {
                if (this.p.hand.getBottomCard().costForTurn == -1) {
                    this.addToTop(new GainEnergyAction(EnergyPanel.getCurrentEnergy()));
                } else if (this.p.hand.getBottomCard().costForTurn > 0) {
                    this.addToTop(new GainEnergyAction(this.p.hand.getBottomCard().costForTurn));
                }

                this.p.hand.moveToExhaustPile(this.p.hand.getBottomCard());
            } else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false);
            }
            this.tickDuration();
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                AbstractCard c;

                // 遍历选定的手牌中的卡片
                for (Iterator<AbstractCard> var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group
                        .iterator(); var1.hasNext(); ) {
                    c = var1.next(); // 获取下一张卡片
                    if (c.costForTurn > 0) {
                        // 如果卡片有正的费用，获得相应的格挡
                        addToTop(new GainBlockAction(AbstractDungeon.player,
                                magnification * EnergyPanel.getCurrentEnergy()));
                    }

                    // 将卡片移动到弃牌堆
                    this.p.hand.moveToExhaustPile(c);
                }
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            }

            this.tickDuration();
        }
    }
}
