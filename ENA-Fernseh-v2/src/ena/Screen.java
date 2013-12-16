package ena;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

public class Screen {

	private JFrame frame;
	private final JPanel panelScreenTV = new JPanel();
	private JTable tableScreenChannellist;
	private JPanel panelScreenPiP;

	private JLabel lblScreenTvPicture;
	private JLabel lblScreenPiPPicture;

	private RemoteControl remote;

	/**
	 * Create the application.
	 */
	public Screen(RemoteControl remote) {
		this.remote = remote;
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
		panelScreenTV.setBackground(Color.BLACK);
		panelScreenTV.setBounds(0, 0, 1280, 720);
		frame.getContentPane().add(panelScreenTV);
		panelScreenTV.setLayout(null);

		panelScreenPiP = new JPanel();
		panelScreenPiP.setVisible(false);
		panelScreenPiP.setBounds(886, 11, 384, 216);
		panelScreenTV.add(panelScreenPiP);

		lblScreenPiPPicture = new JLabel("");
		panelScreenPiP.add(lblScreenPiPPicture);

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

		lblScreenTvPicture = new JLabel("");
		panelScreenTV.add(lblScreenTvPicture);
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

	public void setChannelPicture(String picturePath, boolean pip, boolean zoom) {
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

	private void movePanelUp(JPanel panel, int max, int min) {
		try {
			int time = 300 / Math.abs(max - min);
			if (panel.getBounds().y == max) {
				panel.setVisible(true);
				while (panel.getBounds().y > min) {
					panel.setBounds(panel.getBounds().x, panel.getBounds().y - 1, panel.getBounds().width, panel.getBounds().height);
					Thread.sleep(time);
				}
			} else if (panel.getBounds().y == min) {
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
}
