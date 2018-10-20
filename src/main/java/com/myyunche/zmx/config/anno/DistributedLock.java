package com.myyunche.zmx.config.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DistributedLock {

    /**
     * 当前锁的：key
     * <p>
     * 默认取：注解标注的-当前方法名
     *
     * @return
     */
    String key() default "";

    /**
     * 当前锁的：自动过期时间  （单位：微秒）默认值：1000
     *
     * @return
     */
    long timeOut() default 1000;
}
