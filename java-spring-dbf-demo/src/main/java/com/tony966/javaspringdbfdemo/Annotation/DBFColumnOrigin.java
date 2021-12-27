package com.tony966.javaspringdbfdemo.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记只需从一个DBF文件获取值的字段
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBFColumnOrigin {
	String origin() default "";
	String column() default "";
}
