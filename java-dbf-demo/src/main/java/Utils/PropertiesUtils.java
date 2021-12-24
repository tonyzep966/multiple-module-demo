package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {
	public static String readProperty(String key) {
		String value = "";
		try (InputStream inputStream = new FileInputStream("src/main/resources/dbf.properties")) {
			Properties properties = new Properties();
			properties.load(inputStream);
			value = properties.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}
}
