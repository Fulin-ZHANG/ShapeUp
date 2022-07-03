package fr.utt.lo02.projet.modele.strategie;

import java.util.HashMap;
//import java.util.Iterator;

import fr.utt.lo02.projet.modele.carte.Carte;
import fr.utt.lo02.projet.modele.formeTapis.Disposition;

/**
 * The class {@code StPlacerMemeForme} is used to play a card that has the
 * same shape as the victory card near any card which has the same shape as this
 * card if there is one on the table.
 * <p>
 * 
 * This class implements the interface {@code StrategiePlacer}
 * 
 * @author Dewen WU
 * @version 1.0
 */
public class StPlacerMemeForme implements StrategiePlacer {

	/**
	 * The method of playing the card
	 * 
	 * <pre>
	 * 
	 * Compare the card wants to be played with the victory card:
	 * If the shape is the same, then play this card on the table 
	 * in any direction possible of a card which has the same shape 
	 * as this card if there is one on the table. And return true, 
	 * otherwise, return false.
	 * </pre>
	 * 
	 * @param carteVictoire The victory card of player
	 * @param cartePlacer   The card wants to be played
	 * @param disposition   The layout
	 * @return {@code true} if the card can be played
	 */
	@Override
	public boolean play(Carte carteVictoire, Carte cartePlacer, Disposition disposition) {
		if (disposition.cartesTapis.isEmpty()) {
			int x = (int) (Math.random() * 5);
			int y = (int) (Math.random() * 5);
			int xy = x * 10 + y;
			disposition.cartesTapis.put(xy, cartePlacer);
			return true;
		} else if (carteVictoire.getForme() == cartePlacer.getForme()) { // 如果要放置的牌的颜色与胜利牌形状一样

			HashMap<Integer, Carte> tapisTemporaire = new HashMap<>();
			tapisTemporaire.putAll(disposition.cartesTapis);

			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					int x = i * 10 + j;
					Carte carte = disposition.cartesTapis.get(x);

					if (carte != null && cartePlacer.getForme() == carte.getForme()) { // 选择颜色相同的牌
						if (disposition.formeTapis.peutEtrePlacer(x, cartePlacer, "d")) {
							return true;
						} else {
							disposition.cartesTapis.clear();
							disposition.cartesTapis.putAll(tapisTemporaire);
						}
						if (disposition.formeTapis.peutEtrePlacer(x, cartePlacer, "b")) {
							return true;
						} else {
							disposition.cartesTapis.clear();
							disposition.cartesTapis.putAll(tapisTemporaire);
						}
						if (disposition.formeTapis.peutEtrePlacer(x, cartePlacer, "g")) {
							return true;
						} else {
							disposition.cartesTapis.clear();
							disposition.cartesTapis.putAll(tapisTemporaire);
						}
						if (disposition.formeTapis.peutEtrePlacer(x, cartePlacer, "h")) {
							return true;
						} else {
							disposition.cartesTapis.clear();
							disposition.cartesTapis.putAll(tapisTemporaire);
						}
					}
				}
			}
			return false;
		} else {
			return false;
		}
	}
}
