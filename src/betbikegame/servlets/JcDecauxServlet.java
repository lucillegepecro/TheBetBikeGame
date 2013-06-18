package betbikegame.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
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
		
		String url_contract = "https://api.jcdecaux.com/vls/v1/contracts?apiKey=" + apikey;
		
		String country = null;
		
		Double km = 0.0;

		List<String> villes = new ArrayList<String>();

		// récupère la liste des villes
		String st_contract = connection(url_contract);	
		
		// parser br_km pour sortir la liste des villes : remplir "villes"
		villes = getContracts(st_contract);
		
		// pour chaque ville, récupérer la liste des stations avec leur nb de vélos empruntés
		for (int i=0 ; i<villes.size() ; i++){
			country = villes.get(i);
			
			String url_km = "https://api.jcdecaux.com/vls/v1/stations?contract="+ country + "&apiKey=" + apikey;
			
			// récupère le flux json des velos empruntés par ville
			String st_km = connection(url_km);
				
			// nb de km dans la ville
			km = getKm(st_km);
							
			// Get the Datastore Service
			 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			 
			 // The Query interface assembles a query
			 Query q = new Query("Ville");
			 q.addFilter("contract", Query.FilterOperator.EQUAL, country);

			 // PreparedQuery contains the methods for fetching query results
			 // from the datastore
			 PreparedQuery pq = datastore.prepare(q);
			 		 
			 if (pq != null){

				 for (Entity result : pq.asIterable()) {
					 
				   Double km_old = (Double) result.getProperty("km");
				   System.out.println(km_old);
				   System.out.println(km);
				   
				   km = km + km_old;
				   
				   result.setProperty("km", km);
				   datastore.delete();
				   datastore.put(result);
				   
				   System.out.println(km);
				   System.out.println();
				 }
				 
			 } else {
			 			
				Entity ville = new Entity("Ville", KeyFactory.createKey("ListeVilles", "villes"));
				
				ville.setProperty("contract", country);	
				
				ville.setProperty("km", km);
				
				System.out.println("ville inexistante");
				
				datastore.put(ville);
			 }
		}			
	}
	
	
	/**
	 * Rï¿½cupï¿½ration des donnï¿½es JcDecaux
	 * @param urlJcdecaux
	 * @return
	 */
	public String connection (String urlJcdecaux){
	
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
		
		String inputLine;
		String source = "";
		// On remplit le String retourné avec les infos récupérées
		try {
		if (in != null) {
		while ((inputLine = in.readLine()) != null)
		source += inputLine;
		in.close();
		}
		} catch (IOException e) {

		}
		return source;
		
		
	}
			

	/**
	 * Parse le JSON de jcdecaux en une liste de villes
	 * @param json
	 * @return
	 */
	public List<String> getContracts(String json){
		
		List<String> contracts = new ArrayList<String>();
  	  
	 	try {

	 		JSONArray jsonArray = new JSONArray(json);
	 		
	 		for (int  i=0 ; i< jsonArray.length() ; i++){
	 			
	 			Object obj2 = jsonArray.get(i);
	 			
	 			JSONObject jo = new JSONObject(obj2.toString());
 			
				contracts.add(jo.getString("name").toString());
	 		}
	 		
	 	} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 			
		return contracts;
	}
	
	
	/**
	 * Parse le JSON de jcdecaux en un nombre de km
	 * @param json
	 * @return
	 */
	public Double getKm(String json){
		
		Double km = null;
  	  
		List<Integer> nbVelo = new ArrayList<Integer>();
	 	
	 	List<Integer> nbEmprunt = new ArrayList<Integer>();
	 	
	 	try {
	  	
	 		JSONArray jsonArray = new JSONArray(json);
	
	 		for (int  i=0 ; i< jsonArray.length() ; i++){
	 			
	 			Object obj2 = jsonArray.get(i);
	 			
	 			JSONObject jo = new JSONObject(obj2.toString());

	 			nbVelo.add(jo.getInt("bike_stands"));
	 			
	 			nbEmprunt.add(jo.getInt("available_bikes"));
	 		}

	 		Double nbTotalVelo=0.0;
	 		
	 		for (int i= 0; i < nbVelo.size(); i++) {
	 			nbTotalVelo= nbTotalVelo + nbVelo.get(i);
	 		}
	 		
	 		nbTotalVelo = nbTotalVelo * 0.4;
	 		
	 		Double nbDispoVelo = 0.0;
	 		
	 		for (int i= 0; i < nbVelo.size(); i++) {
	 			nbDispoVelo = nbDispoVelo + nbEmprunt.get(i);
	 		}
	 		
	 		nbDispoVelo = nbDispoVelo * 0.4;
	 		
	 		km = (nbTotalVelo - nbDispoVelo) * 5;
	 		
	 	
	 	} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return km;
	}
}
