package fr.utt.lo02.projet.vue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import fr.utt.lo02.projet.controleur.MouseCliquer;
import fr.utt.lo02.projet.controleur.Move1;
import fr.utt.lo02.projet.controleur.Move2;
import fr.utt.lo02.projet.controleur.Move3;
import fr.utt.lo02.projet.controleur.Next1;
import fr.utt.lo02.projet.controleur.Next2;
import fr.utt.lo02.projet.controleur.Next3;
import fr.utt.lo02.projet.controleur.Play1;
import fr.utt.lo02.projet.controleur.Play2;
import fr.utt.lo02.projet.controleur.Play3;
import fr.utt.lo02.projet.modele.carte.Carte;
import fr.utt.lo02.projet.modele.connecteur.Connector;
import fr.utt.lo02.projet.modele.formeTapis.Disposition;
import fr.utt.lo02.projet.modele.joueur.Joueur;
import fr.utt.lo02.projet.modele.mode.ModeAdvanced;
import fr.utt.lo02.projet.modele.mode.ModeInverse;
import fr.utt.lo02.projet.modele.mode.ModeNormal;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
//import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.awt.event.ActionEvent;

/**
 * The class {@code GameFrame} is used to to display the main GUI of the game
 * 
 * <pre>
 * 
 * A {@code GameFrame} object mainly contains eleven buttons: each player has a play
 * button, a move button, and a next button. And there is a back button for backing to the menu
 * and a quit button is used to quit the game. The labels are used to display the information of players.
 * </pre>
 * 
 * @author Dewen WU
 * @version 1.0
 */
public class GameFrame implements Observer {

	/**
	 * The main frame of the game
	 */
	private JFrame frame;

	/**
	 * The panel of the game
	 */
	private GamePanel gamePanel;

	/**
	 * An object of the class {@code MouseCliquer}
	 */
	private MouseCliquer mouseCliquer;

	/**
	 * The back button is used to back to menu of the game
	 */
	private JButton buttonBack;

	/**
	 * The quit button is used to quit the game
	 */
	private JButton buttonQuit;

	/**
	 * The play button for player 1
	 */
	private JButton buttonPlay1;
	/**
	 * The move button for player 1
	 */
	private JButton buttonMove1;
	/**
	 * The next button for player 1
	 */
	private JButton buttonNext1;

	/**
	 * The play button for player 2
	 */
	private JButton buttonPlay2;
	/**
	 * The move button for player 2
	 */
	private JButton buttonMove2;
	/**
	 * The next button for player 2
	 */
	private JButton buttonNext2;

	/**
	 * The play button for player 3
	 */
	private JButton buttonPlay3;
	/**
	 * The move button for player 3
	 */
	private JButton buttonMove3;
	/**
	 * The next button for player 3
	 */
	private JButton buttonNext3;

	/**
	 * The label victory card for player 1
	 */
	private JLabel labelCarteVictoire1;
	/**
	 * The label victory card for player 2
	 */
	private JLabel labelCarteVictoire2;
	/**
	 * The label victory card for player 3
	 */
	private JLabel labelCarteVictoire3;

	/**
	 * The label of the first card in hand for player 1
	 */
	private JLabel labelCarteMain11;
	/**
	 * The label of the second card in hand for player 1
	 */
	private JLabel labelCarteMain12;
	/**
	 * The label of the third card in hand for player 1
	 */
	private JLabel labelCarteMain13;

	/**
	 * The label of the first card in hand for player 2
	 */
	private JLabel labelCarteMain21;
	/**
	 * The label of the second card in hand for player 2
	 */
	private JLabel labelCarteMain22;
	/**
	 * The label of the third card in hand for player 2
	 */
	private JLabel labelCarteMain23;

	/**
	 * The label of the first card in hand for player 3
	 */
	private JLabel labelCarteMain31;
	/**
	 * The label of the second card in hand for player 3
	 */
	private JLabel labelCarteMain32;
	/**
	 * The label of the third card in hand for player 3
	 */
	private JLabel labelCarteMain33;

//	private JLabel labelNomJoueur1;
//	private JLabel labelNomJoueur2;
//	private JLabel labelNomJoueur3;

	/**
	 * Used to store all the cards on the table
	 */
	private HashMap<Integer, JLabel> labelCarte = new HashMap<>();

	/**
	 * An object of class Connector
	 */
	private Connector connector;

	/**
	 * The x coordinate corresponds to the specific value in the frame
	 */
	private final int[] x = { 150, 250, 350, 450, 550 };

	/**
	 * The y coordinate corresponds to the specific value in the frame
	 */
	private final int[] y = { 136, 236, 336, 436, 536 };

	/**
	 * Constructor method of class GameFrame
	 * 
	 * @param connector An object of class {@code Connector}
	 */
	public GameFrame(Connector connector) {
		this.connector = connector;
		initialize();

		connector.getJeu().addObserver(this);
		connector.getJeu().getDisposition().addObserver(this);

		mouseCliquer = new MouseCliquer(frame, connector);
		frame.addMouseListener(mouseCliquer);
		connector.getJeu().setMouse(mouseCliquer);

		new Play1(connector, buttonPlay1, mouseCliquer);
		new Move1(connector, buttonMove1, mouseCliquer);
		new Next1(connector, buttonNext1);

		new Play2(connector, buttonPlay2, mouseCliquer);
		new Move2(connector, buttonMove2, mouseCliquer);
		new Next2(connector, buttonNext2);

		new Play3(connector, buttonPlay3, mouseCliquer);
		new Move3(connector, buttonMove3, mouseCliquer);
		new Next3(connector, buttonNext3);

		new GameTexte(connector);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Shape Up");
		frame.setVisible(true);
		frame.setSize(800, 800); // 实际画panel高为770
//		frame.setBounds(100, 100, 450, 300);
		frame.setLocationRelativeTo(null); // 居中
//		frame.getContentPane().setLayout(null);   // absolute
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		gamePanel = new GamePanel(this.connector);
		gamePanel.setBounds(0, 0, 800, 772);
		frame.getContentPane().add(gamePanel);
		gamePanel.setLayout(null);

		buttonBack = new JButton("Back");
		buttonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		buttonBack.setBounds(706, 36, 79, 30);
		gamePanel.add(buttonBack);

		buttonQuit = new JButton("Quit");
		buttonQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		buttonQuit.setBounds(706, 77, 79, 30);
		gamePanel.add(buttonQuit);

		buttonPlay1 = new JButton("Play");
		buttonPlay1.setBounds(18, 158, 60, 25);
		gamePanel.add(buttonPlay1);
		buttonPlay1.setVisible(false);

		buttonMove1 = new JButton("Move");
		buttonMove1.setBounds(75, 158, 60, 25);
		gamePanel.add(buttonMove1);
		buttonMove1.setVisible(false);

		buttonNext1 = new JButton("Next");
		buttonNext1.setBounds(75, 183, 60, 25);
		gamePanel.add(buttonNext1);
//		buttonNext1.setVisible(false);

		buttonPlay2 = new JButton("Play");
		buttonPlay2.setBounds(665, 158, 60, 25);
		gamePanel.add(buttonPlay2);
		buttonPlay2.setVisible(false);

		buttonMove2 = new JButton("Move");
		buttonMove2.setBounds(722, 158, 60, 25);
		gamePanel.add(buttonMove2);
		buttonMove2.setVisible(false);

		buttonNext2 = new JButton("Next");
		buttonNext2.setBounds(722, 183, 60, 25);
		gamePanel.add(buttonNext2);
//		buttonNext2.setVisible(false);

		buttonPlay3 = new JButton("Play");
		buttonPlay3.setBounds(156, 680, 60, 25);
		gamePanel.add(buttonPlay3);
		buttonPlay3.setVisible(false);

		buttonMove3 = new JButton("Move");
		buttonMove3.setBounds(156, 710, 60, 25);
		gamePanel.add(buttonMove3);
		buttonMove3.setVisible(false);

		buttonNext3 = new JButton("Next");
		buttonNext3.setBounds(156, 740, 60, 25);
		gamePanel.add(buttonNext3);

		// initialiser les labels de carte
		labelCarteVictoire1 = new JLabel();
		labelCarteVictoire1.setIcon(new ImageIcon());
		gamePanel.add(labelCarteVictoire1);

		labelCarteVictoire2 = new JLabel();
		labelCarteVictoire2.setIcon(new ImageIcon());
		gamePanel.add(labelCarteVictoire2);

		labelCarteVictoire3 = new JLabel();
		labelCarteVictoire3.setIcon(new ImageIcon());
		gamePanel.add(labelCarteVictoire3);

		labelCarteMain11 = new JLabel();
		labelCarteMain11.setIcon(new ImageIcon());
		gamePanel.add(labelCarteMain11);

		labelCarteMain12 = new JLabel();
		labelCarteMain12.setIcon(new ImageIcon());
		gamePanel.add(labelCarteMain12);

		labelCarteMain13 = new JLabel();
		labelCarteMain13.setIcon(new ImageIcon());
		gamePanel.add(labelCarteMain13);

		labelCarteMain21 = new JLabel();
		labelCarteMain21.setIcon(new ImageIcon());
		gamePanel.add(labelCarteMain21);

		labelCarteMain22 = new JLabel();
		labelCarteMain22.setIcon(new ImageIcon());
		gamePanel.add(labelCarteMain22);

		labelCarteMain23 = new JLabel();
		labelCarteMain23.setIcon(new ImageIcon());
		gamePanel.add(labelCarteMain23);

		labelCarteMain31 = new JLabel();
		labelCarteMain31.setIcon(new ImageIcon());
		gamePanel.add(labelCarteMain31);

		labelCarteMain32 = new JLabel();
		labelCarteMain32.setIcon(new ImageIcon());
		gamePanel.add(labelCarteMain32);

		labelCarteMain33 = new JLabel();
		labelCarteMain33.setIcon(new ImageIcon());
		gamePanel.add(labelCarteMain33);

//		labelNomJoueur1 = new JLabel("JP1");
//		labelNomJoueur1.setForeground(Color.WHITE);
//		labelNomJoueur1.setFont(new Font("LingWai SC", Font.BOLD, 15));
//		labelNomJoueur1.setBounds(95, 139, 50, 20);
//		;
//		gamePanel.add(labelNomJoueur1);

		// 创建棋盘上的label，i为纵坐标，j为横坐标
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				int yx = i * 10 + j;
				labelCarte.put(yx, new JLabel());
				labelCarte.get(yx).setIcon(new ImageIcon(""));
				gamePanel.add(labelCarte.get(yx));
			}
		}

	}

	/**
	 * Get a corresponding ImageIcon of a card
	 * 
	 * @param carte The card needs to be changed to an ImageIcon
	 * @return An ImageIcon corresponding to the card
	 */
	public ImageIcon changeToImage(Carte carte) {
		switch (carte.getForme()) {
		case Circle:
			if (carte.getContenu().ordinal() == 0) { // Filled
				if (carte.getCouleur().ordinal() == 0) { // red
					return new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/CFR.png")));
				} else if (carte.getCouleur().ordinal() == 1) { // green
					return new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/CFG.png")));
				} else { // blue
					return new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/CFB.png")));
				}
			} else {
				if (carte.getCouleur().ordinal() == 0) { // red
					return new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/CHR.png")));
				} else if (carte.getCouleur().ordinal() == 1) { // green
					return new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/CHG.png")));
				} else { // blue
					return new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/CHB.png")));
				}
			}
		case Triangle:
			if (carte.getContenu().ordinal() == 0) { // Filled
				if (carte.getCouleur().ordinal() == 0) { // red
					return new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/TFR.png")));
				} else if (carte.getCouleur().ordinal() == 1) { // green
					return new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/TFG.png")));
				} else { // blue
					return new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/TFB.png")));
				}
			} else {
				if (carte.getCouleur().ordinal() == 0) { // red
					return new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/THR.png")));
				} else if (carte.getCouleur().ordinal() == 1) { // green
					return new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/THG.png")));
				} else { // blue
					return new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/THB.png")));
				}
			}
		case Square:
			if (carte.getContenu().ordinal() == 0) { // Filled
				if (carte.getCouleur().ordinal() == 0) { // red
					return new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/SFR.png")));
				} else if (carte.getCouleur().ordinal() == 1) { // green
					return new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/SFG.png")));
				} else { // blue
					return new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/SFB.png")));
				}
			} else {
				if (carte.getCouleur().ordinal() == 0) { // red
					return new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/SHR.png")));
				} else if (carte.getCouleur().ordinal() == 1) { // green
					return new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/SHG.png")));
				} else { // blue
					return new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/SHB.png")));
				}
			}
		default:
			return null;
		}
	}

	/**
	 * The method to update the main graphical interface of the game.
	 */
	@Override
	public void update(Observable o, Object arg) {

		// update le jeu du mode normal
		if (o instanceof ModeNormal) {
			ModeNormal jeu = (ModeNormal) o;
			if (jeu.getOrdreJoueur() == 1) { // joueur 1
				Joueur joueur = (jeu.getJoueurs()).get(0);

				// set visible of the victory card
				labelCarteVictoire1.setIcon(this.changeToImage(joueur.getCarteVictoire()));
				labelCarteVictoire1.setBounds(26, 217, 100, 100);
				gamePanel.add(labelCarteVictoire1);

				labelCarteVictoire2
						.setIcon(new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/DOWN.png"))));
				labelCarteVictoire2.setBounds(673, 217, 100, 100);
				gamePanel.add(labelCarteVictoire2);

				if (jeu.getNbJoueur() == 3) {
					labelCarteVictoire3.setIcon(
							new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/DOWN.png"))));
					labelCarteVictoire3.setBounds(this.x[1] - 28, 669, 100, 100);
					gamePanel.add(labelCarteVictoire3);
				}

				// set visible of the card in main
				if (joueur.returnCarteMain(0) != null) {
					labelCarteMain11.setIcon(this.changeToImage(joueur.returnCarteMain(0)));
					labelCarteMain11.setBounds(26, this.y[2], 100, 100);
					gamePanel.add(labelCarteMain11);
				}

				// set visible of the buttons
				buttonNext1.setVisible(true);
				if (joueur.play == false) {
					buttonPlay1.setVisible(true);
				} else {
					buttonPlay1.setVisible(false);
				}
				if (joueur.move == false) {
					buttonMove1.setVisible(true);
				} else {
					buttonMove1.setVisible(false);
				}

				buttonNext2.setVisible(false);
				buttonMove2.setVisible(false);
				buttonNext3.setVisible(false);
				buttonMove3.setVisible(false);

			} else if (jeu.getOrdreJoueur() == 2) { // joueur 2
				Joueur joueur = (jeu.getJoueurs()).get(1);
				// set visible of the victory card
				labelCarteVictoire2.setIcon(this.changeToImage(joueur.getCarteVictoire()));
				labelCarteVictoire2.setBounds(673, 217, 100, 100);
				gamePanel.add(labelCarteVictoire2);

				labelCarteVictoire1
						.setIcon(new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/DOWN.png"))));
				labelCarteVictoire1.setBounds(26, 217, 100, 100);
				gamePanel.add(labelCarteVictoire1);

				if (jeu.getNbJoueur() == 3) {
					labelCarteVictoire3.setIcon(
							new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/DOWN.png"))));
					labelCarteVictoire3.setBounds(this.x[1] - 28, 669, 100, 100);
					gamePanel.add(labelCarteVictoire3);
				}

				// set visible of the card in main
				if (joueur.returnCarteMain(0) != null) {
					labelCarteMain21.setIcon(this.changeToImage(joueur.returnCarteMain(0)));
					labelCarteMain21.setBounds(673, this.y[2], 100, 100);
					gamePanel.add(labelCarteMain21);
				}

				// set visible of the buttons
				buttonNext2.setVisible(true);
				if (joueur.play == false) {
					buttonPlay2.setVisible(true);
				} else {
					buttonPlay2.setVisible(false);
				}
				if (joueur.move == false) {
					buttonMove2.setVisible(true);
				} else {
					buttonMove2.setVisible(false);
				}

				buttonNext1.setVisible(false);
				buttonMove1.setVisible(false);
				buttonNext3.setVisible(false);
				buttonMove3.setVisible(false);

			} else { // joueur 3
				Joueur joueur = (jeu.getJoueurs()).get(2);
				// set visible of the victory card
				labelCarteVictoire3.setIcon(this.changeToImage(joueur.getCarteVictoire()));
				labelCarteVictoire3.setBounds(this.x[1] - 28, 669, 100, 100);
				gamePanel.add(labelCarteVictoire3);

				labelCarteVictoire1
						.setIcon(new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/DOWN.png"))));
				labelCarteVictoire1.setBounds(26, 217, 100, 100);
				gamePanel.add(labelCarteVictoire1);
				labelCarteVictoire2
						.setIcon(new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/DOWN.png"))));
				labelCarteVictoire2.setBounds(673, 217, 100, 100);
				gamePanel.add(labelCarteVictoire2);

				// set visible of the card in main
				if (joueur.returnCarteMain(0) != null) {
					labelCarteMain31.setIcon(this.changeToImage(joueur.returnCarteMain(0)));
					labelCarteMain31.setBounds(x[2], 669, 100, 100);
					gamePanel.add(labelCarteMain31);
				}

				// set visible of the buttons
				buttonNext3.setVisible(true);
				if (joueur.play == false) {
					buttonPlay3.setVisible(true);
				} else {
					buttonPlay3.setVisible(false);
				}
				if (joueur.move == false) {
					buttonMove3.setVisible(true);
				} else {
					buttonMove3.setVisible(false);
				}

				buttonNext1.setVisible(false);
				buttonMove1.setVisible(false);
				buttonNext2.setVisible(false);
				buttonMove2.setVisible(false);
			}
			// afficher tous les cartes victoires, si le jeu est termine
			if (jeu.getPartieEnCours() == false) {
				Joueur joueur1 = (jeu.getJoueurs()).get(0);
				Joueur joueur2 = (jeu.getJoueurs()).get(1);
				labelCarteVictoire1.setIcon(this.changeToImage(joueur1.getCarteVictoire()));
				labelCarteVictoire2.setIcon(this.changeToImage(joueur2.getCarteVictoire()));

				if (jeu.getNbJoueur() == 3) {
					Joueur joueur3 = (jeu.getJoueurs()).get(2);
					labelCarteVictoire3.setIcon(this.changeToImage(joueur3.getCarteVictoire()));
				}
			}
		}

		// update le jeu du mode advanced
		if (o instanceof ModeAdvanced) {
			ModeAdvanced jeu = (ModeAdvanced) o;
			if (jeu.getOrdreJoueur() == 1) { // joueur 1
				Joueur joueur = (jeu.getJoueurs()).get(0);

				// set visible of the card in main
				Carte carteMain1 = joueur.returnCarteMain(0);
				Carte carteMain2 = joueur.returnCarteMain(1);
				Carte carteMain3 = joueur.returnCarteMain(2);
				if (carteMain1 != null) {
					labelCarteMain11.setIcon(this.changeToImage(carteMain1));
					labelCarteMain11.setBounds(26, this.y[2], 100, 100);
					gamePanel.add(labelCarteMain11);
				} else {
					labelCarteMain11.setIcon(new ImageIcon());
				}
				if (carteMain2 != null) {
					labelCarteMain12.setIcon(this.changeToImage(carteMain2));
					labelCarteMain12.setBounds(26, this.y[3], 100, 100);
					gamePanel.add(labelCarteMain12);
				} else {
					labelCarteMain12.setIcon(new ImageIcon());
				}
				if (carteMain3 != null) {
					labelCarteMain13.setIcon(this.changeToImage(carteMain3));
					labelCarteMain13.setBounds(26, this.y[4], 100, 100);
					gamePanel.add(labelCarteMain13);
				} else {
					labelCarteMain13.setIcon(new ImageIcon());
				}

				labelCarteMain21
						.setIcon(new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/DOWN.png"))));
				labelCarteMain21.setBounds(673, this.y[2], 100, 100);
				gamePanel.add(labelCarteMain21);
				labelCarteMain22
						.setIcon(new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/DOWN.png"))));
				labelCarteMain22.setBounds(673, this.y[3], 100, 100);
				gamePanel.add(labelCarteMain22);
				labelCarteMain23
						.setIcon(new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/DOWN.png"))));
				labelCarteMain23.setBounds(673, this.y[4], 100, 100);
				gamePanel.add(labelCarteMain23);

				if (jeu.getNbJoueur() == 3) {
					labelCarteMain31.setIcon(
							new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/DOWN.png"))));
					labelCarteMain31.setBounds(x[2], 669, 100, 100);
					gamePanel.add(labelCarteMain31);
					labelCarteMain32.setIcon(
							new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/DOWN.png"))));
					labelCarteMain32.setBounds(x[3], 669, 100, 100);
					gamePanel.add(labelCarteMain32);
					labelCarteMain33.setIcon(
							new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/DOWN.png"))));
					labelCarteMain33.setBounds(x[4], 669, 100, 100);
					gamePanel.add(labelCarteMain33);
				}

				// set visible of the buttons
				buttonNext1.setVisible(true);
				if (joueur.play == false) {
					buttonPlay1.setVisible(true);
				} else {
					buttonPlay1.setVisible(false);
				}
				if (joueur.move == false) {
					buttonMove1.setVisible(true);
				} else {
					buttonMove1.setVisible(false);
				}

				buttonNext2.setVisible(false);
				buttonMove2.setVisible(false);
				buttonNext3.setVisible(false);
				buttonMove3.setVisible(false);

			} else if (jeu.getOrdreJoueur() == 2) { // joueur 2
				Joueur joueur = (jeu.getJoueurs()).get(1);

				// set visible of the card in main
				Carte carteMain1 = joueur.returnCarteMain(0);
				Carte carteMain2 = joueur.returnCarteMain(1);
				Carte carteMain3 = joueur.returnCarteMain(2);
				if (carteMain1 != null) {
					labelCarteMain21.setIcon(this.changeToImage(carteMain1));
					labelCarteMain21.setBounds(673, this.y[2], 100, 100);
					gamePanel.add(labelCarteMain21);
				} else {
					labelCarteMain21.setIcon(new ImageIcon());
				}
				if (carteMain2 != null) {
					labelCarteMain22.setIcon(this.changeToImage(carteMain2));
					labelCarteMain22.setBounds(673, this.y[3], 100, 100);
					gamePanel.add(labelCarteMain22);
				} else {
					labelCarteMain22.setIcon(new ImageIcon());
				}
				if (carteMain3 != null) {
					labelCarteMain23.setIcon(this.changeToImage(carteMain3));
					labelCarteMain23.setBounds(673, this.y[4], 100, 100);
					gamePanel.add(labelCarteMain23);
				} else {
					labelCarteMain23.setIcon(new ImageIcon());
				}

				labelCarteMain11
						.setIcon(new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/DOWN.png"))));
				labelCarteMain11.setBounds(26, this.y[2], 100, 100);
				gamePanel.add(labelCarteMain11);
				labelCarteMain12
						.setIcon(new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/DOWN.png"))));
				labelCarteMain12.setBounds(26, this.y[3], 100, 100);
				gamePanel.add(labelCarteMain12);
				labelCarteMain13
						.setIcon(new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/DOWN.png"))));
				labelCarteMain13.setBounds(26, this.y[4], 100, 100);
				gamePanel.add(labelCarteMain13);

				if (jeu.getNbJoueur() == 3) {
					labelCarteMain31.setIcon(
							new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/DOWN.png"))));
					labelCarteMain31.setBounds(x[2], 669, 100, 100);
					gamePanel.add(labelCarteMain31);
					labelCarteMain32.setIcon(
							new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/DOWN.png"))));
					labelCarteMain32.setBounds(x[3], 669, 100, 100);
					gamePanel.add(labelCarteMain32);
					labelCarteMain33.setIcon(
							new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/DOWN.png"))));
					labelCarteMain33.setBounds(x[4], 669, 100, 100);
					gamePanel.add(labelCarteMain33);
				}

				// set visible of the buttons
				buttonNext2.setVisible(true);
				if (joueur.play == false) {
					buttonPlay2.setVisible(true);
				} else {
					buttonPlay2.setVisible(false);
				}
				if (joueur.move == false) {
					buttonMove2.setVisible(true);
				} else {
					buttonMove2.setVisible(false);
				}

				buttonNext1.setVisible(false);
				buttonMove1.setVisible(false);
				buttonNext3.setVisible(false);
				buttonMove3.setVisible(false);
			}
			// joueur 3
			else {
				Joueur joueur = (jeu.getJoueurs()).get(2);

				// set visible of the card in main
				Carte carteMain1 = joueur.returnCarteMain(0);
				Carte carteMain2 = joueur.returnCarteMain(1);
				Carte carteMain3 = joueur.returnCarteMain(2);
				if (carteMain1 != null) {
					labelCarteMain31.setIcon(this.changeToImage(carteMain1));
					labelCarteMain31.setBounds(x[2], 669, 100, 100);
					gamePanel.add(labelCarteMain31);
				} else {
					labelCarteMain31.setIcon(new ImageIcon());
				}
				if (carteMain2 != null) {
					labelCarteMain32.setIcon(this.changeToImage(carteMain2));
					labelCarteMain32.setBounds(x[3], 669, 100, 100);
					gamePanel.add(labelCarteMain32);
				} else {
					labelCarteMain32.setIcon(new ImageIcon());
				}
				if (carteMain3 != null) {
					labelCarteMain33.setIcon(this.changeToImage(carteMain3));
					labelCarteMain33.setBounds(x[4], 669, 100, 100);
					gamePanel.add(labelCarteMain33);
				} else {
					labelCarteMain33.setIcon(new ImageIcon());
				}

				labelCarteMain11
						.setIcon(new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/DOWN.png"))));
				labelCarteMain11.setBounds(26, this.y[2], 100, 100);
				gamePanel.add(labelCarteMain11);
				labelCarteMain12
						.setIcon(new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/DOWN.png"))));
				labelCarteMain12.setBounds(26, this.y[3], 100, 100);
				gamePanel.add(labelCarteMain12);
				labelCarteMain13
						.setIcon(new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/DOWN.png"))));
				labelCarteMain13.setBounds(26, this.y[4], 100, 100);
				gamePanel.add(labelCarteMain13);

				labelCarteMain21
						.setIcon(new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/DOWN.png"))));
				labelCarteMain21.setBounds(673, this.y[2], 100, 100);
				gamePanel.add(labelCarteMain21);
				labelCarteMain22
						.setIcon(new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/DOWN.png"))));
				labelCarteMain22.setBounds(673, this.y[3], 100, 100);
				gamePanel.add(labelCarteMain22);
				labelCarteMain23
						.setIcon(new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/DOWN.png"))));
				labelCarteMain23.setBounds(673, this.y[4], 100, 100);
				gamePanel.add(labelCarteMain23);

				// set visible of the buttons
				buttonNext3.setVisible(true);
				if (joueur.play == false) {
					buttonPlay3.setVisible(true);
				} else {
					buttonPlay3.setVisible(false);
				}
				if (joueur.move == false) {
					buttonMove3.setVisible(true);
				} else {
					buttonMove3.setVisible(false);
				}

				buttonNext1.setVisible(false);
				buttonMove1.setVisible(false);
				buttonNext2.setVisible(false);
				buttonMove2.setVisible(false);
			}

			// 结束时显示胜利牌
			if (jeu.getPartieEnCours() == false) {
				// joueur 1
				Joueur joueur1 = (jeu.getJoueurs()).get(0);
				labelCarteVictoire1.setIcon(this.changeToImage(joueur1.getCarteVictoire()));
				labelCarteVictoire1.setBounds(26, 217, 100, 100);
				gamePanel.add(labelCarteVictoire1);
				if (joueur1.returnCarteMain(0) != null) {
					labelCarteMain11.setIcon(this.changeToImage(joueur1.returnCarteMain(0)));
				} else {
					labelCarteMain11.setIcon(new ImageIcon());
				}
				if (joueur1.returnCarteMain(1) != null) {
					labelCarteMain12.setIcon(this.changeToImage(joueur1.returnCarteMain(1)));
				} else {
					labelCarteMain12.setIcon(new ImageIcon());
				}
				if (joueur1.returnCarteMain(2) != null) {
					labelCarteMain13.setIcon(this.changeToImage(joueur1.returnCarteMain(2)));
				} else {
					labelCarteMain13.setIcon(new ImageIcon());
				}
				// joueur 2
				Joueur joueur2 = (jeu.getJoueurs()).get(1);
				labelCarteVictoire2.setIcon(this.changeToImage(joueur2.getCarteVictoire()));
				labelCarteVictoire2.setBounds(673, 217, 100, 100);
				gamePanel.add(labelCarteVictoire2);
				if (joueur2.returnCarteMain(0) != null) {
					labelCarteMain21.setIcon(this.changeToImage(joueur2.returnCarteMain(0)));
				} else {
					labelCarteMain21.setIcon(new ImageIcon());
				}
				if (joueur2.returnCarteMain(1) != null) {
					labelCarteMain22.setIcon(this.changeToImage(joueur2.returnCarteMain(1)));
				} else {
					labelCarteMain22.setIcon(new ImageIcon());
				}
				if (joueur2.returnCarteMain(2) != null) {
					labelCarteMain23.setIcon(this.changeToImage(joueur2.returnCarteMain(2)));
				} else {
					labelCarteMain23.setIcon(new ImageIcon());
				}
				// joueur 3
				if (jeu.getNbJoueur() == 3) {
					Joueur joueur3 = (jeu.getJoueurs()).get(2);
					labelCarteVictoire3.setIcon(this.changeToImage(joueur3.getCarteVictoire()));
					labelCarteVictoire3.setBounds(this.x[1] - 28, 669, 100, 100);
					gamePanel.add(labelCarteVictoire3);
					if (joueur3.returnCarteMain(0) != null) {
						labelCarteMain31.setIcon(this.changeToImage(joueur3.returnCarteMain(0)));
					} else {
						labelCarteMain31.setIcon(new ImageIcon());
					}
					if (joueur3.returnCarteMain(1) != null) {
						labelCarteMain32.setIcon(this.changeToImage(joueur3.returnCarteMain(1)));
					} else {
						labelCarteMain32.setIcon(new ImageIcon());
					}
					if (joueur3.returnCarteMain(2) != null) {
						labelCarteMain33.setIcon(this.changeToImage(joueur3.returnCarteMain(2)));
					} else {
						labelCarteMain33.setIcon(new ImageIcon());
					}
				}
			}
		}

		// update le jeu du mode inverse
		if (o instanceof ModeInverse) {
			ModeInverse jeu = (ModeInverse) o;
			if (jeu.getOrdreJoueur() == 1) { // joueur 1
				Joueur joueur = (jeu.getJoueurs()).get(0);

				// set visible of the victory card
//				labelCarteVictoire1.setIcon(this.changeToImage(joueur.getCarteVictoire()));
				labelCarteVictoire1
						.setIcon(new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/DOWN.png"))));
				labelCarteVictoire1.setBounds(26, 217, 100, 100);
				gamePanel.add(labelCarteVictoire1);

				labelCarteVictoire2.setIcon(this.changeToImage(jeu.getJoueurs().get(1).getCarteVictoire()));
				labelCarteVictoire2.setBounds(673, 217, 100, 100);
				gamePanel.add(labelCarteVictoire2);

				if (jeu.getNbJoueur() == 3) {
					labelCarteVictoire3.setIcon(this.changeToImage(jeu.getJoueurs().get(2).getCarteVictoire()));
					labelCarteVictoire3.setBounds(this.x[1] - 28, 669, 100, 100);
					gamePanel.add(labelCarteVictoire3);
				}

				// set visible of the card in main
				if (joueur.returnCarteMain(0) != null) {
					labelCarteMain11.setIcon(this.changeToImage(joueur.returnCarteMain(0)));
					labelCarteMain11.setBounds(26, this.y[2], 100, 100);
					gamePanel.add(labelCarteMain11);
				}

				// set visible of the buttons
				buttonNext1.setVisible(true);
				if (joueur.play == false) {
					buttonPlay1.setVisible(true);
				} else {
					buttonPlay1.setVisible(false);
				}
				if (joueur.move == false) {
					buttonMove1.setVisible(true);
				} else {
					buttonMove1.setVisible(false);
				}

				buttonNext2.setVisible(false);
				buttonMove2.setVisible(false);
				buttonNext3.setVisible(false);
				buttonMove3.setVisible(false);

			} else if (jeu.getOrdreJoueur() == 2) { // joueur 2
				Joueur joueur = (jeu.getJoueurs()).get(1);
				// set visible of the victory card
				labelCarteVictoire2
						.setIcon(new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/DOWN.png"))));
				labelCarteVictoire2.setBounds(673, 217, 100, 100);
				gamePanel.add(labelCarteVictoire2);

				labelCarteVictoire1.setIcon(this.changeToImage(jeu.getJoueurs().get(0).getCarteVictoire()));
				labelCarteVictoire1.setBounds(26, 217, 100, 100);
				gamePanel.add(labelCarteVictoire1);

				if (jeu.getNbJoueur() == 3) {
					labelCarteVictoire3.setIcon(this.changeToImage(jeu.getJoueurs().get(2).getCarteVictoire()));
					labelCarteVictoire3.setBounds(this.x[1] - 28, 669, 100, 100);
					gamePanel.add(labelCarteVictoire3);
				}

				// set visible of the card in main
				if (joueur.returnCarteMain(0) != null) {
					labelCarteMain21.setIcon(this.changeToImage(joueur.returnCarteMain(0)));
					labelCarteMain21.setBounds(673, this.y[2], 100, 100);
					gamePanel.add(labelCarteMain21);
				}

				// set visible of the buttons
				buttonNext2.setVisible(true);
				if (joueur.play == false) {
					buttonPlay2.setVisible(true);
				} else {
					buttonPlay2.setVisible(false);
				}
				if (joueur.move == false) {
					buttonMove2.setVisible(true);
				} else {
					buttonMove2.setVisible(false);
				}

				buttonNext1.setVisible(false);
				buttonMove1.setVisible(false);
				buttonNext3.setVisible(false);
				buttonMove3.setVisible(false);

			} else { // joueur 3
				Joueur joueur = (jeu.getJoueurs()).get(2);
				// set visible of the victory card
				labelCarteVictoire3
						.setIcon(new ImageIcon((GamePanel.class.getResource("/fr/utt/lo02/projet/images/DOWN.png"))));
				labelCarteVictoire3.setBounds(this.x[1] - 28, 669, 100, 100);
				gamePanel.add(labelCarteVictoire3);

				labelCarteVictoire1.setIcon(this.changeToImage(jeu.getJoueurs().get(0).getCarteVictoire()));
				labelCarteVictoire1.setBounds(26, 217, 100, 100);
				gamePanel.add(labelCarteVictoire1);

				labelCarteVictoire2.setIcon(this.changeToImage(jeu.getJoueurs().get(1).getCarteVictoire()));
				labelCarteVictoire2.setBounds(673, 217, 100, 100);
				gamePanel.add(labelCarteVictoire2);

				// set visible of the card in main
				if (joueur.returnCarteMain(0) != null) {
					labelCarteMain31.setIcon(this.changeToImage(joueur.returnCarteMain(0)));
					labelCarteMain31.setBounds(x[2], 669, 100, 100);
					gamePanel.add(labelCarteMain31);
				}

				// set visible of the buttons
				buttonNext3.setVisible(true);
				if (joueur.play == false) {
					buttonPlay3.setVisible(true);
				} else {
					buttonPlay3.setVisible(false);
				}
				if (joueur.move == false) {
					buttonMove3.setVisible(true);
				} else {
					buttonMove3.setVisible(false);
				}

				buttonNext1.setVisible(false);
				buttonMove1.setVisible(false);
				buttonNext2.setVisible(false);
				buttonMove2.setVisible(false);
			}
			// afficher tous les cartes victoires, si le jeu est termine
			if (jeu.getPartieEnCours() == false) {
				Joueur joueur1 = (jeu.getJoueurs()).get(0);
				Joueur joueur2 = (jeu.getJoueurs()).get(1);
				labelCarteVictoire1.setIcon(this.changeToImage(joueur1.getCarteVictoire()));
				labelCarteVictoire2.setIcon(this.changeToImage(joueur2.getCarteVictoire()));

				if (jeu.getNbJoueur() == 3) {
					Joueur joueur3 = (jeu.getJoueurs()).get(2);
					labelCarteVictoire3.setIcon(this.changeToImage(joueur3.getCarteVictoire()));
				}
			}
		}
		// update les cartes sur tapis
		if (o instanceof Disposition) {
			Disposition disposition = (Disposition) o;
			Integer[][] yx = disposition.yx;

			// i 表示纵坐标y，j表示横坐标x
			for (int i = 0; i < yx.length; i++) {
				for (int j = 0; j < yx[0].length; j++) {
					int keyYX = i * 10 + j;
					if (disposition.cartesTapis.get(yx[i][j]) != null) {
						Carte carte = disposition.cartesTapis.get(yx[i][j]);
						labelCarte.get(keyYX).setIcon(this.changeToImage(carte));
						labelCarte.get(keyYX).setBounds(x[j], y[i], 100, 100);
						gamePanel.add(labelCarte.get(keyYX));
					} else {
						labelCarte.get(keyYX).setIcon(new ImageIcon());
					}
				}
			}
		}

	}

}
