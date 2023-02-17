package controlleur;

import metier.Catalogue;
import dal.ProduitDAO;
import dal.ProduitDAOFactory;
import dal.ProduitDAO_Oracle;

public class ControlleurPrincipal {
	
	private Catalogue catalogue;
	
	private ProduitDAO dao = ProduitDAOFactory.getInstance().createProduitDAO();
	
	private ControlleurAffichage ctrAffichage = new ControlleurAffichage();
	private ControlleurProduit ctrProduit = new ControlleurProduit();
	private ControlleurStock ctrStock = new ControlleurStock();

	public ControlleurPrincipal() {
		this.dao.ouvrirConnexion();
		this.catalogue = new Catalogue(dao.read());
		this.dao.fermerConnexion();
		
	}
	
	public String afficher() {
		return ctrAffichage.getAffichage(this.catalogue);
	}
	
	public String[] getListe() {
		return this.catalogue.getNomProduits();
	}
	
	public boolean nouveauProduit(String nom, double prix, int qte){
		return this.ctrProduit.addProduit(this.catalogue,nom,prix,qte);
	}
	
	public boolean remove(String nom) {
		return this.ctrProduit.remove(this.catalogue,nom);
	}
	
	public boolean acheterProduit(String nom, int qte) {
		return this.ctrStock.acheterStock(this.catalogue, nom, qte);
	}
	
	public boolean venteProduit(String nom, int qte) {
		return this.ctrStock.vendreStock(this.catalogue, nom, qte);
	}
}