import Utils.AnnotationUtils;
import annotation.Utils;
import object.TestObject;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Set;

public class main {
	public static void main(String[] args) {
		ArrayList<String> test = FolderFileScanner.scanFilesWithNoRecursion("D:\\Desktop\\工作\\文件夹\\录取库");
		test.forEach(System.out::println);
	}
}
