package betbikegame.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import betbikegame.beans.Pari;
import betbikegame.beans.Ville;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * Prise en charge du pari (ville, mise) du joueur : table Pari, table Joueur
 * @author anna
 *
 */
public class BetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BetServlet() {
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
        
		HttpSession session = request.getSession();

		// Envoi en base de donn�es le pari du joueur
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();		
		Entity pari = new Entity("Pari", KeyFactory.createKey("ListeParis", "paris"));
		pari.setProperty("date", session.getAttribute("date"));	
		pari.setProperty("ville", request.getParameter("villePari"));
		pari.setProperty("user", user.toString());	
		pari.setProperty("mise", request.getParameter("misePari"));
		pari.setProperty("resultat", "en attente");
		datastore.put(pari);
		
		//Pari pariJoueur = new Pari(request.getParameter("villePari"), user, (String) session.getAttribute("date"), "");
		session.setAttribute("villePari", request.getParameter("villePari"));
		
		long cagnote_old = 0;
		Integer mise =0;
		
		// Récupère la cagnote du joueur
		Query q = new Query("Joueur");
		
		PreparedQuery pq = datastore.prepare(q);
		
		for (Entity result : pq.asIterable()) {		
			System.out.println(result.getProperty("user").toString());
			System.out.println(user.toString());
			System.out.println();
			if ((result.getProperty("user").toString()).equals(user.toString())){
				cagnote_old = (Long) result.getProperty("cagnote");
				mise = Integer.parseInt((String)request.getParameter("misePari"));
				System.out.println("init : " + cagnote_old);
				System.out.println("E/S : " + mise);
				System.out.println("final : " + (cagnote_old - mise));
				result.setProperty("cagnote", (cagnote_old - mise) );
				
				datastore.put(result);
			}
		}

		

		this.getServletContext().getRequestDispatcher("/mesparis.jsp" ).forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
