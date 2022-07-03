package fr.utt.lo02.projet.modele.calculateur;

import fr.utt.lo02.projet.modele.formeTapis.Disposition;
import fr.utt.lo02.projet.modele.mode.ModeJeu;
import fr.utt.lo02.projet.modele.strategie.StAdvanced;
import fr.utt.lo02.projet.modele.strategie.StInverse;

/**
 * The interface {@code Visiteur} is the interface of the visitor in the visitor
 * mode.
 * 
 * @author Fulin and Dewen
 */
public interface Visiteur {

	/**
	 * Visitors can access the ModeJeu class.
	 * 
	 * @param jeu
	 */
	public void visit(ModeJeu jeu);

	/**
	 * Visitors can visit the board class.
	 * 
	 * @param disposition
	 */
	public void visit(Disposition disposition);

	/**
	 * Visitors can access the strategy of MaxScore class
	 * 
	 * @param stPlacerMaxScore
	 */
	public void visit(StAdvanced stAdvanced);

	/**
	 * Visitors can access the strategy for Inverse mode class
	 * 
	 * @param stInverse
	 */
	public void visit(StInverse stInverse);
}
