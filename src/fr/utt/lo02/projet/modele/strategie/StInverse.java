package fr.utt.lo02.projet.modele.strategie;

import java.util.HashMap;

import fr.utt.lo02.projet.modele.calculateur.Calculateur;
import fr.utt.lo02.projet.modele.calculateur.Element;
import fr.utt.lo02.projet.modele.calculateur.Visiteur;
import fr.utt.lo02.projet.modele.carte.Carte;
import fr.utt.lo02.projet.modele.formeTapis.Disposition;

/**
 * Implementation of Inverse Mode for Strategy Interface in Strategy Mode
 * 
 * @author Fulin and Dewen
 * @version 1.0
 */
public class StInverse implements StrategiePlacer, Element {

	/**
	 * The distribution of the cards on the board, used to calculate the score.
	 */
	public HashMap<Integer, Carte> tapisTemporaire;

	/**
	 * The victory card
	 */
	public Carte carteVictoire;

	/**
	 * Temporarily store the board distribution.
	 */
	public HashMap<Integer, Carte> tapisTempo;

	/**
	 * Constructor
	 */
	public StInverse() {
		this.carteVictoire = null;
		this.tapisTemporaire = new HashMap<Integer, Carte>();
		this.tapisTempo = new HashMap<Integer, Carte>();
	}

	/**
	 * Allow calculators to access this class.
	 */
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}

	/**
	 * Role: Find out where to put it so that the total score of other players is
	 * the lowest.
	 * 
	 * <pre>
	 * (1)If the board is empty, then randomly select a position to release cards.
	 * (2)If the board is not empty, Finding where to place the cards in turn can make 
	 * the total score of other players lowest and return the value. Traverse the board, 
	 * find the position of the card, put the card in all the positions that can be placed, 
	 * and use the calculator to find the score. Then finally return the position information 
	 * that can make other players the lowest score.
	 * </pre>
	 * 
	 * @param carteVictoire An array of the victory card
	 * @param cartePlacer   The card to play
	 * @param disposition   The layout
	 * @return Information about the placement position. Position and direction.
	 */
	public String[] getPosition(Carte[] carteVictoire, Carte cartePlacer, Disposition disposition) {
		this.tapisTempo.putAll(disposition.cartesTapis);
		;
		Calculateur cal = new Calculateur();

		int sTot = 0;
		int sMin = 100;
		String[] posMin = new String[2];

		if (disposition.cartesTapis.isEmpty()) {
			int x = (int) (Math.random() * 5);
			int y = (int) (Math.random() * 5);
			posMin[0] = String.valueOf(x * 10 + y);
			posMin[1] = "i";
		} else {

			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					int x = i * 10 + j;

					if (disposition.cartesTapis.get(x) != null) {
						if (disposition.formeTapis.peutEtrePlacer(x, cartePlacer, "d")) {
							sTot = 0;
							int z = 0;
							this.tapisTemporaire.clear();
							this.tapisTemporaire.putAll(disposition.cartesTapis);
							while (carteVictoire[z] != null) {
								this.carteVictoire = carteVictoire[z];
								this.accept(cal);
								sTot += cal.ScoreTot();
								z++;
							}
							if (sTot <= sMin) {
								sMin = sTot;
								posMin[0] = String.valueOf(x);
								posMin[1] = "d";
							}
							disposition.cartesTapis.clear();
							disposition.cartesTapis.putAll(tapisTempo);
						}

						if (disposition.formeTapis.peutEtrePlacer(x, cartePlacer, "g")) {
							sTot = 0;
							int z = 0;
							this.tapisTemporaire.clear();
							this.tapisTemporaire.putAll(disposition.cartesTapis);
							while (carteVictoire[z] != null) {
								this.carteVictoire = carteVictoire[z];
								this.accept(cal);
								sTot += cal.ScoreTot();
								z++;
							}
							if (sTot <= sMin) {
								sMin = sTot;
								posMin[0] = String.valueOf(x);
								posMin[1] = "g";
							}
							disposition.cartesTapis.clear();
							disposition.cartesTapis.putAll(tapisTempo);
						}
						if (disposition.formeTapis.peutEtrePlacer(x, cartePlacer, "h")) {
							sTot = 0;
							int z = 0;
							this.tapisTemporaire.clear();
							this.tapisTemporaire.putAll(disposition.cartesTapis);
							while (carteVictoire[z] != null) {
								this.carteVictoire = carteVictoire[z];
								this.accept(cal);
								sTot += cal.ScoreTot();
								z++;
							}
							if (sTot <= sMin) {
								sMin = sTot;
								posMin[0] = String.valueOf(x);
								posMin[1] = "h";
							}
							disposition.cartesTapis.clear();
							disposition.cartesTapis.putAll(tapisTempo);
						}
						if (disposition.formeTapis.peutEtrePlacer(x, cartePlacer, "b")) {
							sTot = 0;
							int z = 0;
							this.tapisTemporaire.clear();
							this.tapisTemporaire.putAll(disposition.cartesTapis);
							while (carteVictoire[z] != null) {
								this.carteVictoire = carteVictoire[z];
								this.accept(cal);
								sTot += cal.ScoreTot();
								z++;
							}
							if (sTot <= sMin) {
								sMin = sTot;
								posMin[0] = String.valueOf(x);
								posMin[1] = "b";
							}
							disposition.cartesTapis.clear();
							disposition.cartesTapis.putAll(tapisTempo);
						}
					}
				}
			}
		}
		return posMin;
	}

	@Override
	/**
	 * To implement the play method, there is no function body because it is not
	 * used here.
	 */
	public boolean play(Carte carteVictoire, Carte cartePlacer, Disposition disposition) {
		return false;
	}

}
