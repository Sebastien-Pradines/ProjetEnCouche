package controlleur;

import dal.ProduitDAO_Oracle;
import metier.Catalogue;

public class ControlleurProduit {
	
	public ProduitDAO_Oracle dao = new ProduitDAO_Oracle();

	public boolean remove(Catalogue c, String nom) {
		return c.removeProduit(nom);
	}
	
	public boolean addProduit(Catalogue c, String nom, double prix, int qte) {
		return c.addProduit(nom, prix, qte);
	}
}