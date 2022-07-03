package fr.utt.lo02.projet.modele.calculateur;

import java.util.*;

import fr.utt.lo02.projet.modele.carte.Carte;
import fr.utt.lo02.projet.modele.carte.Contenu;
import fr.utt.lo02.projet.modele.carte.Couleur;
import fr.utt.lo02.projet.modele.carte.Forme;
import fr.utt.lo02.projet.modele.formeTapis.Disposition;
import fr.utt.lo02.projet.modele.joueur.Joueur;
import fr.utt.lo02.projet.modele.mode.ModeJeu;
import fr.utt.lo02.projet.modele.strategie.StAdvanced;
import fr.utt.lo02.projet.modele.strategie.StInverse;

/**
 * The class {@code Calculateur} is an implementation of the visitor interface
 * in the visitor pattern.
 * <p>
 * It can calculate the score.
 * 
 * @author Fulin and Dewen
 *
 */
public class Calculateur implements Visiteur {

	/**
	 * carteV is the victory card
	 */
	private Carte carteV;

	/**
	 * carteT is the placement of the cards on the board. It is stored by the
	 * HashMap type.
	 */
	private HashMap<Integer, Carte> cartesT;

	/**
	 * joueurs is an array to store all players.
	 */
	private ArrayList<Joueur> joueurs;

	/**
	 * This is the constructor. All properties are initialized in it.
	 */
	public Calculateur() {
		this.cartesT = new HashMap<Integer, Carte>();
		this.carteV = null;
		this.joueurs = null;
	}

	/**
	 * Visit a player and get their victoric card
	 * 
	 * @param The main body of the game.
	 */
	public void visit(ModeJeu jeu) {

		this.joueurs = jeu.getJoueurs();
//		System.out.println("Visiter de joueur bien");
	}

	/**
	 * Visit a layout and get the Cards HashMap
	 * 
	 * @param The checkerboard
	 */
	public void visit(Disposition disposition) {

		this.cartesT.clear();
		this.cartesT.putAll(disposition.cartesTapis);
//		System.out.println("Visiter de disposition bien");
	}

	/**
	 * Access strategies in advanced mode to obtain victory cards and board
	 * distribution.
	 * 
	 * @param The strategy of advanced mode
	 */
	public void visit(StAdvanced stAdvanced) {
		this.carteV = stAdvanced.carteVictoire;
//		System.out.println("Visiter de StA bien");
	}

	/**
	 * Visit the strategy of the Inverse mode to obtain victory cards and board
	 * distribution.
	 * 
	 * @param The strategy of inverse mode
	 */
	public void visit(StInverse stInverse) {

		this.cartesT.clear();
		this.cartesT.putAll(stInverse.tapisTemporaire);
		this.carteV = stInverse.carteVictoire;
//		System.out.println("Visiter de StI bien");
	}

	/**
	 * Find out where there are no cards on the board, and place a circle of invalid
	 * cards around this area and around the board to prepare for the score
	 * calculation.
	 */
	public void setNull() {
		for (int i = 0; i <= 4; i++) {
			for (int j = 0; j <= 4; j++) {
				if (cartesT.get(i * 10 + j) == null) {
					Couleur c = Couleur.Null;
					Forme f = Forme.Null;
					Contenu co = Contenu.Null;
					Carte buffer = new Carte(c, f, co);
					cartesT.put(i * 10 + j, buffer);
				}
			}
		}
		for (int i = 0; i <= 4; i++) {
			Couleur c = Couleur.Null;
			Forme f = Forme.Null;
			Contenu co = Contenu.Null;
			Carte buffer = new Carte(c, f, co);
			cartesT.put(i * 10 - 1, buffer);
			cartesT.put(i * 10 + 5, buffer);
			cartesT.put(i - 10, buffer);
			cartesT.put(5 * 10 + i, buffer);
		}
	}

	/**
	 * Calculate each player's score and save it in the player's score attribute.
	 */
	public void scoreTotal() {

		this.setNull();

		Iterator<Joueur> it2 = joueurs.iterator();
		while (it2.hasNext()) {
			Joueur j = it2.next();
			carteV = j.getCarteVictoire();
			int a = this.scoreForme();
			int b = this.scoreContenu();
			int c = this.scoreCouleur();
			int total = a + b + c;
			j.setScore(total);
			System.out.println("Joueur " + j.getNom() + ": Carte de victoire = " + j.getCarteVictoire() + ", scores = "
					+ j.getScore() + ".");
		}
	}

	/**
	 * Calculate the current score of this player.
	 * 
	 * @return The current score of this player.
	 */
	public int ScoreTot() {

		this.setNull();

		int a = this.scoreForme();
		int b = this.scoreContenu();
		int c = this.scoreCouleur();

		return a + b + c;
	}

	/**
	 * Calculate the color scores.
	 * 
	 * @return The color scores
	 */
	public int scoreCouleur() {
		int scoreCl;
		scoreCl = 0;

		for (int i = 0; i <= 4; i++) {
			if (this.cartesT.get(i * 10 + 0).getCouleur() == carteV.getCouleur()
					&& this.cartesT.get(i * 10 + 1).getCouleur() == carteV.getCouleur()
					&& this.cartesT.get(i * 10 + 2).getCouleur() == carteV.getCouleur()
					&& this.cartesT.get(i * 10 + 3).getCouleur() == carteV.getCouleur()
					&& this.cartesT.get(i * 10 + 4).getCouleur() == carteV.getCouleur()) {
				scoreCl += 6;
			}

			if (this.cartesT.get(i).getCouleur() == carteV.getCouleur()
					&& this.cartesT.get(i + 10).getCouleur() == carteV.getCouleur()
					&& this.cartesT.get(i + 20).getCouleur() == carteV.getCouleur()
					&& this.cartesT.get(i + 30).getCouleur() == carteV.getCouleur()
					&& this.cartesT.get(i + 40).getCouleur() == carteV.getCouleur()) {
				scoreCl += 6;
			}
			for (int j = 0; j <= 1; j++) {
				if (this.cartesT.get(i * 10 + j - 1).getCouleur() != carteV.getCouleur()
						&& this.cartesT.get(i * 10 + j).getCouleur() == carteV.getCouleur()
						&& this.cartesT.get(i * 10 + j + 1).getCouleur() == carteV.getCouleur()
						&& this.cartesT.get(i * 10 + j + 2).getCouleur() == carteV.getCouleur()
						&& this.cartesT.get(i * 10 + j + 3).getCouleur() == carteV.getCouleur()
						&& this.cartesT.get(i * 10 + j + 4).getCouleur() != carteV.getCouleur()) {
					scoreCl += 5;
				}
				if (this.cartesT.get(j * 10 + i - 10).getCouleur() != carteV.getCouleur()
						&& this.cartesT.get(j * 10 + i).getCouleur() == carteV.getCouleur()
						&& this.cartesT.get(j * 10 + i + 10).getCouleur() == carteV.getCouleur()
						&& this.cartesT.get(j * 10 + i + 20).getCouleur() == carteV.getCouleur()
						&& this.cartesT.get(j * 10 + i + 30).getCouleur() == carteV.getCouleur()
						&& this.cartesT.get(j * 10 + i + 40).getCouleur() != carteV.getCouleur()) {
					scoreCl += 5;
				}
			}
			for (int j = 0; j <= 2; j++) {
				if (this.cartesT.get(i * 10 + j - 1).getCouleur() != carteV.getCouleur()
						&& this.cartesT.get(i * 10 + j).getCouleur() == carteV.getCouleur()
						&& this.cartesT.get(i * 10 + j + 1).getCouleur() == carteV.getCouleur()
						&& this.cartesT.get(i * 10 + j + 2).getCouleur() == carteV.getCouleur()
						&& this.cartesT.get(i * 10 + j + 3).getCouleur() != carteV.getCouleur()) {
					scoreCl += 4;
				}
				if (this.cartesT.get(j * 10 + i - 10).getCouleur() != carteV.getCouleur()
						&& this.cartesT.get(j * 10 + i).getCouleur() == carteV.getCouleur()
						&& this.cartesT.get(j * 10 + i + 10).getCouleur() == carteV.getCouleur()
						&& this.cartesT.get(j * 10 + i + 20).getCouleur() == carteV.getCouleur()
						&& this.cartesT.get(j * 10 + i + 30).getCouleur() != carteV.getCouleur()) {
					scoreCl += 4;
				}
			}
		}
		return scoreCl;
	}

	/**
	 * Calculate the scores for filling.
	 * 
	 * @return The scores for filling
	 */
	public int scoreContenu() {
		int scoreCt;
		scoreCt = 0;

		for (int i = 0; i <= 4; i++) {
			if (this.cartesT.get(i * 10 + 0).getContenu() == carteV.getContenu()
					&& this.cartesT.get(i * 10 + 1).getContenu() == carteV.getContenu()
					&& this.cartesT.get(i * 10 + 2).getContenu() == carteV.getContenu()
					&& this.cartesT.get(i * 10 + 3).getContenu() == carteV.getContenu()
					&& this.cartesT.get(i * 10 + 4).getContenu() == carteV.getContenu()) {
				scoreCt += 5;
			}

			if (this.cartesT.get(i).getContenu() == carteV.getContenu()
					&& this.cartesT.get(i + 10).getContenu() == carteV.getContenu()
					&& this.cartesT.get(i + 20).getContenu() == carteV.getContenu()
					&& this.cartesT.get(i + 30).getContenu() == carteV.getContenu()
					&& this.cartesT.get(i + 40).getContenu() == carteV.getContenu()) {
				scoreCt += 5;
			}
			for (int j = 0; j <= 1; j++) {
				if (this.cartesT.get(i * 10 + j - 1).getContenu() != carteV.getContenu()
						&& this.cartesT.get(i * 10 + j).getContenu() == carteV.getContenu()
						&& this.cartesT.get(i * 10 + j + 1).getContenu() == carteV.getContenu()
						&& this.cartesT.get(i * 10 + j + 2).getContenu() == carteV.getContenu()
						&& this.cartesT.get(i * 10 + j + 3).getContenu() == carteV.getContenu()
						&& this.cartesT.get(i * 10 + j + 4).getContenu() != carteV.getContenu()) {
					scoreCt += 4;
				}
				if (this.cartesT.get(j * 10 + i - 10).getContenu() != carteV.getContenu()
						&& this.cartesT.get(j * 10 + i).getContenu() == carteV.getContenu()
						&& this.cartesT.get(j * 10 + i + 10).getContenu() == carteV.getContenu()
						&& this.cartesT.get(j * 10 + i + 20).getContenu() == carteV.getContenu()
						&& this.cartesT.get(j * 10 + i + 30).getContenu() == carteV.getContenu()
						&& this.cartesT.get(j * 10 + i + 40).getContenu() != carteV.getContenu()) {
					scoreCt += 4;
				}
			}
			for (int j = 0; j <= 2; j++) {
				if (this.cartesT.get(i * 10 + j - 1).getContenu() != carteV.getContenu()
						&& this.cartesT.get(i * 10 + j).getContenu() == carteV.getContenu()
						&& this.cartesT.get(i * 10 + j + 1).getContenu() == carteV.getContenu()
						&& this.cartesT.get(i * 10 + j + 2).getContenu() == carteV.getContenu()
						&& this.cartesT.get(i * 10 + j + 3).getContenu() != carteV.getContenu()) {
					scoreCt += 3;
				}
				if (this.cartesT.get(j * 10 + i - 10).getContenu() != carteV.getContenu()
						&& this.cartesT.get(j * 10 + i).getContenu() == carteV.getContenu()
						&& this.cartesT.get(j * 10 + i + 10).getContenu() == carteV.getContenu()
						&& this.cartesT.get(j * 10 + i + 20).getContenu() == carteV.getContenu()
						&& this.cartesT.get(j * 10 + i + 30).getContenu() != carteV.getContenu()) {
					scoreCt += 3;
				}
			}
		}
		return scoreCt;
	}

	/**
	 * Calculate the scores for the shape.
	 * 
	 * @return The scores for the shape
	 */
	public int scoreForme() {
		int scoreF;
		scoreF = 0;

		for (int i = 0; i <= 4; i++) {
			if (this.cartesT.get(i * 10 + 0).getForme() == carteV.getForme()
					&& this.cartesT.get(i * 10 + 1).getForme() == carteV.getForme()
					&& this.cartesT.get(i * 10 + 2).getForme() == carteV.getForme()
					&& this.cartesT.get(i * 10 + 3).getForme() == carteV.getForme()
					&& this.cartesT.get(i * 10 + 4).getForme() == carteV.getForme()) {
				scoreF += 4;
			}

			if (this.cartesT.get(i).getForme() == carteV.getForme()
					&& this.cartesT.get(i + 10).getForme() == carteV.getForme()
					&& this.cartesT.get(i + 20).getForme() == carteV.getForme()
					&& this.cartesT.get(i + 30).getForme() == carteV.getForme()
					&& this.cartesT.get(i + 40).getForme() == carteV.getForme()) {
				scoreF += 4;
			}
			for (int j = 0; j <= 1; j++) {
				if (this.cartesT.get(i * 10 + j - 1).getForme() != carteV.getForme()
						&& this.cartesT.get(i * 10 + j).getForme() == carteV.getForme()
						&& this.cartesT.get(i * 10 + j + 1).getForme() == carteV.getForme()
						&& this.cartesT.get(i * 10 + j + 2).getForme() == carteV.getForme()
						&& this.cartesT.get(i * 10 + j + 3).getForme() == carteV.getForme()
						&& this.cartesT.get(i * 10 + j + 4).getForme() != carteV.getForme()) {
					scoreF += 3;
				}
				if (this.cartesT.get(j * 10 + i - 10).getForme() != carteV.getForme()
						&& this.cartesT.get(j * 10 + i).getForme() == carteV.getForme()
						&& this.cartesT.get(j * 10 + i + 10).getForme() == carteV.getForme()
						&& this.cartesT.get(j * 10 + i + 20).getForme() == carteV.getForme()
						&& this.cartesT.get(j * 10 + i + 30).getForme() == carteV.getForme()
						&& this.cartesT.get(j * 10 + i + 40).getForme() != carteV.getForme()) {
					scoreF += 3;
				}
			}
			for (int j = 0; j <= 2; j++) {
				if (this.cartesT.get(i * 10 + j - 1).getForme() != carteV.getForme()
						&& this.cartesT.get(i * 10 + j).getForme() == carteV.getForme()
						&& this.cartesT.get(i * 10 + j + 1).getForme() == carteV.getForme()
						&& this.cartesT.get(i * 10 + j + 2).getForme() == carteV.getForme()
						&& this.cartesT.get(i * 10 + j + 3).getForme() != carteV.getForme()) {
					scoreF += 2;
				}
				if (this.cartesT.get(j * 10 + i - 10).getForme() != carteV.getForme()
						&& this.cartesT.get(j * 10 + i).getForme() == carteV.getForme()
						&& this.cartesT.get(j * 10 + i + 10).getForme() == carteV.getForme()
						&& this.cartesT.get(j * 10 + i + 20).getForme() == carteV.getForme()
						&& this.cartesT.get(j * 10 + i + 30).getForme() != carteV.getForme()) {
					scoreF += 2;
				}
			}
			for (int j = 0; j <= 3; j++) {
				if (this.cartesT.get(i * 10 + j - 1).getForme() != carteV.getForme()
						&& this.cartesT.get(i * 10 + j).getForme() == carteV.getForme()
						&& this.cartesT.get(i * 10 + j + 1).getForme() == carteV.getForme()
						&& this.cartesT.get(i * 10 + j + 2).getForme() != carteV.getForme()) {
					scoreF += 1;
				}
				if (this.cartesT.get(j * 10 + i - 10).getForme() != carteV.getForme()
						&& this.cartesT.get(j * 10 + i).getForme() == carteV.getForme()
						&& this.cartesT.get(j * 10 + i + 10).getForme() == carteV.getForme()
						&& this.cartesT.get(j * 10 + i + 20).getForme() != carteV.getForme()) {
					scoreF += 1;
				}
			}
		}
		return scoreF;
	}
}
