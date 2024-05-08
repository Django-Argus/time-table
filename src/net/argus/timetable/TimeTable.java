package net.argus.timetable;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimeTable {
	
	public static final TimeTableEvent DEFAULT_EVENT = new TimeTableEvent("No Event", 0, 0, 0, 0, 0);
	
	private List<TimeTableEvent> events = new ArrayList<TimeTableEvent>();
	private List<TimeTableWeek> weeks = new ArrayList<TimeTableWeek>();
	
	private Date weekStarting;
	
	public TimeTable(List<TimeTableEvent> events, List<TimeTableWeek> weeks, Date weekStarting) {
		this.events = events;
		this.weeks = weeks;
		this.weekStarting = weekStarting;
	}
	
	public TimeTableWeek getCurrentWeek() {
		return getWeekAt(Calendar.getInstance());
	}
	
	public TimeTableWeek getWeekAt(Calendar date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(weekStarting);
		
		int i = 1;

		while(!calendar.after(date)) {
			calendar.add(Calendar.WEEK_OF_YEAR, 1);
			i++;
		}
		
		return weeks.get(i % weeks.size());
	}
	
	public TimeTableEvent getEventAt(Calendar date) {
		TimeTableWeek week = getWeekAt(date);
		
		for(TimeTableEvent event : events) {
			if(event.getWeek() != 0 && event.getWeek() != week.getId())
				continue;
			
			if(date.get(Calendar.DAY_OF_WEEK) - 2 != event.getDay())
				continue;
			
			Calendar temp = Calendar.getInstance();
			temp.set(Calendar.HOUR_OF_DAY, event.getHour());
			temp.set(Calendar.MINUTE, event.getMinute());
			temp.set(Calendar.SECOND, 0);
			
			if(temp.get(Calendar.HOUR_OF_DAY) == date.get(Calendar.HOUR_OF_DAY) && temp.get(Calendar.MINUTE) == date.get(Calendar.MINUTE)) {
				return event;
			}
			
			if(!temp.before(date))
				continue;
			
			temp.add(Calendar.MINUTE, event.getDuration());
			if(!temp.after(date))
				continue;
			
			return event;
		}
		return DEFAULT_EVENT;
	}
	
	public TimeTableEvent getCurrentEvent() {
		return getEventAt(Calendar.getInstance());
	}
	
	public TimeTableEvent getNextTodayEvent() {
		TimeTableWeek week = getCurrentWeek();
		TimeTableEvent e = DEFAULT_EVENT;
		Calendar min = Calendar.getInstance();
		min.set(Calendar.HOUR_OF_DAY, 23);
		min.set(Calendar.MINUTE, 59);
		min.set(Calendar.SECOND, 59);
		
		Calendar date = Calendar.getInstance();
		
		for(TimeTableEvent event : events) {
			if(event.equals(getCurrentEvent()))
				continue;
			
			if(event.getWeek() != 0 && event.getWeek() != week.getId())
				continue;
			
			if(date.get(Calendar.DAY_OF_WEEK) - 2 != event.getDay())
				continue;
			Calendar temp = Calendar.getInstance();
			temp.set(Calendar.HOUR_OF_DAY, event.getHour());
			temp.set(Calendar.MINUTE, event.getMinute());
			temp.set(Calendar.SECOND, 0);
			
			if(temp.before(date))
				continue;
			
			
			if(temp.before(min)) {
				min = temp;
				e = event;
			}
			
		}
		return e;
	}
	
	public Date getStartCurrentEvent() {
		TimeTableEvent event = getCurrentEvent();
		if(event.equals(DEFAULT_EVENT))
			return Date.from(Instant.now());
		
		Calendar temp = Calendar.getInstance();
		temp.set(Calendar.HOUR_OF_DAY, event.getHour());
		temp.set(Calendar.MINUTE, event.getMinute());
		temp.set(Calendar.SECOND, 0);
		
		return temp.getTime();
	}
	
	public Date getEndCurrentEvent() {
		TimeTableEvent event = getCurrentEvent();
		if(event.equals(DEFAULT_EVENT))
			return Date.from(Instant.now());
		
		Calendar temp = Calendar.getInstance();
		temp.set(Calendar.HOUR_OF_DAY, event.getHour());
		temp.set(Calendar.MINUTE, event.getMinute());
		temp.set(Calendar.SECOND, 0);
		temp.add(Calendar.MINUTE, event.getDuration());
		
		return temp.getTime();	
	}
	
	public Date getCurrentDate() {
		return Date.from(Instant.now());
	}
	
	@Override
	public String toString() {
		return weeks + ":" + weekStarting + "->" + events;
	}
	

}
