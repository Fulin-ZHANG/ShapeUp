package fr.utt.lo02.projet.vue;

import java.util.Observable;
import java.util.Observer;

import fr.utt.lo02.projet.modele.carte.Carte;
import fr.utt.lo02.projet.modele.connecteur.Connector;
import fr.utt.lo02.projet.modele.formeTapis.Disposition;
import fr.utt.lo02.projet.modele.joueur.Joueur;
import fr.utt.lo02.projet.modele.mode.ModeAdvanced;
import fr.utt.lo02.projet.modele.mode.ModeInverse;
import fr.utt.lo02.projet.modele.mode.ModeJeu;
import fr.utt.lo02.projet.modele.mode.ModeNormal;

public class GameTexte implements Runnable, Observer {

	private Connector connector;
	private ModeJeu jeu;

	public GameTexte(Connector connector) {
		this.connector = connector;
		this.jeu = connector.getJeu();

		this.connector.getJeu().addObserver(this);
		this.connector.getJeu().getDisposition().addObserver(this);

		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {

		do {
			String input = MenuTexte.lireChaine();

			int ordreJoueur = jeu.getOrdreJoueur();
			if (ordreJoueur != 0) {
				Joueur joueur = jeu.getJoueurs().get(ordreJoueur - 1);
				// mode normal
				if (jeu instanceof ModeNormal || jeu instanceof ModeInverse) {
					switch (input) {
					case "p":
						if (joueur.getPlay() == false) {
							System.out.print(joueur.getNom() + " pioche une carte: ");
							joueur.setCarteMain(jeu.getCartes().dealCarte(), 0); // 抓牌
							Carte carteMain = joueur.returnCarteMain(0); // 获取玩家手牌
							System.out.println(carteMain);

							jeu.notifier();// 更新显示玩家手牌

							jeu.getDisposition().jouerCarte(carteMain); // 往棋盘上放牌

							if (jeu.etreTermine()) {
								jeu.calculerWinner(); // 游戏结束，输出胜利者
								System.exit(0);
							}

							joueur.setPlayTrue();
							jeu.notifier();
							jeu.updateButton(joueur);

						} else {
							System.out.println("Vous avez deja place!");
						}
						break;
					case "m":
						if (joueur.getMove() == false) {
							jeu.getDisposition().moveCarte();

							joueur.setMoveTrue();
							jeu.notifier();
							jeu.updateButton(joueur);
						} else {
							System.out.println("Vous avez deja deplace!");
						}
						break;

					case "n":
						if (joueur.getPlay() == true) {
							jeu.nextPlayer(joueur.getNumero());
						} else {
							System.out.println("Pas encore placer!");
						}
						break;
					default:
						break;
					}
				}
				// mode advanced
				else if (jeu instanceof ModeAdvanced) {
					switch (input) {
					case "p":
						if (joueur.getPlay() == false) {
							System.out.println("Cartes main: " + joueur.returnCarteMain(0) + joueur.returnCarteMain(1) + joueur.returnCarteMain(2));
							System.out.println("Veuillez choisir une carte de main: [1/2/3]");
							boolean choisir = false;
							while (!choisir) {
								int nbCarteMain = Integer.parseInt(MenuTexte.lireChaine());
								if ((nbCarteMain == 1 || nbCarteMain == 2 || nbCarteMain == 3) && joueur.returnCarteMain(nbCarteMain - 1) != null) {
									Carte cartePlacer = joueur.getCarteMain(nbCarteMain - 1);
									System.out.println(joueur.getNom() + " choisit la carte: " + cartePlacer);

									jeu.notifier();

									jeu.getDisposition().jouerCarte(cartePlacer); // 往棋盘上放牌

									if (jeu.etreTermine()) {
										jeu.calculerWinner(); // 游戏结束，输出胜利者
										System.exit(0);
									}

									joueur.setPlayTrue();
									jeu.notifier();
									jeu.updateButton(joueur);
									choisir = true;
								} else {
									System.out.println("Commande non reconnue ou carte choisi n'exist pas...");
									System.out.println("Veuillez choisir encore fois");
								}
							}

						} else {
							System.out.println("Vous avez deja place!");
						}
						break;
					case "m":
						if (joueur.getMove() == false) {
							jeu.getDisposition().moveCarte();

							joueur.setMoveTrue();
							jeu.notifier();
							jeu.updateButton(joueur);
						} else {
							System.out.println("Vous avez deja deplace!");
						}
						break;

					case "n":
						if (joueur.getPlay() == true) {
							jeu.nextPlayer(joueur.getNumero());
						} else {
							System.out.println("Pas encore placer!");
						}
						break;
					default:
						break;
					}
				}
			}

		} while (!jeu.etreTermine());

	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof ModeJeu) {
			this.jeu = (ModeJeu) o;
			System.out.println("[p = play|m = move|n = next]");
		}

		if (o instanceof Disposition) {
			Disposition disposition = (Disposition) o;
			System.out.println(disposition);
		}
	}
}
