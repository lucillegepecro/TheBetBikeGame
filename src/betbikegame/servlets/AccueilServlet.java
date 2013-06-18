package betbikegame.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * Servlet implementation class BetServlet
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
        String date = date1.getDay() + " " + date1.getMonth() + " " + date1.getYear();
        HttpSession session = request.getSession();
        session.setAttribute("date", date);

		 session.setAttribute("deja_parie", "false");
		 
		 /**
		  *  récupère les paris en cours
		  */

		// Get the Datastore Service
		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		 // The Query interface assembles a query
		 Query q = new Query("Pari");
		 q.addFilter("date", Query.FilterOperator.EQUAL, date);

		 // PreparedQuery contains the methods for fetching query results
		 // from the datastore
		 PreparedQuery pq = datastore.prepare(q);
		 		 
		 if (pq != null){

			 for (Entity result : pq.asIterable()) {
				 
			   String ville = (String) result.getProperty("ville");
			   String userq = (String) result.getProperty("user");
			   
			   if (userq.equalsIgnoreCase(user.toString())){	   
				   session.setAttribute("deja_parie", "true");
				   session.setAttribute("villePari", ville);
			   }
			 }
		 }
		 
		 this.getServletContext().getRequestDispatcher("/bet.jsp" ).forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
