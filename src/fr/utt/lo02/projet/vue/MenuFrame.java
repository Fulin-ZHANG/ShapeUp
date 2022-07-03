package fr.utt.lo02.projet.vue;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.UIManager;

import fr.utt.lo02.projet.controleur.Initialisation;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * The class {@code MenuFrame} is used to to display the menu GUI of the game <p>
 * 
 * A {@code MenuFrame} object contains three buttons: {@code buttonStart}, {@code buttonQuit} and {@code buttonAbout}, 
 * fours comboBoxes for initializing the game: {@code comboBoxMode}, {@code comboBoxForme}, {@code comboBoxNumRealPlayers} and
 * {@code comboBoxNumVirtualPlayers} and five labels
 * 
 * @author Dewen WU and Fulin Zhang
 * @version 1.0
 */
public class MenuFrame {

	/**
	 * The frame of menu of the game
	 */
	private JFrame frmMenu;
	
	/**
	 * The button to start game
	 */
	private JButton buttonStart;
	
	/**
	 * The button to quit game
	 */
	private JButton buttonQuit;
	
	/**
	 * The button about game
	 */
	private JButton buttonAbout;
	
	/**
	 * The label of the mode of the game
	 */
	private JLabel labelMode;
	
	/**
	 * The label of the shape of the layout
	 */
	private JLabel labelForme;
	
	/**
	 * The label of the number of real players
	 */
	private JLabel labelNumRealPlayers;
	
	/**
	 * The label of the number of virtual players
	 */
	private JLabel labelNumVirtulPlayers;
	
	/**
	 * The label of menu
	 */
	private JLabel labelMenu;
	
	/**
	 * The combo box of the mode
	 */
	private JComboBox<String> comboBoxMode;
	
	/**
	 * The combo box of the shape of the layout
	 */
	private JComboBox<String> comboBoxForme;
	
	/**
	 * The combo box of the number of real players
	 */
	private JComboBox<Integer> comboBoxNumRealPlayers;
	
	/**
	 * The combo box of the number of virtual players
	 */
	private JComboBox<Integer> comboBoxNumVirtulPlayers;

	
	/**
	 * The {@code main} method for launching the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuFrame window = new MenuFrame();
					window.frmMenu.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		new MenuTexte();
	}

	/**
	 * Create the application.
	 */
	public MenuFrame() {
		initialize();
		
		new Initialisation(this.frmMenu, buttonStart, comboBoxMode, comboBoxForme, comboBoxNumRealPlayers, comboBoxNumVirtulPlayers);
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMenu = new JFrame();
		frmMenu.setTitle("Shape Up");
		frmMenu.setSize(428, 410);
		frmMenu.setLocationRelativeTo(null);    //居中
		frmMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMenu.getContentPane().setLayout(null);
		
//		JList list = new JList();
//		list.setBounds(158, 109, 1, 1);
//		frmMenu.getContentPane().add(list);
		
		buttonStart = new JButton("Start");
//		buttonStart.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//			}
//		});
		buttonStart.setForeground(Color.BLACK);
		buttonStart.setBounds(66, 320, 80, 29);
		frmMenu.getContentPane().add(buttonStart);
		
		buttonAbout = new JButton("About");
		buttonAbout.setBounds(173, 320, 80, 29);
		frmMenu.getContentPane().add(buttonAbout);
		
		buttonQuit = new JButton("Quit");
		buttonQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		buttonQuit.setBounds(281, 320, 80, 29);
		frmMenu.getContentPane().add(buttonQuit);
		
		labelMode = new JLabel("Mode: ");
		labelMode.setBounds(66, 100, 61, 16);
		frmMenu.getContentPane().add(labelMode);
		
		labelForme = new JLabel("Forme: ");
		labelForme.setBounds(66, 150, 61, 16);
		frmMenu.getContentPane().add(labelForme);
		
		labelNumRealPlayers = new JLabel("Number of real players: ");
		labelNumRealPlayers.setBounds(66, 200, 157, 16);
		frmMenu.getContentPane().add(labelNumRealPlayers);
		
		labelNumVirtulPlayers = new JLabel("Number of vritual players: ");
		labelNumVirtulPlayers.setBounds(66, 250, 170, 16);
		frmMenu.getContentPane().add(labelNumVirtulPlayers);
		
		labelMenu = new JLabel("Menu");
		labelMenu.setForeground(UIManager.getColor("CheckBox.select"));
		labelMenu.setFont(new Font("Bradley Hand", Font.ITALIC, 30));
		labelMenu.setBounds(175, 30, 88, 34);
		frmMenu.getContentPane().add(labelMenu);
		
		comboBoxMode = new JComboBox<String>();
		comboBoxMode.setModel(new DefaultComboBoxModel<String>(new String[] {"Normal", "Advanced", "Inverse"}));
		comboBoxMode.setBounds(235, 100, 126, 27);
		frmMenu.getContentPane().add(comboBoxMode);
		
		comboBoxForme = new JComboBox<String>();
		comboBoxForme.setModel(new DefaultComboBoxModel<String>(new String[] {"Rectangle", "Triangle"}));
		comboBoxForme.setBounds(235, 150, 126, 27);
		frmMenu.getContentPane().add(comboBoxForme);
		
		comboBoxNumRealPlayers = new JComboBox<Integer>();
		comboBoxNumRealPlayers.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {0, 1, 2, 3}));
		comboBoxNumRealPlayers.setBounds(235, 200, 126, 27);
		frmMenu.getContentPane().add(comboBoxNumRealPlayers);
		
		comboBoxNumVirtulPlayers = new JComboBox<Integer>();
		comboBoxNumVirtulPlayers.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {0, 1, 2, 3}));
		comboBoxNumVirtulPlayers.setBounds(235, 250, 126, 27);
		frmMenu.getContentPane().add(comboBoxNumVirtulPlayers);
	}
	
}
