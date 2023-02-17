package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import metier.Catalogue;
import metier.I_Produit;
import metier.Produit;

public class ProduitDAO_Oracle implements ProduitDAO {
	
	private CallableStatement cst;
	private ResultSet rs;
	private Connection cn;
	private PreparedStatement pst;
	private List<Produit> cat;
	
	public void ouvrirConnexion() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			this.cn = DriverManager.getConnection("jdbc:oracle:thin:@162.38.222.149:1521:iut","pradiness","12101998");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void fermerConnexion() {
		try {
			this.cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void create(String nom, double prix, int qte) {
		try {
			this.cst = this.cn.prepareCall("{call nouveauProduit(?,?,?)}");
			this.cst.setString(1, nom);
			this.cst.setDouble(2, prix);
			this.cst.setInt(3, qte);
			this.cst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<I_Produit> read() {
		ArrayList<I_Produit> cat = new ArrayList<I_Produit>();
		try {
			this.pst = this.cn.prepareStatement("SELECT * FROM Produit");
			this.rs = this.pst.executeQuery();
			while(rs.next()) {
				cat.add(new Produit(this.rs.getString(2), this.rs.getDouble(3), this.rs.getInt(4)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cat;
		}
	
	public void updateStock(String nom, int qte) {
		try {
			this.cst = this.cn.prepareCall("{call updateProduitStock(?,?)}");
			this.cst.setString(1, this.getId(nom));
			this.cst.setInt(2, qte);
			this.cst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(String nom) {
		try {
			this.cst = this.cn.prepareCall("{call deleteProduit(?)}");
			this.cst.setString(1, this.getId(nom));
			this.cst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String getId(String nom) {
		String id = "";
		try {
			this.pst = this.cn.prepareStatement("SELECT * FROM Produit WHERE nomProduit = ?",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			this.pst.setString(1, nom);
			this.rs = this.pst.executeQuery();
			this.rs.first();
			id = this.rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
}