package fr.utt.lo02.projet.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import fr.utt.lo02.projet.modele.connecteur.Connector;

/**
 * The class {@code Next1} is used to monitor the button {@code buttonNext1} of class {@code GameFrame}
 * 
 * @author Dewen WU
 * @version 1.0
 */
public class Next1 {
	
	/**
	 * Constructor method of class Next1
	 * 
	 * @param connector A object of class Connector
	 * @param buttonNext1 buttonNext1 of class GameFrame
	 */
	public Next1(Connector connector, JButton buttonNext1) {
		// TODO Auto-generated constructor stub
		buttonNext1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				((ModeNormal)(connector.getJeu())).nextPlayer(1);
				connector.getJeu().nextPlayer(1);
			}
		});
	}
}
