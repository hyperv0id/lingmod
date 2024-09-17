package lingmod.patch;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.relics.HandDrill;
import lingmod.monsters.AbsSummonMonster;
import lingmod.util.MonsterHelper;
import lingmod.util.Wiz;

import java.util.HashSet;
import java.util.Objects;

import static lingmod.ModCore.logger;

public class SummonMonsterPatch {
    /**
     * from <a href=
     * "https://steamcommunity.com/sharedfiles/filedetails/?id=2672531653">KaltsitMod</a>
     */
    public static class MonsterTakeDamagePatch {
        protected static AbsSummonMonster summonTarget;

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
                summonTarget = (AbsSummonMonster) c;
                logger.info(summonTarget.name + "将替代承受伤害");
                return true;
            } else {
                summonTarget = null;
                return false;
            }
        }

        @SpirePatch(clz = AbstractMonster.class, method = "damage")
        public static class DamagePatch {
            @SpirePrefixPatch
            public static SpireReturn<Void> Prefix(AbstractMonster __inst, DamageInfo info) {
                if (__inst instanceof AbsSummonMonster && (info == null || info.owner == null)) {
                    logger.info("取消指向召唤物的无来源伤害");
                    return SpireReturn.Return(null);
                }
                return SpireReturn.Continue();
            }
        }

        @SpirePatch(clz = AbstractGameAction.class, method = "setValues", paramtypez = {AbstractCreature.class,
                DamageInfo.class})
        public static class ChangeDamageTarget {
            public static void Postfix(AbstractGameAction _inst, AbstractCreature target, DamageInfo info) {
                if (info == null)
                    return;
                // 取消友伤
                if (info.owner == AbstractDungeon.player && target instanceof AbsSummonMonster) {
                    info = null;
                    logger.info("友伤取消");
                }
                if (target != null && info != null && info.type != DamageInfo.DamageType.HP_LOSS
                        && (info.owner == null || !info.owner.isPlayer) && target == AbstractDungeon.player
                        && MonsterTakeDamagePatch.gotSummon()) {
                    _inst.target = MonsterTakeDamagePatch.summonTarget;
                    logger.info("承伤改变" + MonsterTakeDamagePatch.summonTarget);
                }
            }
        }
    }


    @SpirePatch(
            clz = GameActionManager.class,
            method = "getNextAction"
    )
    public static class StartOfTurnPatch {
        public StartOfTurnPatch() {
        }

        @SpireInsertPatch(
                rloc = 240
        )
        public static void Insert(GameActionManager _inst) {
            if (!MonsterTakeDamagePatch.gotSummon()) {
                return;
            }
            AbsSummonMonster m = MonsterTakeDamagePatch.summonTarget;
            if (m != null) {
                if (!m.hasPower("Barricade") && !m.hasPower("Blur")) {
                    if (!AbstractDungeon.player.hasRelic("Calipers")) {
                        m.loseBlock();
                    } else {
                        m.loseBlock(15);
                    }
                }

                m.applyStartOfTurnPowers();
                m.applyStartOfTurnPostDrawPowers();
                Wiz.addToBotAbstract(m::applyPowers);
            }
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
                    ReflectionHacks.getPrivate(_inst, AbstractGameAction.class, "duration"),
                    ReflectionHacks.getPrivate(_inst, AbstractGameAction.class,
                            "startDuration"))
                    && MonsterTakeDamagePatch.gotSummon()) {
                _inst.target = MonsterTakeDamagePatch.summonTarget;
                logger.info("Block Target Changed To: " + _inst.target.name);
            }
        }
    }

    @SpirePatch(clz = Burn.class, method = "use")
    public static class BurnPatch {
        public static SpireReturn<Void> Prefix(Burn _inst, AbstractPlayer p, AbstractMonster m) {
            if (_inst.dontTriggerOnUseCard) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player,
                        new DamageInfo(null, _inst.magicNumber, DamageInfo.DamageType.THORNS),
                        AbstractGameAction.AttackEffect.FIRE));
            }
            return SpireReturn.Return(null);
        }
    }

    @SpirePatch(
            clz = HandDrill.class,
            method = "onBlockBroken"
    )
    public static class HandDrillPatch {
        public HandDrillPatch() {
        }

        public static SpireReturn<Void> Prefix(HandDrill _inst, AbstractCreature c) {
            return c instanceof AbsSummonMonster ? SpireReturn.Return(null) : SpireReturn.Continue();
        }
    }


    @SpirePatch(clz = MonsterGroup.class, method = "areMonstersDead")
    public static class EndBattleCheck_DeadPatch {
        @SpirePrefixPatch
        public static void Prefix() {
            if (MonsterHelper.areMonstersDead()) {
                MonsterHelper.getSummons().forEach(sm -> sm.isDying = true);
            } else {
                MonsterHelper.getSummons().forEach(sm -> sm.isDying = false);
            }
        }
    }

    @SpirePatch(clz = MonsterGroup.class, method = "applyPreTurnLogic")
    public static class SummonPreTurnPatch {
        static final HashSet<AbstractMonster> escapedMonsters = new HashSet<>();

        @SpirePrefixPatch
        public static void Prefix(MonsterGroup __inst) {
            escapedMonsters.clear();
            __inst.monsters.forEach(mo -> {
                if (mo instanceof AbsSummonMonster && !mo.isDeadOrEscaped()) {
                    escapedMonsters.add(mo);
                    mo.isEscaping = true;
                }
            });
        }

        @SpirePostfixPatch
        public static void Postfix(MonsterGroup __inst) {
            escapedMonsters.forEach(mo -> mo.isEscaping = false);
            escapedMonsters.clear();
        }
    }

}
