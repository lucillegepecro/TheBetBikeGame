package betbikegame.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import betbikegame.actions.PMF;
import betbikegame.beans.Ville;
import betbikegame.utils.Constantes;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.labs.repackaged.org.json.JSONObject;



/**
 * 
 * @author anna
 *
 */
public class JcDecauxServlet extends HttpServlet {

	private static final long serialVersionUID = 8864311233072028263L;

	private static final Logger log = Logger.getLogger(LivreOrServlet.class.getName());
	
	static final String JCDECAUX_PROPERTIES = "JcDecaux";
	
    static ResourceBundle jcdecaux = PropertyResourceBundle.getBundle(JCDECAUX_PROPERTIES);
	
    /**
     * 
     * @param req
     * @param resp
     * @throws IOException
     */
    @Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)  
            throws IOException {
				
		String apikey = jcdecaux.getString("apikey");
		
		String country = "paris";
		
		String url = "https://api.jcdecaux.com/vls/v1/stations?contract="+ country + "&apiKey=" + apikey;
		
			
		
		
		// Requete JcDecaux
		//BufferedReader br = connection(url);
		
		// Lecture fichier Json
		String station;
		List<String> stations = new ArrayList<String>();
		String available_bike_standsS;
		String contract = null;
		Integer available_bike_stands = null;
		

		// Envoi � la base donn�es
		if (contract != null && stations!=null && available_bike_stands!=null){
			Ville ville = new Ville(contract, stations, available_bike_stands);
			
			PersistenceManager pm = PMF.get().getPersistenceManager();
	        try {
	            pm.makePersistent(ville);
	        } finally {
	            pm.close();
	        }
		}
	}
	
	
	/**
	 * R�cup�ration des donn�es JcDecaux
	 * @param urlJcdecaux
	 * @return
	 */
	public BufferedReader connection (String urlJcdecaux){
	
		URL url = null;
		
		// Creation url
		try {			
			url = new URL(urlJcdecaux);		
		} catch (MalformedURLException e) {	
			log.warning(e.getMessage());
		}
		
		InputStream yc = null;
		
		// Connexion		
		try {			
			yc = url.openStream();		
		} catch (IOException e) {	
			log.warning(e.getMessage());
		}
		
		BufferedReader in = null;
		
		if (yc != null) {			
			in = new BufferedReader(new InputStreamReader(yc));
		
		}
	
		return in;
	}
			

	
	
	
}
