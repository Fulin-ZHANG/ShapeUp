package fr.utt.lo02.projet.modele.carte;

/**
 * The class of card
 * 
 * @author Dewen and Fulin
 * @version 1.0
 */
public class Carte {

	/**
	 * The color of the card
	 */
	private Couleur couleur;
	
	/**
	 * The shape of the card
	 */
	private Forme forme;
	
	/**
	 * The filling of the card
	 */
	private Contenu contenu;

	/**
	 * Constructor of the class {@code Carte}
	 * 
	 * @param couleur The color
	 * @param forme   The shape
	 * @param contenu filled or hollow
	 */
	public Carte(Couleur couleur, Forme forme, Contenu contenu) {
		this.couleur = couleur;
		this.forme = forme;
		this.contenu = contenu;
	}
	
	
	/**
	 * Override the toString method. Used to output cards.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
//		sb.append(this.couleur.toString());
		switch(couleur) {
		case Red:
			sb.append("  ");
			sb.append(this.couleur.toString());
			break;
		case Green:
			sb.append(this.couleur.toString());
			break;
		case Blue:
			sb.append(" ");
			sb.append(this.couleur.toString());
			break;
		default:
			break;
		}
		
		switch(forme) {
		case Circle:
			if(this.contenu.ordinal() == 0 ) {   // Filled
				sb.append("●");
			}
			else {
				sb.append("○");
			}
			break;
		case Triangle:
			if(this.contenu.ordinal() == 0 ) {   // Filled
				sb.append("▲");
			}
			else {
				sb.append("△");
			}
			break;
		case Square:
			if(this.contenu.ordinal() == 0 ) {   // Filled
				sb.append("■");
			}
			else {
				sb.append("□");
			}
			break;
		default:
			break;
		}
//		sb.append(this.forme.toString());
//		sb.append(this.contenu.toString());
		return sb.toString();
	}
	
	/**
	 * Get the color of the card
	 * 
	 * @return The color of the card
	 */
	public Couleur getCouleur() {
		return this.couleur;
	}
	
	/**
	 * Get the shape of the card
	 * 
	 * @return The shape of the card
	 */
	public Forme getForme() {
		return this.forme;
	}
	
	/**
	 * Get the filling of the card
	 * 
	 * @return The filling of the card
	 */
	public Contenu getContenu() {
		return this.contenu;
	}

}
