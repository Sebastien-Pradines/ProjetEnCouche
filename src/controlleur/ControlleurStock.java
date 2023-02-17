package controlleur;

import metier.Catalogue;

public class ControlleurStock {


	public boolean acheterStock(Catalogue c,String nomProduit, int qteAchetee) {
		return c.acheterStock(nomProduit, qteAchetee);
	}
	
	public boolean vendreStock(Catalogue c,String nomProduit, int qteVendue) {
		return c.vendreStock(nomProduit, qteVendue);
	}
}