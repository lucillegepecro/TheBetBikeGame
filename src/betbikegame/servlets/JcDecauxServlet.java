package betbikegame.servlets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import betbikegame.actions.PMF;
import betbikegame.beans.Ville;
import betbikegame.utils.Constantes;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
;



public class JcDecauxServlet {

	private static final Logger log = Logger.getLogger(LivreOrServlet.class.getName());
	
	static final String JCDECAUX_PROPERTIES = "JcDecaux";
	
    static ResourceBundle jcdecaux = PropertyResourceBundle.getBundle(JCDECAUX_PROPERTIES);
	
    /**
     * doGet
     * @param req
     * @param resp
     * @throws IOException
     */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
				
		String apikey = jcdecaux.getString("apikey");
		
		String country = "paris";
		
		String url = "https://api.jcdecaux.com/vls/v1/stations?contract="+ country + "&apiKey=" + apikey;
			
		// Requete JcDecaux
		BufferedReader br = connection(url);
		
		// Lecture fichier Json
		String station = parserJson(br, "station");
		
		String[] st = null;
		st = station.split(Constantes.VIRGULE);
		List<String> stations = Arrays.asList(st);
		
		String available_bike_standsS = parserJson(br, "available_bike_stands");
		
		Integer available_bike_stands = Integer.parseInt(available_bike_standsS);
		
		String contract = parserJson(br, "contract");
		
		log.info("stations : " + stations.toString());
		log.info("available_bike_stands : " + available_bike_stands);
		log.info("contract : " + contract);
		
		// envoi à la base données
		Ville ville = new Ville(contract, stations, available_bike_stands);
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            pm.makePersistent(ville);
        } finally {
            pm.close();
        }
	}
	
	
	/**
	 * Récupération des données JcDecaux
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
		
		URLConnection yc = null;
		
		// Connexion		
		try {			
			yc = url.openConnection();		
		} catch (IOException e) {	
			log.warning(e.getMessage());
		}
		
		BufferedReader in = null;
		
		// Lecture
		try {			
			if (yc != null) {				
			in = new BufferedReader(new InputStreamReader(yc.getInputStream()));			
			}		
		} catch (IOException e) {	
			log.warning(e.getMessage());
		}
	
		return in;
	}
			

	/**
	 * 
	 * @param br : flux de lecture
	 * @param name : nom du paramètre à récupérer
	 * @return
	 */
	public String parserJson (BufferedReader br, String name){
		
		JSONObject json = (JSONObject) JSONSerializer.toJSON(br); 
		
		String res = null;
		
		try {

			res = (String) json.get(name);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	
}
