package com.tony966.javaspringdbfdemo.Utils;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.tony966.javaspringdbfdemo.Annotation.DBFClass;
import com.tony966.javaspringdbfdemo.Annotation.DBFColumnExternal;
import com.tony966.javaspringdbfdemo.Annotation.DBFColumnOrigin;
import com.tony966.javaspringdbfdemo.DTO.EnrRecruitBmkDTO;
import com.tony966.javaspringdbfdemo.Filter.RegexFilter;
import com.tony966.javaspringdbfdemo.PO.EnrRecruit;
import com.tony966.javaspringdbfdemo.constant.CityConstant;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DBFUtils {
	private static HashMap<String, HashMap<String, String>> currentTdd = new HashMap<>();  // 学号 - 列名 - 值
	private static HashMap<String, HashMap<String, String>> currentBmk = new HashMap<>();  // 学号 - 列名 - 值
	private static HashMap<String, HashMap<String, String>> currentMap = new HashMap<>();   // 字典名 - 当前省市的映射字典

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
	 *
	 * @param path 删除的根目录
	 * @return 删除成功
	 */
	private static boolean deleteDir(String path) {
		File deletePath = new File(path);
		if ((!deletePath.exists())) {
			return false;
		} else {
			return FileSystemUtils.deleteRecursively(deletePath);
		}
	}

	/**
	 * 获取文件的绝对路径，忽略文件名的大小写
	 * @param rootPath 文件所在的文件夹路径(可以是相对路径)
	 * @param fileName 文件名
	 * @return
	 */
	private static String getRealAbsolutePathIgnoreCase(String rootPath, String fileName) {
		File root = new File(rootPath);
		if (!root.exists()) {
			return null;
		}
		File[] find = root.listFiles(new RegexFilter(".*" + fileName + "\\.dbf"));
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
		HashMap<String, String> resultMap = new HashMap<>();
		// 检查指定的映射文件所在路径是否存在
		if (StringUtils.isEmpty(path))
			return null;
		// 查看预加载字典中是否已经写入该路径的映射
		if (null != currentMap.get(path)) {
			return currentMap.get(path);
		}
		// 支持多字段映射，通过简单拼接字符串组成新的key实现，以减号分隔
		if (keyColumn.contains("$")) {
			// 将字符串拆分回作为key值的列名列表
			List<String> keyColumnList = Arrays.asList(StringUtils.split(keyColumn, "$"));
			// 逐行读入DBF文件记录，可根据字段名获取值
			try (DBFReader reader = new DBFReader(new FileInputStream(path), Charset.forName(PropertiesUtils.readProperty("dbf.encode")))) {
				DBFRow row;
				while ((row = reader.nextRow()) != null) {
					StringBuilder key = new StringBuilder();
					for (String item : keyColumnList) {
						// 放入字典时重新将多个key拼接为一个字符串作为一个key使用
						key.append(row.getObject(item)).append("$");
					}
					key.deleteCharAt(key.length() - 1);
					String value = String.valueOf(row.getObject(valueColumn));
					resultMap.put(key.toString(), value);
				}
			} catch (DBFException | IOException e) {
				e.printStackTrace();
			}
		} else {
			// 一对一映射时直接放入key和value即可
			try (DBFReader reader = new DBFReader(new FileInputStream(path), Charset.forName(PropertiesUtils.readProperty("dbf.encode")))) {
				DBFRow row;
				while ((row = reader.nextRow()) != null) {
					String key = String.valueOf(row.getObject(keyColumn));
					String value = String.valueOf(row.getObject(valueColumn));
					resultMap.put(key, value);
				}
			} catch (DBFException | IOException e) {
				e.printStackTrace();
			}
		}

		// 以映射文件作为key来索引不同的字典
		currentMap.put(path, resultMap);
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
	 * 预加载整张表的映射，该方法只提供T_TDD或T_BMK的预加载
	 * @param rootPath 文件所在文件夹的路径
	 * @param fileName 文件名
	 * @param mode 读取BMK还是TDD
	 */
	private static void getWholeTable(String rootPath, String fileName, String mode) {
		// 获取文件绝对目录
		String path = getRealAbsolutePathIgnoreCase(rootPath, fileName);
		// 预加载字典使用考生号作为key值做索引, 所以需要外部声明
		String ksh = "";
		// 验证文件是否存在
		if (StringUtils.isEmpty(path))
			return;
		try (DBFReader reader = new DBFReader(new FileInputStream(path), Charset.forName("GB2312"))) {
			int numberOfFields = reader.getFieldCount();
			// 逐行读取DBF记录
			DBFRow row;
			while ((row = reader.nextRow()) != null) {
				// 一行记录的字典(列名, 该列单元格的值)
				HashMap<String, String> result = new HashMap<>();
				// 因为不能够确定字段具体名称, 故使用列数下标的方式进行该行的读取
				for (int column = 0; column < numberOfFields; column++) {
					// 获取该列的值
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
						// 字段值默认存储为字符串
						value = String.valueOf(dbfValue);
					}
					// 获取列名
					String columnName = reader.getField(column).getName();
					// 获取考生号
					if (StringUtils.equals(columnName, "KSH"))
						ksh = value;
					result.put(columnName, value);
				}
				// 写入预加载的对象
				if (StringUtils.equals(mode, "BMK")) {
					currentBmk.put(ksh, result);
				} else {
					currentTdd.put(ksh, result);
				}
			}
		} catch (DBFException | IOException e) {
			e.printStackTrace();
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
				File[] tddList = headDirectory.listFiles(new RegexFilter(".*T_TDD\\.dbf"));
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

	/**
	 * 扫描路径名，匹配省市标准名称(生源地)
	 * @param rootPath DBF文件所在路径
	 * @return 省市标准名称
	 */
	private static String getProvinceName(String rootPath) {
		StringBuilder patternString = new StringBuilder();
		for (String key : CityConstant.originPlanMap.keySet()) {
			patternString.append(key).append("|");
		}
		patternString = new StringBuilder("(" + StringUtils.chop(patternString.toString()) + ")");

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
	private static List<EnrRecruit> provinceHandler(String root, Class<?> handleType, String year, String level, @Nullable String province) {
		// 从配置文件读取DBF文件的编码
		String charset = PropertiesUtils.readProperty("dbf.encode");
		// 该省市录取全部学生信息
		List<EnrRecruit> recruits = new ArrayList<>();
		// 初始默认给出T_TDD文件路径
		String tddPath = root.endsWith("/") ? root + "T_TDD.dbf" : root + "/" + "T_TDD.dbf";
		// 本表查询的成员属性和其注解属性(origin, column)的字典
		LinkedHashMap<String, Pair<String, String>> originMap = new LinkedHashMap<>();
		// 本表查询的成员属性名
		LinkedList<String> originField = new LinkedList<>();
		// 外表连接的映射, LinkedList保持注解属性的顺序
		LinkedList<LinkedList<String>> externalMap = new LinkedList<>();
		// 需要外表连接的成员属性名
		LinkedList<String> externalField = new LinkedList<>();

		// 生源地，如果单个省市导入则手动指定，否则可以扫描路径名获取
		String syd = StringUtils.isEmpty(province) ? getProvinceName(root) : province;

		// 查找该目录下的T_TDD文件真实名称
		File[] findTdd = new File(root).listFiles(new RegexFilter(".*T_TDD\\.dbf"));
		if (ObjectUtils.isNotEmpty(findTdd)) {
			tddPath = findTdd[0].getAbsolutePath();
		}

		// 预加载主表
		getWholeTable(root, "T_TDD", "TDD");
		if (EnrRecruitBmkDTO.class == handleType) {
			getWholeTable(root, "T_BMK", "BMK");
		}

		try (DBFReader reader = new DBFReader(new FileInputStream(tddPath), Charset.forName(charset))) {
			// 整理省市对应实体类的字段数据来源, 分为单表和两表连接
			for (Field field : handleType.getDeclaredFields()) {
				// 将字段名和注解属性放入字典，按照注解类型分类
				if (field.isAnnotationPresent(DBFColumnOrigin.class)) {
					DBFColumnOrigin annotation = field.getAnnotation(DBFColumnOrigin.class);
					String origin = annotation.origin();
					String column = annotation.column();
					originMap.put(field.getName(), new ImmutablePair<>(origin, column));
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

			// 逐行读取主表
			DBFRow row;
			while ((row = reader.nextRow()) != null) {

				int originFieldCount = 0;
				EnrRecruit recruit = new EnrRecruit();
				String ksh = row.getString("KSH");
				recruit.setKsh(ksh);
				recruit.setSyd(syd);

				// 处理来源于单表查询的字段
				for (Map.Entry<String, Pair<String, String>> entry : originMap.entrySet()) {
					// 目标类的类型Class
					Class<?> recruitClass = recruit.getClass();
					// 来源表
					String origin = entry.getValue().getKey();
					// 来源字段
					String column = entry.getValue().getValue();
					// 目标成员属性名
					String originFieldName = originField.get(originFieldCount);
					if (StringUtils.equals(entry.getKey(), originFieldName)) {
						Map<String, String> selected;
						if (StringUtils.equals(origin, "T_BMK")) {
							selected = currentBmk.get(ksh);
						} else {
							selected = currentTdd.get(ksh);
						}
//						Map<String, String> selected = selectSingleDBFRow(root, origin, "KSH", ksh);
						if (MapUtils.isNotEmpty(selected)) {
							// 通过Field赋值
							Field field = recruitClass.getDeclaredField(originFieldName);
							field.setAccessible(true);
							if ("java.lang.String".equalsIgnoreCase(field.getGenericType().getTypeName()))
								field.set(recruit, selected.get(column));
							else {
								// 恢复为目标类成员属性的数据类型
								field.set(recruit, ConvertUtils.convert(selected.get(column), field.getType()));
							}
						}
					}
					originFieldCount++;
				}

				int externalMapCount = 0;
				// 处理来源于两表连接查询的字段
				while (externalMapCount < externalMap.size()) {
					LinkedList<String> current = externalMap.get(externalMapCount);
					// Key所在表
					String refer = current.get(0);
					// Key所在列名
					String referColumn = current.get(1);
					// 目标字典表
					String target = current.get(2);
					// 对应目标Key所在列
					String key = current.get(3);
					// 目标Value所在列
					String value = current.get(4);
					Map<String, String> selected;
					// 从预加载主表获取映射
					if (StringUtils.equals(refer, "T_BMK")) {
						selected = currentBmk.get(ksh);
					} else {
						selected = currentTdd.get(ksh);
					}
					// 如果映射存在，开始赋值
					if (MapUtils.isNotEmpty(selected)) {
						String keyProperty;
						//如果对应多个字段(字符串为拼接)
						if (referColumn.contains("$")) {
							// 拆分列名
							List<String> referColumnList = Arrays.asList(StringUtils.split(referColumn, "$"));
							// 将列名映射为所在该列单元格的值
							ArrayList<String> keyPropertyList = referColumnList.stream().map(selected::get).collect(Collectors.toCollection(ArrayList::new));
							// 将值拼接回字符串
							keyProperty = StringUtils.join(keyPropertyList, "$");
						} else {
							// 如果是一对一映射，则直接从预加载的主表中获取
							keyProperty = selected.get(referColumn);
						}
						// 获取字典(key可以是拼接的字符串，代表多个列)
						Map<String, String> dictionary = getMap(root, target, key, value);
						if (MapUtils.isNotEmpty(dictionary)) {
							// 获取字典映射的值
							String valueProperty = dictionary.get(keyProperty);
							// 获取Field, 通过Field赋值
							Field field = recruit.getClass().getDeclaredField(externalField.get(externalMapCount));
							field.setAccessible(true);
							// 字符串不用转换
							if ("java.lang.String".equalsIgnoreCase(field.getGenericType().getTypeName()))
								field.set(recruit, valueProperty);
							else {
								// 恢复为目标类成员属性的数据类型
								field.set(recruit, ConvertUtils.convert(valueProperty, field.getType()));
							}
						}
					}
					externalMapCount++;
				}
				String sydm = recruit.getSydm();
				if (StringUtils.isNotEmpty(sydm)) {
					// 考区码截取生源地码(地区代码)前两位
					recruit.setKqm(sydm.substring(0, 2));
				}
				// 层次名称
				recruit.setCcmc(level);
				// 是否有成绩支援表
				recruit.setCjzyb(0);
				// 体检表
				recruit.setTjb(0);
				recruit.setRegisterFlag(0);
				recruit.setYxFlag(0);
				recruit.setPayFlag(0);
				recruit.setDormFlag(0);
				recruit.setAffirmFlag(0);
				recruit.setCheckFlag(0);
				recruit.setGatherFlag(0);
				recruit.setDeleteFlag(0);
//				recruit.setCreateUserId(LoginInfoUtils.getLoginUserId());
				long currentTime = System.currentTimeMillis();
				Date currentDate = new Date(currentTime);
				recruit.setCreateTime(currentTime);
				recruit.setCreateDay(currentDate);
				recruit.setSourceType("2");
				recruits.add(recruit);
			}
		} catch (NoSuchFieldException | IllegalAccessException | IOException | DBFException e) {
			e.printStackTrace();
		} finally {
			// 每个省市读取完，将预加载的对象清空
			currentTdd.clear();
			currentBmk.clear();
			currentMap.clear();
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
		File[] result = folder.listFiles(new RegexFilter(".*T_BMK\\.dbf"));
		return ObjectUtils.isNotEmpty(result);
	}

	/**
	 * 自动识别该目录下所有省市的学生信息, 添加到List(速度过慢)
	 *
	 * @param rootPath 根目录
	 * @return 学生信息
	 */
	@Deprecated
	public static List<EnrRecruit> assembleRecruitAllFolders(String rootPath, String year, String level) {
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
				recruits.addAll(provinceHandler(current, bmkClass, year, level, null));
			} else {
				recruits.addAll(provinceHandler(current, tddClass, year, level, null));
			}
		}
		// 组装为List后删除删除的目录及目录下面的文件
		deleteDir(rootPath);
		return recruits;
	}

	public static List<EnrRecruit> assembleRecruitSingleFolder(String rootPath, String year, String level, String province) {
		File root = new File(rootPath);

		LinkedList<String> scanPath = new LinkedList<>(scanFile(rootPath));
		String path = scanPath.get(0);

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

		// 如果没有找到，则Class为DBFClass.class
		if (ClassUtils.isAssignable(bmkClass, DBFClass.class) || ClassUtils.isAssignable(tddClass, DBFClass.class)) {
			return null;
		}

		List<EnrRecruit> recruits = new ArrayList<>();

		if (haveBMK(path)) {
			recruits.addAll(provinceHandler(path, bmkClass, year, level, province));
		} else {
			recruits.addAll(provinceHandler(path, tddClass, year, level, province));
		}

		// 组装为List后删除的目录及目录下面的文件
		deleteDir(path);

		return recruits;
	}
}
