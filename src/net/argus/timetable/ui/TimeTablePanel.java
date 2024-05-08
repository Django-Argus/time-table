package net.argus.timetable.ui;

import javax.swing.JPanel;

import net.argus.timetable.TimeTable;

public class TimeTablePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5292633914154580001L;
	
	private TimeTable timeTable;
	
	public TimeTablePanel(TimeTable timetable) {
		this.timeTable = timetable;
	}
	
	public TimeTable getTimeTable() {
		return timeTable;
	}

}
