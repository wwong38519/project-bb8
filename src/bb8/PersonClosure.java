package bb8;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import util.CommonUtil;
import util.SampleDataUtil;

public class PersonClosure {

	public static void main(String[] args) {
		List<Person> list = SampleDataUtil.getSampleData();
		// not good
		//Map<String, Person> map = list.stream().collect(Collectors.toMap(p -> p.getName(), p -> p));
		
		// better
		// will not work if there is duplicated key
		//Map<String, Person> map = list.stream().collect(Collectors.toMap(Person::getName, Function.identity()));
		
		// Merge Function: what to do when there is duplicated key
		//Map<String, Person> map = list.stream().collect(Collectors.toMap(Person::getName, Function.identity(), (p1, p2) -> p1 ));
		
	// TODO: Can I change the key to put it in the map? Or it is immutable?
		//Map<String, Person> map = list.stream().collect(Collectors.toMap(Person::getName, Function.identity(), (p1, p2) -> {p1.setLastName(p1.getLastName()+p2.getLastName()); return p1;} ));
		
		// list to map
		Map<String, Person> map = list.stream().collect(Collectors.toMap(Person::getId, Function.identity()));
		
		// it gives Map of List
		//Map<String, List<Person>> mapOfList = list.stream().collect(Collectors.groupingBy(Person::getName));
		
		// not sorted
		//map.entrySet().stream().forEach(x -> System.out.println(x.getKey() + " : " + x.getValue().getAge()));
		
		// not Comparable, ClassCastException
		//map.entrySet().stream().sorted().forEach(x -> System.out.println(x.getKey() + " : " + x.getValue().getAge()));
		
		// sort by age
		map.entrySet().stream().sorted((p1, p2) -> p1.getValue().getAge() - p2.getValue().getAge()).forEach(p -> System.out.println(p.getValue().print()));
		
		// or print like this
		System.out.println(map.values().stream().sorted((p1, p2) -> p1.getAge() - p2.getAge()).map(Person::print).collect(Collectors.joining("\n")));
		
		SampleDataUtil.addSampleSkills(list);
		SampleDataUtil.addSampleLanguages(list);
		
		// let's see the added contents first....
		System.out.println(list.stream().sorted((p1, p2) -> p1.getAge() - p2.getAge()).map(Person::printAbility).collect(Collectors.joining("\n")));
		
		List<String> languagesList = list.stream().map(p -> p.getLanguages()).flatMap(l -> l.stream()).distinct().sorted().collect(Collectors.toList());
		List<String> skillsList = list.stream().map(p -> p.getSkills()).flatMap(l -> l.keySet().stream()).distinct().sorted().collect(Collectors.toList());

		// let's see what do they all know
		System.out.println(String.join(CommonUtil.NAMEDELIM, languagesList));
		System.out.println(String.join(CommonUtil.NAMEDELIM, skillsList));
		

		// Step 1. Put everyone's skills into a list
		List<Entry<String, Integer>> skillsAsList = 
		list.stream()
			.map(p -> p.getSkills())
			.flatMap(l -> l.entrySet().stream())
			.collect(Collectors.toList());
		skillsAsList.stream().forEach(System.out::println);

		// Step 2. Group the skills as map
		Map<String, List<Entry<String, Integer>>> skillsAsMap = 
		list.stream()
			.map(p -> p.getSkills())
			.flatMap(l -> l.entrySet().stream())
			.collect(Collectors.toList())
			.stream()
			.collect(Collectors.groupingBy(x -> x.getKey()));
		skillsAsMap.entrySet().stream().forEach(System.out::println);
		
		// Step 3. Calculate average for each skill
		Map<String, Double> skillAverage = 
		list.stream()
			.map(p -> p.getSkills())
			.flatMap(l -> l.entrySet().stream())
			.collect(Collectors.toList())
			.stream()
			.collect(Collectors.groupingBy(x -> x.getKey(), Collectors.mapping(x -> x.getValue(), Collectors.averagingInt(n -> n))));
		skillAverage.entrySet().stream().forEach(System.out::println);
		
	// TODO_checked: how to find out what everyone's strongest skill is?
		// turn it is pretty simple
		Map<Person, Optional> strength = 
		list.stream()
			.collect(Collectors.toMap(Function.identity(), p -> p.getSkills().entrySet().stream().max(Entry.comparingByValue())));
		strength.entrySet().stream().sorted((x1, x2) -> x1.getKey().getName().compareTo(x2.getKey().getName()))
			.map(x -> x.getKey().printAbility() + CommonUtil.DELIM + x.getValue()).forEach(System.out::println);

		// .isPresent() to check if Optional has value, .get() is to return Entry from Optional
		strength.entrySet().stream().sorted((x1, x2) -> x1.getKey().getName().compareTo(x2.getKey().getName()))
			.map(x -> x.getKey().printAbility() + CommonUtil.DELIM + (x.getValue().isPresent() ?  x.getValue().get() : "This guy has no skill")).forEach(System.out::println);

		// or use .orElse() for a if-not-present value
		// Optional is not parameterized so that to show String value
		strength.entrySet().stream().sorted((x1, x2) -> x1.getKey().getName().compareTo(x2.getKey().getName()))
			.map(x -> x.getKey().printAbility() + CommonUtil.DELIM + x.getValue().orElse("This guy still has no skill")).forEach(System.out::println);
		
	// TODO: how to find out who is the strongest in a particular skill?
	}
	
	

}
