package betbikegame.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
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

		// Envoi en base de données le pari du joueur
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();		
		Entity pari = new Entity("Pari", KeyFactory.createKey("ListeParis", "paris"));
		pari.setProperty("date", session.getAttribute("date"));	
		pari.setProperty("ville", request.getParameter("villePari"));
		pari.setProperty("user", user.getNickname());	
		pari.setProperty("mise", request.getParameter("misePari"));
		datastore.put(pari);

		this.getServletContext().getRequestDispatcher("/accueil.jsp" ).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
