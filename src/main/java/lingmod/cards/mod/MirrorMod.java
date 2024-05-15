package lingmod.cards.mod;

import static lingmod.ModCore.logger;

import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;

/**
 * 重进酒：打出卡牌时，此牌消耗，变为那张牌的复制
 */
public class MirrorMod extends AbstractCardModifier{

    public MirrorMod() {
    }

    @Override
    public void onOtherCardPlayed(AbstractCard card, AbstractCard otherCard, CardGroup group) {
        super.onOtherCardPlayed(card, otherCard, group);
        // 1. 消耗自己
        logger.info(card.cardID + " should exhaust---------------");
        addToTop(new ExhaustSpecificCardAction(card, group));
        // 2. 创建复制
        AbstractCard cp = otherCard.makeCopy();
        this.addToTop(new MakeTempCardInHandAction(cp, 1));
        logger.info("copied " + cp.cardID);
        // 3. 添加Mod
        CardModifierManager.addModifier(cp, new MirrorMod());
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new MirrorMod();
    }
    
}
