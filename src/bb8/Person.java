package bb8;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import util.CommonUtil;
import util.CommonUtil.Sex;

public class Person {

	private String id;
	private String firstName;
	private String lastName;
	private Date birthdate;
	private Sex sex;
	private Set<String> languages;
	private Map<String, Integer> skills;
	
	@SuppressWarnings("unused")
	private Person() {
	}
	public Person(String id) {
		this(id, null, null, null, null);
	}
	public Person(String firstName, String lastName) {
		this(null, firstName, lastName, null, null);
	}
	public Person(String firstName, String lastName, Date birthdate, Sex sex) {
		this(null, firstName, lastName, birthdate, sex);
	}
	public Person(String id, String firstName, String lastName, Date birthdate, Sex sex) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.sex = sex;
		this.languages = new TreeSet<String>();
		this.skills = new TreeMap<String, Integer>();
	}
	public String getId() {
		return id == null ? getName()+getSex()+getBirthdate(): id;
	}
	public String getName() {
		return lastName+CommonUtil.NAMEDELIM+firstName;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getBirthdate() {
		return birthdate == null ? CommonUtil.BLANK : CommonUtil.DATEFORMAT.format(birthdate);
	}
	public String getSex() {
		return sex == null ? CommonUtil.BLANK : sex.toString();
	}
	public Integer getAge() {
		if (birthdate == null) return -1;
		Calendar now = Calendar.getInstance();
		Calendar birth = Calendar.getInstance();
		birth.setTime(birthdate);
		int age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
		if ((now.get(Calendar.MONTH) < birth.get(Calendar.MONTH)) || 
			(now.get(Calendar.MONTH) == birth.get(Calendar.MONTH) && now.get(Calendar.DATE) < birth.get(Calendar.DATE))) {
			age--;
		}
		return age;
	}
	public Set<String> getLanguages() {
		return languages;
	}
	public Map<String, Integer> getSkills() {
		return skills;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	public void addLanguage(String...language) {
		this.languages.addAll(Arrays.asList(language));
	}
	public void addSkill(String skill, Integer rate) {
		this.skills.put(skill, rate);
	}
	public String print() {
		return String.join(CommonUtil.DELIM, getBirthdate(), getAge().toString(), getSex(), getName());
	}
	public String printAbility() {
		String languages = String.join(CommonUtil.NAMEDELIM, getLanguages());
		String skills = getSkills().entrySet().stream().map(x -> x.getKey()+"("+x.getValue()+")").collect(Collectors.joining(CommonUtil.NAMEDELIM)); 
		return String.join(CommonUtil.DELIM, print(), languages, skills);
	}
}
