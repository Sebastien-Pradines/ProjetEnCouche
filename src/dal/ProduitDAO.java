package dal;

import java.util.ArrayList;
import java.util.List;

import metier.Catalogue;
import metier.I_Produit;
import metier.Produit;

public interface ProduitDAO {
	public abstract void ouvrirConnexion();
	public abstract void fermerConnexion();
	public abstract void create(String nom, double prix, int qte);
	public abstract ArrayList<I_Produit> read();
	public abstract void updateStock(String nom, int qte);
	public abstract void delete(String nom);

}
