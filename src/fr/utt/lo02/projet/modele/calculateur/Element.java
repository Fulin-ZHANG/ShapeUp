package fr.utt.lo02.projet.modele.calculateur;

/**
 * The interface {@code Element} is the interface of the visited end in the
 * visitor pattern.
 * 
 * @author Fulin and Dewen
 */
public interface Element {

	/**
	 * The interviewee allows the visitor to visit
	 * 
	 * @param visiteur
	 */
	public void accept(Visiteur visiteur);
}
