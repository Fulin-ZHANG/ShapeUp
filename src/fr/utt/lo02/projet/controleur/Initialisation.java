package fr.utt.lo02.projet.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import fr.utt.lo02.projet.modele.connecteur.Connector;
//import fr.utt.lo02.projet.vue.ErrorDialog;
import fr.utt.lo02.projet.vue.GameFrame;

/**
 * The class {@code Initialisation} is used to initialize the game and generate
 * the game interface
 * <p>
 * 
 * An {@code Initialisation} object contains four attributes for initializing
 * the game: {@code button}, {@code forme}, {@code nbRealPlayers} and
 * {@code nbVirtulPlayers}
 * 
 * @author Dewen WU
 * @version 1.0
 */
public class Initialisation {

	/**
	 * Used to monitor the buttonStart of class {@code MenuFrame}
	 */
	private JButton button;

	/**
	 * The mode of the game. The mode normal or the mode advanced or mode inverse
	 */
	private String mode;

	/**
	 * Final shape of the layout. Rectangle or Triangle
	 */
	private String forme;

	/**
	 * The number of real players
	 */
	private Integer nbRealPlayers;

	/**
	 * The number of virtual players
	 */
	private Integer nbVirtualPlayers;

	/**
	 * An object of Class {@code Connector}
	 */
	private Connector connector;

	/**
	 * Initialize the game through the graphical interface.
	 * 
	 * Monitor the buttonStart of class {@code MenuFrame}. When this button is
	 * pressed, get the parameters in ComboBoxes, and generate the game interface
	 * only when the total number of players is greater than 2 and less than 3.
	 * 
	 * @param frmMenu                  JFrame of class {@code MenuFrame}
	 * @param button                   buttonStart of class {@code MenuFrame}
	 * @param comboBoxMode             comboBoxMode of class {@code MenuFrame}
	 * @param comboBoxForme            comboBoxForme of class {@code MenuFrame}
	 * @param comboBoxNumRealPlayers   comboBoxNumRealPlayers of class
	 *                                 {@code MenuFrame}
	 * @param comboBoxNumVirtulPlayers comboBoxNumVirtulPlayers of class
	 *                                 {@code MenuFrame}
	 */
	public Initialisation(JFrame frmMenu, JButton button, JComboBox<String> comboBoxMode,
			JComboBox<String> comboBoxForme, JComboBox<Integer> comboBoxNumRealPlayers,
			JComboBox<Integer> comboBoxNumVirtulPlayers) {
		// TODO Auto-generated constructor stub
		this.button = button;
		this.button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				System.out.println(comboBoxMode.getSelectedItem());
				mode = (String) comboBoxMode.getSelectedItem();
				forme = (String) comboBoxForme.getSelectedItem();
//				System.out.println(comboBoxNumRealPlayers.getSelectedItem());
				nbRealPlayers = (Integer) comboBoxNumRealPlayers.getSelectedItem();
				nbVirtualPlayers = (Integer) comboBoxNumVirtulPlayers.getSelectedItem();

				if (nbRealPlayers + nbVirtualPlayers > 3 || nbRealPlayers + nbVirtualPlayers < 2) {
//					ImageIcon imageError = new ImageIcon(Controleur.class.getResource("/fr/utt/lo02/projet/images/error.jpg"));
					JOptionPane.showMessageDialog(frmMenu, "La sélection a été faite par erreur.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					System.out.println("\n" + mode + "\n" + forme + "\n" + nbRealPlayers + "\n" + nbVirtualPlayers);

					connector = new Connector(mode, forme);

					connector.getJeu().setJoueur(nbRealPlayers, nbVirtualPlayers);

					new GameFrame(connector);

					connector.getJeu().dealCarte();

				}
			}
		});
	}

	/**
	 * Initialize the game through the command line by calling this constructor
	 * method via class {@code MenuTexte}
	 * 
	 * @param mode            The mode selected via the command line
	 * @param forme           The shape of layout selected via the command line
	 * @param nbRealPlayers   The number of real players input via the command line
	 * @param nbVirtulPlayers The number of real players input via the command line
	 * @see the class {@code MenuTexte}
	 */
	public Initialisation(String mode, String forme, int nbRealPlayers, int nbVirtulPlayers) {
		System.out.println("\n" + mode + "\n" + forme + "\n" + nbRealPlayers + "\n" + nbVirtulPlayers + "\n");

		connector = new Connector(mode, forme);

		connector.getJeu().setJoueur(nbRealPlayers, nbVirtulPlayers);

		new GameFrame(connector);

		connector.getJeu().dealCarte();
	}
}
