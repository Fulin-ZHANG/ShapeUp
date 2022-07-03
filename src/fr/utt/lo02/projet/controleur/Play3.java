package fr.utt.lo02.projet.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import fr.utt.lo02.projet.modele.connecteur.Connector;


/**
 * The class {@code Play3} is used to monitor the button {@code buttonPlay3} of class {@code GameFrame}
 * 
 * @author Dewen WU
 * @version 1.0
 */
public class Play3 {

	
	/**
	 * Constructor method of class Play3
	 * 
	 * @param connector A object of class Connector
	 * @param buttonPlay3 buttonPlay3 of class GameFrame
	 * @param mouseCliquer A object of class MouseCliquer
	 */
	public Play3(Connector connector, JButton buttonPlay3, MouseCliquer mouseCliquer) {

		buttonPlay3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				if (connector.getJeu() instanceof ModeNormal) {
//					((ModeNormal) (connector.getJeu())).play(3, mouseCliquer);
//				}
				
				connector.getJeu().play(3, mouseCliquer);
			}
		});
	}
}
