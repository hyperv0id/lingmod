package lingmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static lingmod.ModCore.makeID;

public class Sui7DealAction extends AbstractGameAction {
    public static final String ID = makeID(Sui7DealAction.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = uiStrings.TEXT;

    public Sui7DealAction(AbstractCreature source) {
        this.setValues(AbstractDungeon.player, source, -1);
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    

    public void update() {
        if (this.duration == 0.5F) {
            if (AbstractDungeon.player.hand.group.size() >= 1)
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 99, true, true);
            else
                this.isDone = true;
            this.addToBot(new WaitAction(0.25F));
            this.tickDuration();
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                int size = 0;
                for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                    // 消耗
                    this.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                    // 随机卡
                    AbstractCard nc = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
                    if (c.upgraded) nc.upgrade(); // 好心地升级
                    this.addToTop(new MakeTempCardInHandAction(nc, false));
                    size++;
                }
                // 交易后获得 力量
                addToTop(new ApplyPowerAction(source, source, new StrengthPower(source, size)));
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }
            this.tickDuration();
        }
    }
}