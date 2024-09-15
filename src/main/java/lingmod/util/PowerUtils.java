package lingmod.util;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

import static lingmod.ModCore.logger;

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

    public static AbstractPower copyPower(AbstractPower original, HashMap<String, Object> mapTable) {
        // 创建新实例
        AbstractPower copy = createNewInstance(original);
        if (copy == null) {
            logger.info("复制能力失败TAT");
            return null;
        }
        // 复制字段
        copyFields(original, copy);
        // 深拷贝特定字段
        deepCopyFields(original, copy, mapTable);

        return copy;
    }

    private static AbstractPower createNewInstance(AbstractPower original) {
        try {
            // 使用无参构造函数创建新实例
            Constructor<? extends AbstractPower> constructor = original.getClass().getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception e) {
            logger.error("???AbstractPower存在无参构造呀");
            logger.error(e.getMessage());
            return null;
        }
    }

    private static void copyFields(AbstractPower original, AbstractPower copy) {
        Class<?> currentClass = original.getClass();
        while (currentClass != null && currentClass != Object.class) {
            for (Field field : currentClass.getDeclaredFields()) {
                try {
                    field.setAccessible(true);
                    field.set(copy, field.get(original));
                } catch (IllegalAccessException e) {
                    logger.warn("Failed to copy field: {} {}", field.getName(), e);
                }
            }
            currentClass = currentClass.getSuperclass();
        }
    }

    private static void deepCopyFields(AbstractPower original, AbstractPower copy, HashMap<String, Object> mapTable) {
        if (mapTable == null) return;
        mapTable.forEach((key, value) -> {
            String className, fieldName;
            String[] parts = key.split("\n");
            if (parts.length != 2) {
                fieldName = parts[0];
                className = copy.getClass().getName(); // 如果未指定类，使用最子的类
            } else {
                className = parts[0];
                fieldName = parts[1];
            }
            try {
                // 查找匹配的类
                Class<?> clazz = findMatchingClass(original.getClass(), className);
                if (clazz == null) {
                    throw new IllegalArgumentException("No matching class found for: " + className);
                }
                // 获取字段
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                // 检查字段是否为静态
                if (!Modifier.isStatic(field.getModifiers())) {
                    // 只有非静态字段才设置新值
                    field.set(copy, value);
                } else {
                    System.out.println("Skipping static field: " + fieldName);
                }
                // 设置新值
                field.set(copy, value);
            } catch (Exception e) {
                logger.warn("Failed to set field: {}{}", fieldName, e.getMessage());
            }
        });
    }

    private static Class<?> findMatchingClass(Class<?> startClass, String targetClassName) {
        Class<?> currentClass = startClass;
        while (currentClass != null && currentClass != Object.class) {
            if (currentClass.getSimpleName().equals(targetClassName)) {
                return currentClass;
            }
            currentClass = currentClass.getSuperclass();
        }
        return null;
    }
}
