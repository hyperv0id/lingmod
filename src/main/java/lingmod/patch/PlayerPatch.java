package lingmod.patch;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CtBehavior;
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

    public static AbsSummonMonster getSummon() {
        return PlayerPatch.Fields.summonedMonster.get(Wiz.adp());
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "renderHoverReticle"
    )
    public static class ReticlePatch {
        public ReticlePatch() {
        }

        public static void Postfix(AbstractPlayer _inst, SpriteBatch sb) {
            if (_inst.hoveredCard == null || _inst.hoveredCard.target == null) return;
            AbstractCard.CardTarget tar = _inst.hoveredCard.target;
            boolean render = tar == AbstractCard.CardTarget.SELF || tar == AbstractCard.CardTarget.SELF_AND_ENEMY;
            //                _inst.renderReticle(sb);
            AbsSummonMonster m = getSummon();
            if (m != null) {
                render = render || m.hb.hovered || m.intentHb.hovered || m.healthHb.hovered;
                if (render) m.renderReticle(sb);
            }
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "updateSingleTargetInput")
    public static class HoveredMonsterPatch {
        @SpireInsertPatch(loc = 1109)
        public static void Insert(AbstractPlayer __inst) {
            if (ReflectionHacks.getPrivate(__inst, AbstractPlayer.class, "hoveredMonster") == null && getSummon() != null && getSummon().hovered) {
                ReflectionHacks.setPrivate(__inst, AbstractPlayer.class, "hoveredMonster", getSummon());
            }
        }

        public static class Locator extends SpireInsertLocator {

            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(InputAction.class, "isJustPressed");
                return LineFinder.findAllInOrder(ctBehavior, m);
            }
        }
    }

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "onModifyPower"
    )
    public static class OnModifyPowerPatch {
        public static void Postfix() {
            if (AbstractDungeon.player != null && AbstractDungeon.getMonsters() != null) {
                AbsSummonMonster m = getSummon();
                if (m != null) {
                    m.applyPowers();
                }
            }
        }
    }

    @SpirePatch(
            clz = GameActionManager.class,
            method = "callEndOfTurnActions"
    )
    public static class EndOfTurnPatch {
        public static void Postfix(GameActionManager _inst) {
            AbsSummonMonster m = getSummon();
            if (m != null) {
                m.takeTurn();
                m.applyEndOfTurnTriggers();
            }
        }
    }


    @SpirePatch(
            clz = AbstractRoom.class,
            method = "render",
            paramtypez = {SpriteBatch.class}
    )
    public static class MonsterRenderPatch {
        public MonsterRenderPatch() {
        }

        @SpireInsertPatch(
                rloc = 12
        )
        public static void Insert(AbstractRoom _inst, SpriteBatch sb) {
            if (_inst.monsters != null) {
                AbsSummonMonster m = getSummon();
                if (m != null) {
                    m.render(sb);
                }
            }
        }
    }

    @SpirePatch(
            clz = AbstractRoom.class,
            method = "update"
    )
    public static class MonsterUpdatePatch {
        public MonsterUpdatePatch() {
        }

        public static void Postfix(AbstractRoom _inst) {
            AbsSummonMonster m = getSummon();
            if (m != null) {
                m.update();
                m.updateAnimations();
                if (m.isDeadOrEscaped()) {
                    Fields.summonedMonster.set(AbstractDungeon.player, null);
                }
            }
        }
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
