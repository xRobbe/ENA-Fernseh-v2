package ena;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class RunnableActionListener implements ActionListener,Runnable {
	private Thread thread;
	
	public void actionPerformed(ActionEvent arg0) {
		thread = new Thread(this);
		thread.start();		
	}

	public abstract void run(); 
	
	public Thread getThread() {
		return thread;
	}

}
