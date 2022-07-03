package fr.utt.lo02.projet.modele.strategie;

import java.util.HashMap;

import fr.utt.lo02.projet.modele.calculateur.*;
import fr.utt.lo02.projet.modele.carte.Carte;
import fr.utt.lo02.projet.modele.formeTapis.Disposition;

/**
 * Implementation of Advanced Mode for Strategy Interface in Strategy Mode
 * 
 * @author Dewen and Fulin
 * @version 1.0
 *
 */
public class StAdvanced implements StrategiePlacer, Element {

	/**
	 * Temporarily store board information.
	 */
	public HashMap<Integer, Carte> tapisTemporaire;

	/**
	 * The victory card
	 */
	public Carte carteVictoire;

	/**
	 * Constructor
	 */
	public StAdvanced() {
		this.carteVictoire = null;
		this.tapisTemporaire = new HashMap<Integer, Carte>();
	}

	/**
	 * Allow calculator access.
	 */
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}

	/**
	 * To implement the play method.
	 * <pre>
	 * there is no function body because it is not
	 * used here.</pre>
	 */
	@Override
	public boolean play(Carte carteVictoire, Carte cartePlacer, Disposition disposition) {
		return true;
	}

	/**
	 * Find out which of these three hands has the highest score as a winning
	 * card and which one has the lowest. 
	 * <pre>
	 * The three cards in the hand
	 * are regarded as victory cards and the scores are calculated separately, and
	 * the highest and lowest score are recorded. The lowest one will be left, and
	 * the highest one will be played.
	 * </pre>
	 * 
	 * @param cartePlacer The array of the hand card.
	 * @param disposition The board.
	 * @return A array of the number of the card, the 1st is the min, the 2nd is the
	 *         max.
	 */
	public int[] playTheCard(Carte[] cartePlacer, Disposition disposition) {

		this.tapisTemporaire.putAll(disposition.cartesTapis);

		Calculateur cal = new Calculateur();

		int score = 0;
		int Min = 100;
		int Max = 0;
		int[] Pos = new int[2];

		for (int a = 0; a < 3; a++) {
			if (cartePlacer[a] != null) {
				this.carteVictoire = cartePlacer[a];
				disposition.accept(cal);
				this.accept(cal);
				score = cal.ScoreTot();
				System.out.println("Carte score:" + a);
				System.out.println(score);
				if (score <= Min) {
					Min = score;
					Pos[0] = a;
				}
				if (score >= Max) {
					Max = score;
					Pos[1] = a;
				}
			}
		}
		if (Pos[0] == Pos[1]) {
			if(cartePlacer[0] == null) {
				Pos[0] = 1;
				Pos[1] = 2;
			}else if(cartePlacer[1] == null) {
				Pos[0] = 0;
				Pos[1] = 2;
			}else if(cartePlacer[2] == null) {
				Pos[0] = 0;
				Pos[1] = 1;
			}else {
				Pos[0] = 0;
				Pos[1] = 1;
			}
		}
		return Pos;
	}

}
