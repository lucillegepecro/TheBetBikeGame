package betbikegame.beans;

import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


import com.google.appengine.api.datastore.Key;
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
	private Date date;

	


	/**
	 * 
	 * @param ville
	 * @param user
	 */
	public Pari(String ville, User user, Date date) {
		super();
		this.date = date;
		this.ville = ville;
		this.user = user;
	}
	
	/**
	 * 
	 */
	public Pari(){
		
	}

	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
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
	

}
