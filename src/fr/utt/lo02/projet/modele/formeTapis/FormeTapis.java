package fr.utt.lo02.projet.modele.formeTapis;

import java.util.Iterator;

import fr.utt.lo02.projet.modele.carte.Carte;

/**
 * Abstract class of layout of the cards
 * 
 * @author Dewen and Fulin
 *@version 1.0
 */
public abstract class FormeTapis {

	/**
	 * The layout
	 */
	protected Disposition disposition;

	/**
	 * Constructor of class {@code FormeTapis}
	 * 
	 * @param disposition The layout
	 */
	public FormeTapis(Disposition disposition) {
		this.disposition = disposition;
	}

	/**
	 * Judge whether the card can be placed at the selected position, if it can,
	 * then play the card and return true; if not, return false.
	 * 
	 * @param point     The position of the selected card (place carteMain around
	 *                  this card)
	 * @param carte     Cards to be placed
	 * @param direction Placement direction
	 * @return {@code true} if the card can be placed
	 */
	public abstract boolean peutEtrePlacer(int point, Carte carte, String direction);

	/**
	 * Calculate the length of the lines in the entire layout and the left and right end points
	 * 
	 * @return {@code Integer[0]}Longest line length, {@code Integer[1]} Maximum abscissa, {@code Integer[3]}Minimum abscissa
	 */
	public Integer[] maxLigne() {
		Integer[] ligne = new Integer[3];
		Integer max = 0;
		Integer min = 5;

		Iterator<Integer> itHm = this.disposition.cartesTapis.keySet().iterator(); // HashMap遍历
		while (itHm.hasNext()) {
			Integer key = itHm.next();
			if (key % 10 > max) {
				max = key % 10;
			}
			if (key % 10 < min) {
				min = key % 10;
			}
		}
		ligne[0] = max - min + 1; // maxLigne
		ligne[1] = max; // xMax
		ligne[2] = min; // xMin
		return ligne;
	}

	/**
	 * Calculate the column length and the upper and lower end points in the entire layout
	 * 
	 * @return {@code Integer[0]} Longest column length, {@code Integer[0]} Maximum ordinate, {@code Integer[0]} ordinate minimum
	 */
	public Integer[] maxColonne() {
		Integer[] colonne = new Integer[3];
		Integer max = 0;
		Integer min = 5;

		Iterator<Integer> itHm = this.disposition.cartesTapis.keySet().iterator(); // HashMap遍历
		while (itHm.hasNext()) {
			Integer key = itHm.next();
			if (key / 10 > max) {
				max = key / 10;
			}
			if (key / 10 < min) {
				min = key / 10;
			}
		}
		colonne[0] = max - min + 1; // maxColonne
		colonne[1] = max; // yMax
		colonne[2] = min; // yMin
		return colonne;
	}
}
