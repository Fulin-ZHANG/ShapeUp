package fr.utt.lo02.projet.modele.mode;

import java.util.Iterator;

import fr.utt.lo02.projet.controleur.MouseCliquer;
import fr.utt.lo02.projet.modele.carte.Carte;
import fr.utt.lo02.projet.modele.joueur.Joueur;
import fr.utt.lo02.projet.modele.joueur.JoueurVirtuel;

/**
 * Inherited the game mode class. Is the specific process of advanced mode.
 * 
 * @author Fulin and Dewen
 */
public class ModeAdvanced extends ModeJeu {
//	private int input;

	/**
	 * Constructor
	 */
	public ModeAdvanced() {
		super();
	}

	/**
	 * Deal each player a card and randomly determine the first player to play
	 */
	@Override
	public void dealCarte() {
		partieEnCours = true;
//		Calculateur calculateur = new Calculateur();

		// cacher une carte
		getCartes().cacherCarte(); // 藏一张牌

		// every player gets a victory card x
		Iterator<Joueur> it = joueurs.iterator(); // 给每个玩家发三张手牌
		while (it.hasNext()) {
			Joueur j = it.next();
			for (int i = 0; i < 3; i++) {
				j.setCarteMain(getCartes().dealCarte(), i);
			}
		}

		this.ordreJoueur = (int) (1 + Math.random() * this.joueurs.size()); // 随机选一个玩家开始游戏
		System.out.println("[Joueur" + ordreJoueur + " commence de jouer !]");
		System.out.println("-------------------------------------------------------------------------");

		this.setChanged();
		this.notifyObservers();

		Joueur joueur = this.joueurs.get(ordreJoueur - 1);
//		joueur.notifier();

		// 如果第一个出牌的玩家为虚拟玩家
		if (joueur instanceof JoueurVirtuel) {
			this.setVirtualPlayerl(joueur);
		}
	}

	/**
	 * Play a card for the button "Play". When the player presses the button "Play",
	 * the class {@code MouseCliquer} will call this method. Only physical players
	 * can press the button
	 * 
	 * @param i:            The number of the player, starting from 0.
	 * @param mouseCliquer: The mouse object.
	 */
	@Override
	public void play(int i, MouseCliquer mouseCliquer) {
		if (partieEnCours == true) {
			this.ordreJoueur = i;
			Joueur joueur = this.joueurs.get(ordreJoueur - 1); // 到此玩家出牌
//			// 按下play键时就应该将其play属性设为true
//			joueur.setPlayTrue();
			System.out.println("Cartes main: " + joueur.returnCarteMain(0) + joueur.returnCarteMain(1)
					+ joueur.returnCarteMain(2));

			this.setChanged();
			this.notifyObservers();

			// 使用鼠标选择放牌
//				mouseCliquer.playCard(carteMain, i);
			mouseCliquer.setChoiceCardHand(joueur);

		}
	}

	/**
	 * If the player has played and has moved, then go to the next round.
	 * 
	 * @param joueur The current player
	 */
	@Override
	public void updateButton(Joueur joueur) {
		if (joueur.play == true && joueur.move == true) {

			if (!getCartes().estVide()) { // 如果牌堆不为空，则抓一张牌
				System.out.print(joueur.getNom() + " pioche une carte: ");
				for (int i = 0; i < 3; i++) {
					if (joueur.returnCarteMain(i) == null) { // 找出刚刚出掉的牌的位置
						joueur.setCarteMain(this.getCartes().dealCarte(), i);
						System.out.println(joueur.returnCarteMain(i));
					}
				}
				this.setChanged();
				this.notifyObservers();
			} else {
				System.out.println("The deck of cardes is vide");
			}

			// 当手中的牌仅剩一张的时候，将此牌设为胜利牌
			this.isVictoryCard(joueur);

			ordreJoueur++;
			if (ordreJoueur > this.joueurs.size()) {
				ordreJoueur = ordreJoueur - this.joueurs.size();
			}
			Joueur joueurNext = this.joueurs.get(ordreJoueur - 1);
			joueurNext.setPlayFalse();
			joueurNext.setMoveFalse();

			System.out.println("-------------------------------------------------------------------------");
			System.out.println("Joueur" + ordreJoueur + " " + joueurNext.getNom() + " jouer: ");

			if (joueurNext instanceof JoueurVirtuel) {
//				joueurNext.notifier();
				this.setVirtualPlayerl(joueurNext);
			} else {
				this.setChanged();
				this.notifyObservers();
//				joueurNext.notifier();
			}
		}
	}

	/**
	 * The method for the button "Next". When the player presses the button "Next",
	 * the class {@code Next} will call this method.
	 * 
	 * @param i The number of current player
	 */
	@Override
	public void nextPlayer(int i) {
		this.ordreJoueur = i;
		Joueur joueur = this.joueurs.get(ordreJoueur - 1);
		if (joueur.play == true) {

			if (!getCartes().estVide()) { // 如果牌堆不为空，则抓一张牌
				System.out.print(joueur.getNom() + " pioche une carte: ");
				for (int n = 0; n < 3; n++) {
					if (joueur.returnCarteMain(n) == null) { // 找出刚刚出掉的牌的位置
						joueur.setCarteMain(this.getCartes().dealCarte(), n);
						System.out.println(joueur.returnCarteMain(n));
					}
				}
				this.setChanged();
				this.notifyObservers();
			} else {
				System.out.println("The deck of cardes is vide");
			}

			// 当手中的牌仅剩一张的时候，将此牌设为胜利牌
			this.isVictoryCard(joueur);

			ordreJoueur++;
			if (ordreJoueur > this.joueurs.size()) {
				ordreJoueur = ordreJoueur - this.joueurs.size();
			}
			Joueur joueurNext = this.joueurs.get(ordreJoueur - 1);
			joueurNext.setPlayFalse();
			joueurNext.setMoveFalse();

			System.out.println("-------------------------------------------------------------------------");
			System.out.println("Joueur" + ordreJoueur + " " + joueurNext.getNom() + " jouer: ");

			if (joueurNext instanceof JoueurVirtuel) {
//				joueurNext.notifier();
				this.setVirtualPlayerl(joueurNext);
			} else {
				this.setChanged();
				this.notifyObservers();
//				joueurNext.notifier();
			}
		}
	}

	/**
	 * Set the last hand card as the victory card.
	 * 
	 * @param joueur The current player
	 */
	public void isVictoryCard(Joueur joueur) {
		if (joueur.getNumbreCarteMain() == 1) {
			Carte[] carteVictoire = { joueur.returnCarteMain(0), joueur.returnCarteMain(1), joueur.returnCarteMain(2) };
			for (int n = 0; n < 3; n++) {
				if (carteVictoire[n] != null) {
					joueur.setCarteVictoire(carteVictoire[n]);
				}
			}
		}
	}

	/**
	 * Set for virtual player
	 * 
	 * @param joueur The current player
	 */
	@Override
	public void setVirtualPlayerl(Joueur joueur) {
		Carte[] carteMain = { joueur.returnCarteMain(0), joueur.returnCarteMain(1), joueur.returnCarteMain(2) };
		System.out.println("Cartes en main: " + carteMain[0] + ", " + carteMain[1] + ", " + carteMain[2]);

		boolean jouer = false;
		while (!jouer) {
//			int i = (int) (Math.random() * 3);
			if (joueur.getNumbreCarteMain() > 1) {
//				if (carteMain[i] != null) {
//					Carte cartePlacer = joueur.getCarteMain(i);
//					((JoueurVirtuel) joueur).jouer(getDisposition(), cartePlacer);
//					jouer = true;
//					this.getDisposition().afficherLesCartes();
//				}
				((JoueurVirtuel) joueur).playCard(getDisposition());
				jouer = true;
				this.isVictoryCard(joueur);
			}
		}
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
