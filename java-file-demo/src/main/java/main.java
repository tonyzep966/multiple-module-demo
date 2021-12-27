import org.apache.commons.beanutils.ConvertUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;

public class main {
	public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
//		ArrayList<String> test = FolderFileScanner.scanFilesWithNoRecursion("D:\\Desktop\\工作\\文件夹\\录取库");
//		test.forEach(System.out::println);
		Test test = new Test();
		test.handle(test);
		test.print();
	}

	private static class Test {
		private Date number = new Date(System.currentTimeMillis());
		private String name;

		public void handle(Object object) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
			Field numberField = Test.class.getDeclaredField("number");
			Field nameField = Test.class.getDeclaredField("name");

			nameField.set(object, ConvertUtils.convert(numberField.get(object), nameField.getType()));
		}

		public void print(){
			System.out.println(name);
		}
	}
}
