package lingmod.patch;

import basemod.ReflectionHacks;
import basemod.interfaces.OnPlayerDamagedSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.InvinciblePower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import lingmod.powers.NingZuoWuPower;
import lingmod.powers.WinePower;

import static lingmod.ModCore.logger;

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
            if (p != null) {
                wine = (WinePower) p.getPower(WinePower.POWER_ID);
                ok = wine != null && wine.amount > 0;
            }
            if (ok)
                wine.dampLater();
            else
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, __inst));
        }
    }

    @SpirePatch(clz = RemoveSpecificPowerAction.class, method = "update")
    public static class OnRemovePowerPatch {
        @SpirePrefixPatch
        public static void Prefix(RemoveSpecificPowerAction __inst) {
            // 获得要被删除的实例
            AbstractPower p2r = ReflectionHacks.getPrivate(__inst, RemoveSpecificPowerAction.class, "powerInstance");
            if (p2r == null) {
                String pid = ReflectionHacks.getPrivate(__inst, RemoveSpecificPowerAction.class, "powerToRemove");
                if (pid == null)
                    return;
                AbstractCreature tar = __inst.target;
                p2r = tar.getPower(pid);
            }
            if (p2r == null)
                return; // 仍然是null
            AbstractCreature owner = p2r.owner;
            if (owner == null || owner.getPower(NingZuoWuPower.ID) == null)
                return; // 没有宁作吾

            if (p2r instanceof InvinciblePower || p2r.type == null)
                return; // 特判隐藏，可被移除
            if (p2r.ID.equals(NingZuoWuPower.ID))
                return; // 特判宁作吾，可被移除
            if (hasOverrideDmgRecv(p2r))
                return; // 影响了伤害结算
            // 跳过逻辑
            logger.info("NingZuoWu Reject Power Remove: " + p2r);
            ReflectionHacks.setPrivate(__inst, AbstractGameAction.class, "duration", 0.0F);
            __inst.isDone = true;
        }

        public static boolean hasOverrideDmgRecv(AbstractPower child) {
            String[] methods = { "onAttacked", "onAttackToChangeDamage", "onLoseHp", "wasHPLost","atDamageFinalReceive","atDamageReceive"};
            for (String method : methods) {
                try {
                    Class<?> clz = child.getClass();
                    if (!clz.getMethod(method).getDeclaringClass().equals(clz)) {
                        return false;
                    }
                } catch (Exception ignored) {
                    logger.info("No Such Method: " + method);
                }
            }
            if (child instanceof OnPlayerDamagedSubscriber)
                return false;
            return true;
        }
    }
}
