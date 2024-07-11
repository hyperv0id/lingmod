package lingmod.actions;

import basemod.BaseMod;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.ExhumeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import lingmod.cards.skill.GuLeiXinLiu;

import java.util.stream.Collectors;

/**
 * 战士发掘改版，并取消 卡牌的消耗特性
 */
public class GuLeiXinLiuAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhumeAction");
    private final AbstractPlayer p;
    public static final String[] TEXT = uiStrings.TEXT;
    private static ExhumeAction refer;
    private final CardGroup exhumeGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public GuLeiXinLiuAction() {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            // 特判满
            if (p.hand.size() == BaseMod.MAX_HAND_SIZE) {
                p.createHandIsFullDialog();
                this.isDone = true;
            }
            // 构建牌组
            this.exhumeGroup.group.addAll(p.exhaustPile.group.stream().filter(this::validCard).collect(Collectors.toList()));
            // 特判 空/1
            if (this.exhumeGroup.group.isEmpty()) {
                isDone = true;
            } else if (this.exhumeGroup.group.size() == 1) {
                exhumeToHand(exhumeGroup.group.get(0));
                this.isDone = true;
            } else {
                // 选择页面
                AbstractDungeon.gridSelectScreen.open(this.exhumeGroup, 1, TEXT[0], false);
                this.tickDuration();
            }
        } else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                exhumeToHand(c);// 没有在消耗堆中删除，但是有效
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            p.hand.refreshHandLayout();
            this.exhumeGroup.clear();
        }
        this.tickDuration();
    }

    boolean validCard(AbstractCard card) {
        return !card.cardID.equals("Exhume") && !card.cardID.equals(GuLeiXinLiu.ID);
    }

    /**
     * 将消耗的牌返回手牌，并去除消耗字样
     *
     * @param c 要返回的手牌
     */
    void exhumeToHand(AbstractCard c) {
        c.unfadeOut();
        CardModifierManager.removeModifiersById(c, ExhaustMod.ID, false);
        c.exhaust = false;
        this.p.hand.addToHand(c);
        this.p.exhaustPile.removeCard(c);
        c.unhover();
        c.fadingOut = false;
    }
}
