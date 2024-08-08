package lingmod.util;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.AcidSlime_S;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.monsters.exordium.SpikeSlime_S;

import java.lang.reflect.Constructor;

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
            if (m.intent == intent) return true;
        }
        return false;
    }


    /**
     * calculate damage for all monsters
     *
     * @return total damage
     */
    public static int calcIntentDmg() {
        int total = 0;
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
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
            if (!MonsterHelper.isAttackIntent(mo)) return 0;
            int moDamage = basemod.ReflectionHacks.getPrivate(mo, AbstractMonster.class, "intentDmg");
            if (moDamage <= 0) return 0;
            if ((boolean) basemod.ReflectionHacks.getPrivate(mo, AbstractMonster.class, "isMultiDmg")) {
                moDamage *= (Integer) ReflectionHacks.getPrivate(mo, AbstractMonster.class, "intentMultiAmt");
            }
            total += moDamage;
        }
        return total;
    }

    public static AbstractMonster createMonster(Class<? extends AbstractMonster> amClass) {
        //Exceptions
        if (amClass.equals(AcidSlime_S.class)) {
            return new AcidSlime_S(0, 0, 0);
        } else if (amClass.equals(SpikeSlime_S.class)) {
            return new SpikeSlime_S(0, 0, 0);
        } else if (amClass.getName().equals("monsters.pet.ScapeGoatPet")) {
            return null;
        }

        Constructor<?>[] con = amClass.getDeclaredConstructors();
        if (con.length > 0) {
            Constructor<?> c = con[0];
            try {
                int paramCt = c.getParameterCount();
                Class[] params = c.getParameterTypes();
                Object[] paramz = new Object[paramCt];

                for (int i = 0; i < paramCt; i++) {
                    Class param = params[i];
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
                //e.printStackTrace();
                return null;
            }
        }
        return null;
    }

}
