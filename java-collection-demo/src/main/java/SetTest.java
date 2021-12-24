import java.util.HashSet;
import java.util.Set;

public class SetTest {
	public static void main(String[] args) {
		Set<String> stringHashSet = new HashSet<>();
		stringHashSet.add("test");
		System.out.println(stringHashSet.contains("test"));
	}
}
