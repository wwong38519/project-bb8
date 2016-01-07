import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class DateTime {
	
	public static LocalDateTime localDateTime = LocalDateTime.now();
	public static LocalDate fixPointInTime = LocalDate.of(1963, 11, 23);	// You know this is the birthday
	public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
	public static DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_DATE;
	public static Map<String, DateTimeFormatter> formatMap = new TreeMap<String, DateTimeFormatter>();
	static {
		formatMap.put("BASIC_ISO_DATE",	DateTimeFormatter.BASIC_ISO_DATE);
		formatMap.put("ISO_DATE",	DateTimeFormatter.ISO_DATE);
		formatMap.put("ISO_DATE_TIME",	DateTimeFormatter.ISO_DATE_TIME);
		formatMap.put("ISO_INSTANT",	DateTimeFormatter.ISO_INSTANT);
		formatMap.put("ISO_LOCAL_DATE",	DateTimeFormatter.ISO_LOCAL_DATE);
		formatMap.put("ISO_LOCAL_DATE_TIME",	DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		formatMap.put("ISO_LOCAL_TIME",	DateTimeFormatter.ISO_LOCAL_TIME);
		formatMap.put("ISO_OFFSET_DATE",	DateTimeFormatter.ISO_OFFSET_DATE);
		formatMap.put("ISO_OFFSET_DATE_TIME",	DateTimeFormatter.ISO_OFFSET_DATE_TIME);
		formatMap.put("ISO_OFFSET_TIME",	DateTimeFormatter.ISO_OFFSET_TIME);
		formatMap.put("ISO_ORDINAL_DATE",	DateTimeFormatter.ISO_ORDINAL_DATE);
		formatMap.put("ISO_TIME",	DateTimeFormatter.ISO_TIME);
		formatMap.put("ISO_WEEK_DATE",	DateTimeFormatter.ISO_WEEK_DATE);
		formatMap.put("ISO_ZONED_DATE_TIME",	DateTimeFormatter.ISO_ZONED_DATE_TIME);
		formatMap.put("RFC_1123_DATE_TIME",	DateTimeFormatter.RFC_1123_DATE_TIME);
	}
	
	public static void main(String[] args) {
		System.out.println("This year " + localDateTime.get(ChronoField.YEAR) + " is Leap Year? " + localDateTime.getChronology().isLeapYear(localDateTime.getYear()));
		System.out.println("The birth year " + fixPointInTime.get(ChronoField.YEAR) + " is Leap Year? " + fixPointInTime.getChronology().isLeapYear(fixPointInTime.getYear()));

		System.out.println(String.join(" ", "Right now the date and time is:", localDateTime.format(dateTimeFormatter)));
		//java.time.temporal.UnsupportedTemporalTypeException: Unsupported field: HourOfDay
		//System.out.println(String.join(" ", "Birthday on:", birthday.format(dateTimeFormatter)));
		System.out.println(String.join(" ", "Birthday on:", fixPointInTime.format(dateFormatter)));

		// Date Diff : Period : (LocalDate)
		Period period = Period.between(fixPointInTime, localDateTime.toLocalDate());
		displayPeriod(period);
		
		// Date Diff : Duration : (LocalDateTime)
		//java.time.temporal.UnsupportedTemporalTypeException: Unsupported unit: Seconds
		//Duration duration = Duration.between(birthday, localDateTime);
		Duration duration = Duration.between(fixPointInTime.atStartOfDay(), localDateTime);
		displayDuration(duration);
		
		timeTravelling();
		
		displayFormat(localDateTime);
		displayFormat(localDateTime.toLocalDate());
		displayFormat(localDateTime.toLocalTime());
	}
	
	// TODO_checked: Date Time Format
	public static void displayFormat(Temporal temporal) {
		System.out.println("----- " + temporal.getClass().getName() + " -----");
		System.out.println(temporal.getClass().getName());
		for (Entry<String, DateTimeFormatter> entry : formatMap.entrySet()) {
			String name = entry.getKey();
			DateTimeFormatter f = entry.getValue();
			try {
				System.out.println(name + " : " + f.format(temporal));
			} catch (DateTimeException e) {
				System.out.println(name + " not supported");
			}
		}
	}
	
	// TODO_checked: Date Add/Minus
	public static void timeTravelling() {
		LocalDate tardisDate = LocalDate.of(2016, 11, 23);
		LocalDate targetDate = fixPointInTime;

		System.out.println("----- LocalDate Add / Minus -----");
		displayDifference(tardisDate, targetDate);

		System.out.println("----- LocalDate Add 1 Day -----");
		//java.time.temporal.UnsupportedTemporalTypeException: Unsupported unit: Seconds
		//moving = moving.plus(1, ChronoUnit.SECONDS);
		tardisDate = tardisDate.plus(1, ChronoUnit.DAYS);
		displayDifference(tardisDate, targetDate);
		
		System.out.println("----- LocalDate Minus Period -----");
		Period movingPeriod = Period.between(fixPointInTime, tardisDate);
		// You know it when Tardis doesn't like where the Doctor is heading to
		tardisDate = tardisDate.minus(movingPeriod.multipliedBy(2));
		displayDifference(tardisDate, targetDate);

		// The Doctor can be kind of picky sometimes
		System.out.println("----- LocalDateTime Add / Minus -----");
		LocalDateTime tardisDateTime = LocalDate.of(2016, 11, 23).atTime(23, 59, 59);
		LocalDateTime targetDateTime = fixPointInTime.atTime(0, 0);
		displayDifference(tardisDateTime, targetDateTime);
		
		System.out.println("----- LocalDateTime Add 1 Sec -----");
		tardisDateTime = tardisDateTime.plus(1, ChronoUnit.SECONDS);
		displayDifference(tardisDateTime, targetDateTime);

		System.out.println("----- LocalDateTime Minus Duration -----");
		// Let's help the Doctor to get to his destination
		Duration movingDuration = Duration.between(targetDateTime, tardisDateTime);
		tardisDateTime = tardisDateTime.minus(movingDuration);
		displayDifference(tardisDateTime, targetDateTime);

	}
	
	public static void displayDifference(LocalDate currentDate, LocalDate fixPointInTime) {
		// create new instances
		Period movingPeriod = Period.between(fixPointInTime, currentDate);
		Duration movingDuration = Duration.between(fixPointInTime.atTime(0, 0), currentDate.atTime(23, 59));
		System.out.println(String.join(" ", "The Doctor has travelled to :", currentDate.format(dateFormatter)));
		System.out.println(String.join(" ", "Target Point in Time is :", fixPointInTime.format(dateFormatter)));
		displayPeriod(movingPeriod);
		displayDuration(movingDuration);
	}
	public static void displayDifference(LocalDateTime currentDateTime, LocalDateTime fixPointInTime) {
		// create new instances
		Period movingPeriod = Period.between(fixPointInTime.toLocalDate(), currentDateTime.toLocalDate());	// Period: LocalDate
		Duration movingDuration = Duration.between(fixPointInTime, currentDateTime);	// Duration: LocalDateTime
		System.out.println(String.join(" ", "The Doctor has travelled to :", currentDateTime.format(dateFormatter)));
		System.out.println(String.join(" ", "Target Point in Time is :", fixPointInTime.format(dateFormatter)));
		displayPeriod(movingPeriod);
		displayDuration(movingDuration);
	}

	public static void displayPeriod(Period p) {
		System.out.println(p.getYears() + " years " + p.getMonths() + " months " + p.getDays() + " days");
	}
	public static void displayDuration(Duration d) {
		System.out.println(d.toDays() + " days " + d.toHours() + " hours " + d.toMinutes() + " minutes ");
	}

}
