package lingmod.util;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.lang.reflect.Constructor;

public class PowerUtils {
    /**
     * 把能力添加到monster
     */
    public static AbstractPower forkPower(AbstractPower origin, AbstractMonster target) {
        Constructor<?>[] constructors = getPowerConstructors(origin.getClass());

        for (Constructor<?> constructor : constructors) {
            if (constructor == null) continue;
            try {
                int pc = constructor.getParameterCount();
                if (pc == 1) {
                    return (AbstractPower) constructor.newInstance(target);
                } else if (pc == 2) {
                    return (AbstractPower) constructor.newInstance(target, origin.amount);
                } else {
                    return (AbstractPower) constructor.newInstance(target, origin.amount, false);
                }
            } catch (Exception ignored) {
            }
        }
        // 如果所有尝试都失败，返回null
        return null;
    }

    private static Constructor<?>[] getPowerConstructors(Class<?> powerClass) {
        return new Constructor<?>[]{
                getConstructor(powerClass, AbstractCreature.class),
                getConstructor(powerClass, AbstractMonster.class),
                getConstructor(powerClass, AbstractCreature.class, int.class),
                getConstructor(powerClass, AbstractMonster.class, int.class),
                getConstructor(powerClass, AbstractCreature.class, int.class, boolean.class) // 易伤虚弱
        };
    }

    private static Constructor<?> getConstructor(Class<?> clazz, Class<?>... parameterTypes) {
        try {
            return clazz.getConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

}
