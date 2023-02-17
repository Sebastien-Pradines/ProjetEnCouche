package controlleur;

import metier.Catalogue;

public class ControlleurAffichage {
	
	public String getAffichage(Catalogue c) {
		return c.toString();
	}

}
