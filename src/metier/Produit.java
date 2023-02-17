package metier;

public class Produit implements I_Produit {
	
	private int quantiteStock;
	
	private String nom;
	
	private double prixUnitaireHT;
	
	private static double tauxTVA = 0.2;
	
	public Produit(String nom, double prix, int qte) {
		this.nom = nom;
		this.prixUnitaireHT = prix;
		this.quantiteStock = qte;
	}

	@Override
	public boolean ajouter(int qteAchetee) {
		this.quantiteStock = this.quantiteStock + qteAchetee;
		return false;
	}

	@Override
	public boolean enlever(int qteVendue) {
		this.quantiteStock = this.quantiteStock - qteVendue;
		return false;
	}

	@Override
	public String getNom() {
		return this.nom;
	}

	@Override
	public int getQuantite() {
		return this.quantiteStock;
	}

	@Override
	public double getPrixUnitaireHT() {
		return this.prixUnitaireHT;
	}

	@Override
	public double getPrixUnitaireTTC() {
		return this.prixUnitaireHT+(this.prixUnitaireHT * Produit.tauxTVA);
	}

	@Override
	public double getPrixStockTTC() {
		return this.getPrixUnitaireTTC() * this.quantiteStock;
	}
	
}
