package fr.utt.lo02.projet.modele.connecteur;

import fr.utt.lo02.projet.modele.formeTapis.FormeRectangle;
import fr.utt.lo02.projet.modele.formeTapis.FormeTriangle;
import fr.utt.lo02.projet.modele.mode.ModeAdvanced;
import fr.utt.lo02.projet.modele.mode.ModeInverse;
import fr.utt.lo02.projet.modele.mode.ModeJeu;
import fr.utt.lo02.projet.modele.mode.ModeNormal;

/**
 * The class {@code Connector} is used to create a game object {@code ModeJeu}
 * and a layout object, which establish a connection between the model and the
 * view.
 * 
 * @author Dewen WU and Fulin ZHANG
 * @version 1.0
 */
public class Connector {

	/**
	 * A game object of class ModeJeu
	 */
	private ModeJeu jeu;

	/**
	 * Constructor of class {@code Connector}
	 * 
	 * @param mode  The mode of the game
	 * @param forme The shape of the layout
	 */
	public Connector(String mode, String forme) {
		// set mode
		if (mode.equals("Normal")) {
			jeu = new ModeNormal();
		} else if (mode.equals("Advanced")) {
			jeu = new ModeAdvanced();
		} else {
			jeu = new ModeInverse();
		}

		// set forme
		if (forme.equals("Rectangle")) {
			jeu.getDisposition().setFormeTapis(new FormeRectangle(jeu.getDisposition()));
		} else if (forme.equals("Triangle")) {
			jeu.getDisposition().setFormeTapis(new FormeTriangle(jeu.getDisposition()));
		}

		jeu.getCartes().shuffle();

		System.out.print(jeu.getCartes());
	}

	/**
	 * Get the game object
	 * 
	 * @return The game object
	 */
	public ModeJeu getJeu() {
		return this.jeu;
	}
}
