package betbikegame.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mortbay.log.Log;

import betbikegame.utils.Constantes;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * Prise en charge de l'arriv�e sur l'appli, le joueur a-t-il d�j� pari� aujourd'hui : table Pari
 * @author anna
 *
 */
public class AccueilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccueilServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        
        Date date1 = new java.util.Date();
        String date = date1.getDate() + " " + date1.getMonth() + " " + date1.getYear();
        HttpSession session = request.getSession();
        session.setAttribute("date", date);

		session.setAttribute("deja_parie", "false");
		 
		 /**
		  *  r�cup�re les paris en cours
		  */

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();		
		
		 // The Query interface assembles a query
		 Query q = new Query("Pari");
		 q.addFilter("user", Query.FilterOperator.EQUAL, user.toString());
		 
		 // r�cup�re tous les paris du joueur
		 PreparedQuery pq = datastore.prepare(q);
		 
		for (Entity result : pq.asIterable()) {

			String ville = (String) result.getProperty("ville");
			String dateq = (String) result.getProperty("date");
			
			// si le user a d�j� pari� aujourd'hui, on r�cup�re les valeurs de son pari
			if (dateq.equalsIgnoreCase(date)) {
				session.setAttribute("deja_parie", "true");
				session.setAttribute("villePari", ville);
			} 
			
		}
		// si aucun pari pour ce joueur, alors sa cagnote est initialis�e
		if (pq.countEntities() == 0){
			Entity joueur = new Entity("Joueur", KeyFactory.createKey("ListeJoueurs", "joueurs"));
			joueur.setProperty("cagnote", Constantes.CAGNOTE_INIT);
			joueur.setProperty("user", user.toString());
			datastore.put(joueur);
		}
		 
		 this.getServletContext().getRequestDispatcher("/bet.jsp" ).forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
