package fr.utt.lo02.projet.modele.mode;

import java.util.Iterator;

import fr.utt.lo02.projet.controleur.MouseCliquer;
import fr.utt.lo02.projet.modele.carte.Carte;
import fr.utt.lo02.projet.modele.joueur.Joueur;
import fr.utt.lo02.projet.modele.joueur.JoueurVirtuel;

/**
 * Inherited the game mode class. Is the specific process of normal mode.
 * 
 * @author Fulin and Dewen
 */
public class ModeNormal extends ModeJeu {

	/**
	 * Constructor
	 */
	public ModeNormal() {
		super();
	}

	/**
	 * Deal each player a card and randomly determine the first player to play
	 */
	@Override
	public void dealCarte() {
		partieEnCours = true;

		// cacher une carte
		getCartes().cacherCarte(); // 藏一张牌

		// every player gets a victory card x
		Iterator<Joueur> it = joueurs.iterator(); // 给每个玩家发一张胜利牌
		while (it.hasNext()) {
			Joueur j = it.next();
			j.setCarteVictoire(getCartes().dealCarte());
		}

		this.ordreJoueur = (int) (1 + Math.random() * this.joueurs.size()); // 随机选一个玩家开始游戏
		System.out.println("[Joueur" + ordreJoueur + " commence de jouer !]");
		System.out.println("-------------------------------------------------------------------------");

		this.setChanged();
		this.notifyObservers();

		Joueur joueur = this.joueurs.get(ordreJoueur - 1);

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
			Carte carteMain = null;

			System.out.print(joueur.getNom() + " pioche une carte: ");
			joueur.setCarteMain(this.getCartes().dealCarte(), 0); // 抓牌
			carteMain = joueur.returnCarteMain(0); // 获取玩家手牌
			System.out.println(carteMain);

			this.setChanged();
			this.notifyObservers();

			// 使用鼠标选择放牌
//				mouseCliquer.playCard(carteMain, i);
			mouseCliquer.setPlayCard(joueur, carteMain);

		}
	}

//	/**
//	 * move the card for button "Move"
//	 * 只有物理玩家采用可能按button
//	 * @author
//	 * @version
//	 */
//	public void move(int i, MouseCliquer mouseCliquer) {
//		this.ordreJoueur = i;
//		Joueur joueur = this.joueurs.get(ordreJoueur - 1); // 到此玩家出牌
//
//		System.out.println("Joeur" + i + " move la carte");
//		
//		mouseCliquer.setMoveCard(joueur);
//		
//	}

	/**
	 * If the player has played and has moved, then go to the next round.
	 * 
	 * @param joueur The current player
	 */
	@Override
	public void updateButton(Joueur joueur) {
		if (joueur.play == true && joueur.move == true) {
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
	 * Set for virtual player
	 * 
	 * @param joueur The current player
	 */
	@Override
	public void setVirtualPlayerl(Joueur joueur) {
		System.out.print(joueur.getNom() + " pioche une carte: ");
		joueur.setCarteMain(this.getCartes().dealCarte(), 0); // 抓牌

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Carte carteMain = joueur.returnCarteMain(0); // 获取玩家手牌

		System.out.println(carteMain);

		((JoueurVirtuel) joueur).jouer(getDisposition(), carteMain);
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

	public void setPlayed(int numeroJoueur) {
		Joueur j = this.joueurs.get(numeroJoueur - 1);
		j.play = true;
		this.setChanged();
		this.notifyObservers();
	}
	
}