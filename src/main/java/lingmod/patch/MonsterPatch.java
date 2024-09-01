package lingmod.patch;

import static lingmod.ModCore.logger;

import java.util.ArrayList;
import java.util.Objects;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import basemod.ReflectionHacks;
import lingmod.monsters.AbsSummonMonster;
import lingmod.util.MonsterHelper;
import lingmod.util.Wiz;

public class MonsterPatch {
    /**
     * from <a href=
     * "https://steamcommunity.com/sharedfiles/filedetails/?id=2672531653">KaltsitMod</a>
     */
    public static class MonsterTakeDamagePatch {
        private static final ArrayList<String> acceptDebuffs = new ArrayList<>();
        protected static AbstractCreature summonTarget;

        static {
            acceptDebuffs.add(WeakPower.POWER_ID);
            acceptDebuffs.add(VulnerablePower.POWER_ID);
            acceptDebuffs.add(PoisonPower.POWER_ID);
        }

        /**
         * 获得召唤物，在角色被攻击时，角色替代受到伤害
         *
         * @return 你的召唤物
         */
        public static boolean gotSummon() {
            AbstractCreature c = AbstractDungeon.getMonsters().monsters.stream()
                    .filter(mo -> mo instanceof AbsSummonMonster)
                    .filter(mo -> !mo.isDeadOrEscaped()).findFirst()
                    .orElse(null);
            if (c != null && !c.isDeadOrEscaped()) {
                summonTarget = c;
                logger.info(summonTarget.name + "将替代承受伤害");
                return true;
            } else {
                summonTarget = null;
                return false;
            }
        }

        /**
         * 获得格挡效果转移到怪物上
         */
        @SpirePatch(clz = GainBlockAction.class, method = "update")
        public static class GainBlockPatch {
            public static void Prefix(GainBlockAction _inst) {
                if (!Wiz.isPlayerLing())
                    return; // 其他角色无效
                if (_inst.target == AbstractDungeon.player && Objects.equals(
                        (Float) ReflectionHacks.getPrivate(_inst, AbstractGameAction.class, "duration"),
                        (Float) ReflectionHacks.getPrivate(_inst, AbstractGameAction.class,
                                "startDuration"))
                        && MonsterTakeDamagePatch.gotSummon()) {
                    _inst.target = MonsterTakeDamagePatch.summonTarget;
                    logger.info("Block Target Changed To: " + _inst.target.name);
                }
            }
        }

        /**
         * 施加能力效果转移到怪物上
         */
        // @SpirePatch(
        // clz = ApplyPowerAction.class,
        // method = "<ctor>",
        // paramtypez = {AbstractCreature.class, AbstractCreature.class,
        // AbstractPower.class, int.class, boolean.class,
        // AbstractGameAction.AttackEffect.class}
        // )
        // public static class ChangeApplyBuffTarget {
        // public static void Postfix(ApplyPowerAction _inst, AbstractCreature target,
        // AbstractCreature source, AbstractPower power, int n, boolean b,
        // AbstractGameAction.AttackEffect e) {
        // if (!Wiz.isPlayerLing()) return; // 其他角色无效
        // if (_inst != null && (source == null || !source.isPlayer) && target != null
        // && target != source && (target == AbstractDungeon.player || power.owner ==
        // AbstractDungeon.player) &&
        // MonsterTakeDamagePatch.acceptDebuffs.contains(power.ID) &&
        // MonsterTakeDamagePatch.gotSummon()) {
        // _inst.target = MonsterTakeDamagePatch.summonTarget;
        // power.owner = _inst.target;
        // logger.info("本给予 " + target.name + " 的能力给予能力改变了目标。目前：target:" +
        // _inst.target.name + ",owner:" + power.owner);
        // }
        // }
        // }

        /**
         * 闪退服了
         */
        @SpirePatch(clz = Burn.class, method = "use")
        public static class BurnPatch {
            public static void Prefix(Burn _inst, AbstractPlayer p, AbstractMonster m) {
                if (!Wiz.isPlayerLing())
                    return; // 其他角色无效
                if (_inst.dontTriggerOnUseCard) {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player,
                            new DamageInfo((AbstractCreature) null, _inst.magicNumber, DamageInfo.DamageType.THORNS),
                            AbstractGameAction.AttackEffect.FIRE));
                }
            }
        }

        @SpirePatch(clz = MonsterGroup.class, method = "areMonstersDead")
        public static class EndBattleCheckPatch {
            @SpirePostfixPatch
            public static boolean Postfix(boolean __result, MonsterGroup __inst) {
                return MonsterHelper.areMonstersDead();
            }
        }

        @SpirePatch(clz = AbstractGameAction.class, method = "setValues", paramtypez = { AbstractCreature.class,
                DamageInfo.class })
        public static class ChangeDamageTarget {
            public static void Postfix(AbstractGameAction _inst, AbstractCreature target, DamageInfo info) {
                if (!Wiz.isPlayerLing())
                    return; // 其他角色无效
                if (info == null)
                    return;
                // 取消友伤
                if (target != null && info.owner != null && info.owner.isPlayer && target.isPlayer
                        && info.owner != target) {
                    // _inst.target = MonsterHelper.getMoNotSummon(true, null);
                    // _inst.isDone = true; // 取消这个
                    // logger.info("友伤取消: " + info.owner + " " + target);
                }
                if (target != null && info.type != DamageInfo.DamageType.HP_LOSS
                        && (info.owner == null || !info.owner.isPlayer) && target == AbstractDungeon.player
                        && MonsterTakeDamagePatch.gotSummon()) {
                    _inst.target = MonsterTakeDamagePatch.summonTarget;
                    logger.info("承伤改变" + MonsterTakeDamagePatch.summonTarget);
                } else {
                    logger.info("角色承受伤害");
                }

            }
        }
    }

}
