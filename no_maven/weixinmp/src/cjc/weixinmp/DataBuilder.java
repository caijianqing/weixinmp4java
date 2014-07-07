package cjc.weixinmp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * <pre>
 * 数据模型构造器抽象接口
 * 构造器用于<b>快速安全</b>地构建<b>复杂</b>的数据对象
 * 
 * 注意：数据构造器多线程不安全，请勿同一线程使用同一个构造器
 * </pre>
 * 
 * @author jianqing.cai@qq.com, 2014年6月24日 下午10:13:42, https://github.com/caijianqing/weixinmp4java/
 * @param <P> 数据模型对象的类型
 * 
 */
public abstract class DataBuilder<P> {

    /** 数据对象，即泛型P的实例，自动创建，直接使用 */
    protected P p;

    /**
     * 自动创建obj对象
     */
    @SuppressWarnings("unchecked")
    public DataBuilder() {
        // 根据泛型构建obj对象
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Class<?> clazz = (Class<?>) params[0];
        try {
            p = (P) clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 使用当前数据构建一个全新的数据对象模型<br>
     * 
     * 注意：如果使用build()方法后需要重用构造器，请视情况调用reset()方法清空数据
     * 
     * @return 返回完整的数据对象，这个对象是经过深层复制的，与当前构造器没有任何关系
     * @see #build(RequriedType)
     * @see #reset()
     * 
     */
    public P build() {
        return deepClone(p);
    }

    /**
     * 使用当前数据构建一个全新的数据对象模型<br>
     * 
     * 注意：如果使用build()方法后需要重用构造器，请视情况调用reset()方法清空数据
     * 
     * @param requriedType 自建了一套简单的数据校验机制，可以快速校验简单的IS NULL问题
     * @return 返回完整的数据对象，这个对象是经过深层复制的，与当前构造器没有任何关系
     * @see #build
     * @see #reset()
     */
    public P build(RequriedType requriedType) {
        if (requriedType != null && requriedType != RequriedType.NONE) {
            verify(requriedType, p.getClass(), p);
        }
        return deepClone(p);
    }

    private void verify(RequriedType verifyType, Class<?> clazz, Object obj) {
        Field[] filelds = clazz.getDeclaredFields();
        for (Field field : filelds) {
            // 过滤静态字段
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            // 过滤非public字段，因为数据模型中的所有字段均使用public修饰
            if (!Modifier.isPublic(field.getModifiers())) {
                continue;
            }

            // 判断是否为基本类型：
            Class<?> type = field.getType();
            boolean isPrimitive = false;
            if (type.isPrimitive() || String.class.isAssignableFrom(type)) {
                isPrimitive = true;
            }
            // 尝试判断封装类型的基本类型
            if (!isPrimitive) {
                try {
                    Field f = type.getDeclaredField("TYPE");
                    Class<?> t = (Class<?>) f.get(null);
                    if (t.isPrimitive()) {
                        isPrimitive = true;
                    }
                } catch (NoSuchFieldException e) {
                    // ignore
                } catch (SecurityException e) {
                    // ignore
                } catch (IllegalArgumentException e) {
                    // ignore
                } catch (IllegalAccessException e) {
                    // ignore
                }
            }
            // 取得当前字段的值
            Object fieldValue;
            if (obj == null) {
                // 对象为空，字段只能为空
                fieldValue = null;
            } else {
                try {
                    field.setAccessible(true);
                    fieldValue = field.get(obj);
                    field.setAccessible(false);
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException(e.getMessage(), e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
            // 验证字段
            Requried rv = field.getAnnotation(Requried.class);
            if (rv != null) {
                verify(verifyType, field, rv, type, fieldValue);
            }
            // 循环验证集合
            if (fieldValue != null && List.class.isAssignableFrom(type)) {
                List<?> list = (List<?>) fieldValue;
                for (Object listObj : list) {
                    verify(verifyType, listObj.getClass(), listObj);
                }
                continue;
            }
            // 递归处理复合类型
            if (!isPrimitive && fieldValue != null) {
                verify(verifyType, type, fieldValue);
            }
            // 验证父字段
            if (clazz.getSuperclass() != null) {
                verify(verifyType, clazz.getSuperclass(), obj);
            }
        }
    }

    /**
     * 这是一个递归方法调用的代码片段，如果你跟踪到这里，请在IDE的Debug窗口查看调用堆栈往上翻查哪个字段失败为NULL或者空白字符窜
     */
    private void verify(RequriedType verifyType, Field field, Requried rv, Class<?> type, Object fieldValue) {
        for (RequriedType req : rv.value()) {
            if (req == verifyType) {
                switch (verifyType) {
                case ADD:
                    if (fieldValue == null || (type == String.class && fieldValue.toString().trim().length() == 0)) {
                        throw new RuntimeException("ADD操作不允许这个字段为NULL或者Empty：" + field);
                    }
                    break;
                case DELETE:
                    if (fieldValue == null || (type == String.class && fieldValue.toString().trim().length() == 0)) {
                        throw new RuntimeException("DELETE操作不允许这个字段为NULL或者Empty：" + field);
                    }
                    break;
                case QUERY:
                    if (fieldValue == null || (type == String.class && fieldValue.toString().trim().length() == 0)) {
                        throw new RuntimeException("QUERY操作不允许这个字段为NULL或者Empty：" + field);
                    }
                    break;
                case UPDATE:
                    if (fieldValue == null || (type == String.class && fieldValue.toString().trim().length() == 0)) {
                        throw new RuntimeException("UPDATE操作不允许这个字段为NULL或者Empty：" + field);
                    }
                    break;
                case NONE:
                    break;
                }
                break; // 判断结束跳出for循环
            }
        }
    }

    /**
     * 重置当前数据结构（重建内置obj对象）<br>
     * 对build()方法返回的对象不影响
     */
    @SuppressWarnings("unchecked")
    public void reset() {
        try {
            p = (P) p.getClass().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 深层复制一个对象
     * 
     * @author caijianqing, 2014-1-23 下午1:43:13
     * @param obj 被复制的对象
     * @return 全新的对象
     */
    @SuppressWarnings("unchecked")
    private P deepClone(final P obj) {
        P t = null;
        // 将对象写到流中
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            out = new ObjectOutputStream(os);
            out.writeObject(obj);
            ByteArrayInputStream bi = new ByteArrayInputStream(os.toByteArray());
            in = new ObjectInputStream(bi);
            t = (P) in.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            // ignore
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // ignore
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        return t;
    }

    /**
     * 子构造器，用于对数据进行分类，方便录入
     * 
     * @param <P> 父数据构造器类型
     * @param <S> 子构造器本身类型，用于优化代码
     */
    public static abstract class SubBuilder<P, S> {

        /** 父数据结构对象构造器 */
        protected P p;

        /**
         * 传入父数据构造器<br>
         * 在父数据构造器中创建对象时的标准用法：new XXXBuilder().p(this)
         * 
         * @param p 父数据构造器
         */
        @SuppressWarnings("unchecked")
        public S p(P p) {
            this.p = p;
            return (S) this;
        }

        /**
         * 返回父对象以支持继续链式操作
         * 
         * @return 父对象
         */
        public P parent() {
            return p;
        }

    }

    /**
     * 验证类型
     */
    public static enum RequriedType {
        /** 不作任何验证，适合非增删改查的操作使用，但需要自行验证 */
        NONE,
        /** 新增时不允许为NULL */
        ADD,
        /** 更新操作时不允许为NULL */
        UPDATE,
        /** 查询操作时不允许为NULL */
        QUERY,
        /** 删除操作时不允许为NULL */
        DELETE;
    }

    /**
     * <pre>
     * 用来描述实体字段是否允许为NULL，分别用于四种操作方式：ADD,UPDATE,DELETE,QUERY
     * </pre>
     * 
     * @author jianqing.cai@qq.com, 2014年6月30日 下午10:08:59, https://github.com/caijianqing/weixinmp4java/
     */
    @Target({ ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Requried {

        /** 这些操作的时候不允许为NULL */
        RequriedType[] value() default {};

    }
}
