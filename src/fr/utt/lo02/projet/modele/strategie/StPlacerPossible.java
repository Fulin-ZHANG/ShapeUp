package fr.utt.lo02.projet.modele.strategie;

import java.util.HashMap;
//import java.util.Iterator;

import fr.utt.lo02.projet.modele.carte.Carte;
import fr.utt.lo02.projet.modele.formeTapis.Disposition;

/**
 * The class {@code StPlacerPossible} is used to play a card in any possible
 * position on the table
 * <p>
 * 
 * This class implements the interface {@code StrategiePlacer}
 * 
 * @author Dewen WU
 * @version 1.0
 */
public class StPlacerPossible implements StrategiePlacer {

	/**
	 * The method of playing the card
	 * 
	 * <pre>
	 * 
	 * playing a card in any possible position on the table
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
		} else {

			HashMap<Integer, Carte> tapisTemporaire = new HashMap<>();
			tapisTemporaire.putAll(disposition.cartesTapis);

			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					int x = i * 10 + j;
					Carte carte = disposition.cartesTapis.get(x);

					if (carte != null) { // 不为空的时候才能放牌
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
//			Iterator<Integer> it = disposition.cartesTapis.keySet().iterator(); // HashMap遍历
//			while (it.hasNext()) {
//				Integer x = it.next();
//
//				if (disposition.formeTapis.peutEtrePlacer(x, cartePlacer, "d")) {
//					return true;
//				}
//				if (disposition.formeTapis.peutEtrePlacer(x, cartePlacer, "b")) {
//					return true;
//				}
//				if (disposition.formeTapis.peutEtrePlacer(x, cartePlacer, "g")) {
//					return true;
//				}
//				if (disposition.formeTapis.peutEtrePlacer(x, cartePlacer, "h")) {
//					return true;
//				}
//			}
		}
		return false;
	}
}
