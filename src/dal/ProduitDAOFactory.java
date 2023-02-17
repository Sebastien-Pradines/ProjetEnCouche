package dal;



public class ProduitDAOFactory {
	protected static ProduitDAOFactory instance;
	protected ProduitDAOFactory() {
		
	}
	
	public static ProduitDAOFactory getInstance() {
		if(instance == null) {
			instance = new ProduitDAOFactory();
		}
		return instance;
	}
	
	public ProduitDAO createProduitDAO() {
		return new ProduitDAO_Oracle();
	}
}
