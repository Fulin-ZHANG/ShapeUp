package fr.utt.lo02.projet.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import fr.utt.lo02.projet.modele.connecteur.Connector;

/**
 * The class {@code Move1} is used to monitor the button {@code buttonMove1} of class {@code GameFrame}
 * 
 * @author Dewen WU
 * @version 1.0
 */
public class Move1 {
	/**
	 * Constructor method of class Move1
	 * 
	 * @param connector A object of class Connector
	 * @param buttonMove1 buttonMove1 of class GameFrame
	 * @param mouseCliquer A object of class MouseCliquer
	 */
	public Move1(Connector connector, JButton buttonMove1, MouseCliquer mouseCliquer) {
		
		buttonMove1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				if(connector.getJeu() instanceof ModeNormal) {
//					((ModeNormal)(connector.getJeu())).move(1, mouseCliquer);
//				}
				
				connector.getJeu().move(1, mouseCliquer);
			}
		});
	}
	
}
