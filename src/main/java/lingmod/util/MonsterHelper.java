package lingmod.util;

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
}
