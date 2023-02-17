package affichage;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import controlleur.ControlleurPrincipal;

public class FenetreSuppressionProduit extends JFrame implements ActionListener {

	private JButton btSupprimer;
	private JComboBox<String> combo;
	
	private ControlleurPrincipal controlleur;
	
	public FenetreSuppressionProduit(String lesProduits[], ControlleurPrincipal controlleur) {
		
		this.controlleur = controlleur;
		
		setTitle("Suppression produit");
		setBounds(500, 500, 200, 105);
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
		btSupprimer = new JButton("Supprimer");

		combo = new JComboBox<String>(lesProduits);
		combo.setPreferredSize(new Dimension(100, 20));
		contentPane.add(new JLabel("Produit"));
		contentPane.add(combo);
		contentPane.add(btSupprimer);

		btSupprimer.addActionListener(this);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		this.controlleur.remove(this.combo.getSelectedItem().toString());
		this.dispose();
	}

}
