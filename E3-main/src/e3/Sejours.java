package e3;

import java.io.Serializable;
import java.util.Objects;
 
/**
 * Classe Sejours, une liste d'entre eux est la composantee principale d'un historique
 * @param annee année du séjour int
 * @param host hôte du séjour Teenager
 * @param guest invité du séjour Teenager
 */
public class Sejours implements Serializable{
	int annee;
	Teenager host;
	Teenager guest;
	private static final long serialVersionUID=1L;

	/**
	 * Constructeur de la classe Sejour
	 * @param annee année du séjour int
	 * @param host hôte du séjour Teenager
	 * @param guest invité du séjour Teenager
	 */
	public Sejours(int annee, Teenager host, Teenager guest) {
		this.annee=annee;
		this.host=host;
		this.guest=guest;
	}
	
	/**
	 * @return année du séjour
	 */
	public int getAnnee(){
	    return this.annee;
	}
	
	public Teenager getHost() {
		return this.host;
	}
	
	public Teenager getGuest() {
		return this.guest;
	}
	
	public String toString() {
		return "["+this.host+"],["+this.guest+"]:"+this.annee;
	}

	@Override
	public int hashCode() {
		return Objects.hash(annee, guest, host);
	}

	/**
	 * @param obj Objet pour a comparer
	 * @return revoie true si égale sinon false
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Sejours other = (Sejours) obj;
		return annee == other.annee && (this.getHost().getName().equals(other.getHost().getName()) && this.getGuest().getName().equals(other.getGuest().getName()) );
	}
}