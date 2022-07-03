package fr.utt.lo02.projet.modele.carte;

import java.util.*;

/**
 * Pile of cards.
 * It is used to perform operations on the card pile, shuffling,
 * hiding, dealing, and judging whether it is empty, etc.
 * 
 * @author Fulin and Dewen
 * @version 1.0
 */
public class PaquetCarte {
	/**
	 * The pile of card
	 */
	private ArrayList<Carte> tasDeCartes;
	
	/**
	 * Hidden cards.
	 */
	private Carte carteCachee;

	/**
	 * Constructor of class {@code PaquetCarte}. Create a deck of cards and initialize the attributes fill, shape, and color
	 */
	public PaquetCarte() {
		tasDeCartes = new ArrayList<Carte>();
		int i = 0;
		while( i < 4 && Couleur.values()[i].name() != "Null") {
			int j = 0;
			while( j < 4 && Forme.values()[j].name() != "Null") {
				int k = 0;
				while( k < 3 && Contenu.values()[k].name() != "Null") {
					Couleur c = Couleur.values()[i];
					Forme f = Forme.values()[j];
					Contenu co = Contenu.values()[k];
					Carte carte = new Carte(c, f, co);
					tasDeCartes.add(carte);
					k++;
				}
				j++;
			}
			i++;
		}

	}
	
	/**
	 * Method of shuffling the cards
	 */
	public void shuffle() {
		
		Collections.shuffle(this.tasDeCartes);
	}
	
	/**
	 * Hide a card. Get a card from the deck and remove it.
	 */
	public void cacherCarte() {
		carteCachee = tasDeCartes.get(0);
		tasDeCartes.remove(0);
	}
	
	/**
	 * Get a card from the top of deck, return him, and then remove him.
	 * 
	 * @return The card of deal
	 */
	public Carte dealCarte() {
		Carte carteTop = tasDeCartes.get(0);
		tasDeCartes.remove(0);
		return carteTop;
	}
	
	/**
	 * Determine whether the deck is empty.
	 * 
	 * @return {@code true} if the pile of cards is empty
	 */
	public boolean estVide() {
		return tasDeCartes.isEmpty();
	}
	
	/**
	 * Override the toString method. Used to output the current card distribution.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("The deck of cards: ");
			for(int i = 0; i < tasDeCartes.size(); i++) {
				if (i % 6 == 0) {
					sb.append("\n");
				}
				sb.append(tasDeCartes.get(i) + "   ");
			}
		sb.append("\n");	
		sb.append(
				"‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣");
		sb.append("\n");
		return sb.toString();
	}
}
