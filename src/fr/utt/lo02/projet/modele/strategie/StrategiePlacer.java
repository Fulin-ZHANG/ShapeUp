package fr.utt.lo02.projet.modele.strategie;

import fr.utt.lo02.projet.modele.carte.Carte;
import fr.utt.lo02.projet.modele.formeTapis.Disposition;

/**
 * Interface {@code StrategiePlacer} is used to complete the action of playing
 * cards for AI.
 * 
 * @author Dewen WU
 * @version 1.0
 */
public interface StrategiePlacer {

	/**
	 * The method of playing the card
	 * 
	 * @param carteVictoire The victory card of player
	 * @param cartePlacer   The card wants to be played
	 * @param disposition   The layout
	 */
	public boolean play(Carte carteVictoire, Carte cartePlacer, Disposition disposition);
}
