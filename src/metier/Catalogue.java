package metier;



import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dal.ProduitDAO;
import dal.ProduitDAOFactory;



public class Catalogue implements I_Catalogue{

    private ArrayList<I_Produit>lesProduits = new ArrayList<I_Produit>();
    private ProduitDAO dao = ProduitDAOFactory.getInstance().createProduitDAO();

    public Catalogue(ArrayList<I_Produit> arrayList) {
    	this.lesProduits = arrayList;
    }
	public Catalogue(){
		this.lesProduits = new ArrayList<I_Produit>();
	}
    
    @Override
    public boolean addProduit(I_Produit produit) {
        boolean addProduit = false;
        
        if(produit != null) {
        	if(!this.existe(produit.getNom().trim()) && produit.getPrixUnitaireTTC() > 0 && produit.getQuantite() >= 0) {
            	this.lesProduits.add(produit);
            	addProduit = true;
            }
        }
        
        return addProduit;
    }

    @Override
    public boolean addProduit(String nom, double prix, int qte) {
    	nom = nom.replace("\t", " ");
    	nom = nom.trim();
        boolean addProduit = false;
        if(!this.existe(nom) && prix > 0 && qte >= 0 && this.formatNom(nom)) {
        	this.lesProduits.add(new Produit(nom,prix,qte));
        	this.dao.ouvrirConnexion();
    		this.dao.create(nom, prix, qte);
    		this.dao.fermerConnexion();
        	addProduit = true;
        }
        
        return addProduit;
    }

    @Override
    public int addProduits(List<I_Produit> l) {
    	int produitAjoutes = 0;
        if(l != null) {
        	for(I_Produit produit : l) {
            	if(this.addProduit(produit.getNom(), produit.getPrixUnitaireTTC(), produit.getQuantite())) {
            		produitAjoutes++;
            	}
            }
        }
        return produitAjoutes;
    }

    @Override
    public boolean removeProduit(String nom) {
    	boolean removed = false;
    	I_Produit p = null;
    	
    	for(I_Produit produit : this.lesProduits) {
    		if(produit.getNom().equals(nom)) {
    			p = produit;
    			removed = true;
    		}
    	}
    	if(removed == true) {
    		this.lesProduits.remove(p);
    		this.dao.ouvrirConnexion();
    		this.dao.delete(nom);
    		this.dao.fermerConnexion();
    	}
    	
        return removed;
    }

    @Override
    public boolean acheterStock(String nomProduit, int qteAchetee) {
    	boolean achete = false;
    	if(qteAchetee > 0) {
    		for (I_Produit Produit : this.lesProduits) {
        		if(Produit.getNom() == nomProduit) {
        			Produit.ajouter(qteAchetee);
        			this.dao.ouvrirConnexion();
        			this.dao.updateStock(nomProduit,qteAchetee);
        			this.dao.fermerConnexion();
        			achete = true;
        		}
            }
    	}
    	
        return achete;
    }

    @Override
    public boolean vendreStock(String nomProduit, int qteVendue) {
    	boolean vendu = false;
    	if(qteVendue > 0) {
    		for (I_Produit Produit : this.lesProduits) {
        		if(Produit.getNom() == nomProduit && Produit.getQuantite() >= qteVendue) {
        			Produit.enlever(qteVendue);
        			int qteVendueNegative = qteVendue * (-1);
        			this.dao.ouvrirConnexion();
        			this.dao.updateStock(nomProduit,qteVendueNegative);
        			this.dao.fermerConnexion();
        			vendu = true;
        		}
            }
    	}
    	
        return vendu;
    }

    @Override
    public String[] getNomProduits() {
    	ArrayList<String> arrayListProduits = new ArrayList<String>();
    	for(I_Produit produit : this.lesProduits) {
    		arrayListProduits.add(produit.getNom().replace("\t", " "));
    	}
    	Collections.sort(arrayListProduits);
    	String[] nomProduit = arrayListProduits.toArray(new String[0]);

    	return nomProduit;
    }

    @Override
    public double getMontantTotalTTC() {
    	double montantTotalTTC = 0;
    	for (I_Produit Produit : this.lesProduits) {
    		montantTotalTTC = montantTotalTTC + Produit.getPrixUnitaireTTC() * Produit.getQuantite();
        }
        return this.arrondirPrix(montantTotalTTC);
    }

    @Override
    public void clear() {
    	this.lesProduits.clear();
    }
    
    public String toString() {
    	String theString = "";
    	if(this.lesProduits.size() == 0) {
    		theString = "\n" + "Montant total TTC du stock : 0,00 €";
    	}
    	else {
    		for(I_Produit produit : this.lesProduits) {
        		theString = theString + produit.getNom() +" - prix HT : "+ this.arrondirPrixString(produit.getPrixUnitaireHT()) +" € - prix TTC : "+ this.arrondirPrixString(produit.getPrixUnitaireTTC()) +" € - quantité en stock : "+ produit.getQuantite() + "\n";
        	}
    		theString = theString + "\n" + "Montant total TTC du stock : "+ this.arrondirPrixString(this.getMontantTotalTTC()) +" €";
    	}
    	theString = theString.replace(".", ",");
    	
    	return theString;
    }
    
    private boolean existe(String nom) {
    	boolean existe = false;
    	for(I_Produit Produit : this.lesProduits) {
    		if(Produit.getNom().equals(nom)) {
    			existe = true;
    		}
    	}
    	return existe;
    }
    
    private boolean formatNom(String nom) {
    	boolean format = true;
    	if(nom.contains("\t")) {
    		format = false;
    	}
    	return format;
    }
    
    private Double arrondirPrix(double prix) {
    	BigDecimal bd =new BigDecimal(prix).setScale(2,RoundingMode.HALF_UP);
    	return bd.doubleValue();
    }
    
    private String arrondirPrixString(double prix) {
    	BigDecimal bd =new BigDecimal(prix).setScale(2,RoundingMode.HALF_UP);
    	return bd.toString();
    }
    
}