package lingmod.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import lingmod.powers.WinePower;

public class PowerPatch {
    /**
     * 修改活力逻辑，使有酒时不消耗活力，改为消耗 1 酒
     */
    @SpirePatch(clz = VigorPower.class, method = "onUseCard")
    public static class VigorDampPatch {
        public static void Replace(VigorPower __inst, AbstractCard card, UseCardAction action) {
            if (card.type != AbstractCard.CardType.ATTACK) {
                return;
            }
            // 临时不检查
            if (card.dontTriggerOnUseCard)
                return;
            boolean ok = false;
            WinePower wine = null;
            AbstractPlayer p = AbstractDungeon.player;
            if (p != null && p.hasPower(WinePower.POWER_ID)) {
                wine = (WinePower) p.getPower(WinePower.POWER_ID);
                ok = wine.amount > 0;
            }
            if (ok) wine.dampLater();
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, __inst));
        }
    }
}
