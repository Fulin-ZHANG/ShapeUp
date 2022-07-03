package fr.utt.lo02.projet.modele.formeTapis;

import java.util.HashMap;

import fr.utt.lo02.projet.modele.carte.Carte;

/**
 * Triangle layout of the cards. In this class, it can be judged whether
 * operations such as placing cards or moving cards conform to the shape of the
 * layout.
 * 
 * @author Fulin and Dewen
 * @version 1.0
 */
public class FormeTriangle extends FormeTapis {

	/**
	 * Constructor of class {@code FormeTriangle}
	 * 
	 * @param disposition An object of class {@code Disposition}
	 */
	public FormeTriangle(Disposition disposition) {
		super(disposition);
	}

	/**
	 * Determine whether cards can be placed in a direction selected under the
	 * condition of triangular layout.
	 * 
	 * @param point     The position of the selected card (place carteMain around
	 *                  this card)
	 * @param carte     Cards to be placed
	 * @param direction Placement direction
	 * @return {@code true} if it conforms to the triangle layout after placing the
	 *         card
	 */
	@Override
	public boolean peutEtrePlacer(int point, Carte carte, String direction) {
		int maxLigne = (this.maxLigne())[0];
		int maxColonne = this.maxColonne()[0];
		switch (direction) {
		case "d":
			if (point % 10 < 4 && this.disposition.cartesTapis.get(point + 1) == null) { // 不需要移牌的情况，即所选牌的横坐标小于4且右侧为空
				this.disposition.cartesTapis.put(point + 1, carte); // 放牌
			} else if (point % 10 == 4 && maxLigne < 5) { // 需要移牌的情况
				this.disposition.deplacerAGauche(); // 将所有的牌往左移动
				point = point - 1;
				this.disposition.cartesTapis.put(point + 1, carte); // 放牌
			} else {
//				System.out.println("右侧不能放牌");
				return false;
			}

			if (this.estTriangle()) {
				return true;
			} else {
				// remove the card
				this.disposition.cartesTapis.remove(point + 1);
				return false;
			}

		case "g":
			if (point % 10 > 0 && this.disposition.cartesTapis.get(point - 1) == null) {
				this.disposition.cartesTapis.put(point - 1, carte); // 放牌
			} else if (point % 10 == 0 && maxLigne < 5) { // 需要移牌的情况，即所选牌的位置位于左侧顶端
				this.disposition.deplacerADroit(); // 将所有的牌往右移动
				point = point + 1;
				this.disposition.cartesTapis.put(point - 1, carte); // 放牌
			} else {
//				System.out.println("左侧不能放牌!");
				return false;
			}

			if (this.estTriangle()) {
				return true;
			} else {
				// 此处把牌移除
				this.disposition.cartesTapis.remove(point - 1);
				return false;
			}

		case "h":
			if (point / 10 > 0 && this.disposition.cartesTapis.get(point - 10) == null) { // 不需要移牌的情况
				this.disposition.cartesTapis.put(point - 10, carte); // 放牌
			} else if (point / 10 == 0 && maxColonne < 5) { // 需要移牌的情况，即所选牌的位置位于上侧顶端
				this.disposition.deplacerALaBaisse(); // 将所有的牌往下移动
				point = point + 10;
				this.disposition.cartesTapis.put(point - 10, carte); // 放牌
			} else {
//				System.out.println("上侧不能放牌!");
				return false;
			}
			if (this.estTriangle()) {
				return true;
			} else {
				// 此处把牌移除
				this.disposition.cartesTapis.remove(point - 10);
				return false;
			}

		case "b":
			if (point / 10 < 4 && this.disposition.cartesTapis.get(point + 10) == null) {
				this.disposition.cartesTapis.put(point + 10, carte); // 放牌
			} else if (point / 10 == 4 && maxColonne < 5) {
				this.disposition.deplacerVersLeHaut();
				point = point - 10;
				this.disposition.cartesTapis.put(point + 10, carte); // 放牌
			} else {
//				System.out.println("Ne pouvez pas la jouer ici!");
				return false;
			}

			if (this.estTriangle()) {
				return true;
			} else {
				// 此处把牌移除
				this.disposition.cartesTapis.remove(point + 10);
				return false;
			}

		default:
			System.out.println("Saissez faux, saissez encore une fois.");
			return false;
		}
	}

	/**
	 * Determine whether it matches one of the four triangles <p>
	 * 
	 * Because the final layout of the cards is triangular and there are only four
	 * possible positions for the triangle, lower left, upper left, upper right or
	 * lower right. Therefore, we only need to determine whether the current layout matches one of them.
	 * 
	 * 1. Put the cards first. 
	 * 2. Move all the cards to the lower left corner, and
	 * judge whether it matches after moving the card; if it matches, return true;
	 * otherwise, step 3. 
	 * 3. Move to the upper left corner. 
	 * 4. Move to the upper right corner. 
	 * 5. Move to the lower right corner.
	 * 
	 * @return {@code true} if it matches one of the four triangles
	 */
	public boolean estTriangle() {
		HashMap<Integer, Carte> tapisTemporaire = new HashMap<>();
		tapisTemporaire.putAll(this.disposition.cartesTapis);

		boolean downLeft = true;
		boolean topLeft = true;
		boolean topRight = true;
		boolean downRight = true;

		// 左下角移动
		int xMin = (this.maxLigne())[2];
		int yMax = (this.maxColonne())[1];
		for (int x = 0; x < xMin; x++) {
			this.disposition.deplacerAGauche();
		}
		for (int y = 4; y > yMax; y--) {
			this.disposition.deplacerALaBaisse();
		}

		for (int x = 0; x < 5; x++) {
			Integer[] length = this.lengthThisLigne(x);
			if (length[0] != null && length[0] > x) { // length[0]是最右侧牌的x坐标
				downLeft = false;
			}
		}

		// 如果左下不符合，则将牌往上移
		// 左上的情况
		if (!downLeft) {
			int yMin = (this.maxColonne())[2];
			for (int y = 0; y < yMin; y++) {
				this.disposition.deplacerVersLeHaut();
			}
			for (int x = 0; x < 5; x++) {
				Integer[] length = this.lengthThisLigne(x);
				if (length[0] != null && length[0] > 4 - x) { // length[0]是最右侧牌的x坐标
					topLeft = false;
				}
			}
		}

		// 如果左上和左下都不符合，将牌往右移动
		// 右上的情况
		if (!downLeft && !topLeft) {
			int xMax = (this.maxLigne())[1];
			for (int x = 4; x > xMax; x--) {
				this.disposition.deplacerADroit();
			}
			for (int x = 0; x < 5; x++) {
				Integer[] length = this.lengthThisLigne(x);
				if (length[1] != null && 4 - length[1] > 4 - x) { // length[1]是最左侧牌的x坐标
					topRight = false;
				}
			}
		}

		// 右下的情况
		if (!downLeft && !topLeft && !topRight) {
			yMax = (this.maxColonne())[1];
			for (int y = 4; y > yMax; y--) {
				this.disposition.deplacerALaBaisse();
			}
			for (int x = 0; x < 5; x++) {
				Integer[] length = this.lengthThisLigne(x);
				if (length[1] != null && 4 - length[1] > x) { // length[1]是最左侧牌的x坐标
					downRight = false;
				}
			}
		}

		// Vider tout et restaurer les données
		this.disposition.cartesTapis.clear();
		this.disposition.cartesTapis.putAll(tapisTemporaire);
		return downLeft || topLeft || topRight || downRight;
	}

	/**
	 * Determine the length of the input line
	 * 
	 * @param i The number of line
	 * @return {@code Integer[0]} Largest abscissa, {@code Integer[1]} Smallest abscissa
	 */
	public Integer[] lengthThisLigne(int i) {
		Integer max = null;
		Integer min = null;
		Integer[] maxMin = new Integer[2];
		if (i < 0 || i > 4) {
			return null;
		} else {
			for (int j = 0; j < disposition.yx[i].length; j++) {
				if (this.disposition.cartesTapis.get(disposition.yx[i][j]) != null) {
					if (max == null) {
						max = j;
					}
					if (min == null) {
						min = j;
					}
					if (j < min) {
						min = j;
					}
					if (j > max) {
						max = j;
					}
				}
			}

			maxMin[0] = max;
			maxMin[1] = min;
			return maxMin; // 返回该行的长度
		}
	}

}
