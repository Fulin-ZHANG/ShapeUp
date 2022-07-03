package fr.utt.lo02.projet.vue;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import fr.utt.lo02.projet.modele.connecteur.Connector;

/**
 * The class {@code GamePanel} is the panel of the main GUI of the game. It is
 * used to set the game background image and to display the player's
 * information.
 * 
 * @author Dewen WU
 * @version 1.0
 */
public class GamePanel extends JPanel {

	/**
	 * The serial Version UID of this class
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * An object of class {@code Connector}
	 */
	private Connector connector;

	/**
	 * The x coordinate corresponds to the specific value in the panel
	 */
	private final int[] x = { 150, 250, 350, 450, 550 };

//	/**
//	 * The y coordinate corresponds to the specific value in the panel
//	 */
//	private final int[] y = { 136, 236, 336, 436, 536 };

	/**
	 * The image of the game background
	 */
	public static final Image BACKGROUND = new ImageIcon(
			GamePanel.class.getResource("/fr/utt/lo02/projet/images/BACKGROUND.png")).getImage();

	/**
	 * Constructor method of class {@code GamePanel}
	 * 
	 * @param connector An object of class {@code Connector}
	 */
	public GamePanel(Connector connector) {
		this.connector = connector;
	}

	/**
	 * Set the game background image and to display the player's information.
	 */
	@Override
	protected void paintComponent(Graphics g) {

		this.setBackground(Color.LIGHT_GRAY);

		g.drawImage(BACKGROUND, 0, 0, this.getWidth(), this.getHeight(), null);

		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.PLAIN, 15)); // 字体样式

		g.drawString("Joueur 1: ", 24, 154); // 距离左上角坐标16，117，相距8
		g.drawString("Joueur 2: ", 671, 154); // 相距8

		g.setFont(new Font("Arial", Font.PLAIN, 12));
		// 玩家1手牌的位置
		g.drawString("[ Victory card ]", 38, 217);
		g.drawString("[ Card in hand ]", 35, 337);

		// 玩家2手牌的位置
		g.drawString("[ Victory card ]", 685, 217);
		g.drawString("[ Card in hand ]", 680, 337);

		if (connector.getJeu().getNbJoueur() == 3) {
			g.setFont(new Font("Arial", Font.PLAIN, 15));
			g.drawString("Joueur 3: ", 158, 671); // 相距8
			g.drawRect(x[0], 650, 500, 120); // 玩家3的框
			g.setFont(new Font("Arial", Font.PLAIN, 12));
			g.drawString("[ Victory card ]", x[1] - 15, 671);
			g.drawString("[ Card in hand ]", x[2] + 8, 671);
		}
	}

}
