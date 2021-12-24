package Utils;

import Annotation.DBFClass;
import POJO.EnrRecruit;
import org.reflections.Reflections;

import java.util.List;
import java.util.Set;

public class AnnotationUtils {
	public List<EnrRecruit> assemble(){
		String scanPackage = PropertiesUtils.readProperty("dbf.eneity.package");
		Reflections reflections = new Reflections(scanPackage);
		Set<Class<?>> baseObject = reflections.getTypesAnnotatedWith(DBFClass.class);
	}
}
