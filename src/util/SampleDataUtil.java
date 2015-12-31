package util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import bb8.Person;
import util.CommonUtil.Sex;

public class SampleDataUtil {

	public static List<Person> getSampleData() {
		ArrayList<Person> list = new ArrayList<Person>();
		list.add(new Person("John", "Doe"));
		list.add(new Person("Jane", "Doe"));
		list.add(new Person("John", "Smith", date(1963,11,23), Sex.MALE));
		list.add(new Person("John", "Adams", date(1984,1,12), Sex.MALE));
		list.add(new Person("Rachel", "Adams", date(1990,10,17), Sex.FEMALE));
		list.add(new Person("Amy", "Chan", date(1990,12,29), Sex.FEMALE));
		list.add(new Person("Chris", "Wong", date(1990,12,30), Sex.FEMALE));
		list.add(new Person("Ethan", "Lee", date(1990,12,31), Sex.MALE));
		list.add(new Person("Chris", "Wong", date(1991,1,1), Sex.MALE));
		return list;
	}
	
	public static void addSampleLanguages(List<Person> list) {
		int maxPerPerson = 5;
		String[] pool = {"Spanish", "Korean", "German", "French", "Italian", "Russian", "Japanese"};
		Random r = new Random(System.currentTimeMillis());
		//list.stream().forEach(p -> {for(int i = 0; i < r.nextInt(5); i++) p.addLanguage(pool[r.nextInt(pool.length)]);});
		// Use Random.ints()
		list.stream().forEach(p -> {r.ints(0, pool.length).limit(r.nextInt(maxPerPerson)).forEach(x -> {p.addLanguage(pool[x]);});});
	}
	
	public static void addSampleSkills(List<Person> list) {
		int maxPerPerson = 5;
		String[] pool = {"HTML5", "Java", "CSS", "PHP", "MongoDB", "node.js", "Vagrant", "Express.js", "Angular.js", "Scala", "Groovy", "Python", "Ruby"};
		Random r = new Random(System.currentTimeMillis());
		//list.stream().forEach(p -> {for(int i = 0; i < r.nextInt(5); i++) p.addSkill(pool[r.nextInt(pool.length)], r.nextInt(5)+1);});
		// Use Random.ints()
		list.stream().forEach(p -> {r.ints(0, pool.length).limit(r.nextInt(maxPerPerson)).forEach(x -> {p.addSkill(pool[x], r.nextInt(5)+1);});});
	}
	
	public static Date date(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.set(year, month-1, day);
		return c.getTime();
	}
}
