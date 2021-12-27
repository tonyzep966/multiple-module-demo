package com.tony966.javaspringdbfdemo.Utils;

import com.tony966.javaspringdbfdemo.Annotation.DBFClass;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;

public class AnnotationUtils {
	public static Set<Class<?>> scanTargetClass() {
		String scanPackage = PropertiesUtils.readProperty("dbf.eneity.package");
		Reflections reflections = new Reflections(scanPackage);
		Set<Class<?>> baseObjectSet = reflections.getTypesAnnotatedWith(DBFClass.class);
		return baseObjectSet;
	}

	public static Set<Field> scanTargetField(Class<Annotation> annotation) {
		String scanPackage = PropertiesUtils.readProperty("dbf.eneity.package");
		Reflections reflections = new Reflections(scanPackage);
		return reflections.getFieldsAnnotatedWith(annotation);
	}
}
