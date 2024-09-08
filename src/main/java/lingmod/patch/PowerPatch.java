package lingmod.patch;

import basemod.ReflectionHacks;
import basemod.interfaces.OnPlayerDamagedSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.InvinciblePower;
import lingmod.powers.NingZuoWuPower;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static lingmod.ModCore.logger;

public class PowerPatch {
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

        public static HashMap<String, Class<?>[]> methods = new HashMap<>();

        static {
            methods.put("onAttacked", new Class[]{DamageInfo.class, int.class});
            methods.put("onAttackToChangeDamage", new Class[]{DamageInfo.class, int.class});
            methods.put("onLoseHp", new Class[]{int.class});
            methods.put("wasHPLost", new Class[]{DamageInfo.class, int.class});
            methods.put("atDamageFinalReceive", new Class[]{float.class, DamageInfo.DamageType.class});
            methods.put("atDamageReceive", new Class[]{float.class, DamageInfo.DamageType.class});
        }

        public static boolean hasOverrideDmgRecv(AbstractPower child) {

            for (Map.Entry<String, Class<?>[]> entry : methods.entrySet()) {
                String methodName = entry.getKey();
                Class<?>[] paramTypes = entry.getValue();
                try {
                    Method method = child.getClass().getMethod(methodName, paramTypes);
                    if (method.getDeclaringClass() != AbstractPower.class) {
                        return true;
                    }
                } catch (NoSuchMethodException e) {
                    logger.info("No Such Method: " + methodName);
                }
            }
            return child instanceof OnPlayerDamagedSubscriber;
        }
    }
}
