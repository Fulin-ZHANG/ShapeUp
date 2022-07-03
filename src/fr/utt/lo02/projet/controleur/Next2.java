package fr.utt.lo02.projet.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import fr.utt.lo02.projet.modele.connecteur.Connector;

/**
 * The class {@code Next2} is used to monitor the button {@code buttonNext2} of class {@code GameFrame}
 * 
 * @author Dewen WU
 * @version 1.0
 */
public class Next2 {
	
	/**
	 * Constructor method of class Next2
	 * 
	 * @param connector A object of class Connector
	 * @param buttonNext2 buttonNext1 of class GameFrame
	 */
	public Next2(Connector connector, JButton buttonNext2) {
		// TODO Auto-generated constructor stub
		buttonNext2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				((ModeNormal)(connector.getJeu())).nextPlayer(2);
				
				connector.getJeu().nextPlayer(2);
			}
		});
	}
}
