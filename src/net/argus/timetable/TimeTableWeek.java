package net.argus.timetable;

public class TimeTableWeek {
	
	private int id;
	private String libelle;
	
	public TimeTableWeek(int id, String libelle) {
		this.id = id;
		this.libelle = libelle;
	}
	
	public static TimeTableWeek createWeek(String entry) {
		String libelle = entry.substring(0, entry.lastIndexOf('@'));
		
		int id = Integer.valueOf(entry.substring(entry.lastIndexOf('@') + 1));
		
		return new TimeTableWeek(id, libelle);
	}
	
	public String getLibelle() {
		return libelle;
	}
	
	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return libelle + "@" + id;
	}

}
