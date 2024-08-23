package lingmod.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 素材来源
 * 格式： 用户名@平台
 * 卡面：显示在NAME上面
 * 事件：显示在TITLE上面
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Credit {
    static final String WEIBO = "微博";
    static final String BILI = "bilibili";
    static final String LOFTER = "lofter";
    static final String PIXIV = "pixiv";
    
    String platform() default "";

    String username() default "";

    String link() default "";

    String title() default "";
}
