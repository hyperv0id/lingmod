package lingmod.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.util.MonsterHelper;
import lingmod.util.Wiz;

public class PlayerPatch {
    public static class PlayerDamagePatch {

        @SpirePatch(
                clz = AbstractGameAction.class,
                method = "setValues",
                paramtypez = {AbstractCreature.class, DamageInfo.class}
        )
        public static class ChangeDamageTarget {
            public static void Postfix(AbstractGameAction _inst, AbstractCreature target, DamageInfo info) {
                if (!Wiz.isPlayerLing()) return; // 其他角色无效
                // 取消友伤
                if (target == null || info == null || info.owner == null) return;
                if (info.owner.isPlayer && target.isPlayer) {
                    AbstractMonster targetMonster = MonsterHelper.getMoNotSummon(true, null);
                    if (targetMonster != null)
                        _inst.target = targetMonster;
                }
            }
        }
    }
}