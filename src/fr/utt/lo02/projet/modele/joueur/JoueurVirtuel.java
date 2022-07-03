package fr.utt.lo02.projet.modele.joueur;

import java.util.ArrayList;
import java.util.Iterator;

import fr.utt.lo02.projet.modele.carte.Carte;
import fr.utt.lo02.projet.modele.formeTapis.Disposition;
import fr.utt.lo02.projet.modele.strategie.StAdvanced;
import fr.utt.lo02.projet.modele.strategie.StInverse;
import fr.utt.lo02.projet.modele.strategie.StPlacerMemContenu;
import fr.utt.lo02.projet.modele.strategie.StPlacerMemeCouleur;
import fr.utt.lo02.projet.modele.strategie.StPlacerMemeForme;
import fr.utt.lo02.projet.modele.strategie.StPlacerPossible;
import fr.utt.lo02.projet.modele.strategie.StrategiePlacer;

/**
 * The class of IA.
 * 
 * @author Dewen WU and Fulin ZHANG
 * @version 1.0
 */
public class JoueurVirtuel extends Joueur {

	/**
	 * An array of strategies.
	 */
	private ArrayList<StrategiePlacer> strategiePlacer;

	/**
	 * Advanced mode strategy.
	 */
	private StrategiePlacer SA;

	/**
	 * The strategy for mode Inverse
	 */
	private StrategiePlacer stInverse;

	/**
	 * Constructor of class {@code JoueurVirtuel}
	 * 
	 * @param nom    player's name
	 * @param numero player's number
	 */
	public JoueurVirtuel(String nom, int numero) {
		super(nom, numero);
		strategiePlacer = new ArrayList<>();
		SA = new StAdvanced();
	}

	/**
	 * The method to play for IA
	 * 
	 * <pre>
	 * Compare the card wants to be played with the victory card:
	 * 1. If the color is the same, play this card near the card 
	 * which has the same color as this card if there is one on the 
	 * table. Otherwise, go to next step. 
	 * 2. If the content(filled or hollow) is the same, play this 
	 * card near the card which has the same content as this card 
	 * if there is one on the table. Otherwise, go to next step. 
	 * 3. If the shape is the same, play this card near the card which
	 * has the same shape as this card if there is one on the table. 
	 * Otherwise, go to next step. 
	 * 4. If the content(filled or hollow) is the same, play this card
	 * near the card which has the same content as this card if there 
	 * is one on the table. Otherwise, go to next step. 5. If they don't 
	 * have any attributes in common, play the card in any place possible 
	 * on the table.
	 * </pre>
	 * 
	 * @param disp  The layout
	 * @param carte The card wants to be played
	 */
	public void jouer(Disposition disp, Carte carte) {

		strategiePlacer.add(new StPlacerMemeCouleur());
		strategiePlacer.add(new StPlacerMemContenu());
		strategiePlacer.add(new StPlacerMemeForme());
		strategiePlacer.add(new StPlacerPossible());

		Iterator<StrategiePlacer> it = strategiePlacer.iterator();
		while (it.hasNext()) {
			StrategiePlacer st = it.next();
			if (st.play(getCarteVictoire(), carte, disp)) {
				disp.afficherLesCartes();
				break;
			}
		}
	}

	/**
	 * The play card for Advanced mode to call
	 * 
	 * <pre>
	 * 1. If the layout is empty, Randomly placed.
	 * 2. If the layout is not empty, call the strategy for advanced to look for
	 * which the card to play.
	 * </pre>
	 * 
	 * @param disposition The board
	 */
	public void playCard(Disposition disposition) {

		if (disposition.cartesTapis.isEmpty()) {
			int x = (int) (Math.random() * 5);
			int y = (int) (Math.random() * 5);
			int xy = x * 10 + y;
			int c = (int) (Math.random() * 3);
			disposition.cartesTapis.put(xy, this.getCarteMain(c));
			disposition.afficherLesCartes();
		} else {
			int[] Pos = ((StAdvanced) SA).playTheCard(this.carteMain, disposition);
			this.setCarteVictoire(this.carteMain[Pos[1]]);
			this.jouer(disposition, this.getCarteMain(Pos[0]));
			if (this.getNumbreCarteMain() > 1) {
				this.removeCarteVictoire();
			}
		}
	}

	/**
	 * The method play the card for AI of mode Inverse
	 * 
	 * @param carteVictoires The victory cards of other players
	 * @param carteMain      The card wants to be played
	 * @param disposition    The layout
	 */
	public void jouer(Carte[] carteVictoires, Carte carteMain, Disposition disposition) {
		stInverse = new StInverse();
		String[] position = ((StInverse) stInverse).getPosition(carteVictoires, carteMain, disposition);
		
		if (position[1] == "i") {
			disposition.cartesTapis.put(Integer.parseInt(position[0]), carteMain);
		} else {
			disposition.formeTapis.peutEtrePlacer(Integer.parseInt(position[0]), carteMain, position[1]);
		}
		disposition.afficherLesCartes();
	}

}
