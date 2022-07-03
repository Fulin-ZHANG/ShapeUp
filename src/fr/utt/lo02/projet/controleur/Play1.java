package fr.utt.lo02.projet.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import fr.utt.lo02.projet.modele.connecteur.Connector;


/**
 * The class {@code Play1} is used to monitor the button {@code buttonPlay1} of class {@code GameFrame}
 * 
 * @author Dewen WU
 * @version 1.0
 */
public class Play1 {

	
	/**
	 * Constructor method of class Play1
	 * 
	 * @param connector A object of class Connector
	 * @param buttonPlay1 buttonPlay1 of class GameFrame
	 * @param mouseCliquer A object of class MouseCliquer
	 */
	public Play1(Connector connector, JButton buttonPlay1, MouseCliquer mouseCliquer) {

		buttonPlay1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				if (connector.getJeu() instanceof ModeNormal) {
//					((ModeNormal) (connector.getJeu())).play(1, mouseCliquer);
//				}
				
				connector.getJeu().play(1, mouseCliquer);
			}
		});
	}
}
