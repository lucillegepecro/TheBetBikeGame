package betbikegame.actions;

import java.util.List;
import javax.jdo.PersistenceManager;

import betbikegame.beans.Pari;


public class JDO {

	public void putJDO(Object obj){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		 
	     try {
	        pm.makePersistent(obj);
	     } finally {
	        pm.close();
	     }
	}
	
	public List<Pari> getJDOPari(String request){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		 String query = request + Pari.class.getName();
		 List<Pari> obj = (List<Pari>) pm.newQuery(query).execute();
		 return obj;
	}
}
