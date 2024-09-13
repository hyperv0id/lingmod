package lingmod.patch.card;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lingmod.powers.YuGuoZhuoYingPower;
import lingmod.util.Wiz;

import static lingmod.ModCore.logger;

public class CardGroupPatch {
    /**
     * 卡牌进入 #b抽牌堆 时，获得与其耗能等量的格挡
     */
    @SpirePatch(clz = CardGroup.class, method = "moveToDiscardPile")
    public static class MoveToDiscardPilePatch {
        public static boolean hasYuGuoZhuoYing() {
            AbstractPlayer p = Wiz.adp();
            return p != null && p.hasPower(YuGuoZhuoYingPower.POWER_ID);
        }

        @SpirePostfixPatch
        public static void Postfix(CardGroup __inst, AbstractCard c) {
            AbstractPower pp = Wiz.adp().getPower(YuGuoZhuoYingPower.POWER_ID);
            if (pp == null) return;
            YuGuoZhuoYingPower po = (YuGuoZhuoYingPower) pp;
            logger.info("雨过濯缨 丢弃" + c.name);
            po.accept(c); // 丢弃处理逻辑
        }
    }
}
