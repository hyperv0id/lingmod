package lingmod.util;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

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
}
