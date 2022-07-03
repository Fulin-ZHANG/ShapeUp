package fr.utt.lo02.projet.vue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import fr.utt.lo02.projet.controleur.Initialisation;

/**
 * The class {@code MenuTexte} is used to input the initial parameters of the
 * game on the command line
 * <p>
 * 
 * A {@code MenuTexte} object contains fours attributes: {@code mode},
 * {@code forme} and {@code nbJoueurPhysique} and {@code nbJoueurVirtuel}
 * 
 * @author Dewen WU
 * @version 1.0
 */
public class MenuTexte implements Runnable {

	private String mode;
	private String forme;
	private int nbJoueurPhysique;
	private int nbJoueurVirtuel;

	/**
	 * Constructor method of class MenuTexte
	 * <p>
	 * Create a thread for the command line to input the initial parameters of the
	 * game
	 */
	public MenuTexte() {

		Thread t = new Thread(this);
		t.start();
	}

	/**
	 * Start of the thread and generate an object Initialisation at the end
	 */
	@Override
	public void run() {

		this.setMode();
		this.setForme();
		this.setNbJoueur();

		new Initialisation(mode, forme, nbJoueurPhysique, nbJoueurVirtuel);
	}

	/**
	 * To set the mode of the game
	 */
	public void setMode() {
		while (true) {
			System.out.println("Choisir le mode de jeu: [n = Noraml|a = Advanced|i = Inverse]");

			String mode = MenuTexte.lireChaine();

			if (mode.equals("n")) {
				this.mode = "Normal";
				break;
			} else if (mode.equals("a")) {
				this.mode = "Advanced";
				break;
			} else if (mode.equals("i")) {
				this.mode = "Inverse";
				break;
			} else {
				System.out.println("Commande non reconnue...");
			}
		}
	}

	/**
	 * To set the shape of the layout
	 */
	public void setForme() {
		while (true) {
			System.out.println("Choisir la forme du tapis: [r = rectangle|t = triangle]");

			String forme = MenuTexte.lireChaine();

			if (forme.equals("r")) {
				this.forme = "Rectangle";
				break;
			} else if (forme.equals("t")) {
				this.forme = "Triangle";
				break;
			} else {
				System.out.println("Commande non reconnue...");
			}
		}
	}

	/**
	 * To set the number of players
	 */
	public void setNbJoueur() {
		while (true) {
			System.out.println("Saisir le nombre de joueurs physique:");
			int nbJoueurPhysique = Integer.parseInt(MenuTexte.lireChaine());

			System.out.println("Saisir le nombre de joueurs virtuel:");
			int nbJoueurVirtuel = Integer.parseInt(MenuTexte.lireChaine());

			if ((nbJoueurPhysique + nbJoueurVirtuel) > 1 && (nbJoueurPhysique + nbJoueurVirtuel) < 4) {
				this.nbJoueurPhysique = nbJoueurPhysique;
				this.nbJoueurVirtuel = nbJoueurVirtuel;
				break;
			} else {
				System.out.println("Le nombre de joueurs est incorrect. ");
			}
		}
	}

	/**
	 * Read input from the command line
	 * 
	 * @return The String entered on the command line
	 */
	public static String lireChaine() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String resultat = null;
		try {
			System.out.print("> ");
			resultat = br.readLine();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return resultat;
	}

}
