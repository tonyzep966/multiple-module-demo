import constant.CityConstant;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main {
	public static void main(String[] args) {
		ArrayList<String> test = FolderFileScanner.scanFilesWithNoRecursion("D:\\Desktop\\工作\\文件夹\\录取库");
		test.forEach(System.out::println);
		System.out.println("----------------------------------------------");
		String rootPath = "D:\\Desktop\\工作\\文件夹\\录取库\\甘肃\\甘南专项\\理科";
		StringBuffer patternString = new StringBuffer();
		for (String key : CityConstant.originPlanMap.keySet()) {
			patternString.append(key).append("|");
		}
		patternString = new StringBuffer("(" + StringUtils.chop(patternString.toString()) + ")");

		System.out.println(rootPath);
		System.out.println(patternString);

		Pattern pattern = Pattern.compile(patternString.toString());
		Matcher matcher = pattern.matcher(rootPath);
		if (matcher.find())
			System.out.println("Here is " + matcher.group(0));
		else
			System.out.println("Not Found");
	}
}
