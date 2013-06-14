package betbikegame.beans;

import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Classement {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private List<Ville> cl;
	
	
	/**
	 * 
	 */
	public Classement(){
	}
	
	/**
	 * 
	 * @param ville
	 * @param km
	 */
	public Classement (List<String> ville){
		this.cl = cl;
	}

	
	
	
}
