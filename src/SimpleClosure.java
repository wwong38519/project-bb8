import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SimpleClosure {

	public static void main(String[] args) {
		int count = 10, max = 100;
		
		List<Integer> intList = getRandomIntegers(count, max);
		
		System.out.println("List");
		intList.forEach(x -> System.out.println(x));
		
		System.out.println("Sort Ascending");
		intList.sort((a, b) -> a - b);
		intList.forEach(System.out::println);
		
		System.out.println("Sort Descending");
		intList.sort((a, b) -> b - a);
		intList.forEach(System.out::println);
		
		System.out.println("----------");
		
		List<Integer> anotherList = getRandomIntegers(count, max);
		
		System.out.println("List");
		System.out.println(anotherList.stream().map(x -> x.toString()).collect(Collectors.joining(",", "[", "]")));
		
		System.out.println("Stream sorted");
		System.out.println(anotherList.stream().sorted().map(x -> x.toString()).collect(Collectors.joining(",", "[", "]")));
	}
	
	public static List<Integer> getRandomIntegers(int count, int max) {
		List<Integer> list = new ArrayList<Integer>();
		Random random = new Random(System.currentTimeMillis());
		for (int i = 0; i < count; i++) {
			list.add(random.nextInt(max));
		}
		return list;
	}
}
