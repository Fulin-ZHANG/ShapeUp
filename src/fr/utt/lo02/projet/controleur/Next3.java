package fr.utt.lo02.projet.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import fr.utt.lo02.projet.modele.connecteur.Connector;


/**
 * The class {@code Next3} is used to monitor the button {@code buttonNext3} of class {@code GameFrame}
 * 
 * @author Dewen WU
 * @version 1.0
 */
public class Next3 {
	
	/**
	 * Constructor method of class Next3
	 * 
	 * @param connector A object of class Connector
	 * @param buttonNext3 buttonNext1 of class GameFrame
	 */
	public Next3(Connector connector, JButton buttonNext3) {
		// TODO Auto-generated constructor stub
		buttonNext3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				((ModeNormal)(connector.getJeu())).nextPlayer(3);
				
				connector.getJeu().nextPlayer(3);
			}
		});
	}
}
