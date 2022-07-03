package fr.utt.lo02.projet.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import fr.utt.lo02.projet.modele.connecteur.Connector;

/**
 * The class {@code Play2} is used to monitor the button {@code buttonPlay2} of class {@code GameFrame}
 * 
 * @author Dewen WU
 * @version 1.0
 */
public class Play2 {

	
	/**
	 * Constructor method of class Play2
	 * 
	 * @param connector A object of class Connector
	 * @param buttonPlay2 buttonPlay2 of class GameFrame
	 * @param mouseCliquer A object of class MouseCliquer
	 */
	public Play2(Connector connector, JButton buttonPlay2, MouseCliquer mouseCliquer) {

		buttonPlay2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				if (connector.getJeu() instanceof ModeNormal) {
//					((ModeNormal) (connector.getJeu())).play(2, mouseCliquer);
//				}
				
				connector.getJeu().play(2, mouseCliquer);
			}
		});
	}
}
