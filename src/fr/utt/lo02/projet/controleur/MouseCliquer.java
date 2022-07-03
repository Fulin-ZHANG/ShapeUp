package fr.utt.lo02.projet.controleur;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import fr.utt.lo02.projet.modele.carte.Carte;
import fr.utt.lo02.projet.modele.connecteur.Connector;
import fr.utt.lo02.projet.modele.joueur.Joueur;
import fr.utt.lo02.projet.modele.mode.ModeAdvanced;
import fr.utt.lo02.projet.modele.mode.ModeNormal;

/**
 * The class {@code MouseCliquer} is used to handle mouse click events during game play
 * 
 * @author Dewen WU
 * @version 1.0
 */
public class MouseCliquer extends MouseAdapter {

	/**
	 * JFrame of class {@code GameFrame}
	 */
	private JFrame frame;
	/**
	 * An object of class {@code Connector}
	 */
	private Connector connector;

	/**
	 * The coordinates of the selected card, xyCarteChoisi[0] is the x-axis, xyCarteChoisi[1] is the y-axis
	 */
	private int[] xyCarteChoisi; // xyCarteChoisi[0]表示x, xyCarteChoisi[1]表示y
	/**
	 * The coordinates of the card to be placed, xyPlacer[0] is the x-axis, xyPlacer[1] is the y-axis
	 */
	private int[] xyPlacer; // 放牌的坐标
	/**
	 * The coordinates of the card to be moved, xyCarteMove[0] is the x-axis, xyCarteMove[1] is the y-axis
	 */
	private int[] xyCarteMove; // 选中移牌的坐标
	/**
	 * The direction of the card. "h", "b", "d", "g".
	 */
	private String direction;
	/**
	 * When it is true, it means that the currently pressed coordinate is the first coordinate, which is the adjacent coordinate of the coordinate to be placed. 
	 * Otherwise, it is the second coordinate, which is the coordinate where the card wants to be placed.
	 */
	private boolean etrePremierXY; // 当前鼠标点击位置是否为第一个坐标
	/**
	 * When it is true, it means that the currently pressed coordinate is the coordinate of the card that wants to be moved
	 */
	private boolean etreCarteMoveXY; // 当前鼠标点击位置是否为移牌的坐标

	/**
	 * Only when this attribute is true can the card be placed
	 */
	private boolean peutEtrePlacer; // 当前是否能放牌
	/**
	 * Only when this attribute is true can the card be moved
	 */
	private boolean peutEtreDeplacer; // 当前是否能移牌

	/**
	 * The card wants to be placed
	 */
	private Carte cartePlay;
	/**
	 * The card wants to be moved
	 */
	private Carte carteMove;
	/**
	 * Current player
	 */
	private Joueur joueur;

	/**
	 * When it is true, it means that the currently pressed coordinate is the coordinate of one of cards in hand
	 */
	private boolean choiceCardHandXY;
	/**
	 * The coordinates of the card in hand, xyCardMain[0] is the number of player, xyCarteMian[1] is number of card in hand
	 */
	private int[] xyCardMain; // 手牌坐标

	/**
	 * Construction method of class {@code MouseCliquer}
	 * @param frame The JFrame of class {@code GameFrame}
	 * @param connector Object of the class {@code Connector}
	 */
	public MouseCliquer(JFrame frame, Connector connector) {
		this.frame = frame;
		this.connector = connector;

		xyCarteChoisi = new int[2];
		xyPlacer = new int[2];
		xyCarteMove = new int[2];
		etrePremierXY = true;
		etreCarteMoveXY = true;

		peutEtrePlacer = false;
		peutEtreDeplacer = false;

		xyCardMain = new int[2];
	}

	/**
	 * The listener method of mouse pressed
	 * 
	 * This method is used to get the coordinates of the mouse click and 
	 * to place or move the cards according to the clicked coordinates.
	 * 
	 * @param e MouseEvent
	 */
	@Override
	public void mousePressed(MouseEvent e) {

		// mode normal
		if (connector.getJeu() instanceof ModeNormal) {
			// placer la carte
			if (peutEtrePlacer == true) {
				int x = e.getX();
				int y = e.getY();
				// 判断鼠标点击位置是否在棋盘上
//				if (x > 150 && x <= 650 && y > 166 && y <= 666) {
				if (x > 125 && x <= 675 && y > 145 && y <= 690) {
					int[] xy = this.getCoordinate(x, y);
					if (connector.getJeu().getDisposition().cartesTapis.isEmpty()) {
						//判断是否在框内
						if ((xy[0] != -1 && xy[0] != 5) && (xy[1] != -1 && xy[1] != 5)) {
							// 此处直接调用disposition来放牌
							connector.getJeu().getDisposition().placerCarte(cartePlay, xy);
							joueur.setPlayTrue();
							connector.getJeu().notifier();
							peutEtrePlacer = false;
						} else {
							JOptionPane.showMessageDialog(frame, "Out of space.",
									"Error choisi", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						// 第一个坐标为选中的牌的坐标
						if (etrePremierXY == true) {
							xyCarteChoisi[0] = xy[0];
							xyCarteChoisi[1] = xy[1];
							int point = xyCarteChoisi[1] * 10 + xyCarteChoisi[0];
							if (connector.getJeu().getDisposition().cartesTapis.get(point) != null) {
								etrePremierXY = false;
								JOptionPane.showMessageDialog(frame,
										"Vous avez choisi [" + xyCarteChoisi[0] + ", " + xyCarteChoisi[1] + "]" + "\n"
												+ "Veuillez choisir une direction.",
										"Carte choisi", JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(frame, "Veuillez choisir une carte existante.",
										"Error choisi", JOptionPane.ERROR_MESSAGE);
							}
						} else { // 第二个坐标为放牌的坐标
							xyPlacer[0] = xy[0];
							xyPlacer[1] = xy[1];

							// 判断两个点是否相邻
							if (sontAdjacents(xyCarteChoisi, xyPlacer) == true) {
								// 此处调用disposition来放牌
								JOptionPane.showMessageDialog(frame, "Vous avez choisi [ " + direction + " ]",
										"Direction choisi", JOptionPane.INFORMATION_MESSAGE);
								// 放牌成功之后，将peutEtrePress重置为false，并使下一次选中为第一个坐标(etrePremierXY = true)
								if (connector.getJeu().getDisposition().placerCarte(cartePlay, xyCarteChoisi,
										direction)) {
									// 判断结束
									if (connector.getJeu().etreTermine()) {
										this.terminerLeJeu();
									}
									
									joueur.setPlayTrue();
									connector.getJeu().notifier();
									connector.getJeu().updateButton(joueur);

									etrePremierXY = true;
									peutEtrePlacer = false;

								} else {
									etrePremierXY = true;
									JOptionPane.showMessageDialog(frame, "Ne pouvez pas la placer ici.", "Error choisi",
											JOptionPane.ERROR_MESSAGE);
								}
							} else {
								etrePremierXY = true; // 如果不相邻，重新选择第一个坐标
								JOptionPane.showMessageDialog(frame,
										"Ne sont adjacents, veuillez sélectionner à nouveau..", "Erreur choisi",
										JOptionPane.ERROR_MESSAGE);
							}
						}

					}
				}
			}
			// deplacer la carte
			else if (peutEtreDeplacer == true) {
				if (connector.getJeu().getDisposition().cartesTapis.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Tapis is empty.", "Cannot Move", JOptionPane.INFORMATION_MESSAGE);
					joueur.setMoveTrue();
					connector.getJeu().notifier();
					peutEtreDeplacer = false;
				} else {
					int x = e.getX();
					int y = e.getY();
					// 判断鼠标点击位置是否在棋盘上
//					if (x > 150 && x <= 650 && y > 166 && y <= 666) {
					if (x > 125 && x <= 675 && y > 145 && y <= 690) {
						int[] xy = this.getCoordinate(x, y);

						// 选择要移动的牌
						if (etreCarteMoveXY == true) {
							xyCarteMove[0] = xy[0];
							xyCarteMove[1] = xy[1];
							int point = xyCarteMove[1] * 10 + xyCarteMove[0];
							carteMove = connector.getJeu().getDisposition().cartesTapis.get(point);
							if (carteMove != null) {
								etreCarteMoveXY = false;
								JOptionPane.showMessageDialog(frame,
										"Vous avez choisi [" + xyCarteMove[0] + ", " + xyCarteMove[1] + "]" + "\n"
												+ "Veuillez choisir une carte.",
										"Carte Move", JOptionPane.INFORMATION_MESSAGE);
								connector.getJeu().getDisposition().removeCarte(point);
							} else {
								JOptionPane.showMessageDialog(frame, "Veuillez choisir une carte pour deplacer.",
										"Error choisi", JOptionPane.ERROR_MESSAGE);
							}
						}
						// 第一个坐标为选中的牌的坐标
						else if (etrePremierXY == true) {
							xyCarteChoisi[0] = xy[0];
							xyCarteChoisi[1] = xy[1];
							int point = xyCarteChoisi[1] * 10 + xyCarteChoisi[0];
							if (connector.getJeu().getDisposition().cartesTapis.get(point) != null) {
								etrePremierXY = false;
//								System.out.println("xy1: " + "x=" + xyCarteChoisi[0] + ", y=" + xyCarteChoisi[1]);
								JOptionPane.showMessageDialog(frame,
										"Vous avez choisi [" + xyCarteChoisi[0] + ", " + xyCarteChoisi[1] + "]" + "\n"
												+ "Veuillez choisir une direction.",
										"Carte choisi", JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(frame, "Veuillez choisir une carte existante.",
										"Error choisi", JOptionPane.ERROR_MESSAGE);
							}
						} else { // 第二个坐标为放牌的坐标
							xyPlacer[0] = xy[0];
							xyPlacer[1] = xy[1];
//							System.out.println("xy2: " + "x=" + xyPlacer[0] + ", y=" + xyPlacer[1]);

							// 判断两个点是否相邻
							if (sontAdjacents(xyCarteChoisi, xyPlacer) == true) {
								// 此处调用disposition来放牌
//								System.out.println("ils sont adjacents: " + direction + "接着放牌");
								JOptionPane.showMessageDialog(frame, "Vous avez choisi [ " + direction + " ]",
										"Direction choisi", JOptionPane.INFORMATION_MESSAGE);
								// 放牌成功之后，将peutEtrePress重置为false，并使下一次选中为第一个坐标(etrePremierXY = true)
								if (connector.getJeu().getDisposition().deplacerCarte(carteMove, xyCarteChoisi,
										direction)) {
									joueur.setMoveTrue();
									connector.getJeu().notifier();
									connector.getJeu().updateButton(joueur);
									etrePremierXY = true;
									peutEtreDeplacer = false;

									// 判断结束
									if (connector.getJeu().etreTermine()) {
										this.terminerLeJeu();
									}
								} else {
									etrePremierXY = true;
									JOptionPane.showMessageDialog(frame, "Ne pouvez pas la placer ici.", "Error choisi",
											JOptionPane.ERROR_MESSAGE);
								}
							} else {
								etrePremierXY = true; // 如果不相邻，重新选择第一个坐标
								JOptionPane.showMessageDialog(frame,
										"Ne sont adjacents, veuillez sélectionner à nouveau..", "Erreur choisi",
										JOptionPane.ERROR_MESSAGE);
							}
						}

					}
				}
			}
		}
		// mode advanced
		else {
			if (choiceCardHandXY == true) {
				int x = e.getX();
				int y = e.getY();
				if (this.isACardHand(x, y)) {
//					System.out.println("xy = " + xyCardMain[0] + xyCardMain[1]);
					if (joueur.getNumero() == xyCardMain[0]) {
//						System.out.println("Cartes main: " + joueur.returnCarteMain(0) + joueur.returnCarteMain(1) + joueur.returnCarteMain(2));
						cartePlay = joueur.getCarteMain(xyCardMain[1]);
						connector.getJeu().notifier();
						choiceCardHandXY = false;

					}
				}
			}
			// 开始放牌
			else if (peutEtrePlacer == true) {
				int x = e.getX();
				int y = e.getY();
				// 判断鼠标点击位置是否在棋盘上
//				if (x > 150 && x <= 650 && y > 166 && y <= 666) {
				if (x > 125 && x <= 675 && y > 145 && y <= 690) {
					int[] xy = this.getCoordinate(x, y);

					if (connector.getJeu().getDisposition().cartesTapis.isEmpty()) {
						//判断是否在框内
						if ((xy[0] != -1 && xy[0] != 5) && (xy[1] != -1 && xy[1] != 5)) {
							// 此处直接调用disposition来放牌
							connector.getJeu().getDisposition().placerCarte(cartePlay, xy);
//							System.out.println("放第一张牌");

							joueur.setPlayTrue();
							connector.getJeu().notifier();
							peutEtrePlacer = false;
						} else {
							JOptionPane.showMessageDialog(frame, "Out of space.", "Error choisi",
									JOptionPane.ERROR_MESSAGE);
						}
					} else {
						// 第一个坐标为选中的牌的坐标
						if (etrePremierXY == true) {
							xyCarteChoisi[0] = xy[0];
							xyCarteChoisi[1] = xy[1];
							int point = xyCarteChoisi[1] * 10 + xyCarteChoisi[0];
							if (connector.getJeu().getDisposition().cartesTapis.get(point) != null) {
								etrePremierXY = false;
//								System.out.println("xy1: " + "x=" + xyCarteChoisi[0] + ", y=" + xyCarteChoisi[1]);
								JOptionPane.showMessageDialog(frame,
										"Vous avez choisi [" + xyCarteChoisi[0] + ", " + xyCarteChoisi[1] + "]" + "\n"
												+ "Veuillez choisir une direction.",
										"Carte choisi", JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(frame, "Veuillez choisir une carte existante.",
										"Error choisi", JOptionPane.ERROR_MESSAGE);
							}
						} else { // 第二个坐标为放牌的坐标
							xyPlacer[0] = xy[0];
							xyPlacer[1] = xy[1];

							// 判断两个点是否相邻
							if (sontAdjacents(xyCarteChoisi, xyPlacer) == true) {
								// 此处调用disposition来放牌
//								System.out.println("ils sont adjacents: " + direction + "接着放牌");
								JOptionPane.showMessageDialog(frame, "Vous avez choisi [ " + direction + " ]",
										"Direction choisi", JOptionPane.INFORMATION_MESSAGE);
								// 放牌成功之后，将peutEtrePress重置为false，并使下一次选中为第一个坐标(etrePremierXY = true)
								if (connector.getJeu().getDisposition().placerCarte(cartePlay, xyCarteChoisi,
										direction)) {
									// 如果只剩一张手牌，则将其设为胜利牌
									((ModeAdvanced) connector.getJeu()).isVictoryCard(joueur);
									// 判断结束
									if (connector.getJeu().etreTermine()) {
										this.terminerLeJeu();
									}
									
									joueur.setPlayTrue();
									connector.getJeu().notifier();
									connector.getJeu().updateButton(joueur);

									etrePremierXY = true;
									peutEtrePlacer = false;
									
								} else {
									etrePremierXY = true;
									JOptionPane.showMessageDialog(frame, "Ne pouvez pas la placer ici.", "Error choisi",
											JOptionPane.ERROR_MESSAGE);
								}
							} else {
								etrePremierXY = true; // 如果不相邻，重新选择第一个坐标
								JOptionPane.showMessageDialog(frame,
										"Ne sont adjacents, veuillez sélectionner à nouveau..", "Erreur choisi",
										JOptionPane.ERROR_MESSAGE);
							}
						}

					}
				}
			}

			// deplacer la carte
			else if (peutEtreDeplacer == true) {
				if (connector.getJeu().getDisposition().cartesTapis.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Tapis is empty.", "Cannot Move", JOptionPane.INFORMATION_MESSAGE);
					joueur.setMoveTrue();
					connector.getJeu().notifier();
					peutEtreDeplacer = false;
				} else {
					int x = e.getX();
					int y = e.getY();
					// 判断鼠标点击位置是否在棋盘上
//					if (x > 150 && x <= 650 && y > 166 && y <= 666) {
					if (x > 125 && x <= 675 && y > 145 && y <= 690) {
						int[] xy = this.getCoordinate(x, y);

						// 选择要移动的牌
						if (etreCarteMoveXY == true) {
							xyCarteMove[0] = xy[0];
							xyCarteMove[1] = xy[1];
							int point = xyCarteMove[1] * 10 + xyCarteMove[0];
							carteMove = connector.getJeu().getDisposition().cartesTapis.get(point);
							if (carteMove != null) {
								etreCarteMoveXY = false;
								JOptionPane.showMessageDialog(frame,
										"Vous avez choisi [" + xyCarteMove[0] + ", " + xyCarteMove[1] + "]" + "\n"
												+ "Veuillez choisir une carte.",
										"Carte Move", JOptionPane.INFORMATION_MESSAGE);
								connector.getJeu().getDisposition().removeCarte(point);
							} else {
								JOptionPane.showMessageDialog(frame, "Veuillez choisir une carte pour deplacer.",
										"Error choisi", JOptionPane.ERROR_MESSAGE);
							}
						}
						// 第一个坐标为选中的牌的坐标
						else if (etrePremierXY == true) {
							xyCarteChoisi[0] = xy[0];
							xyCarteChoisi[1] = xy[1];
							int point = xyCarteChoisi[1] * 10 + xyCarteChoisi[0];
							if (connector.getJeu().getDisposition().cartesTapis.get(point) != null) {
								etrePremierXY = false;
//								System.out.println("xy1: " + "x=" + xyCarteChoisi[0] + ", y=" + xyCarteChoisi[1]);
								JOptionPane.showMessageDialog(frame,
										"Vous avez choisi [" + xyCarteChoisi[0] + ", " + xyCarteChoisi[1] + "]" + "\n"
												+ "Veuillez choisir une direction.",
										"Carte choisi", JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(frame, "Veuillez choisir une carte existante.",
										"Error choisi", JOptionPane.ERROR_MESSAGE);
							}
						} else { // 第二个坐标为放牌的坐标
							xyPlacer[0] = xy[0];
							xyPlacer[1] = xy[1];
//							System.out.println("xy2: " + "x=" + xyPlacer[0] + ", y=" + xyPlacer[1]);

							// 判断两个点是否相邻
							if (sontAdjacents(xyCarteChoisi, xyPlacer) == true) {
								// 此处调用disposition来放牌
//								System.out.println("ils sont adjacents: " + direction + "接着放牌");
								JOptionPane.showMessageDialog(frame, "Vous avez choisi [ " + direction + " ]",
										"Direction choisi", JOptionPane.INFORMATION_MESSAGE);
								// 放牌成功之后，将peutEtrePress重置为false，并使下一次选中为第一个坐标(etrePremierXY = true)
								if (connector.getJeu().getDisposition().deplacerCarte(carteMove, xyCarteChoisi,
										direction)) {
									joueur.setMoveTrue();
									connector.getJeu().notifier();
									connector.getJeu().updateButton(joueur);
									etrePremierXY = true;
									peutEtreDeplacer = false;

									// 判断结束
									if (connector.getJeu().etreTermine()) {
										this.terminerLeJeu();
									}
								} else {
									etrePremierXY = true;
									JOptionPane.showMessageDialog(frame, "Ne pouvez pas la placer ici.", "Error choisi",
											JOptionPane.ERROR_MESSAGE);
								}
							} else {
								etrePremierXY = true; // 如果不相邻，重新选择第一个坐标
								JOptionPane.showMessageDialog(frame,
										"Ne sont adjacents, veuillez sélectionner à nouveau..", "Erreur choisi",
										JOptionPane.ERROR_MESSAGE);
							}
						}

					}
				}
			}
		}

	}

	/**
	 * The method sontAdjacents is used to determine whether two coordinates are adjacent to each other
	 * If two coordinates are adjacent to each other, determine their relative positions and saved to attribute direction
	 * 
	 * @param xyCarteChoisi The coordinates of the selected card
	 * @param xyPlacer The coordinates of the card wants to be placed
	 * @return {@code true} if the two coordinates are adjacent
	 */
	private boolean sontAdjacents(int[] xyCarteChoisi, int[] xyPlacer) {
		if (xyCarteChoisi[0] == xyPlacer[0]) {
			if (xyCarteChoisi[1] - xyPlacer[1] == 1) {
				direction = "h"; // haut
				return true;
			} else if (xyPlacer[1] - xyCarteChoisi[1] == 1) {
				direction = "b"; // bas
				return true;
			} else {
				return false;
			}
		} else if (xyCarteChoisi[1] == xyPlacer[1]) {
			if (xyCarteChoisi[0] - xyPlacer[0] == 1) {
				direction = "g"; // gauche
				return true;
			} else if (xyPlacer[0] - xyCarteChoisi[0] == 1) {
				direction = "d"; // droit
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * Transform the coordinates of mouse clicks to the coordinates of the layout
	 * 
	 * @param x x coordinates of the mouse pointer
	 * @param y y coordinates of the mouse pointer
	 * @return the coordinates of the layout
	 */
	public int[] getCoordinate(int x, int y) {
		int[] xy = new int[2];
		if (x > 125 && x <= 150) {
			xy[0] = -1;
		} else if (x > 150 && x <= 250) {
			xy[0] = 0;
		} else if (x > 250 && x <= 350) {
			xy[0] = 1;
		} else if (x > 350 && x <= 450) {
			xy[0] = 2;
		} else if (x > 450 && x <= 550) {
			xy[0] = 3;
		} else if (x > 550 && x <= 650) {
			xy[0] = 4;
		} else if (x > 650 && x <= 675) {
			xy[0] = 5;
		}

		if (y > 145 && y <= 166) {
			xy[1] = -1;
		} else if (y > 166 && y <= 266) {
			xy[1] = 0;
		} else if (y > 266 && y <= 366) {
			xy[1] = 1;
		} else if (y > 366 && y <= 466) {
			xy[1] = 2;
		} else if (y > 466 && y <= 566) {
			xy[1] = 3;
		} else if (y > 566 && y <= 666) {
			xy[1] = 4;
		} else if (y > 666 && y <= 690) {
			xy[1] = 5;
		}

		return xy;
	}

	/**
	 * Determine whether the coordinates pinter is the position of the card in the
	 * hand If it is the position of the card in the hand, then set the attribute
	 * xyCardMain
	 * 
	 * @param x x coordinates of the mouse pointer
	 * @param y y coordinates of the mouse pointer
	 * @return {@code true} if the coordinates pointer is the position of the card
	 *         in the hand
	 */
	public boolean isACardHand(int x, int y) {
		if (x >= 26 && x <= 126) {
			if (y >= 366 && y <= 466) {
				xyCardMain[0] = 1;
				xyCardMain[1] = 0;
				return true;
			} else if (y > 466 && y <= 566) {
				xyCardMain[0] = 1;
				xyCardMain[1] = 1;
				return true;
			} else if (y > 566 && y <= 666) {
				xyCardMain[0] = 1;
				xyCardMain[1] = 2;
				return true;
			} else {
				return false;
			}
		} else if (x >= 673 && x <= 773) {
			if (y >= 366 && y <= 466) {
				xyCardMain[0] = 2;
				xyCardMain[1] = 0;
				return true;
			} else if (y > 466 && y <= 566) {
				xyCardMain[0] = 2;
				xyCardMain[1] = 1;
				return true;
			} else if (y > 566 && y <= 666) {
				xyCardMain[0] = 2;
				xyCardMain[1] = 2;
				return true;
			} else {
				return false;
			}
		} else if ((x >= 350 && x <= 650) && (y >= 710 && y <= 790)) {
			if (x >= 350 && x <= 450) {
				xyCardMain[0] = 3;
				xyCardMain[1] = 0;
				return true;
			} else if (x > 450 && x <= 550) {
				xyCardMain[0] = 3;
				xyCardMain[1] = 1;
				return true;
			} else if (x > 550 && x <= 650) {
				xyCardMain[0] = 3;
				xyCardMain[1] = 2;
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Initialize the action of playing a card of mode normal via clicking the mouse
	 * This method is called by class ModeNormal
	 * 
	 * @param joueur Current player
	 * @param carteMain A card in hand
	 */
	public void setPlayCard(Joueur joueur, Carte carteMain) {
		if (!connector.getJeu().getDisposition().cartesTapis.isEmpty()) {
			JOptionPane.showMessageDialog(frame, "Veuillez choisir une carte existante.", "Choisissez une carte",
					JOptionPane.INFORMATION_MESSAGE);
		}
		peutEtrePlacer = true;
		etrePremierXY = true; // 确保选中的第一个坐标是棋盘上已存在的牌，将牌放置在该牌四围
		cartePlay = carteMain;

		this.joueur = joueur;
	}

	// 用于ModeNormal调用
	/**
	 * Initialize the action of moving a card via clicking the mouse
	 * This method is called by class ModeJeu
	 * 
	 * @param joueur Current player
	 */
	public void setMoveCard(Joueur joueur) {
		if (!connector.getJeu().getDisposition().cartesTapis.isEmpty()) {
			JOptionPane.showMessageDialog(frame, "Veuillez choisir une carte pour deplacer.", "Deplacer une carte",
					JOptionPane.INFORMATION_MESSAGE);
		}
		peutEtreDeplacer = true;
		etreCarteMoveXY = true; // 确保选中的第一个坐标是需要移动的牌
//		numeroJoueur = joueur.getNumero();

		this.joueur = joueur;
	}

	// choice a card in hand for mode advanced
	// 用于ModeAdvanced调用
	/**
	 * Initialize the action of playing a card of mode advanced via clicking the mouse
	 * This method is called by class ModeAdvanced
	 * 
	 * @param joueur Current player
	 */
	public void setChoiceCardHand(Joueur joueur) {
		JOptionPane.showMessageDialog(frame, "Veuillez choisir une carte main pour placer.", "Choisissez une carte",
				JOptionPane.INFORMATION_MESSAGE);
		this.choiceCardHandXY = true;
		this.joueur = joueur;

		peutEtrePlacer = true;
		etrePremierXY = true;
	}

	// 游戏结束时需要进行的处理
	/**
	 * When the game is over, to display the scores of each player and the winner who got the highest score
	 */
	public void terminerLeJeu() {
		System.out.println("‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣‣");
		connector.getJeu().calculerScores();
		int[] score = new int[3];
		int scoreWin = 0;
		int nbJoueurWin = 0;
		Iterator<Joueur> it = connector.getJeu().getJoueurs().iterator();
		while (it.hasNext()) {
			Joueur j = it.next();
			score[j.getNumero() - 1] = j.getScore();
			if (j.getScore() > scoreWin) {
				scoreWin = j.getScore();
				nbJoueurWin = j.getNumero();
			}
		}
		if (connector.getJeu().getNbJoueur() == 2) {
			System.out.println("Joueur " + nbJoueurWin + " win!");
			JOptionPane.showMessageDialog(frame,
					"Joueur 1: " + score[0] + "\n" + "Joueur 2: " + score[1] + "\n" + "Joueur " + nbJoueurWin + " win!",
					"Game Over", JOptionPane.INFORMATION_MESSAGE);
		} else {
			System.out.println("Joueur " + nbJoueurWin + " win!");
			JOptionPane
					.showMessageDialog(frame,
							"Joueur 1: " + score[0] + "\n" + "Joueur 2: " + score[1] + "\n" + "Joueur 3: " + score[2]
									+ "\n" + "Joueur " + nbJoueurWin + " win!",
							"Game Over", JOptionPane.INFORMATION_MESSAGE);
		}
		frame.dispose();
	}

}
