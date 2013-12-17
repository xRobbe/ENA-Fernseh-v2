package ena;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JProgressBar;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RemoteControl {

	private JFrame frmRemotecontrol;
	private JPanel panelRemoteSettings;
	private JPanel panelRemoteControl;

	private ScreenTest screen;
	private TvElectronics electronics;
	private PersistentConfig config;
	private PersistentChannel channel;
	private JButton btnRemoteControlPiPActivate;
	private JToggleButton tglbtnRemoteControlPiPSwitch;
	private JButton btnRemoteControlTimeshiftStop;
	private JButton btnRemoteControlTimeshiftStart;
	private JButton btnRemoteControlTimeshiftFastforward;
	private JComboBox comboBoxSettingsUsermode;
	private JComboBox comboBoxSettingsAspectratio;
	private JTable tableRemoteControlChannellist;
	private JButton btnRemoteControlVolumeMute;
	private JButton btnRemoteControlVolumeDown;
	private JSlider sliderRemoteControlVolume;
	private JButton btnRemoteControlVolumeUp;

	private int timer;
	private Thread showInfo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RemoteControl window = new RemoteControl();
					window.frmRemotecontrol.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RemoteControl() {
		screen = new ScreenTest(this);
		electronics = new TvElectronics(screen, this);
		config = new PersistentConfig();
		channel = new PersistentChannel(electronics);
		initialize();
		updateUsermodeLayout();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		// *******************************************************************
		// Frame
		// *******************************************************************

		frmRemotecontrol = new JFrame();
		frmRemotecontrol.setTitle("RemoteControl");
		frmRemotecontrol.setResizable(false);
		frmRemotecontrol.setBounds(20, 50, 366, 666);
		frmRemotecontrol.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRemotecontrol.getContentPane().setLayout(null);

		// *******************************************************************
		// Control
		// *******************************************************************

		panelRemoteControl = new JPanel();
		panelRemoteControl.setBounds(0, 0, 360, 640);
		frmRemotecontrol.getContentPane().add(panelRemoteControl);
		panelRemoteControl.setLayout(null);

		JButton btnRemoteControlSettings = new JButton("Settings");
		btnRemoteControlSettings.setToolTipText("Settings");
		btnRemoteControlSettings.setBounds(10, 11, 77, 77);
		panelRemoteControl.add(btnRemoteControlSettings);

		btnRemoteControlPiPActivate = new JButton("PiP");
		btnRemoteControlPiPActivate.setEnabled(false);
		btnRemoteControlPiPActivate.setToolTipText("Picture in Picture");
		btnRemoteControlPiPActivate.setBounds(97, 11, 77, 77);
		panelRemoteControl.add(btnRemoteControlPiPActivate);

		tglbtnRemoteControlPiPSwitch = new JToggleButton("PiP Switch");
		tglbtnRemoteControlPiPSwitch.setEnabled(false);
		tglbtnRemoteControlPiPSwitch.setToolTipText("Switch control between PiP and Screen");
		tglbtnRemoteControlPiPSwitch.setBounds(184, 11, 77, 77);
		panelRemoteControl.add(tglbtnRemoteControlPiPSwitch);

		JButton btnRemoteControlPower = new JButton("On/Off");
		btnRemoteControlPower.setToolTipText("Turn screen on/off");
		btnRemoteControlPower.setBounds(273, 11, 77, 77);
		panelRemoteControl.add(btnRemoteControlPower);

		JScrollPane scrollPaneRemoteControlChannellist = new JScrollPane();
		scrollPaneRemoteControlChannellist.setBounds(10, 98, 340, 409);
		panelRemoteControl.add(scrollPaneRemoteControlChannellist);

		tableRemoteControlChannellist = new JTable();
		scrollPaneRemoteControlChannellist.setViewportView(tableRemoteControlChannellist);
		tableRemoteControlChannellist.setModel(new ChannelTableModelList(channel.getChannelList()));
		formatTable();

		btnRemoteControlVolumeMute = new JButton("Mute");
		btnRemoteControlVolumeMute.setEnabled(false);
		btnRemoteControlVolumeMute.setToolTipText("Mute Volume");
		btnRemoteControlVolumeMute.setBounds(10, 518, 25, 25);
		panelRemoteControl.add(btnRemoteControlVolumeMute);

		btnRemoteControlVolumeDown = new JButton("V-");
		btnRemoteControlVolumeDown.setEnabled(false);
		btnRemoteControlVolumeDown.setToolTipText("Decrease Volume");
		btnRemoteControlVolumeDown.setBounds(45, 518, 25, 25);
		panelRemoteControl.add(btnRemoteControlVolumeDown);

		sliderRemoteControlVolume = new JSlider();
		sliderRemoteControlVolume.setEnabled(false);
		sliderRemoteControlVolume.setBounds(80, 518, 235, 23);
		panelRemoteControl.add(sliderRemoteControlVolume);

		btnRemoteControlVolumeUp = new JButton("V+");
		btnRemoteControlVolumeUp.setEnabled(false);
		btnRemoteControlVolumeUp.setToolTipText("Increase Volume");
		btnRemoteControlVolumeUp.setBounds(325, 518, 25, 25);
		panelRemoteControl.add(btnRemoteControlVolumeUp);

		btnRemoteControlTimeshiftStop = new JButton("TS Stop");
		btnRemoteControlTimeshiftStop.setEnabled(false);
		btnRemoteControlTimeshiftStop.setToolTipText("Stop TimeShift");
		btnRemoteControlTimeshiftStop.setBounds(10, 552, 77, 77);
		panelRemoteControl.add(btnRemoteControlTimeshiftStop);

		btnRemoteControlTimeshiftStart = new JButton("TS Start");
		btnRemoteControlTimeshiftStart.setEnabled(false);
		btnRemoteControlTimeshiftStart.setToolTipText("Start/Pause TimeShift");
		btnRemoteControlTimeshiftStart.setBounds(146, 552, 77, 77);
		panelRemoteControl.add(btnRemoteControlTimeshiftStart);

		btnRemoteControlTimeshiftFastforward = new JButton("TS FF");
		btnRemoteControlTimeshiftFastforward.setEnabled(false);
		btnRemoteControlTimeshiftFastforward.setToolTipText("Fastforward TimeShift");
		btnRemoteControlTimeshiftFastforward.setBounds(273, 552, 77, 77);
		panelRemoteControl.add(btnRemoteControlTimeshiftFastforward);

		// *******************************************************************
		// Control Handler
		// *******************************************************************

		btnRemoteControlSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				config.reload();
				comboBoxSettingsUsermode.setSelectedIndex(config.getUsermode());
				comboBoxSettingsAspectratio.setSelectedIndex(config.getRatio());
				panelRemoteControl.setVisible(false);
				panelRemoteSettings.setVisible(true);
			}
		});

		btnRemoteControlPiPActivate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (screen.pipIsVisible()) {
					electronics.setPictureInPicture(false);
					tglbtnRemoteControlPiPSwitch.setEnabled(false);
					tglbtnRemoteControlPiPSwitch.setSelected(false);
				} else {
					electronics.setPictureInPicture(true);
					tglbtnRemoteControlPiPSwitch.setEnabled(true);
					try {
						electronics.setChannel(channel.getChannelList().get(config.getProgramm()).getChannel(),
								true,
								channel.getChannelList().get(config.getProgramm()).getChannelPicturePath());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		btnRemoteControlPower.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (screen.isVisible()) {
					screen.setVisible(false);
					screenON(false);
				} else {
					try {
						electronics.setChannel(channel.getChannelList().get(config.getProgramm()).getChannel(),
								false,
								channel.getChannelList().get(config.getProgramm()).getChannelPicturePath());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					screen.setVisible(true);
					screenON(true);
				}

			}
		});

		tableRemoteControlChannellist.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2 && screen.isVisible()) {
					try {
						electronics.setChannel(channel.getChannelList().get(tableRemoteControlChannellist.getSelectedRow()).getChannel(),
								tglbtnRemoteControlPiPSwitch.isSelected(),
								channel.getChannelList().get(tableRemoteControlChannellist.getSelectedRow()).getChannelPicturePath());
						showInfo();
						if (config.getRatio() >= 1) {
							electronics.setZoom(true);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		btnRemoteControlVolumeMute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					sliderRemoteControlVolume.setValue(0);
					electronics.setVolume(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		btnRemoteControlVolumeDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					if (sliderRemoteControlVolume.getValue() - 1 >= 0) {
						sliderRemoteControlVolume.setValue(sliderRemoteControlVolume.getValue() - 1);
						electronics.setVolume(sliderRemoteControlVolume.getValue() - 1);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		sliderRemoteControlVolume.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				try {
					electronics.setVolume(sliderRemoteControlVolume.getValue());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		btnRemoteControlVolumeUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					if (sliderRemoteControlVolume.getValue() + 1 <= 100) {
						sliderRemoteControlVolume.setValue(sliderRemoteControlVolume.getValue() + 1);
						electronics.setVolume(sliderRemoteControlVolume.getValue() + 1);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		btnRemoteControlTimeshiftStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					electronics.recordTimeShift(false);
					electronics.playTimeShift(false, 0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		btnRemoteControlTimeshiftStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					if (!electronics.isRecording()) {
						electronics.recordTimeShift(true);
						electronics.playTimeShift(false, 0);
					} else {
						if (electronics.isPlaying()) {
							electronics.playTimeShift(false, screen.getOffset());
						} else {
							electronics.playTimeShift(true, screen.getOffset());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		btnRemoteControlTimeshiftFastforward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.forwardTimeShift(true, screen.getOffset());
			}
		});

		// *******************************************************************
		// Settings
		// *******************************************************************

		panelRemoteSettings = new JPanel();
		panelRemoteSettings.setVisible(false);
		panelRemoteSettings.setBounds(0, 0, 360, 638);
		frmRemotecontrol.getContentPane().add(panelRemoteSettings);
		panelRemoteSettings.setLayout(null);

		JLabel lblSettingsUsermode = new JLabel("Usermode");
		lblSettingsUsermode.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSettingsUsermode.setBounds(10, 40, 130, 20);
		panelRemoteSettings.add(lblSettingsUsermode);

		comboBoxSettingsUsermode = new JComboBox();
		comboBoxSettingsUsermode.setName("usermode");
		comboBoxSettingsUsermode.setFont(new Font("Tahoma", Font.BOLD, 16));
		comboBoxSettingsUsermode.setModel(new DefaultComboBoxModel(new String[] { "Easy", "Normal", "Expert" }));
		comboBoxSettingsUsermode.setBounds(150, 40, 200, 20);
		panelRemoteSettings.add(comboBoxSettingsUsermode);

		JLabel lblAspectRatio = new JLabel("Aspect Ratio");
		lblAspectRatio.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblAspectRatio.setBounds(10, 80, 130, 20);
		panelRemoteSettings.add(lblAspectRatio);

		comboBoxSettingsAspectratio = new JComboBox();
		comboBoxSettingsAspectratio.setName("aspectratio");
		comboBoxSettingsAspectratio.setModel(new DefaultComboBoxModel(new String[] { "16:9     Widescreen", "4:3       Normal", "2.35:1  Cinemascope" }));
		comboBoxSettingsAspectratio.setFont(new Font("Tahoma", Font.BOLD, 16));
		comboBoxSettingsAspectratio.setBounds(150, 80, 200, 20);
		panelRemoteSettings.add(comboBoxSettingsAspectratio);

		final JButton btnSettingsChannelscan = new JButton("Channelscan");
		btnSettingsChannelscan.setBounds(115, 186, 130, 25);
		panelRemoteSettings.add(btnSettingsChannelscan);

		final JProgressBar progressBarSettingsChannelscan = new JProgressBar();
		progressBarSettingsChannelscan.setBounds(10, 246, 340, 25);
		panelRemoteSettings.add(progressBarSettingsChannelscan);

		final JButton btnSettingsCancel = new JButton("Cancel");
		btnSettingsCancel.setBounds(10, 577, 130, 50);
		panelRemoteSettings.add(btnSettingsCancel);

		final JButton btnSettingsSave = new JButton("Save");
		btnSettingsSave.setBounds(220, 577, 130, 50);
		panelRemoteSettings.add(btnSettingsSave);

		// *******************************************************************
		// Settings Handler
		// *******************************************************************

		// unable to cancel, change is active without save
		btnSettingsChannelscan.addActionListener(new RunnableActionListener() {
			public void run() {
				btnSettingsSave.setEnabled(false);
				btnSettingsChannelscan.setEnabled(false);
				btnSettingsCancel.setEnabled(false);
				progressBarSettingsChannelscan.setIndeterminate(true);
				channel.scanChannel();
				tableRemoteControlChannellist.setModel(new ChannelTableModelList(channel.getChannelList()));
				btnSettingsSave.setEnabled(true);
				btnSettingsChannelscan.setEnabled(true);
				btnSettingsCancel.setEnabled(true);
				progressBarSettingsChannelscan.setIndeterminate(false);
				tableRemoteControlChannellist.setRowSelectionInterval(0, 0);
				try {
					config.setProgramm(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		btnSettingsCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelRemoteSettings.setVisible(false);
				panelRemoteControl.setVisible(true);
			}
		});

		btnSettingsSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveSettings();
				updateUsermodeLayout();
				if (config.getRatio() >= 1)
					electronics.setZoom(true);
				else
					electronics.setZoom(false);
				panelRemoteSettings.setVisible(false);
				panelRemoteControl.setVisible(true);
			}
		});
	}

	// *******************************************************************
	// Functions
	// *******************************************************************

	private void saveSettings() {
		try {
			config.setUsermode(comboBoxSettingsUsermode.getSelectedIndex());
			config.setRatio(comboBoxSettingsAspectratio.getSelectedIndex());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateUsermodeLayout() {
		btnRemoteControlPiPActivate.setVisible(false);
		tglbtnRemoteControlPiPSwitch.setVisible(false);
		btnRemoteControlTimeshiftStop.setVisible(false);
		btnRemoteControlTimeshiftStart.setVisible(false);
		btnRemoteControlTimeshiftFastforward.setVisible(false);

		if (config.getUsermode() >= 1) {
			btnRemoteControlTimeshiftStop.setVisible(true);
			btnRemoteControlTimeshiftStart.setVisible(true);
			btnRemoteControlTimeshiftFastforward.setVisible(true);
		}
		if (config.getUsermode() >= 2) {
			btnRemoteControlPiPActivate.setVisible(true);
			tglbtnRemoteControlPiPSwitch.setVisible(true);
		}
	}

	private void screenON(boolean on) {
		btnRemoteControlPiPActivate.setEnabled(on);
		btnRemoteControlVolumeMute.setEnabled(on);
		btnRemoteControlVolumeDown.setEnabled(on);
		sliderRemoteControlVolume.setEnabled(on);
		btnRemoteControlVolumeUp.setEnabled(on);
		btnRemoteControlTimeshiftStop.setEnabled(on);
		btnRemoteControlTimeshiftStart.setEnabled(on);
		btnRemoteControlTimeshiftFastforward.setEnabled(on);
		tableRemoteControlChannellist.setEnabled(on);
		try {
			if (electronics.isRecording())
				electronics.recordTimeShift(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void formatTable() {
		tableRemoteControlChannellist.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tableRemoteControlChannellist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableRemoteControlChannellist.setRowSelectionInterval(0, config.getProgramm());
		tableRemoteControlChannellist.getColumnModel().getColumn(0).setPreferredWidth(70);
		tableRemoteControlChannellist.setRowHeight(31);
		tableRemoteControlChannellist.getColumnModel().getColumn(1).setPreferredWidth(250);
	}

	public PersistentConfig getConfig() {
		return config;
	}

	public TvElectronics getElectronics() {
		return electronics;
	}

	public PersistentChannel getChannel() {
		return channel;
	}

	public int getSelectedChannel() {
		return tableRemoteControlChannellist.getSelectedRow();
	}

	private void showInfo() {
		timer = 1200;
		if (showInfo != null)
			if (showInfo.isAlive())
				showInfo.stop();
		showInfo = new Thread(new Runnable() {
			public void run() {
				screen.showChannelInfo(channel.getChannelList().get(config.getProgramm()).getChannelName(), true);
				while (timer > 0) {
					timer -= 200;
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				screen.showChannelInfo(channel.getChannelList().get(config.getProgramm()).getChannelName(), false);
			}
		});
		showInfo.start();

	}
}
