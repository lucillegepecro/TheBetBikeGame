package betbikegame.beans;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;

/**
 * 
 * @author anna
 *
 */
@PersistenceCapable
public class Pari {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private String ville;
	
	@Persistent
	private User user;
	
	@Persistent
	private String date;

	private String resultat;


	/**
	 * 
	 * @param ville
	 * @param user
	 */
	public Pari(String ville, User user, String date, String res) {
		super();
		this.date = date;
		this.ville = ville;
		this.user = user;
		this.resultat = res;
	}
	
	/**
	 * 
	 */
	public Pari(){
		super();
	}

	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}
	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getResultat() {
		return resultat;
	}

	public void setResultat(String resultat) {
		this.resultat = resultat;
	}
	

	/**
	 * Récupère tous les paris du joueur
	 * @param user
	 * @return
	 */
	public ArrayList getPari(User user){
		
		Pari pari = new Pari();
		pari.setUser(user);
		System.out.println("user : " + user.getNickname());
		
		ArrayList listparis = new ArrayList();
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		// Récupère le classement final de la journée
		Query q = new Query("Pari");
		q.addFilter("user", Query.FilterOperator.EQUAL, user.getNickname());
		PreparedQuery pq = datastore.prepare(q);

		for (Entity result : pq.asIterable()) {
			pari.setDate((String) result.getProperty("date"));
			pari.setVille((String) result.getProperty("ville"));
			listparis.add(pari);
			System.out.println(pari.getUser());
			System.out.println(pari.getVille());
			System.out.println(pari.getResultat());
		}
		
		return listparis;
		
	}
}
