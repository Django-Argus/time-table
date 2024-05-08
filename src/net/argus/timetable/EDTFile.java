package net.argus.timetable;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.argus.file.CardinalFile;
import net.argus.file.Filter;

public class EDTFile extends CardinalFile {

	public static final Filter EDT_FILTER = new Filter("edt", "EDT file");
	
	public EDTFile(File file) {
		super(file);
	}
	
	public TimeTable readTimeTable() {
		List<TimeTableEvent> events = new ArrayList<TimeTableEvent>();
		List<TimeTableWeek> weeks = new ArrayList<TimeTableWeek>();
		Date weekStarting = null;
		
		List<String> lines = toList();
		
		boolean header = true;
		boolean setWeek = false;
		
		for(String line : lines) {
			if(header) {
				if(line.isEmpty()) {
					header = false;
					setWeek = true;
					continue;
				}
				
				weeks.add(TimeTableWeek.createWeek(line));
				continue;
			}
			
			if(setWeek) {
				if(!line.isEmpty()) {
					try {weekStarting = new SimpleDateFormat("dd/MM/yyyy").parse(line);}
					catch(ParseException e) {e.printStackTrace();}
					
					setWeek = false;
				}
				
				continue;
			}
			
			if(line.isEmpty())
				continue;
			TimeTableEvent e = TimeTableEvent.createEvent(line);
			if(e != null)
				events.add(e);
		}
		
		return new TimeTable(events, weeks, weekStarting);
	}

}
