package ena;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Screen {

	private JFrame frame;
	private final JPanel panelScreenTV = new JPanel();
	private JTable tableScreenChannellist;
	private JPanel panelScreenPiP;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Screen window = new Screen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Screen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setResizable(false);
		frame.setBounds(450, 50, 1286, 746);
		frame.getContentPane().setLayout(null);
		panelScreenTV.setBounds(0, 0, 1280, 720);
		frame.getContentPane().add(panelScreenTV);
		panelScreenTV.setLayout(null);

		panelScreenPiP = new JPanel();
		panelScreenPiP.setVisible(false);
		panelScreenPiP.setBounds(886, 11, 384, 216);
		panelScreenTV.add(panelScreenPiP);

		JPanel panelScreenChannelinfo = new JPanel();
		panelScreenChannelinfo.setVisible(false);
		panelScreenChannelinfo.setBounds(256, 720, 768, 128);
		panelScreenTV.add(panelScreenChannelinfo);

		JScrollPane scrollPaneScreenChannellist = new JScrollPane();
		scrollPaneScreenChannellist.setVisible(false);
		scrollPaneScreenChannellist.setBounds(-256, 0, 256, 720);
		panelScreenTV.add(scrollPaneScreenChannellist);

		tableScreenChannellist = new JTable();
		scrollPaneScreenChannellist.setViewportView(tableScreenChannellist);
	}

	public JPanel getScreen() {
		return panelScreenTV;
	}

	public JPanel getPiPScreen() {
		return panelScreenPiP;
	}
}
