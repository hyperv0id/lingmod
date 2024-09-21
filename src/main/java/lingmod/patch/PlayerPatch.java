package lingmod.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.monsters.AbsSummonMonster;
import lingmod.util.MonsterHelper;
import lingmod.util.Wiz;

public class PlayerPatch {

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "<class>"
    )
    public static class Fields {
        public static SpireField<AbsSummonMonster> summonedMonster = new SpireField<>(() -> null);
    }

    public static AbsSummonMonster getSummonMonster() {
        return PlayerPatch.Fields.summonedMonster.get(Wiz.adp());
    }


    public static class PlayerDamagePatch {

        @SpirePatch(
                clz = AbstractGameAction.class,
                method = "setValues",
                paramtypez = {AbstractCreature.class, DamageInfo.class}
        )
        public static class ChangeDamageTarget {
            public static void Postfix(AbstractGameAction _inst, AbstractCreature target, DamageInfo info) {
                if (!Wiz.isPlayerLing()) return; // 其他角色无效
                if (target == null || info == null || info.owner == null) return;
                // 取消友伤
                if (info.owner.isPlayer && target.isPlayer && info.owner != target) {
                    AbstractMonster targetMonster = MonsterHelper.getMoNotSummon(true, null);
                    if (targetMonster != null)
                        _inst.target = targetMonster;
                }
            }
        }
    }
}
