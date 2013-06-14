package betbikegame.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import betbikegame.actions.JDO;
import betbikegame.actions.PMF;
import betbikegame.beans.Pari;
import betbikegame.beans.Ville;

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
        
        Date date = new java.util.Date();
        HttpSession session = request.getSession();
        session.setAttribute("date", date);
        
        String ville = null;
		 Pari pari = new Pari(ville, user, date);

		 session.setAttribute("deja_parie", "false");
		 // r�cup�re les paris en cours
		 /*JDO jdo = new JDO();
		 List<Pari> paris = jdo.getJDOPari("select from ");
		 
		 System.out.println("USR : " + user);
		 
		 session.setAttribute("deja_parie", "false");
		 if (paris != null) {
		 	for (int i=0 ; i<paris.size() ; i++) {
		 		System.out.println(paris.get(i).getUser());
		 		System.out.println(paris.get(i).getVille());
		 		if (paris.get(i).getUser() == user) {
			 		if ((paris.get(i).getDate().getDay() ==  date.getDay()) 
			 				&& ( paris.get(i).getDate().getMonth() == date.getMonth()) 
			 				&&  (paris.get(i).getDate().getYear()) == date.getYear()){
			 			session.setAttribute("villePari", paris.get(i).getVille());
			 			session.setAttribute("deja_parie", "true");
			 		}
		 		}
		 	}
		 }*/
		 
		 this.getServletContext().getRequestDispatcher("/bet.jsp" ).forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
