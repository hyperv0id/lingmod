package lingmod.cards.mod;

import basemod.ReflectionHacks;
import basemod.abstracts.AbstractCardModifier;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import lingmod.actions.FastExhaustAction;

import static lingmod.ModCore.logger;
import static lingmod.ModCore.makeID;

/**
 * 重进酒：打出卡牌时，此牌消耗，变为那张牌的复制
 */
public class MirrorMod extends AbsLingCardModifier {

    public static final String ID = makeID(MirrorMod.class.getSimpleName());
    public static final UIStrings uis = CardCrawlGame.languagePack.getUIString(ID);

    public boolean isTempCard;
    public int wineAmt = 1;

    public MirrorMod(boolean isTempCard) {
        this.isTempCard = isTempCard;
    }

    @Override
    public void onOtherCardPlayed(AbstractCard card, AbstractCard otherCard, CardGroup group) {
        super.onOtherCardPlayed(card, otherCard, group);
        // 0. 需要在手牌才能打出
        if (!(group == AbstractDungeon.player.hand))
            return;
        // 1. 消耗自己
        addToBot(new FastExhaustAction(card, group));
        // 2. 创建复制
        AbstractCard cp = otherCard.makeStatEquivalentCopy();
        if (isTempCard) {
            try {
                cp.name += "*";
                ReflectionHacks.RMethod method = ReflectionHacks.privateMethod(AbstractCard.class,
                        "initializeTitle");
                method.invoke(cp);
            } catch (Exception e) {
                logger.info("Failed to Rename MirrorCard: " + card.name);
            }
        }
        addToBot(new MakeTempCardInHandAction(cp, 1));
        // 3. 添加Mod
        CardModifierManager.addModifier(cp, this.makeCopy());
        // 如果自己消耗，那么复制体也应该消耗
        if (!cp.exhaust && this.isTempCard) {
            CardModifierManager.addModifier(cp, new ExhaustMod());
        }
    }

    @Override
    public Color getGlow(AbstractCard card) {
        return Color.BLUE;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new MirrorMod(isTempCard);
    }
}
