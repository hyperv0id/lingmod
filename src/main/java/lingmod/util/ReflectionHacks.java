//
// Source code recreated from a .class file by Quiltflower
//

package lingmod.util;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * from: KoishiMod
 */
public class ReflectionHacks {
    public static final Logger logger = LogManager.getLogger(ReflectionHacks.class.getName());
    private static final Map<ReflectionHacks.FieldInfo, Field> fieldMap = new HashMap();
    private static final Map<String, Method> methodMap = new HashMap();
    private static final ReflectionHacks.FieldInfoPool fieldInfoPool = new ReflectionHacks.FieldInfoPool();

    private ReflectionHacks() {
    }

    private static String toDescriptor(Class<?> clz, String methodName, Class<?>... parameterTypes) {
        StringBuilder buf = new StringBuilder();
        buf.append(clz.getName().replace('.', '/')).append('.').append(methodName).append(":(");

        for(Class<?> paramType : parameterTypes) {
            toDescriptor(buf, paramType);
        }

        buf.append(')');
        return buf.toString();
    }

    private static void toDescriptor(StringBuilder buf, Class<?> clz) {
        if (clz.isPrimitive()) {
            if (clz == Byte.TYPE) {
                buf.append('B');
            } else if (clz == Character.TYPE) {
                buf.append('C');
            } else if (clz == Float.TYPE) {
                buf.append('F');
            } else if (clz == Double.TYPE) {
                buf.append('D');
            } else if (clz == Integer.TYPE) {
                buf.append('I');
            } else if (clz == Long.TYPE) {
                buf.append('J');
            } else if (clz == Short.TYPE) {
                buf.append('S');
            } else if (clz == Boolean.TYPE) {
                buf.append('Z');
            } else {
                if (clz != Void.TYPE) {
                    throw new RuntimeException("Unrecognized primitive " + clz);
                }

                buf.append('V');
            }
        } else if (clz.isArray()) {
            buf.append('[');
            toDescriptor(buf, clz.getComponentType());
        } else {
            buf.append('L').append(clz.getName().replace('.', '/')).append(';');
        }
    }

    public static Field getCachedField(Class<?> clz, String fieldName) {
        ReflectionHacks.FieldInfo fieldInfo = fieldInfoPool.obtain(clz, fieldName);
        Field ret = (Field)fieldMap.get(fieldInfo);
        if (ret == null) {
            try {
                ret = clz.getDeclaredField(fieldName);
                ret.setAccessible(true);
                fieldMap.put(fieldInfo, ret);
            } catch (NoSuchFieldException var5) {
                logger.error("Exception occurred when getting field " + fieldName + " of " + clz.getName(), var5);
                var5.printStackTrace();
            }
        } else {
            fieldInfoPool.free(fieldInfo);
        }

        return ret;
    }

    public static Method getCachedMethod(Class<?> clz, String methodName, Class<?>... parameterTypes) {
        String descriptor = toDescriptor(clz, methodName, parameterTypes);
        Method ret = (Method)methodMap.get(descriptor);
        if (ret == null) {
            try {
                ret = clz.getDeclaredMethod(methodName, parameterTypes);
                ret.setAccessible(true);
                methodMap.put(descriptor, ret);
            } catch (NoSuchMethodException var6) {
                logger.error("Exception occurred when getting method " + methodName + " of " + clz.getName(), var6);
                var6.printStackTrace();
            }
        }

        return ret;
    }

    public static <T> T getPrivateStatic(Class<?> objClass, String fieldName) {
        try {
            return (T)getCachedField(objClass, fieldName).get(null);
        } catch (Exception var3) {
            logger.error("Exception occurred when getting private static field " + fieldName + " of " + objClass.getName(), var3);
            return null;
        }
    }

    public static void setPrivateStatic(Class<?> objClass, String fieldName, Object newValue) {
        try {
            getCachedField(objClass, fieldName).set(null, newValue);
        } catch (Exception var4) {
            logger.error("Exception occurred when setting private static field " + fieldName + " of " + objClass.getName(), var4);
        }
    }

    public static void setPrivateStaticFinal(Class<?> objClass, String fieldName, Object newValue) {
        try {
            Field f = getCachedField(objClass, fieldName);
            Field modifiers = getCachedField(Field.class, "modifiers");
            modifiers.setInt(f, f.getModifiers() & -17);
            f.set(null, newValue);
            modifiers.setInt(f, f.getModifiers() & 16);
        } catch (Exception var5) {
            logger.error("Exception occurred when setting private static (final) field " + fieldName + " of " + objClass.getName(), var5);
        }
    }

    public static <T> T getPrivate(Object obj, Class<?> objClass, String fieldName) {
        try {
            return (T)getCachedField(objClass, fieldName).get(obj);
        } catch (Exception var4) {
            logger.error("Exception occurred when getting private field " + fieldName + " of " + objClass.getName(), var4);
            return null;
        }
    }

    public static void setPrivate(Object obj, Class<?> objClass, String fieldName, Object newValue) {
        try {
            getCachedField(objClass, fieldName).set(obj, newValue);
        } catch (Exception var5) {
            logger.error("Exception occurred when setting private field " + fieldName + " of " + objClass.getName(), var5);
        }
    }

    public static void setPrivateFinal(Object obj, Class<?> objClass, String fieldName, Object newValue) {
        try {
            Field f = getCachedField(objClass, fieldName);
            Field modifiers = getCachedField(Field.class, "modifiers");
            modifiers.setInt(f, f.getModifiers() & -17);
            f.set(obj, newValue);
            modifiers.setInt(f, f.getModifiers() & 16);
        } catch (Exception var6) {
            logger.error("Exception occurred when setting private (final) field " + fieldName + " of " + objClass.getName(), var6);
        }
    }

    public static <T> T getPrivateInherited(Object obj, Class<?> objClass, String fieldName) {
        for(Class<?> var7 = objClass.getSuperclass(); var7 != null && var7 != Object.class; var7 = var7.getSuperclass()) {
            try {
                Field f = var7.getDeclaredField(fieldName);
                f.setAccessible(true);

                try {
                    return (T)f.get(obj);
                } catch (IllegalAccessException var5) {
                    logger.error("Exception occurred when getting private field " + fieldName + " of the superclass of " + var7.getName(), var5);
                    return null;
                }
            } catch (NoSuchFieldException var6) {
            }
        }

        return null;
    }

    public static void setPrivateInherited(Object obj, Class<?> objClass, String fieldName, Object newValue) {
        for(Class<?> var8 = objClass.getSuperclass(); var8 != null && var8 != Object.class; var8 = var8.getSuperclass()) {
            try {
                Field f = var8.getDeclaredField(fieldName);
                f.setAccessible(true);

                try {
                    f.set(obj, newValue);
                } catch (IllegalAccessException var6) {
                    logger.error("Exception occurred when setting private field " + fieldName + " of the superclass of " + var8.getName(), var6);
                }

                return;
            } catch (NoSuchFieldException var7) {
            }
        }
    }

    public static ReflectionHacks.RMethod privateMethod(Class<?> objClass, String methodName, Class<?>... parameterTypes) {
        return new ReflectionHacks.RMethod(objClass, methodName, parameterTypes);
    }

    public static ReflectionHacks.RStaticMethod privateStaticMethod(Class<?> objClass, String methodName, Class<?>... parameterTypes) {
        return new ReflectionHacks.RStaticMethod(objClass, methodName, parameterTypes);
    }

    private static class FieldInfo implements Poolable {
        private Class<?> clz;
        private String fieldName;

        public FieldInfo() {
            this(null, null);
        }

        public FieldInfo(Class<?> key, String value) {
            this.clz = key;
            this.fieldName = value;
        }

        public String toString() {
            return this.clz + "." + this.fieldName;
        }

        public boolean equals(Object o) {
            if (!(o instanceof ReflectionHacks.FieldInfo)) {
                return false;
            } else {
                ReflectionHacks.FieldInfo other = (ReflectionHacks.FieldInfo)o;
                return Objects.equals(this.clz, other.clz) && Objects.equals(this.fieldName, other.fieldName);
            }
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.clz, this.fieldName});
        }

        public void reset() {
            this.clz = null;
            this.fieldName = null;
        }
    }

    private static class FieldInfoPool extends Pool<ReflectionHacks.FieldInfo> {
        private FieldInfoPool() {
        }

        protected ReflectionHacks.FieldInfo newObject() {
            return new ReflectionHacks.FieldInfo();
        }

        public ReflectionHacks.FieldInfo obtain(Class<?> clz, String fieldName) {
            ReflectionHacks.FieldInfo ret = (ReflectionHacks.FieldInfo)this.obtain();
            ret.clz = clz;
            ret.fieldName = fieldName;
            return ret;
        }
    }

    public static class RMethod {
        private final Method method;

        private RMethod(Class<?> clz, String methodName, Class<?>... parameterTypes) {
            this.method = ReflectionHacks.getCachedMethod(clz, methodName, parameterTypes);
        }

        public <R> R invoke(Object instance, Object... args) {
            try {
                return (R)this.method.invoke(instance, args);
            } catch (Exception var4) {
                throw new RuntimeException(var4);
            }
        }
    }

    public static class RStaticMethod extends ReflectionHacks.RMethod {
        private RStaticMethod(Class<?> clz, String methodName, Class<?>... parameterTypes) {
            super(clz, methodName, parameterTypes);
        }

        public <R> R invoke(Object... args) {
            return super.invoke(null, args);
        }
    }
}
