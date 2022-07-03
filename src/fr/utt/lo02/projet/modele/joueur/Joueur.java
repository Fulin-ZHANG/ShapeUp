package fr.utt.lo02.projet.modele.joueur;

import fr.utt.lo02.projet.modele.carte.Carte;

/**
 * The class of player, the parent class of physical players and virtual
 * players.
 * 
 * @author Dewen and Fulin
 * @version 1.0
 */
public class Joueur {
	/**
	 * The name of the player
	 */
	private String nom;

	/**
	 * Player's victory card
	 */
	private Carte carteVictoire;

	/**
	 * The cards in hand of player
	 */
	protected Carte[] carteMain;

	/**
	 * Player's score.
	 */
	private int score;

	/**
	 * Player's number.
	 */
	private int numero;

	/**
	 * The state play of the player. False means the player has not play a card yet
	 */
	public boolean play;

	/**
	 * The state move of the player. False means the player has not move a card yet
	 */
	public boolean move;

	/**
	 * Constructor of class {@code Joueur}
	 * 
	 * @param nom    The name of player
	 * @param numero The player's number
	 */
	public Joueur(String nom, int numero) {
		this.nom = nom;
		this.score = 0;
		this.carteMain = new Carte[3];
		this.carteVictoire = null;
		this.numero = numero;
		this.play = false;
		this.move = false;
	}

	/**
	 * Set player's score.
	 * 
	 * @param score
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Get player's score.
	 * 
	 * @return {@code score} of the player
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Set player's victory card
	 * 
	 * @param score
	 */
	public void setCarteVictoire(Carte carteVictoire) {
		this.carteVictoire = carteVictoire;
	}

	/**
	 * Remove player's victory card(Set as null)
	 */
	public void removeCarteVictoire() {
		this.carteVictoire = null;
	}

	/**
	 * Set player's cards in hand with a number of cards in hand
	 * 
	 * @param carte A card used to set as a card in hand
	 * @param i     The number of cards in hand
	 */
	public void setCarteMain(Carte carte, int i) {
		this.carteMain[i] = carte;
	}

	/**
	 * Get player's one of cards in hand with a number of cards in hand and remove
	 * this card
	 * 
	 * @param i The number of cards in hand
	 * @return One of cards in hand
	 */
	public Carte getCarteMain(int i) { // get and remove
		Carte carte = carteMain[i];
		this.carteMain[i] = null;
		return carte;
	}

	/**
	 * Just get player's one of cards in hand with a number of cards in hand
	 * 
	 * @param i The number of cards in hand
	 * @return One of cards in hand
	 */
	public Carte returnCarteMain(int i) { // just get
		return carteMain[i];
	}

	/**
	 * Get player's victory card
	 * 
	 * @return The victory card of the player
	 */
	public Carte getCarteVictoire() {
		return this.carteVictoire;
	}

	/**
	 * Get the number of cards in the player's hand
	 * 
	 * @return The number of cards
	 */
	public int getNumbreCarteMain() {
		int n = 0;
		for (int i = 0; i < 3; i++) {
			if (this.carteMain[i] != null) {
				n++;
			}
		}
		return n;
	}

	/**
	 * Get the number of player
	 * 
	 * @return The number of player
	 */
	public int getNumero() {
		return this.numero;
	}

	/**
	 * Get the name of player
	 * 
	 * @return The name of player
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * Set the state play of the player to false
	 */
	public void setPlayFalse() {
		this.play = false;
	}

	/**
	 * Set the state play of the player to true
	 */
	public void setPlayTrue() {
		this.play = true;
	}

	/**
	 * Set the state move of the player to false
	 */
	public void setMoveFalse() {
		this.move = false;
	}

	/**
	 * Set the state move of the player to true
	 */
	public void setMoveTrue() {
		this.move = true;
	}

	/**
	 * Get the state play of the player
	 * 
	 * @return {@code true} if player has not play yet
	 */
	public boolean getPlay() {
		return this.play;
	}

	/**
	 * Get the state move of the player
	 * 
	 * @return {@code true} if player has not move yet
	 */
	public boolean getMove() {
		return this.move;
	}

	/**
	 * Rewrite the method toString for the players.
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("Nom: ");
		sb.append(this.nom);
		sb.append(", ");
		sb.append("Numero: ");
		sb.append(this.numero);

		return sb.toString();
	}
}
