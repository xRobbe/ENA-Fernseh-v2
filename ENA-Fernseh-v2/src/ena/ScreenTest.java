package ena;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.CardLayout;

import javax.swing.SwingConstants;
import javax.swing.JProgressBar;

public class ScreenTest {
	private JFrame frame;
	private final JPanel panelScreenTV = new JPanel();
	private JPanel panelScreenPiP = new JPanel();

	private JLabel lblScreenTvPicture;
	private JLabel lblScreenPiPPicture;

	private RemoteControl remote;
	private JPanel panelScreenChannelinfo;
	private JLabel lblScreenChannelInfoName;
	private JProgressBar progressBarScreenTimeshift;

	private boolean isRecording = false;
	private boolean isPlaying = false;
	private Thread recordingThread;
	private Thread playingThread;
	private int timeshift;

	private int tvHeight;
	private int tvWidth;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ScreenTest window = new ScreenTest();
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
	public ScreenTest(/* RemoteControl remote */) {
		// this.remote = remote;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(450, 50, 1286, 746);
		frame.getContentPane().setLayout(new GridBagLayout());
		frame.getContentPane().setBackground(Color.RED);
		
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.2, 0.4921875, .1078125, 0.1921875, 0.0078125, Double.MIN_VALUE };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowWeights = new double[] { 0.0138, 0.3, 0.5083, 0.177, Double.MIN_VALUE };

		

		panelScreenTV.setBackground(Color.BLACK);
		panelScreenTV.setBounds(0, 0, 1280, 720);
		panelScreenTV.setLayout(gridBagLayout);
		frame.getContentPane().add(panelScreenTV);

		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 5;
		c.gridheight = 4;
		frame.getContentPane().add(panelScreenTV, c);

		lblScreenTvPicture = new JLabel("");
		lblScreenTvPicture.setIcon(new ImageIcon("src/screens/zdf"));
		panelScreenTV.add(lblScreenTvPicture, c);

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.3;
		c.weighty = 0.3;
		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		panelScreenTV.add(panelScreenPiP, c);

		new Thread(new Runnable() {
			public void run() {
				try {
					while (true) {
						if (tvHeight != frame.getHeight() || tvWidth != frame.getWidth()) {
							BufferedImage img = resize(ImageIO.read(new File("src/screens/zdf")), panelScreenTV.getWidth(), panelScreenTV.getHeight());
							panelScreenTV.remove(lblScreenTvPicture);
							lblScreenTvPicture = new JLabel(new ImageIcon(img));
							GridBagConstraints c = new GridBagConstraints();

							c.fill = GridBagConstraints.BOTH;
							c.weightx = 1.0;
							c.weighty = 1.0;
							c.gridx = 0;
							c.gridy = 0;
							c.gridwidth = 5;
							c.gridheight = 4;

							panelScreenTV.add(lblScreenTvPicture,c);
							panelScreenTV.revalidate();
							tvHeight = frame.getHeight();
							tvWidth = frame.getWidth();
						}
						Thread.sleep(300);

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public JPanel getScreen() {
		return panelScreenTV;
	}

	public JPanel getPiPScreen() {
		return panelScreenPiP;
	}

	public boolean isVisible() {
		return frame.isVisible();
	}

	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}

	public boolean pipIsVisible() {
		return panelScreenPiP.isVisible();
	}

	public String getScreenImagePath() {
		return remote.getChannel().getChannelList().get(0).getChannelPicturePath();

	}

	public BufferedImage getScreenImage() {
		Icon icon = lblScreenTvPicture.getIcon();
		BufferedImage img = new BufferedImage(icon.getIconWidth(),
				icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics graphic = img.createGraphics();
		icon.paintIcon(null, graphic, 0, 0);
		graphic.dispose();
		return img;
	}

	public BufferedImage resize(BufferedImage img, int newWidth, int newHeight) {
		int width = img.getWidth();
		int height = img.getHeight();
		BufferedImage newImg = new BufferedImage(newWidth, newHeight, img.getType());
		Graphics2D graphic = newImg.createGraphics();
		graphic.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphic.drawImage(img, 0, 0, newWidth, newHeight, 0, 0, width, height, null);
		graphic.dispose();
		return newImg;
	}

	public void setChannelPicture(String picturePath, boolean pip) {
		try {
			BufferedImage img = ImageIO.read(new File(picturePath));
			if (pip) {
				img = resize(img, 384, 216);
				panelScreenPiP.remove(lblScreenPiPPicture);
				lblScreenPiPPicture = new JLabel(new ImageIcon(img));
				lblScreenPiPPicture.setBounds(0, 0, 384, 216);
				panelScreenPiP.add(lblScreenPiPPicture);
				panelScreenPiP.repaint();
			}
			else {
				panelScreenTV.remove(lblScreenTvPicture);
				lblScreenTvPicture = new JLabel(new ImageIcon(img));
				lblScreenTvPicture.setBounds(0, 0, 1280, 720);
				panelScreenTV.add(lblScreenTvPicture);
				panelScreenTV.repaint();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setChannelPicture(boolean zoom, String picturePath) {
		try {
			if (zoom) {
				BufferedImage img = ImageIO.read(new File(picturePath));
				img = resize(img, (int) (img.getWidth() * 1.333333), (int) (img.getHeight() * 1.333333));
				panelScreenTV.remove(lblScreenTvPicture);
				lblScreenTvPicture = new JLabel(new ImageIcon(img));
				lblScreenTvPicture.setBounds(0, 0, 1280, 720);
				panelScreenTV.add(lblScreenTvPicture);
				panelScreenTV.repaint();
			}
			else {
				BufferedImage img = ImageIO.read(new File(picturePath));
				img = resize(img, img.getWidth(), img.getHeight());
				panelScreenTV.remove(lblScreenTvPicture);
				lblScreenTvPicture = new JLabel(new ImageIcon(img));
				lblScreenTvPicture.setBounds(0, 0, 1280, 720);
				panelScreenTV.add(lblScreenTvPicture);
				panelScreenTV.repaint();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setChannelPicture(BufferedImage img) {
		panelScreenTV.remove(lblScreenTvPicture);
		lblScreenTvPicture = new JLabel(new ImageIcon(img));
		lblScreenTvPicture.setBounds(0, 0, 1280, 720);
		panelScreenTV.add(lblScreenTvPicture);
		panelScreenTV.repaint();
	}

	private void movePanelUp(JPanel panel, int max, int min, boolean show) {
		try {
			int time = 300 / Math.abs(max - min);
			if (show) {
				panel.setVisible(true);
				while (panel.getBounds().y > min) {
					panel.setBounds(panel.getBounds().x, panel.getBounds().y - 1, panel.getBounds().width, panel.getBounds().height);
					Thread.sleep(time);
				}
			} else {
				while (panel.getBounds().y < max) {
					panel.setBounds(panel.getBounds().x, panel.getBounds().y + 1, panel.getBounds().width, panel.getBounds().height);
					Thread.sleep(time);
				}
				panel.setVisible(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showChannelInfo(String name, boolean show) {
		lblScreenChannelInfoName.setText(name);
		panelScreenChannelinfo.setVisible(true);
		movePanelUp(panelScreenChannelinfo, panelScreenTV.getHeight(), panelScreenTV.getHeight() - panelScreenChannelinfo.getHeight(), show);
	}

	public void recordTimeShift(boolean recording) {
		this.isRecording = recording;
		progressBarScreenTimeshift.setVisible(isRecording);
		if (recordingThread == null) {
			recordingThread = new Thread(new Runnable() {
				public void run() {
					try {
						while (isRecording) {
							progressBarScreenTimeshift.setMaximum(progressBarScreenTimeshift.getMaximum() + 1);
							Thread.sleep(1000);
						}
						progressBarScreenTimeshift.setMaximum(0);
						progressBarScreenTimeshift.setValue(0);
						recordingThread = null;
						playingThread = null;
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			});
			recordingThread.start();
		} else {
			if (recordingThread.isAlive())
				System.out.println("Recording already started");
		}

	}

	public int getOffset() {
		return progressBarScreenTimeshift.getValue();
	}

	public void playTimeShift(boolean playing, int offset) {
		this.isPlaying = playing;
		this.timeshift = 1;
		if (playingThread == null) {
			playingThread = new Thread(new Runnable() {
				public void run() {
					try {
						while (isRecording) {
							if ((getOffset() + timeshift) > progressBarScreenTimeshift.getMaximum())
								remote.getElectronics().recordTimeShift(false);
							if (isPlaying)
								progressBarScreenTimeshift.setValue(getOffset() + timeshift);
							Thread.sleep(1000);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			});
			playingThread.start();
		}
	}

	public void forwardTimeShift(boolean playing, int offset) {
		this.isPlaying = playing;
		this.timeshift = 5;
	}
}