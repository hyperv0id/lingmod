package lingmod.util;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.exordium.AcidSlime_S;
import com.megacrit.cardcrawl.monsters.exordium.ApologySlime;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.monsters.exordium.SpikeSlime_S;
import com.megacrit.cardcrawl.random.Random;
import lingmod.ModCore;
import lingmod.monsters.AbsSummonMonster;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static lingmod.ModCore.logger;

public class MonsterHelper {
    public static boolean isAttackIntent(AbstractMonster m) {
        AbstractMonster.Intent[] atk_intents = {
                AbstractMonster.Intent.ATTACK,
                AbstractMonster.Intent.ATTACK_BUFF,
                AbstractMonster.Intent.ATTACK_DEBUFF,
                AbstractMonster.Intent.ATTACK_DEFEND
        };
        for (AbstractMonster.Intent intent : atk_intents) {
            if (m.intent == intent)
                return true;
        }
        return false;
    }

    public static int intentMultiAmt(AbstractMonster mo) {
        return ReflectionHacks.getPrivate(mo, AbstractMonster.class, "intentMultiAmt");
    }

    /**
     * calculate damage for all monsters
     *
     * @return total damage
     */
    public static int calcIntentDmg() {
        int total = 0;
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            // 不计算召唤物
            if (mo instanceof AbsSummonMonster) continue;
            total += calcIntentDmg(mo);
        }
        return total;
    }

    /**
     * calculate damage for specific monsters
     *
     * @param mo specific monster
     * @return total damage
     */
    public static int calcIntentDmg(AbstractMonster mo) {
        int total = 0;
        if (!mo.isDeadOrEscaped()) {
            mo.createIntent();
            if (!MonsterHelper.isAttackIntent(mo))
                return 0;
            int moDamage = basemod.ReflectionHacks.getPrivate(mo, AbstractMonster.class, "intentDmg");
            if (moDamage <= 0)
                return 0;
            if ((boolean) basemod.ReflectionHacks.getPrivate(mo, AbstractMonster.class, "isMultiDmg")) {
                moDamage *= (Integer) ReflectionHacks.getPrivate(mo, AbstractMonster.class, "intentMultiAmt");
            }
            total += moDamage;
        }
        return total;
    }

    /**
     * create a monster by its class
     * <a href=
     * "https://github.com/qw2341/Loadout-Mod/blob/master/src/main/java/loadout/screens/MonsterSelectScreen.java">from
     * loadout mod<a>
     */
    public static AbstractMonster createMonster(Class<? extends AbstractMonster> clz) {
        AbstractMonster res = new ApologySlime();
        // Exceptions
        if (clz.equals(AcidSlime_S.class)) {
            return new AcidSlime_S(0, 0, 0);
        } else if (clz.equals(SpikeSlime_S.class)) {
            return new SpikeSlime_S(0, 0, 0);
        } else if (clz.getName().equals("monsters.pet.ScapeGoatPet")) {
            return res;
        }

        Constructor<?>[] con = clz.getDeclaredConstructors();
        if (con.length > 0) {
            Constructor<?> c = con[0];
            try {
                int paramCt = c.getParameterCount();
                Class<?>[] params = c.getParameterTypes();
                Object[] paramz = new Object[paramCt];

                for (int i = 0; i < paramCt; i++) {
                    Class<?> param = params[i];
                    if (int.class.isAssignableFrom(param)) {
                        paramz[i] = 1;
                    } else if (boolean.class.isAssignableFrom(param)) {
                        paramz[i] = true;
                    } else if (float.class.isAssignableFrom(param)) {
                        paramz[i] = 0.0F;
                    } else if (AbstractMonster.class.isAssignableFrom(param)) {
                        paramz[i] = new Cultist(0, 0, false);
                    }
                }
                return (AbstractMonster) c.newInstance(paramz);
            } catch (Exception e) {
                logger.info("Error occurred while trying to instantiate class: " + c.getName());
                // e.printStackTrace();
                return res;
            }
        }
        return res;
    }

    public static float calculateSmartDistance(AbstractCreature m1, AbstractCreature m2) {
        return (m1.hb_w + m2.hb_w) / 2.0F;
    }

    public static AbstractMonster spawnMonster(Class<? extends AbstractMonster> monsterClass) {
        AbstractMonster m = createMonster(monsterClass);
        MonsterGroup mg = AbstractDungeon.getMonsters();
        float monsterDX = (float) Settings.WIDTH / 2.0F;
        float monsterDY = AbstractDungeon.player.drawY;
        AbstractMonster lastMonster = null;
        if (!mg.monsters.isEmpty()) {
            lastMonster = mg.monsters.get(mg.monsters.size() - 1);
            monsterDX = lastMonster.drawX;
            monsterDY = lastMonster.drawY;
        }

        m.drawX = monsterDX - (lastMonster != null ? calculateSmartDistance(lastMonster, m) : 200.0F) * Settings.scale;
        m.drawY = monsterDY;
        m.hb.move(m.drawX, m.drawY);
        m.init();
        m.applyPowers();
        m.useUniversalPreBattleAction();
        m.showHealthBar();
        m.createIntent();
        if (m.type == AbstractMonster.EnemyType.BOSS) {
            CardCrawlGame.music.silenceTempBgmInstantly();
            CardCrawlGame.music.silenceBGMInstantly();
        }

        m.usePreBattleAction();

        for (com.megacrit.cardcrawl.relics.AbstractRelic abstractRelic : AbstractDungeon.player.relics) {
            abstractRelic.onSpawnMonster(m);
        }

        mg.monsters.add(m);
        return m;
    }

    /**
     * 获取怪物，但是不选择 召唤物
     *
     * @param rng 随机数，null表示第一个
     * @return 怪物
     */
    public static AbstractMonster getMoNotSummon(boolean aliveOnly, Random rng) {
        List<AbstractMonster> mos = AbstractDungeon.getMonsters().monsters.stream()
                .filter(mo -> !(mo instanceof AbsSummonMonster))
                .filter(mo -> !aliveOnly || !mo.isDeadOrEscaped())
                .collect(Collectors.toList());
        if (mos.isEmpty())
            return null;
        AbstractMonster res = mos.get(0);
        if (rng != null) {
            int idx = rng.random(mos.size() - 1);
            res = mos.get(idx);
        }
        return res;
    }

    public static int cntSummons() {
        return (int) AbstractDungeon.getMonsters().monsters.stream()
                .filter(mo -> !mo.isDeadOrEscaped())
                .filter(mo -> mo instanceof AbsSummonMonster).count();
    }

    public static AbsSummonMonster getFirstSummon() {
        return (AbsSummonMonster) AbstractDungeon.getMonsters().monsters.stream()
                .filter(mo -> !mo.isDeadOrEscaped())
                .filter(mo -> mo instanceof AbsSummonMonster).findFirst().orElse(null);
    }

    public static Stream<AbstractMonster> getSummons() {
        return AbstractDungeon.getMonsters().monsters.stream()
                .filter(mo -> !mo.isDeadOrEscaped())
                .filter(mo -> mo instanceof AbsSummonMonster);
    }

    public static boolean areMonstersDead() {
        return allMonstersNotSummonStream().allMatch(m -> m.isDead || m.escaped);
    }

    public static boolean areMonstersBasicallyDead() {
        return allMonstersNotSummonStream().allMatch(m -> m.isDying || m.isEscaping);
    }

    /**
     * 获取所有怪物，但是不包含召唤物
     */
    public static List<AbstractMonster> allMonstersNotSummon() {
        return AbstractDungeon.getMonsters().monsters.stream().filter(mo -> !(mo instanceof AbsSummonMonster))
                .filter(mo -> !mo.isDeadOrEscaped())
                .collect(Collectors.toList());
    }

    /**
     * 获取所有怪物，但是不包含召唤物
     */
    public static Stream<AbstractMonster> allMonstersNotSummonStream() {
        return AbstractDungeon.getMonsters().monsters.stream().filter(mo -> !(mo instanceof AbsSummonMonster))
                .filter(mo -> !mo.isDeadOrEscaped());
    }

    public static void MoveMonster(AbstractMonster m, float x, float y) {
        m.drawX = x;
        m.drawY = y;
        m.animX = 0.0F;
        m.animY = 0.0F;
        m.hb.move(m.drawX + m.hb_x, m.drawY + m.hb_y + m.hb_h / 2.0F);
        m.healthHb.move(m.hb.cX, m.hb.cY - m.hb_h / 2.0F - m.healthHb.height / 2.0F);
        m.refreshIntentHbLocation();
    }

    public static void summonMonster(Class<? extends AbsSummonMonster> summonClz, int baseHP, int baseDamage) {
        // 不能是抽象类
        if (summonClz.equals(AbsSummonMonster.class)) {
            ModCore.logger.info("Cannot Summon Abstract Class");
            return;
        }
        AbsSummonMonster summonMonster = MonsterHelper.getFirstSummon();
        // 没有召唤物，直接生成
        if (summonMonster == null) {
            Wiz.addToBotAbstract(() -> {
                AbsSummonMonster mo = (AbsSummonMonster) MonsterHelper
                        .spawnMonster(summonClz);
                mo.currentHealth = mo.maxHealth = baseHP;
                // mo.animX = 1200F * Settings.xScale;
                mo.setDamage(baseDamage);
                mo.init();
                mo.applyPowers();
                mo.useUniversalPreBattleAction();
                mo.showHealthBar();
                mo.createIntent();

                mo.usePreBattleAction();

                // AbstractDungeon.getCurrRoom().monsters.addMonster(0, mo);
            });
        } else if (summonMonster.getClass().equals(summonClz)) {
            summonMonster.combine();
        } else {
            Wiz.atb(new TalkAction(true, "我不能同时召唤多种召唤物", 2.0F, 2.0F));
        }
    }

}
