package fr.utt.lo02.projet.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import fr.utt.lo02.projet.modele.connecteur.Connector;

/**
 * The class {@code Move3} is used to monitor the button {@code buttonMove3} of class {@code GameFrame}
 * 
 * @author Dewen WU
 * @version 1.0
 */
public class Move3 {
	
	/**
	 * Constructor method of class Move3
	 * 
	 * @param connector A object of class Connector
	 * @param buttonMove3 buttonMove3 of class GameFrame
	 * @param mouseCliquer A object of class MouseCliquer
	 */
	public Move3(Connector connector, JButton buttonMove3, MouseCliquer mouseCliquer) {
		
		buttonMove3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				if(connector.getJeu() instanceof ModeNormal) {
//					((ModeNormal)(connector.getJeu())).move(3, mouseCliquer);
//				}
				
				connector.getJeu().move(3, mouseCliquer);
			}
		});
	}
	
}
