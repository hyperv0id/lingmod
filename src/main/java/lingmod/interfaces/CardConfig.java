package lingmod.interfaces;

import lingmod.monsters.AbsSummonMonster;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CardConfig {
    int damage() default -1;

    int damage2() default -1;

    int magic() default -1;

    int magic2() default -1;

    int block() default -1;

    int block2() default -1;

    int wineAmount() default -1;

    boolean isDream() default false;

    Class<? extends AbsSummonMonster> summonClz() default AbsSummonMonster.class;
}
