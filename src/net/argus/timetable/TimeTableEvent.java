package net.argus.timetable;

public class TimeTableEvent {
	
	public static final int MONDAY = 0;
	public static final int TUESDAY = 1;
	public static final int WEDNESDAY = 2;
	public static final int THURSDAY = 3;
	public static final int FRIDAY = 4;
	public static final int SATURDAY = 5;
	public static final int SUNDAY = 6;
	
	private int hour, minute, duration, day, week;

	private String name;
	
	public TimeTableEvent(String name, int hour, int minute, int duration, int day, int week) {
		this.name = name;
		this.hour = hour;
		this.minute = minute;
		this.duration = duration;
		this.day = day;
		this.week = week;
	}
	
	public static TimeTableEvent createEvent(String entry) {
		if(entry.indexOf('@') == -1 || entry.indexOf('?') == -1 || entry.indexOf(';') == -1 || entry.indexOf('+') == -1 || entry.indexOf(':') == -1)
			return null;
		
		String name = entry.substring(0, entry.lastIndexOf('@'));
		
		int week = Integer.valueOf(entry.substring(entry.lastIndexOf('?') + 1));
		int day = Integer.valueOf(entry.substring(entry.lastIndexOf(';') + 1, entry.lastIndexOf('?')));
		int duration = Integer.valueOf(entry.substring(entry.lastIndexOf('+') + 1, entry.lastIndexOf(';')));
		int minute = Integer.valueOf(entry.substring(entry.lastIndexOf(':') + 1, entry.lastIndexOf('+')));
		int hour = Integer.valueOf(entry.substring(entry.lastIndexOf('@') + 1, entry.lastIndexOf(':')));
		
		return new TimeTableEvent(name, hour, minute, duration, day, week);
	}
	
	public int getDuration() {
		return duration;
	}
	
	public int getWeek() {
		return week;
	}
	
	public int getDay() {
		return day;
	}
	
	public int getHour() {
		return hour;
	}
	
	public int getMinute() {
		return minute;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name + "@" + hour + ":" + minute + "+" + duration + ";" + day + "?" + week;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof TimeTableEvent) {
			TimeTableEvent e = (TimeTableEvent) obj;
			return e.name.equals(name) && e.hour == hour && e.minute == minute && e.duration == duration && e.day == day && e.week == week;
		}
		
		return false;
	}

}
