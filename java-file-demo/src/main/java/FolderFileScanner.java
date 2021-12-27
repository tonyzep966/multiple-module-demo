import org.apache.commons.lang3.ObjectUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author 邪恶小先生
 */
public class FolderFileScanner {
	/**
	 * 非递归方式扫描指定文件夹下面的所有文件
	 *
	 * @param folderPath 需要进行文件扫描的文件夹路径
	 * @return ArrayList<Object>
	 */
	public static ArrayList<String> scanFilesWithNoRecursion(String folderPath) throws RuntimeException {
		ArrayList<String> scanFiles = new ArrayList<>();
		LinkedList<File> queueFiles = new LinkedList<>();

		File directory = new File(folderPath);
		if (!directory.isDirectory()) {
			throw new RuntimeException('"' + folderPath + '"' + " input path is not a Directory , please input the right path of the Directory. ^_^...^_^");
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

				File[] tddList = headDirectory.listFiles(new RegexFilter(".*(?i)T_TDD\\.dbf"));
				File[] bmkList = headDirectory.listFiles(new RegexFilter(".*(?i)T_BMK\\.dbf"));
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
//							else {
//								scanFiles.add(currentFile.getAbsolutePath());
//							}
						}
					}
				}
			}
		}

		return scanFiles;
	}
}
