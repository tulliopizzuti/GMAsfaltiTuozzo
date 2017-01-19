package it.asfalti.servlet.common;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.asfalti.db.DBInformation;
import it.asfalti.db.GetInformation;
import it.asfalti.javabean.MagazzinoBean;

/**
 * Servlet che gestisce il login per tutti i tipi di utente
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MagazzinoBean user=null;;
	static GetInformation information;
    static{
    	information=new DBInformation();
    }   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String redirectedPage;
		if(username!=null && password!=null){
			try {
				checkLogin(username, password);
				request.getSession().setAttribute("user", user);
				request.getSession().removeAttribute("error");
				String tipo=user.getTipo();
				redirectedPage="pagecomposer?responsepage="+tipo+"page";
			} catch (Exception e) {
				redirectedPage="pagecomposer?responsepage=login";
				request.getSession().setAttribute("error", e.getMessage());
			}
			response.sendRedirect(redirectedPage);
		}
	}
	private void checkLogin (String username, String password) throws Exception{
		if((user=information.checkLogin(username, password))==null){

			throw new Exception("Login fallito");
			
		}
	}
}
