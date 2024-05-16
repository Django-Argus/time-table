package net.argus.timetable.ui;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import net.argus.system.OS;

public class TimeTableFrame extends JFrame implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6018671858970655619L;
	
	private boolean fullscreen = false;
	
	public TimeTableFrame() {
		super("TimeTable");
		
		setDefaultCloseOperation(3);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setState(JFrame.MAXIMIZED_BOTH);
		addKeyListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_F11 || (OS.currentOS() == OS.OSX && e.getKeyCode() == KeyEvent.VK_F && e.isControlDown())) {
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
