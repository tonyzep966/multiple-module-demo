package com.tony966.javaspringdbfdemo.Utils;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.tony966.javaspringdbfdemo.Annotation.DBFColumnExternal;
import com.tony966.javaspringdbfdemo.Annotation.DBFColumnOrigin;
import com.tony966.javaspringdbfdemo.DTO.EnrRecruitBmkDTO;
import com.tony966.javaspringdbfdemo.DTO.EnrRecruitTddDTO;
import com.tony966.javaspringdbfdemo.Filter.RegexFilter;
import com.tony966.javaspringdbfdemo.PO.EnrRecruit;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

public class DBFUtils {
	/**
	 * 在basePath下保存上传的文件夹
	 *
	 * @param basePath 保存的根路径
	 * @param files    从Controller获取到的文件
	 */
	public static void saveMultiFile(String basePath, MultipartFile[] files) {
		if (files == null || files.length == 0) {
			return;
		}
		if (basePath.endsWith("/")) {
			basePath = basePath.substring(0, basePath.length() - 1);
		}
		for (MultipartFile file : files) {
			String filePath = basePath + "/" + file.getOriginalFilename();
			makeDir(filePath);
			File dest = new File(filePath);
			try {
				file.transferTo(dest);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 确保目录存在，不存在则创建
	 *
	 * @param filePath 文件路径
	 */
	private static void makeDir(String filePath) {
		if (filePath.lastIndexOf('/') > 0) {
			String dirPath = filePath.substring(0, filePath.lastIndexOf('/'));
			File dir = new File(dirPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
	}

	/**
	 * 获取映射关系
	 *
	 * @param path        指定来源的DBF路径
	 * @param keyColumn   key列名
	 * @param valueColumn value列名
	 * @return 映射关系
	 */
	private static Map<String, String> getMap(String path, String keyColumn, String valueColumn) {
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
	 * @return 查询到的一行
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

	/**
	 * 扫描指定文件夹下面的所有包含特定DBF文件的文件夹
	 *
	 * @param folderPath 需要进行扫描的文件夹路径
	 * @return 包含指定DBF文件的路径
	 */
	private static ArrayList<String> scanFile(String folderPath) throws RuntimeException {
		ArrayList<String> scanFiles = new ArrayList<>();
		LinkedList<File> queueFiles = new LinkedList<>();

		File directory = new File(folderPath);
		if (!directory.isDirectory()) {
			throw new RuntimeException('"' + folderPath + '"' + " 该路径不是一个文件夹的合法路径");
		} else {
			//首先将第一层目录扫描一遍
			File[] files = directory.listFiles();
			//遍历扫出的文件数组，如果是文件夹，将其放入到linkedList中稍后处理
			if (ObjectUtils.isNotEmpty(files)) {
				for (File file : files) {
					if (file.isDirectory()) {
						queueFiles.add(file);
					} else {
						//暂时将文件名放入scanFiles中
						scanFiles.add(file.getAbsolutePath());
					}
				}
			}

			//如果linkedList非空遍历linkedList
			while (!queueFiles.isEmpty()) {
				//移出linkedList中的第一个
				File headDirectory = queueFiles.removeFirst();
				// 如果该路径下包含指定DBF文件, 则直接将其放入结果
				File[] tddList = headDirectory.listFiles(new RegexFilter(".*(?i)T_TDD\\.dbf"));
				if (ObjectUtils.isNotEmpty(tddList)) {
					scanFiles.add(headDirectory.getAbsolutePath());
				} else {
					File[] currentFiles = headDirectory.listFiles();
					if (ObjectUtils.isNotEmpty(currentFiles)) {
						for (File currentFile : currentFiles) {
							if (currentFile.isDirectory()) {
								//如果仍然是文件夹，将其放入linkedList中
								queueFiles.add(currentFile);
							}
						}
					}
				}
			}
		}
		return scanFiles;
	}

	private static List<EnrRecruit> provinceHandler(String root, Class<?> handleType) {
		List<EnrRecruit> recruits = new ArrayList<>();
		File[] findTdd = new File(root).listFiles(new RegexFilter(".*(?i)T_TDD\\.dbf"));
		String tddPath = root + "T_TDD.dbf";
		LinkedHashMap<String, String> originMap = new LinkedHashMap<>();
		LinkedList<String> originField = new LinkedList<>();
		LinkedList<LinkedList<String>> externalMap = new LinkedList<>();
		LinkedList<String> externalField = new LinkedList<>();
		if (ObjectUtils.isNotEmpty(findTdd)) {
			tddPath = findTdd[0].getAbsolutePath();
		}
		String charset = PropertiesUtils.readProperty("dbf.encode");
		try (DBFReader reader = new DBFReader(new FileInputStream(tddPath), Charset.forName(charset))) {
			for (Field field : handleType.getDeclaredFields()) {
				if (field.isAnnotationPresent(DBFColumnOrigin.class)) {
					DBFColumnOrigin annotation = field.getAnnotation(DBFColumnOrigin.class);
					String origin = annotation.origin();
					String column = annotation.column();
					originMap.put(origin, column);
					originField.add(field.getName());
				} else if (field.isAnnotationPresent(DBFColumnExternal.class)) {
					LinkedList<String> temp = new LinkedList<>();
					DBFColumnExternal annotation = field.getAnnotation(DBFColumnExternal.class);
					temp.add(annotation.refer());
					temp.add(annotation.referColumn());
					temp.add(annotation.target());
					temp.add(annotation.key());
					temp.add(annotation.value());
					externalMap.add(temp);
					externalField.add(field.getName());
				}
			}

			DBFRow row;
			while ((row = reader.nextRow()) != null) {

				EnrRecruit recruit = new EnrRecruit();
				String ksh = row.getString("KSH");

				for (Map.Entry<String, String> entry : originMap.entrySet()) {
					Class<?> recruitClass = recruit.getClass();
					String origin = entry.getKey();
					String column = entry.getValue();
					Map<String, String> selected = selectSingleDBFRow(root + "/" + origin + ".dbf", "KSH", ksh);
					if (MapUtils.isNotEmpty(selected)) {
						Field field = recruitClass.getDeclaredField(originField.removeFirst());
						field.set(recruit, ConvertUtils.convert(selected.get(column), field.getType()));
					}
					recruits.add(recruit);
				}

				while (!externalMap.isEmpty()) {
					LinkedList<String> current = externalMap.removeFirst();
					String refer = current.get(0);
					String referColumn = current.get(1);
					String target = current.get(2);
					String key = current.get(3);
					String value = current.get(4);
					Map<String, String> selected = selectSingleDBFRow(root + "/" + refer + ".dbf", "KSH", ksh);
					if (MapUtils.isNotEmpty(selected)) {
						String keyTarget = selected.get(referColumn);
						Map<String, String> dictionary = getMap(root + "/" + target + ".dbf", key, value);
						if (MapUtils.isNotEmpty(dictionary)) {
							String valueTarget = dictionary.get(keyTarget);
							Field field = recruit.getClass().getField(externalField.removeFirst());
							field.set(recruit, ConvertUtils.convert(valueTarget, field.getType()));
						}
					}

				}
			}
		} catch (NoSuchFieldException | IllegalAccessException | IOException | DBFException e) {
			e.printStackTrace();
		}
		return recruits;
	}

	private static boolean haveBMK(String path) {
		File folder = new File(path);
		File[] result = folder.listFiles(new RegexFilter(".*(?i)T_BMK\\.dbf"));
		return ObjectUtils.isNotEmpty(result);
	}

	public static List<EnrRecruit> assembleRecruit(String rootPath) {
		List<EnrRecruit> recruits = new ArrayList<>();
		LinkedList<String> scanPath = new LinkedList<>(scanFile(rootPath));
		while (!scanPath.isEmpty()) {
			String current = scanPath.remove();
			if (haveBMK(current)) {
				recruits.addAll(provinceHandler(current, EnrRecruitBmkDTO.class));
			} else {
				recruits.addAll(provinceHandler(current, EnrRecruitTddDTO.class));
			}
		}
		return recruits;
	}
}
