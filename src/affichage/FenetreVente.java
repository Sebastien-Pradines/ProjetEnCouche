package affichage;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import controlleur.ControlleurPrincipal;

public class FenetreVente extends JFrame implements ActionListener {

	private JButton btVente;
	private JTextField txtQuantite;
	private JComboBox<String> combo;
	
	private ControlleurPrincipal controlleur;

	public FenetreVente(String[] lesProduits,ControlleurPrincipal controlleur) {
		
		this.controlleur = controlleur;
		
		setTitle("Vente");
		setBounds(500, 500, 200, 125);
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
		btVente = new JButton("Vente");
		txtQuantite = new JTextField(5);
		txtQuantite.setText("0");

		combo = new JComboBox<String>(lesProduits);
		combo.setPreferredSize(new Dimension(100, 20));
		contentPane.add(new JLabel("Produit"));
		contentPane.add(combo);
		contentPane.add(new JLabel("Quantit� vendue"));
		contentPane.add(txtQuantite);
		contentPane.add(btVente);

		btVente.addActionListener(this);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		this.controlleur.venteProduit(this.combo.getSelectedItem().toString(),Integer.parseInt(this.txtQuantite.getText()));
		this.dispose();
	}

}
