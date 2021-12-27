package com.tony966.javaspringdbfdemo.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBFColumnExternal {
	// 关联字段A所在的DBF文件名
	String refer() default "";

	// 关联的字段A的列名
	String referColumn() default "";

	// 关联的DBF文件名
	String target() default "";

	// 在关联的文件中字段A的列名
	String key () default "";

	// 在关联的文件中该属性对应字段A的映射列名
	String value() default "";
}
