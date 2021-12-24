package Utils;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DBFUtils {
	/**
	 * 获取映射关系
	 *
	 * @param path
	 * @param keyColumn
	 * @param valueColumn
	 * @return
	 */
	public static Map<String, String> getMap(String path, String keyColumn, String valueColumn) {
		Map<String, String> resultMap = new HashMap<>();
		try (DBFReader reader = new DBFReader(new FileInputStream(path), Charset.forName("GB2312"))) {
			DBFRow row;
			while ((row = reader.nextRow()) != null) {
				String key = String.valueOf(row.getObject(keyColumn));
				String value = String.valueOf(row.getObject(valueColumn));
				resultMap.put(key, value);
			}
		} catch (DBFException | IOException e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * 查询与字段值相匹配的一行<br/>
	 * 相当于 select * from DBF where targetColumn = targetValue
	 *
	 * @param path         DBF表文件路径
	 * @param targetColumn 查询判断相等的字段
	 * @param targetValue  字段相等的目标值
	 * @return Map
	 */
	private static Map<String, String> selectSingleDBFRow(String path, String targetColumn, String targetValue) {
		Map<String, String> result = new HashMap<>();
		try (DBFReader reader = new DBFReader(new FileInputStream(path), Charset.forName("GB2312"))) {
			int numberOfFields = reader.getFieldCount();
			Map<String, Integer> columnMap = new HashMap<>();

			for (int i = 0; i < numberOfFields; i++) {
				DBFField field = reader.getField(i);
				columnMap.put(field.getName(), i);
			}

			DBFRow row;
			while ((row = reader.nextRow()) != null) {
				if (StringUtils.equals(String.valueOf(row.getObject(targetColumn)), targetValue)) {
					for (int column = 0; column < numberOfFields; column++) {
						Object dbfValue = row.getObject(column);
						String value;
						if (dbfValue instanceof Date) {
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
							value = format.format(dbfValue);
						} else {
							value = String.valueOf(dbfValue);
						}
						result.put(reader.getField(column).getName(), value);
					}
				}
			}
			return result;
		} catch (DBFException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
