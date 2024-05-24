package lingmod.cards.mod;

import basemod.cardmods.ExhaustMod;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;

/**
 * 重进酒：打出卡牌时，此牌消耗，变为那张牌的复制
 */
public class MirrorMod extends AbstractCardModifier {

    public MirrorMod() {
    }

    @Override
    public void onOtherCardPlayed(AbstractCard card, AbstractCard otherCard, CardGroup group) {
        super.onOtherCardPlayed(card, otherCard, group);
        // 0. 需要在手牌才能打出
        if(!(group == AbstractDungeon.player.hand))
            return;
        // 1. 消耗自己
        addToTop(new ExhaustSpecificCardAction(card, group));
        // 2. 创建复制
        AbstractCard cp = otherCard.makeStatEquivalentCopy();
        addToTop(new MakeTempCardInHandAction(cp, 1));
        // 3. 添加Mod
        CardModifierManager.addModifier(cp, new MirrorMod());
        // 如果自己消耗，那么复制体也应该消耗
        if(card.exhaust) {
            CardModifierManager.addModifier(cp, new ExhaustMod());
        }
    }

    @Override
    public Color getGlow(AbstractCard card) {
        return Color.BLUE;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new MirrorMod();
    }
}
