package fr.utt.lo02.projet.modele.formeTapis;

import java.util.*;

import fr.utt.lo02.projet.modele.calculateur.Element;
import fr.utt.lo02.projet.modele.calculateur.Visiteur;
import fr.utt.lo02.projet.modele.carte.Carte;
import fr.utt.lo02.projet.vue.MenuTexte;

/**
 * The class {@code Disposition} is used to define the layout of cards.
 * <p>
 * 
 * An object of this class has an attribute that holds the cards
 * {@code cartesTapis}, an attribute that defines the layout of the cards
 * {@code formeTapis} and other necessary attributes.
 * 
 * @author Dewen WU and Fulin ZHANG
 * @version 1.0
 */
public class Disposition extends Observable implements Element {

	/**
	 * The coordinates of the table. The tens digit represents the ordinate y, the
	 * units digit represents the abscissa x. The range of x and y is 1 to 5.
	 */
	public final Integer[][] yx = { { 00, 01, 02, 03, 04 }, { 10, 11, 12, 13, 14 }, { 20, 21, 22, 23, 24 },
			{ 30, 31, 32, 33, 34 }, { 40, 41, 42, 43, 44 } };

	/**
	 * Cards on the table
	 */
	public HashMap<Integer, Carte> cartesTapis;

	/**
	 * The coordinates corresponding to a location on the table
	 */
	private int point; // 放牌的位置

	/**
	 * The direction of a card on the table
	 */
	private String direction; // 方向

	/**
	 * The coordinates of the card wants to be moved
	 */
	private int pointMove; // 所选需要移动的牌的位置

	/**
	 * The card wants to be moved
	 */
	private Carte carteMove; // 需要移动的牌

	/**
	 * Defines the shape of the layout
	 */
	public FormeTapis formeTapis;

	/**
	 * Constructor of class {@code Disposition}
	 */
	public Disposition() {
		cartesTapis = new HashMap<>();
	}

	/**
	 * Allow calculators to access this class.
	 */
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}

	/**
	 * The method of playing a card when the table is not empty for GUI
	 * 
	 * @param carte     The card wants to be played，
	 * @param xy        xy[0] stands for the abscissa x，xy[1] stands for the
	 *                  ordinate y
	 * @param direction The direction of the card
	 * @return {@code true} if the card can be played
	 */
	public boolean placerCarte(Carte carte, int[] xy, String direction) {
		int point = xy[1] * 10 + xy[0];
		if (this.formeTapis.peutEtrePlacer(point, carte, direction)) {
			this.setChanged();
			this.notifyObservers();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * The method of playing a card when the table is empty for GUI
	 * 
	 * @param carte The card wants to be played，
	 * @param xy    xy[0] stands for the abscissa x，xy[1] stands for the ordinate y
	 */
	public void placerCarte(Carte carte, int[] xy) {
		cartesTapis.put(xy[1] * 10 + xy[0], carte);
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * The method of moving a card for GUI
	 * 
	 * <pre>
	 * Play the card if the condition is met. Then judge whether the layout 
	 * conforms to the the adjacency rule after placing the card. If it not 
	 * conforms, then the card just placed will be removed.
	 * </pre>
	 * 
	 * @param carte     The card wants to be moved，
	 * @param xy        xy[0] stands for the abscissa x，xy[1] stands for the
	 *                  ordinate y
	 * @param direction The direction of the card
	 * @return {@code true} if the card can be played and the layout after placing
	 *         the card conforms to the the adjacency rule
	 */
	public boolean deplacerCarte(Carte carte, int[] xy, String direction) {
		int point = xy[1] * 10 + xy[0];
		if (this.formeTapis.peutEtrePlacer(point, carte, direction)) {
			if (this.peutEtreDeplacee()) {
				System.out.println("[ Carte a ete deplacee avec succes ! ]"); // 如果符合正交法则，则提示成功移牌
//				choisiDirect = true;
//				choisiCoord = true;
//				choisiCoordMove = true;
//				break;
				this.setChanged();
				this.notifyObservers();
				return true;
			} else { // 放置牌后，不符合正交法则，则将刚刚放置的牌移除
				if ("d".equals(direction)) { // 在右侧放置
					if (point % 10 == 4) {
						cartesTapis.remove(point);
					} else {
						cartesTapis.remove(point + 1);
					}
				} else if ("g".equals(direction)) { // 在左侧放置
					if (point % 10 == 0) {
						cartesTapis.remove(point);
					} else {
						cartesTapis.remove(point - 1);
					}
				} else if ("h".equals(direction)) { // 在上测放置
					if (point / 10 == 0) {
						cartesTapis.remove(point);
					} else {
						cartesTapis.remove(point - 10);
					}
				} else { // 在下测放置
					if (point / 10 == 4) {
						cartesTapis.remove(point);
					}
					cartesTapis.remove(point + 10);
				}
//				cartesTapis.put(pointMove, carteMove);    //将取出的牌放回原来的位置
				System.out.println("Ne pouvez pas de deplacer la carte !");
//				choisiCoord = false;    // 如果不能放牌，则回到重新选牌
//				choisiCoordMove = false;
				this.setChanged();
				this.notifyObservers();
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Remove a card specified by a coordinate.
	 * 
	 * @param pointMove The coordinate of the card wants to be moved
	 */
	public void removeCarte(int pointMove) {
		cartesTapis.remove(pointMove);
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Update the display of the cards in table for GUI.
	 */
	public void afficherLesCartes() {
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * The method of playing a card for the command line
	 * 
	 * <pre>
	 * If the table is empty, choose any coordinate to put the card; otherwise the
	 * player needs to choose a card on the table first, and then choose one of the
	 * directions (top, bottom, left and right) of that card to play.
	 * </pre>
	 * 
	 * @param carte The card wants to be played
	 */
	public void jouerCarte(Carte carte) {

		if (cartesTapis.isEmpty()) {
//			cartesTapis.put(yx[2][2], carte);
			System.out.println("Veuillez choisir une point: ");
			int point = Integer.parseInt(MenuTexte.lireChaine());

			cartesTapis.put(point, carte);
			this.setChanged();
			this.notifyObservers();

		} else {

			boolean choisiCoord = false; // 坐标
			boolean choisiDirect; // 位置

			while (choisiCoord == false) { // 循环，直到选了一张已经存在的牌为止
				System.out.println(this);
				System.out.println("Choisir une coordonnee d'une carte existant:");

//				Scanner inCoord = new Scanner(System.in); // 选牌位置
//				try {
//					this.point = inCoord.nextInt(); // 获取所选牌的位置point。此处需要捕获异常
//				} catch (InputMismatchException i) {
//					System.out.println("[Input error !]");
//					break;
//				}
				this.point = Integer.parseInt(MenuTexte.lireChaine());

				Carte carteChoisi = cartesTapis.get(this.point); // 所选位置对应的牌

				if (carteChoisi != null) {
					choisiCoord = true;
				} else {
					System.out.println("La coordonnes n'existe pas. Veuillez choisir encore une fois");
				}

				choisiDirect = false;
				while (choisiCoord == true && choisiDirect == false) {
					System.out.println("Veuillez choisir la direction: [d = droit|g = gauche|h = haut|b = bas]");

//					Scanner inDirect = new Scanner(System.in);
//
//					this.direction = inDirect.nextLine(); 
					this.direction = MenuTexte.lireChaine();

					if (this.formeTapis.peutEtrePlacer(point, carte, direction)) { // 判断是否可以放牌
						System.out.println("[ Carte a ete jouee avec succes ! ]"); // 可以放牌，则将手上的牌放到盘上
//						System.out.println("-------------------------------------------------------------------------------------");
						choisiDirect = true;
						this.setChanged();
						this.notifyObservers();
					} else {
//						System.out.println("Choisi faux!");
						choisiDirect = true;
						choisiCoord = false; // 如果不能放牌，则回到重新选牌
					}
				}
			}
		}
	}

	/**
	 * The method of moving a card for the command line
	 * 
	 * <pre>
	 * If there are less than 2 cards on the table, the player cannot move the card;
	 * otherwise, the player needs to select a card wants to be moved first, then
	 * choose a card on the table and then choose one of the directions (top,
	 * bottom, left and right) of that card to play.
	 * 
	 * Play the card if the condition is met. Then judge whether the layout 
	 * conforms to the the adjacency rule after placing the card. If it not 
	 * conforms, then the card just placed will be removed.
	 * </pre>
	 * 
	 */
	public void moveCarte() {
		if (cartesTapis.size() < 2) {
			System.out.println("Ne pouvez pas de deplacer car il y a moins de deux cartes!");
		} else {
			boolean choisiCoord = false; // 坐标
			boolean choisiDirect; // 位置
			boolean choisiCoordMove = false;
//			Carte carteMove;
//			int pointMove;

			// 选一张需要移动的牌
			while (choisiCoordMove == false) {
				System.out.println(this);
				System.out.println("Veuillez choisir une carte à déplacer: ");
//				System.out.print("> ");

//				Scanner inMove = new Scanner(System.in); // 选牌位置
				try {
//					pointMove = inMove.nextInt(); // 获取所选牌的位置point。此处需要捕获异常
					pointMove = Integer.parseInt(MenuTexte.lireChaine());

					this.carteMove = cartesTapis.get(pointMove); // 所选位置对应的牌
					if (carteMove != null) {
						choisiCoordMove = true;
						cartesTapis.remove(pointMove); // 将所选牌从cartesTapis上移除
//						System.out.println("Ne pouvez pas de deplacer cette carte!");
						this.setChanged();
						this.notifyObservers();
					} else {
						System.out.println("La coordonnes n'existe pas. Veuillez choisir encore une fois");
					}
				} catch (InputMismatchException i) {
					System.out.println("[Input error !]");
					break;
				}

				// 选一张存在的牌，并将需要移动的牌放在此牌周围
				while (choisiCoord == false) { // 循环，直到选了一张已经存在的牌为止
					System.out.println(this);
					System.out.println("Choisir une coordonnes d'une carte existant: ");
//					System.out.print("> ");

//					Scanner inCoord = new Scanner(System.in); // 选牌位置
					try {
//						this.point = inCoord.nextInt(); // 获取所选牌的位置point。此处需要捕获异常
						this.point = Integer.parseInt(MenuTexte.lireChaine());
					} catch (InputMismatchException i) {
						System.out.println("[Input error !]");
						break;
					}
//				inCoord.close();

					Carte carteChoisi = cartesTapis.get(this.point); // 所选位置对应的牌

					if (carteChoisi != null) {
						choisiCoord = true;
					} else {
						System.out.println("La coordonnes n'existe pas. Veuillez choisir encore une fois");
					}

					choisiDirect = false;
					while (choisiCoord == true && choisiDirect == false) {
						System.out.println("Veuillez choisir la direction: [d = droit|g = gauche|h = haut|b = bas]");
						this.direction = MenuTexte.lireChaine();

						if (this.formeTapis.peutEtrePlacer(point, carteMove, direction)) { // 判断是否可以放牌
							if (this.peutEtreDeplacee()) {
								System.out.println("[ Carte a ete deplacee avec succes ! ]"); // 如果符合正交法则，则提示成功移牌
								choisiDirect = true;
								choisiCoord = true;
								choisiCoordMove = true;
								this.setChanged();
								this.notifyObservers();
								break;
							} else { // 放置牌后，不符合正交法则，则将刚刚放置的牌移除
								if ("d".equals(direction)) { // 在右侧放置
									if (point % 10 == 4) {
										cartesTapis.remove(point);
									} else {
										cartesTapis.remove(point + 1);
									}
								} else if ("g".equals(direction)) { // 在左侧放置
									if (point % 10 == 0) {
										cartesTapis.remove(point);
									} else {
										cartesTapis.remove(point - 1);
									}
								} else if ("h".equals(direction)) { // 在上测放置
									if (point / 10 == 0) {
										cartesTapis.remove(point);
									} else {
										cartesTapis.remove(point - 10);
									}
								} else { // 在下测放置
									if (point / 10 == 4) {
										cartesTapis.remove(point);
									}
									cartesTapis.remove(point + 10);
								}
								System.out.println("Ne pouvez pas de deplacer la carte !");
								choisiCoord = false; // 如果不能放牌，则回到重新选牌
								choisiCoordMove = false;
							}
						} else {
							choisiDirect = true;
							choisiCoord = false; // 如果不能放牌，则回到重新选牌
							choisiCoordMove = false;
						}
					}
				}
			}
		}
	}

	/**
	 * Calculate the number of cards for each row
	 * 
	 * @return The number of cards of each row
	 */
	public Integer[] calculerLengthLigne() {
		Integer[] length = { 0, 0, 0, 0, 0 };
		for (int i = 0; i < yx.length; i++) {
			for (int j = 0; j < yx[0].length; j++) {
				if (this.cartesTapis.get(yx[i][j]) != null) {
					length[i]++;
				}
			}
		}
		return length;
	}

	/**
	 * Calculate the number of cards for each column
	 * 
	 * @return The number of cards of each column
	 */
	public Integer[] calculerLengthColonne() {
		Integer[] length = { 0, 0, 0, 0, 0 };
		for (int j = 0; j < yx[0].length; j++) { // 有多少列
			for (int i = 0; i < yx.length; i++) { // 有多少行
				if (this.cartesTapis.get(yx[i][j]) != null) {
					length[j]++;
				}
			}
		}
		return length;
	}

	/**
	 * Move each card on the table one unit to the left
	 */
	public void deplacerAGauche() {
		HashMap<Integer, Carte> newMap = new HashMap<>();
		Iterator<Map.Entry<Integer, Carte>> iterator = this.cartesTapis.entrySet().iterator();
		Map.Entry<Integer, Carte> entry;
		while (iterator.hasNext()) {
			entry = iterator.next();
			// 往newMap中放入新的Entry
			newMap.put(entry.getKey() - 1, entry.getValue());
			// 删除老的Entry
			iterator.remove();
		}
		this.cartesTapis = newMap;
	}

	/**
	 * Move each card on the table one unit to the right
	 */
	public void deplacerADroit() {
		HashMap<Integer, Carte> newMap = new HashMap<>();
		Iterator<Map.Entry<Integer, Carte>> iterator = this.cartesTapis.entrySet().iterator();
		Map.Entry<Integer, Carte> entry;
		while (iterator.hasNext()) {
			entry = iterator.next();
			// 往newMap中放入新的Entry
			newMap.put(entry.getKey() + 1, entry.getValue());
			// 删除老的Entry
			iterator.remove();
		}
		this.cartesTapis = newMap;
	}

	/**
	 * Move each card on the table up by one unit
	 */
	public void deplacerVersLeHaut() {
		HashMap<Integer, Carte> newMap = new HashMap<>();
		Iterator<Map.Entry<Integer, Carte>> iterator = this.cartesTapis.entrySet().iterator();
		Map.Entry<Integer, Carte> entry;
		while (iterator.hasNext()) {
			entry = iterator.next();
			// 往newMap中放入新的Entry
			newMap.put(entry.getKey() - 10, entry.getValue());
			// 删除老的Entry
			iterator.remove();
		}
		this.cartesTapis = newMap;
	}

	/**
	 * Move each card on the table down by one unit.
	 */
	public void deplacerALaBaisse() {
		HashMap<Integer, Carte> newMap = new HashMap<>();
		Iterator<Map.Entry<Integer, Carte>> iterator = this.cartesTapis.entrySet().iterator();
		Map.Entry<Integer, Carte> entry;
		while (iterator.hasNext()) {
			entry = iterator.next();
			// 往newMap中放入新的Entry
			newMap.put(entry.getKey() + 10, entry.getValue());
			// 删除老的Entry
			iterator.remove();
		}
		this.cartesTapis = newMap;
	}

	public int getNombreCartes() {
		return this.cartesTapis.size();
	}

	/**
	 * Judge whether the layout conforms to the the adjacency rule after placing the
	 * card.
	 * 
	 * <pre>
	 * Use a 5*5 array yx. yx[i][j], i means the ith row, j means the jth column, if
	 * the value of yx[i][j] is equal to 1, then there is a card in this position,
	 * else this position is empty. 
	 * Then determine in turn whether each position with a value of 1 in the array 
	 * xy can be connected to all other positions with a value of 1 by using the 
	 * method checkPass().
	 * </pre>
	 * 
	 * @return {@code true} if each position with a value of 1 in the array xy can
	 *         be connected to all other positions with a value of 1
	 */
	public boolean peutEtreDeplacee() {
		int[][] map = new int[5][5];

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (this.cartesTapis.get(yx[i][j]) != null) { // 如果不为空，则存入数组的对应值为1
					map[i][j] = 1;
				} else { // 否则为0
					map[i][j] = 0;
				}
			}
		}
		int x1;
		int y1;
		int x2;
		int y2;
		for (x1 = 0; x1 < map.length; x1++) {
			for (y1 = 0; y1 < map[0].length; y1++) {
				if (map[x1][y1] == 1) { // 第一个坐标点x1, y1

					for (x2 = x1; x2 < map.length; x2++) {
						if (x2 == x1) { // 如果位于同行，y2取其下一个坐标
							for (y2 = y1 + 1; y2 < map[0].length; y2++) {
								if (map[x2][y2] == 1) { // 第二个坐标点x2, y2
									int[] points = { x1, y1, x2, y2 };
									if (!this.checkPass(map, new int[5][5], points)) {
										return false; // 两个点不连通，返回false
									}
								}
							}
						} else { // 位于不同行，y2从0开始
							for (y2 = 0; y2 < map[0].length; y2++) {
								if (map[x2][y2] == 1) { // 第二个坐标点x2, y2
									int[] points = { x1, y1, x2, y2 };
									if (!this.checkPass(map, new int[5][5], points)) {
										return false; // 两个点不连通，返回false
									}
								}
							}
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * Determine whether the two points x1y1 and x2y2 are connected. Two points are
	 * connected, which means that at least one of the four directions of one point
	 * can form a path with the other point.
	 * 
	 * <pre>
	 * 1. Recursive search to determine whether the values of x1y1 up, down, left,
	 * and right are equal to itself 
	 * 2. If there is an equal direction, take the coordinate of this direction as 
	 * x1y1 and repeat the step 1 
	 * 3. If recursively to the coordinates of x1y1 equal to the coordinates of x2y2, 
	 * return true and end the search; otherwise return false.
	 * </pre>
	 * 
	 * @param map     Two-dimensional array of square matrix
	 * @param visited Indicates whether the corresponding point has been visited
	 * @param points  Two points that need to be queried
	 * @return {@code true} if two points are connected
	 */
	public boolean checkPass(int[][] map, int[][] visited, int[] points) {
		int y1 = points[0];
		int x1 = points[1];
		int y2 = points[2];
		int x2 = points[3];
		boolean top = false;
		boolean down = false;
		boolean left = false;
		boolean right = false;

		visited[y1][x1] = 1; // 先将x1y1标记为1

		if (y1 == y2 && x1 == x2) {
			return true;
		}

		// Deep search based on x1y1, to see if x2y2 can be searched
		// The first judgment is the boundary
		// The second determination is whether the node has already been visited
		// The third judgment is whether the values of the matrix points are the same,
		// if they are not the same it proves that they are not in the same block and
		// the search cannot continue in that direction.

		// to top
		if (y1 - 1 >= 0 && visited[y1 - 1][x1] == 0 && map[y1][x1] == map[y1 - 1][x1]) {
			points[0] = y1 - 1;
			visited[y1 - 1][x1] = 1;
			top = checkPass(map, visited, points);
			// 回溯,回溯的作用就是为后面的递归创造环境
			points[0] = y1;
			if (top == true) {
				return true;
			}
		}
		// to down
		if (y1 + 1 <= map.length - 1 && visited[y1 + 1][x1] == 0 && map[y1][x1] == map[y1 + 1][x1]) {
			points[0] = y1 + 1;
			visited[y1 + 1][x1] = 1;
			down = checkPass(map, visited, points);
			points[0] = y1;
			if (down == true) {
				return true;
			}
		}
		// to left
		if (x1 - 1 >= 0 && visited[y1][x1 - 1] == 0 && map[y1][x1] == map[y1][x1 - 1]) {
			points[1] = x1 - 1;
			visited[y1][x1 - 1] = 1;
			left = checkPass(map, visited, points);
			points[1] = x1;
			if (left == true) {
				return true;
			}
		}
		// to right
		if (x1 + 1 <= map[0].length - 1 && visited[y1][x1 + 1] == 0 && map[y1][x1] == map[y1][x1 + 1]) {
			points[1] = x1 + 1;
			visited[y1][x1 + 1] = 1;
			right = checkPass(map, visited, points);
			points[1] = x1;
			if (right == true) {
				return true;
			}
		}
		return top || down || left || right;
	}

	/**
	 * Define the shape of the layout
	 * 
	 * @param formeTapis A shape of the layout object
	 */
	public void setFormeTapis(FormeTapis formeTapis) {
		this.formeTapis = formeTapis;
	}

	/**
	 * Get the number of cards on the table
	 * 
	 * @return The number of cards on the table
	 */
	public int numbreCartesSurTapis() {
		return cartesTapis.size();
	}

	/**
	 * Override the toString method. Used to output the current card distribution.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < yx.length; i++) {
			for (int j = 0; j < yx[0].length; j++) {
				if (this.cartesTapis.get(yx[i][j]) != null) {
					Carte carte = this.cartesTapis.get(yx[i][j]);
					sb.append(i);
					sb.append(j + "=");
					sb.append(carte);
					sb.append(" | ");
				} else {
					sb.append("            ");
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
