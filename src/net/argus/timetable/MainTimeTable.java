package net.argus.timetable;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.argus.instance.CardinalProgram;
import net.argus.instance.Program;
import net.argus.timetable.ui.GUI;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

@Program(instanceName = "timetable-main")
public class MainTimeTable extends CardinalProgram {
	
	private ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(1);
	
	private TimeTable timeTable;
	
	@Override
	public void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {e.printStackTrace();}
		
		EDTFile file = new EDTFile(new File("./timetable.edt"));
		timeTable = file.readTimeTable();
		
		GUI.init();
		
		GUI.FRAME.setVisible(true);
		
		stpe.scheduleAtFixedRate(getUpdate(), 0, 500, TimeUnit.MILLISECONDS);
	}
	
	private Runnable getUpdate() {
		return () -> {
			try {
				SwingUtilities.invokeAndWait(() -> {
					GUI.update(timeTable);
				});
			} catch (InvocationTargetException | InterruptedException e) {Debug.log("Update error", Info.ERROR); e.printStackTrace();}
		};
	}
	
	

}
