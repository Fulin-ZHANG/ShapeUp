package fr.utt.lo02.projet.modele.formeTapis;

import fr.utt.lo02.projet.modele.carte.Carte;

/**
 * Rectangle layout of the cards.In this class, it can be judged whether
 * operations such as placing cards or moving cards conform to the shape of the
 * layout.
 * 
 * @author Fulin and Dewen
 * @version 1.0
 */
public class FormeRectangle extends FormeTapis {

	/**
	 * The length of each line
	 */
	private Integer[] lengthLigne = { 0, 0, 0, 0, 0 };

	/**
	 * The length of each column
	 */
	private Integer[] lengthColonne = { 0, 0, 0, 0, 0 };

	/**
	 * Constructor of class {@code FormeRectangle}
	 * 
	 * @param disposition An object of class {@code Disposition}
	 */
	public FormeRectangle(Disposition disposition) {
		super(disposition);
	}

	/**
	 * Determine whether cards can be placed in a direction selected under the
	 * condition of rectangular layout.
	 * 
	 * @param point     The position of the selected card (place carteMain around
	 *                  this card)
	 * @param carte     Cards to be placed
	 * @param direction Placement direction
	 * @return {@code true} if the selected location can be placed before placing the card
	 */
	@Override
	public boolean peutEtrePlacer(int point, Carte carte, String direction) {
		lengthLigne = this.disposition.calculerLengthLigne(); // 计算各行实际牌的张数
		lengthColonne = this.disposition.calculerLengthColonne(); // 计算各列实际牌的张数

		int maxLigne = (this.maxLigne())[0]; // 当前桌面最长行的行长度，以及该行的两个端点坐标
		int xMax = (this.maxLigne())[1];
		int xMin = (this.maxLigne())[2];

		int maxColonne = this.maxColonne()[0]; // 当前桌面最长列的列长度，以及该列的两个端点坐标
		int yMax = (this.maxColonne())[1];
		int yMin = (this.maxColonne())[2];

		switch (direction) {
		// 往右侧放牌
		case "d":
			if ((lengthLigne[point / 10] == 5) || lengthLigne[point / 10] == 3 && maxColonne > 3) { // 已放满，不能再放牌
//				System.out.println("Ligne complet!");
				return false;
			}
			// 所有能放牌的情况
			else if ((maxLigne < 4 && maxColonne < 4) // 当行和列最长个数都小于4
					|| (maxLigne > 3 && maxColonne <= 3 && lengthLigne[point / 10] < 5) // 或者行大于3 & 列小于等于3 &
																						// 该行个数小于5（即5x3的情况）
					|| (maxColonne > 3 && maxLigne < 3 && lengthLigne[point / 10] < 3) // 或者列大于3 & 行小于4 &
																						// 该行个数小于3（即3x5的情况）
					|| (maxColonne > 3 && maxLigne == 3 && lengthLigne[point / 10] < 3 && point % 10 < xMax)) {

				if (point % 10 < 4 && this.disposition.cartesTapis.get(point + 1) == null) { // 不需要移牌的情况，即所选牌的横坐标小于4且右侧为空
					this.disposition.cartesTapis.put(point + 1, carte); // 放牌
					return true;
				} else if (point % 10 == 4 && maxLigne < 5) { // 需要移牌的情况
					this.disposition.deplacerAGauche(); // 将所有的牌往左移动
					point = point - 1;
					this.disposition.cartesTapis.put(point + 1, carte); // 放牌
					return true;
				} else {
//					System.out.println("Ne pouvez pas la jouer ici!");
					return false;
				}
			} else {
//				System.out.println("Ne pouvez pas la jouer ici!");
				return false;
			}

		case "g":
			if ((lengthLigne[point / 10] == 5) || lengthLigne[point / 10] == 3 && maxColonne > 3) { // 已放满，不能再放牌
//				System.out.println("Ligne complet!");
				return false;

			} else if ((maxLigne < 4 && maxColonne < 4)
					|| (maxLigne > 3 && maxColonne <= 3 && lengthLigne[point / 10] < 5)
					|| (maxColonne > 3 && maxLigne < 3 && lengthLigne[point / 10] < 3)
					|| (maxColonne > 3 && maxLigne == 3 && lengthLigne[point / 10] < 3 && point % 10 > xMin)) {
				if (point % 10 > 0 && this.disposition.cartesTapis.get(point - 1) == null) {
					this.disposition.cartesTapis.put(point - 1, carte); // 放牌
					return true;
				} else if (point % 10 == 0 && maxLigne < 5) { // 需要移牌的情况，即所选牌的位置位于左侧顶端
					this.disposition.deplacerADroit(); // 将所有的牌往右移动
					point = point + 1;
					this.disposition.cartesTapis.put(point - 1, carte); // 放牌
					return true;
				} else {
//					System.out.println("Ne pouvez pas la jouer ici!");
					return false;
				}
			} else {
//				System.out.println("Ne pouvez pas la jouer ici!");
				return false;
			}

		case "h":
			if ((lengthColonne[point % 10] == 5) || lengthColonne[point % 10] == 3 && maxLigne > 3) { // 已放满，不能再放牌
//				System.out.println("Colonne complet!");
				return false;
			} else if ((maxLigne < 4 && maxColonne < 4)
					|| (maxColonne > 3 && maxLigne <= 3 && lengthColonne[point % 10] < 5)
					|| (maxLigne > 3 && maxColonne < 3 && lengthColonne[point % 10] < 3)
					|| (maxLigne > 3 && maxColonne == 3 && lengthColonne[point % 10] < 3 && point / 10 > yMin)) {

				if (point / 10 > 0 && this.disposition.cartesTapis.get(point - 10) == null) { // 不需要移牌的情况
					this.disposition.cartesTapis.put(point - 10, carte); // 放牌
					return true;

				} else if (point / 10 == 0 && maxColonne < 5) { // 需要移牌的情况，即所选牌的位置位于上侧顶端
					this.disposition.deplacerALaBaisse(); // 将所有的牌往下移动
					point = point + 10;
					this.disposition.cartesTapis.put(point - 10, carte); // 放牌
					return true;
				} else {
//					System.out.println("Ne pouvez pas la jouer ici!");
					return false;
				}
			} else {
//				System.out.println("Ne pouvez pas la jouer ici!");
				return false;
			}

		case "b":
			if ((lengthColonne[point % 10] == 5) || lengthColonne[point % 10] == 3 && maxLigne > 3) { // 已放满，不能再放牌
//				System.out.println("Colonne complet!");
				return false;
			} else if ((maxLigne < 4 && maxColonne < 4)
					|| (maxColonne > 3 && maxLigne <= 3 && lengthColonne[point % 10] < 5)
					|| (maxLigne > 3 && maxColonne < 3 && lengthColonne[point % 10] < 3)
					|| (maxLigne > 3 && maxColonne == 3 && lengthColonne[point % 10] < 3 && point / 10 < yMax)) {

				if (point / 10 < 4 && this.disposition.cartesTapis.get(point + 10) == null) {
					this.disposition.cartesTapis.put(point + 10, carte); // 放牌
					return true;
				} else if (point / 10 == 4 && maxColonne < 5) {
					this.disposition.deplacerVersLeHaut();
					point = point - 10;
					this.disposition.cartesTapis.put(point + 10, carte); // 放牌
					return true;
				} else {
//					System.out.println("Ne pouvez pas la jouer ici!");
					return false;
				}
			} else {
//				System.out.println("Ne pouvez pas la jouer ici!");
				return false;
			}
		default:
			System.out.println("Saissez faux, saissez encore une fois.");
			return false;
		}
	}

}
