package net.argus.timetable.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import net.argus.timetable.TimeTable;
import net.argus.timetable.TimeTableEvent;

public class GUI {
		
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss  EEEE dd MMM");
	public static final SimpleDateFormat RANGE_DATE_FORMAT = new SimpleDateFormat("HH:mm");
	
	public static final Font TEXT_FONT = new Font("Roboto", 0, 50);
	public static final Font MEDIUM_TEXT_FONT = new Font("Roboto", 0, 75);

	public static final Font BIG_TEXT_FONT = new Font("Roboto", 0, 150);

	public static final TimeTableFrame FRAME = new TimeTableFrame();
	
	public static final JPanel MAIN_PANEL = new JPanel();
	public static final JPanel MAIN_TOP_PANEL = new JPanel();
	public static final JPanel MAIN_CENTER_PANEL = new JPanel();
	public static final JPanel MAIN_RIGHT_PANEL = new JPanel();
	
	public static final JPanel MAIN_CENTER_TOP_PANEL = new JPanel();
	public static final JPanel MAIN_CENTER_CENTER_PANEL = new JPanel();
	public static final JPanel MAIN_CENTER_CENTER_CENTER_PANEL = new JPanel();
	public static final JPanel MAIN_CENTER_CENTER_BOTTOM_PANEL = new JPanel();
	public static final JPanel MAIN_CENTER_BOTTOM_PANEL = new JPanel();
	
	public static final JLabel TOP_DATE_LABEL = new JLabel();
	public static final JLabel EVENT_NAME_LABEL = new JLabel();
	public static final JLabel NEXT_EVENT_NAME_LABEL = new JLabel();
	public static final JLabel TODAY_NEXT_EVENT_NAME_LABEL = new JLabel();
	public static final JLabel COUNTDOWN_LABEL = new JLabel();
	public static final JLabel EVENT_RANGE_LABEL = new JLabel();

	
	public static final JProgressBar EVENT_PROGRESS_BAR = new JProgressBar();
	
	public static void init() {		
		MAIN_PANEL.setLayout(new BorderLayout());
		MAIN_CENTER_PANEL.setLayout(new BorderLayout());
		MAIN_CENTER_CENTER_PANEL.setLayout(new BorderLayout());
		
		MAIN_RIGHT_PANEL.setLayout(new BoxLayout(MAIN_RIGHT_PANEL, BoxLayout.Y_AXIS));
		
		TOP_DATE_LABEL.setFont(TEXT_FONT);
		EVENT_NAME_LABEL.setFont(MEDIUM_TEXT_FONT);
		NEXT_EVENT_NAME_LABEL.setFont(TEXT_FONT);
		TODAY_NEXT_EVENT_NAME_LABEL.setFont(TEXT_FONT);
		COUNTDOWN_LABEL.setFont(BIG_TEXT_FONT);
		EVENT_RANGE_LABEL.setFont(TEXT_FONT);
		
		EVENT_PROGRESS_BAR.setPreferredSize(new Dimension(500, 50));
		
		
		NEXT_EVENT_NAME_LABEL.setText(" Next Event: ");
		NEXT_EVENT_NAME_LABEL.setHorizontalAlignment(0);

		
		COUNTDOWN_LABEL.setVerticalTextPosition(SwingConstants.CENTER);
		
		MAIN_TOP_PANEL.add(TOP_DATE_LABEL);
		MAIN_CENTER_TOP_PANEL.add(EVENT_NAME_LABEL);
		MAIN_CENTER_CENTER_CENTER_PANEL.add(COUNTDOWN_LABEL);
		MAIN_CENTER_CENTER_BOTTOM_PANEL.add(EVENT_PROGRESS_BAR);
		
		MAIN_RIGHT_PANEL.add(NEXT_EVENT_NAME_LABEL);
		MAIN_RIGHT_PANEL.add(TODAY_NEXT_EVENT_NAME_LABEL);
		
		MAIN_CENTER_BOTTOM_PANEL.add(EVENT_RANGE_LABEL);
		
		MAIN_TOP_PANEL.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		MAIN_RIGHT_PANEL.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		MAIN_CENTER_TOP_PANEL.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		MAIN_CENTER_CENTER_BOTTOM_PANEL.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		MAIN_CENTER_BOTTOM_PANEL.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		
		MAIN_CENTER_CENTER_PANEL.add(BorderLayout.CENTER, MAIN_CENTER_CENTER_CENTER_PANEL);
		MAIN_CENTER_CENTER_PANEL.add(BorderLayout.SOUTH, MAIN_CENTER_CENTER_BOTTOM_PANEL);
		
		MAIN_CENTER_PANEL.add(BorderLayout.NORTH, MAIN_CENTER_TOP_PANEL);
		MAIN_CENTER_PANEL.add(BorderLayout.CENTER, MAIN_CENTER_CENTER_PANEL);
		MAIN_CENTER_PANEL.add(BorderLayout.SOUTH, MAIN_CENTER_BOTTOM_PANEL);

		
		MAIN_PANEL.add(BorderLayout.EAST, MAIN_RIGHT_PANEL);
		MAIN_PANEL.add(BorderLayout.CENTER, MAIN_CENTER_PANEL);
		MAIN_PANEL.add(BorderLayout.NORTH, MAIN_TOP_PANEL);
		
		FRAME.setContentPane(MAIN_PANEL);
	}
	
	public static void updateDate(TimeTable timeTable) {
		Calendar calendar = Calendar.getInstance();
		TOP_DATE_LABEL.setText(DATE_FORMAT.format(calendar.getTime()) + "    " + timeTable.getCurrentWeek().getLibelle());
	}
	
	public static void updateEventName(TimeTable timeTable) {
		EVENT_NAME_LABEL.setText(timeTable.getCurrentEvent().getName());
	}
	
	public static void updateCountDown(TimeTable timeTable) {
		Date now = Date.from(Instant.now());
		long sec = TimeUnit.SECONDS.convert(timeTable.getEndCurrentEvent().getTime() - now.getTime(), TimeUnit.MILLISECONDS);
		long min = sec / 60;
		sec = sec % 60;
		
		String second =  Long.toString(sec);
		String minute =  Long.toString(min);
		
		
		COUNTDOWN_LABEL.setText((minute.length()==1?"0"+minute:minute) + ":" + (second.length()==1?"0"+second:second));
	}
	
	public static void updateProressBar(TimeTable timeTable) {
		if(timeTable.getCurrentEvent().equals(TimeTable.DEFAULT_EVENT))
			EVENT_PROGRESS_BAR.setModel(new DefaultBoundedRangeModel(1, 0, 0, 1));
		else
			EVENT_PROGRESS_BAR.setModel(new DefaultBoundedRangeModel((int) (timeTable.getCurrentDate().getTime() / 1000), 0,
					(int) (timeTable.getStartCurrentEvent().getTime() / 1000),
					(int) (timeTable.getEndCurrentEvent().getTime() / 1000)));
	}
	
	public static void updateNextEvent(TimeTable timeTable) {
		TimeTableEvent next = timeTable.getNextTodayEvent();

		if(next.equals(TimeTable.DEFAULT_EVENT))
			TODAY_NEXT_EVENT_NAME_LABEL.setText(" No more event today ");
		else
			TODAY_NEXT_EVENT_NAME_LABEL.setText(" " + next.getName() + " ");
	}
	
	public static void updateRangeEvent(TimeTable timeTable) {				
		if(timeTable.getCurrentEvent().equals(TimeTable.DEFAULT_EVENT))
			EVENT_RANGE_LABEL.setText("");
		else
			EVENT_RANGE_LABEL.setText(RANGE_DATE_FORMAT.format(timeTable.getStartCurrentEvent()) + " --> " + RANGE_DATE_FORMAT.format(timeTable.getEndCurrentEvent()));
	}
		
	public static void update(TimeTable timeTable) {
		updateDate(timeTable);
		updateEventName(timeTable);
		updateCountDown(timeTable);
		updateProressBar(timeTable);
		updateNextEvent(timeTable);
		updateRangeEvent(timeTable);
		
		FRAME.repaint();
	}
	
}
