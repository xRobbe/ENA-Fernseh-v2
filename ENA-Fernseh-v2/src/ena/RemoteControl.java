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

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JProgressBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RemoteControl {

	private JFrame frmRemotecontrol;
	private JTable tableRemoteChannellist;

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
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

//******************************************************************* Frame *******************************************************************
		
		frmRemotecontrol = new JFrame();
		frmRemotecontrol.setTitle("RemoteControl");
		frmRemotecontrol.setResizable(false);
		frmRemotecontrol.setBounds(20, 50, 366, 666);
		frmRemotecontrol.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRemotecontrol.getContentPane().setLayout(null);
		
//******************************************************************* Control *******************************************************************
		
		JPanel panelRemoteControl = new JPanel();
		panelRemoteControl.setBounds(0, 0, 360, 640);
		frmRemotecontrol.getContentPane().add(panelRemoteControl);
		panelRemoteControl.setLayout(null);
		
		JButton button = new JButton("Settings");
		button.setToolTipText("Settings");
		button.setBounds(10, 11, 77, 77);
		panelRemoteControl.add(button);
		
		JButton button_1 = new JButton("PiP");
		button_1.setToolTipText("Picture in Picture");
		button_1.setBounds(97, 11, 77, 77);
		panelRemoteControl.add(button_1);
		
		JToggleButton toggleButton = new JToggleButton("PiP Switch");
		toggleButton.setToolTipText("Switch control between PiP and Screen");
		toggleButton.setBounds(184, 11, 77, 77);
		panelRemoteControl.add(toggleButton);
		
		JButton button_2 = new JButton("On/Off");
		button_2.setToolTipText("Turn screen on/off");
		button_2.setBounds(273, 11, 77, 77);
		panelRemoteControl.add(button_2);
		
		JScrollPane scrollPaneRemoteChannellist = new JScrollPane();
		scrollPaneRemoteChannellist.setBounds(10, 98, 340, 409);
		panelRemoteControl.add(scrollPaneRemoteChannellist);
		
		tableRemoteChannellist = new JTable();
		scrollPaneRemoteChannellist.setViewportView(tableRemoteChannellist);
		
		JButton btnRemoteVolumeMute = new JButton("Mute");
		btnRemoteVolumeMute.setToolTipText("Mute Volume");
		btnRemoteVolumeMute.setBounds(10, 518, 25, 25);
		panelRemoteControl.add(btnRemoteVolumeMute);
		
		JButton btnRemoteVolumeDown = new JButton("V-");
		btnRemoteVolumeDown.setToolTipText("Decrease Volume");
		btnRemoteVolumeDown.setBounds(45, 518, 25, 25);
		panelRemoteControl.add(btnRemoteVolumeDown);
		
		JSlider slider = new JSlider();
		slider.setBounds(80, 518, 235, 23);
		panelRemoteControl.add(slider);
		
		JButton btnRemoteVolumeUp = new JButton("V+");
		btnRemoteVolumeUp.setToolTipText("Increase Volume");
		btnRemoteVolumeUp.setBounds(325, 518, 25, 25);
		panelRemoteControl.add(btnRemoteVolumeUp);
		
		JButton btnRemoteTimeshiftStop = new JButton("TS Stop");
		btnRemoteTimeshiftStop.setToolTipText("Stop TimeShift");
		btnRemoteTimeshiftStop.setBounds(10, 552, 77, 77);
		panelRemoteControl.add(btnRemoteTimeshiftStop);
		
		JButton btnRemoteTimeshiftFastforward = new JButton("TS FF");
		btnRemoteTimeshiftFastforward.setToolTipText("Fastforward TimeShift");
		btnRemoteTimeshiftFastforward.setBounds(273, 552, 77, 77);
		panelRemoteControl.add(btnRemoteTimeshiftFastforward);
		
		JButton btnTsStart = new JButton("TS Start");
		btnTsStart.setToolTipText("Start/Pause TimeShift");
		btnTsStart.setBounds(146, 552, 77, 77);
		panelRemoteControl.add(btnTsStart);
		
//******************************************************************* Settings *******************************************************************
		
		JPanel panelRemoteSettings = new JPanel();
		panelRemoteSettings.setBounds(0, 0, 360, 638);
		frmRemotecontrol.getContentPane().add(panelRemoteSettings);
		panelRemoteSettings.setLayout(null);
		
		JLabel lblSettingsUsermode = new JLabel("Usermode");
		lblSettingsUsermode.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSettingsUsermode.setBounds(10, 40, 130, 20);
		panelRemoteSettings.add(lblSettingsUsermode);
		
		JComboBox comboBoxSettingsUsermode = new JComboBox();
		comboBoxSettingsUsermode.setFont(new Font("Tahoma", Font.BOLD, 16));
		comboBoxSettingsUsermode.setModel(new DefaultComboBoxModel(new String[] {"Easy", "Normal", "Expert"}));
		comboBoxSettingsUsermode.setBounds(150, 40, 200, 20);
		panelRemoteSettings.add(comboBoxSettingsUsermode);
		
		JLabel lblAspectRatio = new JLabel("Aspect Ratio");
		lblAspectRatio.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblAspectRatio.setBounds(10, 80, 130, 20);
		panelRemoteSettings.add(lblAspectRatio);
		
		JComboBox comboBoxSettingsAspectratio = new JComboBox();
		comboBoxSettingsAspectratio.setModel(new DefaultComboBoxModel(new String[] {"16:9     Widescreen", "4:3       Normal", "2.35:1  Cinemascope"}));
		comboBoxSettingsAspectratio.setFont(new Font("Tahoma", Font.BOLD, 16));
		comboBoxSettingsAspectratio.setBounds(150, 80, 200, 20);
		panelRemoteSettings.add(comboBoxSettingsAspectratio);
		
		JButton btnSettingsChannelscan = new JButton("Channelscan");
		btnSettingsChannelscan.setBounds(115, 186, 130, 25);
		panelRemoteSettings.add(btnSettingsChannelscan);
		
		JProgressBar progressBarSettingsChannelscan = new JProgressBar();
		progressBarSettingsChannelscan.setBounds(10, 246, 340, 25);
		panelRemoteSettings.add(progressBarSettingsChannelscan);
		
		JButton btnSettingsCancel = new JButton("Cancel");
		btnSettingsCancel.setVisible(false);
		btnSettingsCancel.setBounds(10, 577, 130, 50);
		panelRemoteSettings.add(btnSettingsCancel);
		
		JButton btnSettingsSave = new JButton("Save");
		btnSettingsSave.setBounds(220, 577, 130, 50);
		panelRemoteSettings.add(btnSettingsSave);
	}
}
