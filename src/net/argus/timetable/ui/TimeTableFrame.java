package net.argus.timetable.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class TimeTableFrame extends JFrame implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6018671858970655619L;
	
	private boolean fullscreen = false;
	
	public TimeTableFrame() {
		super("TimeTable");
		
		setDefaultCloseOperation(3);
		setSize(1200, 800);
		addKeyListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_F11) {
			if(fullscreen) {
				getGraphicsConfiguration().getDevice().setFullScreenWindow(null);
				fullscreen = false;
			}else {
				getGraphicsConfiguration().getDevice().setFullScreenWindow(this);
				fullscreen = true;
			}
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {}

}