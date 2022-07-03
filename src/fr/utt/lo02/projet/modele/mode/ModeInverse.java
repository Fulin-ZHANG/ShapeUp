package fr.utt.lo02.projet.modele.mode;

import fr.utt.lo02.projet.modele.carte.Carte;
import fr.utt.lo02.projet.modele.joueur.Joueur;
import fr.utt.lo02.projet.modele.joueur.JoueurVirtuel;

/**
 * Inverse mode class. Inherit most of the normal mode process.
 * 
 * @author Dewen and Fulin
 *
 */
public class ModeInverse extends ModeNormal{
	
	/**
	 * Constructor
	 */
	public ModeInverse() {
		super();
	}
	
	/**
	 * Set for virtual player
	 * 
	 * @param joueur The current player
	 */
	@Override
	public void setVirtualPlayerl(Joueur joueur) {
		System.out.print(joueur.getNom() + " pioche une carte: ");
		joueur.setCarteMain(this.getCartes().dealCarte(), 0); // 抓牌

		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Carte carteMain = joueur.returnCarteMain(0); // 获取玩家手牌
		System.out.println(carteMain);

//		((JoueurVirtuel) joueur).jouer(getDisposition(), carteMain);
		
		Carte[] carteVictoires = new Carte[3];
		int a = 0;
		for (int z = 0; z < this.joueurs.size(); z++) {
			if (z != ordreJoueur - 1) {
				carteVictoires[a] = this.joueurs.get(z).getCarteVictoire();
				a++;
			}
		}

		((JoueurVirtuel) joueur).jouer(carteVictoires, carteMain, this.disposition);
		
		joueur.setPlayTrue();
		joueur.setMoveTrue();

		// 判断是否结束
		if (!this.etreTermine()) {
			this.setChanged();
			this.notifyObservers();
			this.nextPlayer(this.ordreJoueur);
		} else {
			this.setChanged();
			this.notifyObservers();
			this.mouseCliquer.terminerLeJeu();
		}
	}
}
