package betbikegame.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
 * Servlet implementation class BetServlet
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
		
		Integer cagnote = 0;

		// Envoi en base de donn�es le pari du joueur
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();		
		Entity pari = new Entity("Pari", KeyFactory.createKey("ListeParis", "paris"));
		pari.setProperty("date", session.getAttribute("date"));	
		pari.setProperty("ville", request.getParameter("villePari"));
		pari.setProperty("user", user.getNickname());	
		pari.setProperty("mise", request.getParameter("misePari"));
		datastore.put(pari);
			
		session.setAttribute("misePari", request.getParameter("misePari"));
		session.setAttribute("villePari", request.getParameter("villePari"));
		// Récupère la cagnote du jouer
		Query q = new Query("Joueur");
		q.addFilter("user", Query.FilterOperator.EQUAL, user);

		PreparedQuery pq = datastore.prepare(q);

		for (Entity result : pq.asIterable()) {
			Integer cagnote_old = (Integer) result.getProperty("cagnote");
			cagnote = cagnote_old - Integer.parseInt(request.getParameter("misePari"));
		}
		
		Entity joueur = new Entity("Joueur", KeyFactory.createKey("ListeJoueurs", "joueurs"));
		pari.setProperty("user", user.getNickname());	
		pari.setProperty("cagnote", cagnote);
		datastore.put(joueur);

		this.getServletContext().getRequestDispatcher("/mesparis.jsp" ).forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
