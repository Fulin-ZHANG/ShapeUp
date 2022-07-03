package fr.utt.lo02.projet.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import fr.utt.lo02.projet.modele.connecteur.Connector;

/**
 * The class {@code Move2} is used to monitor the button {@code buttonMove2} of class {@code GameFrame}
 * 
 * @author Dewen WU
 * @version 1.0
 */
public class Move2 {
	
	/**
	 * Constructor method of class Move2
	 * 
	 * @param connector A object of class Connector
	 * @param buttonMove2 buttonMove2 of class GameFrame
	 * @param mouseCliquer A object of class MouseCliquer
	 */
	public Move2(Connector connector, JButton buttonMove2, MouseCliquer mouseCliquer) {
		
		buttonMove2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				if(connector.getJeu() instanceof ModeNormal) {
//					((ModeNormal)(connector.getJeu())).move(2, mouseCliquer);
//				}
				
				connector.getJeu().move(2, mouseCliquer);
			}
		});
	}
	
}
