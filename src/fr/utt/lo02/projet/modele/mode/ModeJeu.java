package fr.utt.lo02.projet.modele.mode;

import java.util.*;

import fr.utt.lo02.projet.controleur.MouseCliquer;
import fr.utt.lo02.projet.modele.calculateur.Calculateur;
import fr.utt.lo02.projet.modele.calculateur.Element;
import fr.utt.lo02.projet.modele.calculateur.Visiteur;
import fr.utt.lo02.projet.modele.carte.PaquetCarte;
import fr.utt.lo02.projet.modele.formeTapis.Disposition;
import fr.utt.lo02.projet.modele.joueur.Joueur;
import fr.utt.lo02.projet.modele.joueur.JoueurPhysique;
import fr.utt.lo02.projet.modele.joueur.JoueurVirtuel;

/**
 * Abstract class of game mode. At the same time, it is the interviewee of the
 * calculator. And the observed.
 * 
 * @author Dewen and Fulin
 *
 */
public abstract class ModeJeu extends Observable implements Element {

	/**
	 * The array of the players.
	 */
	protected ArrayList<Joueur> joueurs;

	/**
	 * Pile of cards.
	 */
	private PaquetCarte cartes;

	/**
	 * Whether to start the game, true means start.
	 */
	protected boolean partieEnCours;

	/**
	 * The order of play.
	 */
	protected int ordreJoueur;

	/**
	 * The object of class {@code Disposition}
	 */
	protected Disposition disposition;

	/**
	 * Number of the game rounds.
	 */
	private int tourDeJeu;

	/**
	 * The object of class {@code MouseCliquer}
	 */
	protected MouseCliquer mouseCliquer;

	/**
	 * Constructor
	 */
	public ModeJeu() {
		joueurs = new ArrayList<>();
		setCartes(new PaquetCarte());
		partieEnCours = false;
		setDisposition(new Disposition());
		ordreJoueur = 0;
	}

	/**
	 * Get the list of players.
	 * 
	 * @return The list of players.
	 */
	public ArrayList<Joueur> getJoueurs() {
		return joueurs;
	}

	/**
	 * Allow calculator access.
	 * 
	 * @param Visitor(Calculator)
	 */
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}

	/**
	 * Add a player.
	 * 
	 * @param joueur A player
	 */
	public void ajouterJoueur(Joueur joueur) {
		joueurs.add(joueur);
	}

	/**
	 * Get the object of layout
	 * 
	 * @return The object of layout
	 */
	public Disposition getDisposition() {
		return disposition;
	}

	/**
	 * Set the object of layout
	 * 
	 * @param disposition The object of layout
	 */
	public void setDisposition(Disposition disposition) {
		this.disposition = disposition;
	}

	/**
	 * Get the pile of the cards.
	 * 
	 * @return The pile of the cards
	 */
	public PaquetCarte getCartes() {
		return cartes;
	}

	/**
	 * Set the pile of the cards.
	 * 
	 * @param cartes The pile of the cards
	 */
	public void setCartes(PaquetCarte cartes) {
		this.cartes = cartes;
	}

	/**
	 * Get the number of players.
	 * 
	 * @return The number of the players.
	 */
	public int getNbJoueur() {
		return joueurs.size();
	}

	/**
	 * Determine whether the game is over.
	 * 
	 * <pre>
	 * When the number of players equals 2, there are 15 cards on the table; or when
	 * the number of players equals 3, there are 14 cards on the table.
	 * </pre>
	 * 
	 * @return {@code true} if the game is over
	 */
	public boolean etreTermine() {
		if (this.joueurs.size() == 2 && this.getDisposition().numbreCartesSurTapis() == 15
				|| this.joueurs.size() == 3 && this.getDisposition().numbreCartesSurTapis() == 14) {
			this.partieEnCours = false;
			this.notifier();
			return true;
		} else {
			this.partieEnCours = true;
			return false;
		}
	}

//	/**
//	 * The method for starting the game
//	 */
//	public abstract void commencerLeJeu(); // 开始游戏

	/**
	 * Licensing.
	 */
	public abstract void dealCarte();

	/**
	 * play a card.
	 * 
	 * @param i             :The number of the player, starting from 0.
	 * @param mouseCliquer: The mouse object.
	 */
	public abstract void play(int i, MouseCliquer mouseCliquer);

	/**
	 * The method to add the players.
	 * 
	 * @param nbJoueurPhysique: The number of physical players.
	 * @param nbJoueurVirtuel:  The number of virtual players.
	 */
	public void setJoueur(int nbJoueurPhysique, int nbJoueurVirtuel) {
		for (int i = 0; i < nbJoueurPhysique; i++) {
			this.ajouterJoueur(new JoueurPhysique("JP" + (i + 1), i + 1));
		}

		for (int i = nbJoueurPhysique; i < nbJoueurPhysique + nbJoueurVirtuel; i++) {
			this.ajouterJoueur(new JoueurVirtuel("AI" + (i - nbJoueurPhysique + 1), i + 1));
		}

		System.out.println(this.getJoueurs());
	}

	/**
	 * Get the number of the current player
	 * 
	 * @return The number of the current player
	 */
	public int getOrdreJoueur() {
		return this.ordreJoueur;
	}

	/**
	 * Calculate the score.
	 */
	public void calculerScores() {
		Calculateur calculateur = new Calculateur();
		this.accept(calculateur); // --------Parmi le calculateur d'accéder la liste de joueur
		getDisposition().accept(calculateur);// --------Parmi le calculateur d'accéder la liste de disposition;

		calculateur.scoreTotal();
	}

	/**
	 * Update the display of GUI
	 */
	public void notifier() {
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * If the player has played and has moved, then go next
	 * 
	 * @param The current player
	 */
	public abstract void updateButton(Joueur joueur);

	/**
	 * The method for the button "Next". When the player presses the button "Next",
	 * the class {@code Next} will call this method.
	 * 
	 * @param The number of current player
	 */
	public abstract void nextPlayer(int i);

	/**
	 * The method for the button "Move". When the player presses the button "Move",
	 * the class {@code MouseCliquer} will call this method.
	 * 
	 * @param i:            The number of the player, starting from 0.
	 * @param mouseCliquer: The mouse object.
	 */
	public void move(int i, MouseCliquer mouseCliquer) {
		this.ordreJoueur = i;
		Joueur joueur = this.joueurs.get(ordreJoueur - 1); // 到此玩家出牌

		System.out.println("Joeur" + i + " move la carte");

		mouseCliquer.setMoveCard(joueur);
	}

	/**
	 * Set for virtual player
	 * 
	 * @param The current player
	 */
	public abstract void setVirtualPlayerl(Joueur joueur);

	/**
	 * Determine if the game is still in progress.
	 * 
	 * @return {@code true} if the game is in progress
	 */
	public boolean getPartieEnCours() {
		return partieEnCours;
	}

	/**
	 * Set the object of class MouseCliquer
	 * 
	 * @param mouseCliquer An object of MouseCliquer
	 */
	public void setMouse(MouseCliquer mouseCliquer) {
		this.mouseCliquer = mouseCliquer;
	}

	/**
	 * Output the information of winner on the command line
	 */
	public void calculerWinner() {
		System.out.println("‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣");
		this.calculerScores();
		int[] score = new int[3];
		int scoreWin = 0;
		int nbJoueurWin = 0;
		Iterator<Joueur> it = this.joueurs.iterator();
		while (it.hasNext()) {
			Joueur j = it.next();
			score[j.getNumero() - 1] = j.getScore();
			if (j.getScore() > scoreWin) {
				scoreWin = j.getScore();
				nbJoueurWin = j.getNumero();
			}
		}
		System.out.println("Joueur " + nbJoueurWin + " win!");
	}

}
