package lingmod.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import lingmod.powers.WinePower;
import lingmod.util.Wiz;

public class PowerPatch {
    /**
     * 修改活力逻辑，使有酒时不消耗活力，改为消耗 1 酒
     */
    @SpirePatch(clz = VigorPower.class, method = "onUseCard")
    public static class VigorDampPatch {
        @SpirePostfixPatch
        public static void Replace(VigorPower __inst, AbstractCard card, UseCardAction action) {
            if (card.type != AbstractCard.CardType.ATTACK) {
                return;
            }

            AbstractPlayer p = AbstractDungeon.player;
            if (p != null && p.hasPower(WinePower.POWER_ID)) {
                WinePower wine = (WinePower) p.getPower(WinePower.POWER_ID);
                if (wine.amount > 0) {
                    Wiz.addToBotAbstract(wine::reduce);
                    Wiz.applyToSelf(new VigorPower(p, __inst.amount));
                }
            }
        }
    }
}
