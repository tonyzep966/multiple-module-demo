package com.tony966.javaspringdbfdemo.Utils;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.tony966.javaspringdbfdemo.Annotation.DBFClass;
import com.tony966.javaspringdbfdemo.Annotation.DBFColumnExternal;
import com.tony966.javaspringdbfdemo.Annotation.DBFColumnOrigin;
import com.tony966.javaspringdbfdemo.Filter.RegexFilter;
import com.tony966.javaspringdbfdemo.PO.EnrRecruit;
import com.tony966.javaspringdbfdemo.constant.CityConstant;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	private static boolean deleteDir(String path) {
		File deletePath = new File(path);
		if ((!deletePath.exists()) || deletePath.isDirectory()) {
			return false;
		} else {
			return FileSystemUtils.deleteRecursively(deletePath);
		}
	}

	private static String getRealAbsolutePathIgnoreCase(String rootPath, String fileName) {
		File root = new File(rootPath);
		if (!root.exists() || !root.isDirectory()) {
			return null;
		}
		File[] find = root.listFiles(new RegexFilter(".*(?i)" + fileName + "\\.dbf"));
		if (ObjectUtils.isEmpty(find)) {
			return null;
		}
		return find[0].getAbsolutePath();
	}

	/**
	 * 获取映射关系
	 *
	 * @param rootPath    包含DBF文件的省市根路径
	 * @param fileName    DBF文件名, 不含后缀名
	 * @param keyColumn   key列名
	 * @param valueColumn value列名
	 * @return 映射关系
	 */
	private static Map<String, String> getMap(String rootPath, String fileName, String keyColumn, String valueColumn) {
		String path = getRealAbsolutePathIgnoreCase(rootPath, fileName);
		if (StringUtils.isEmpty(path))
			return null;
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
	 * @param rootPath     包含DBF文件的省市根路径
	 * @param fileName     DBF文件名称, 不含后缀名
	 * @param targetColumn 查询判断相等的字段
	 * @param targetValue  字段相等的目标值
	 * @return 查询到的一行
	 */
	private static Map<String, String> selectSingleDBFRow(String rootPath, String fileName, String targetColumn, String targetValue) {
		String path = getRealAbsolutePathIgnoreCase(rootPath, fileName);
		if (StringUtils.isEmpty(path))
			return null;
		Map<String, String> result = new HashMap<>();
		try (DBFReader reader = new DBFReader(new FileInputStream(path), Charset.forName("GB2312"))) {
			int numberOfFields = reader.getFieldCount();

//			Map<String, Integer> columnMap = new HashMap<>();
//			for (int i = 0; i < numberOfFields; i++) {
//				DBFField field = reader.getField(i);
//				columnMap.put(field.getName(), i);
//			}

			DBFRow row;
			while ((row = reader.nextRow()) != null) {
				if (StringUtils.equals(String.valueOf(row.getObject(targetColumn)), targetValue)) {
					for (int column = 0; column < numberOfFields; column++) {
						Object dbfValue = row.getObject(column);
						String value;
						if (dbfValue instanceof Date) {
							// 日期类型及格式需要手动转换
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
							// 转换为标准日期格式字符串
//							value = format.format(dbfValue);
							// 转换为时间戳字符串
							value = String.valueOf(((Date) dbfValue).getTime());
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
			//首先扫描根目录下的第一级目录
			File[] files = directory.listFiles();
			if (ObjectUtils.isNotEmpty(files)) {
				//如果File是文件夹，将其放入到LinkedList中稍后处理
				for (File file : files) {
					if (file.isDirectory()) {
						queueFiles.add(file);
					} else {
						//暂时将文件名放入scanFiles中
						scanFiles.add(file.getAbsolutePath());
					}
				}
			}

			//如果LinkedList非空遍历LinkedList, LinkedList只会保存文件夹路径
			while (!queueFiles.isEmpty()) {
				//移出LinkedList中的第一个
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
								// 如果仍然是文件夹，将其放回LinkedList中
								queueFiles.add(currentFile);
							}
						}
					}
				}
			}
		}
		return scanFiles;
	}

	private static String getProvinceName(String rootPath) {
		StringBuffer patternString = new StringBuffer();
		for (String key : CityConstant.originPlanMap.keySet()) {
			patternString.append(key).append("|");
		}
		patternString = new StringBuffer("(" + StringUtils.chop(patternString.toString()) + ")");

		Pattern pattern = Pattern.compile(patternString.toString());
		Matcher matcher = pattern.matcher(rootPath);
		if (matcher.find())
			return CityConstant.originPlanMap.get(matcher.group(0));
		else
			return "";
	}

	/**
	 * 省市的通用具体处理方法
	 *
	 * @param root       省市根目录
	 * @param handleType 省市对应的实体类
	 * @return 学生信息
	 */
	private static List<EnrRecruit> provinceHandler(String root, Class<?> handleType) {
		// 从配置文件读取DBF文件的编码
		String charset = PropertiesUtils.readProperty("dbf.encode");
		List<EnrRecruit> recruits = new ArrayList<>();
		File[] findTdd = new File(root).listFiles(new RegexFilter(".*(?i)T_TDD\\.dbf"));
		String tddPath = root.endsWith("/") ? root + "T_TDD.dbf" : root + "/" + "T_TDD.dbf";
		LinkedHashMap<String, String> originMap = new LinkedHashMap<>();
		LinkedList<String> originField = new LinkedList<>();
		LinkedList<LinkedList<String>> externalMap = new LinkedList<>();
		LinkedList<String> externalField = new LinkedList<>();
		if (ObjectUtils.isNotEmpty(findTdd)) {
			tddPath = findTdd[0].getAbsolutePath();
		}

		try (DBFReader reader = new DBFReader(new FileInputStream(tddPath), Charset.forName(charset))) {
			// 整理省市对应实体类的字段数据来源, 分为单表和两表连接
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
				String syd = getProvinceName(root);
				recruit.setSyd(syd);

				// 处理来源于单表查询的字段
				for (Map.Entry<String, String> entry : originMap.entrySet()) {
					Class<?> recruitClass = recruit.getClass();
					String origin = entry.getKey();
					String column = entry.getValue();
					Map<String, String> selected = selectSingleDBFRow(root, origin, "KSH", ksh);
					if (MapUtils.isNotEmpty(selected)) {
						Field field = recruitClass.getDeclaredField(originField.removeFirst());
						field.set(recruit, ConvertUtils.convert(selected.get(column), field.getType()));
					}
				}

				// 处理来源于两表连接查询的字段
				while (!externalMap.isEmpty()) {
					LinkedList<String> current = externalMap.removeFirst();
					String refer = current.get(0);
					String referColumn = current.get(1);
					String target = current.get(2);
					String key = current.get(3);
					String value = current.get(4);
					Map<String, String> selected = selectSingleDBFRow(root, refer, "KSH", ksh);
					if (MapUtils.isNotEmpty(selected)) {
						String keyTarget = selected.get(referColumn);
						Map<String, String> dictionary = getMap(root , target, key, value);
						if (MapUtils.isNotEmpty(dictionary)) {
							String valueTarget = dictionary.get(keyTarget);
							Field field = recruit.getClass().getField(externalField.removeFirst());
							field.set(recruit, ConvertUtils.convert(valueTarget, field.getType()));
						}
					}
				}
				// 考区码截取生源地码(地区代码)前两位
				recruit.setKqm(recruit.getSydm().substring(0,2));
				recruit.setCjzyb(0);
				recruit.setTjb(0);
				recruit.setRegisterFlag(0);
				recruit.setYxFlag(0);
				recruit.setPayFlag(0);
				recruit.setDormFlag(0);
				recruit.setAffirmFlag(0);
				recruit.setCheckFlag(0);
				recruit.setGatherFlag(0);
				recruit.setDeleteFlag(0);
				// TODO
//				recruit.setCreateUserId();
				long currentTime = System.currentTimeMillis();
				Date currentDate = new Date(currentTime);
				recruit.setCreateTime(currentTime);
				recruit.setCreateDay(currentDate);
				recruit.setSourceType("2");
				recruits.add(recruit);
			}
		} catch (NoSuchFieldException | IllegalAccessException | IOException | DBFException e) {
			e.printStackTrace();
		}
		return recruits;
	}

	/**
	 * 判断该省市目录下是否有T_BMK.dbf
	 *
	 * @param path 省市路径
	 * @return 是或否
	 */
	private static boolean haveBMK(String path) {
		File folder = new File(path);
		File[] result = folder.listFiles(new RegexFilter(".*(?i)T_BMK\\.dbf"));
		return ObjectUtils.isNotEmpty(result);
	}

	/**
	 * 将学生信息添加到List
	 *
	 * @param rootPath 根目录
	 * @return 学生信息
	 */
	public static List<EnrRecruit> assembleRecruit(String rootPath) {
		Set<Class<?>> targetClass = AnnotationUtils.scanTargetClass();
		// 扫描使用注解标记的两种处理类
		Class<?> bmkClass = targetClass.stream()
				.filter(item -> StringUtils.equals(item.getAnnotation(DBFClass.class).type(), "BMK"))
				.findAny()
				.orElse(DBFClass.class);

		Class<?> tddClass = targetClass.stream()
				.filter(item -> StringUtils.equals(item.getAnnotation(DBFClass.class).type(), "TDD"))
				.findAny()
				.orElse(DBFClass.class);

		if (ClassUtils.isAssignable(bmkClass, DBFClass.class) || ClassUtils.isAssignable(tddClass, DBFClass.class)) {
			return null;
		}

		List<EnrRecruit> recruits = new ArrayList<>();
		LinkedList<String> scanPath = new LinkedList<>(scanFile(rootPath));
		while (!scanPath.isEmpty()) {
			String current = scanPath.remove();
			if (haveBMK(current)) {
				recruits.addAll(provinceHandler(current, bmkClass));
			} else {
				recruits.addAll(provinceHandler(current, tddClass));
			}
		}
		// 组装为List后删除删除的目录及目录下面的文件
		deleteDir(rootPath);
		return recruits;
	}
}
