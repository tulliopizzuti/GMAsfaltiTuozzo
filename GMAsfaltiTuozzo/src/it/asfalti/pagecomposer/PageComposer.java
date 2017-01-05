package it.asfalti.pagecomposer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.asfalti.javabean.MagazzinoBean;

/**
 * Servlet implementation class PageComposer
 */
@WebServlet("/pagecomposer")
public class PageComposer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PageComposer() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		MagazzinoBean user=(MagazzinoBean)session.getAttribute("user");
		String page=request.getParameter("responsepage");
		
		if(page==null)
			page="login";
		if(user!=null){
			if(user.getTipo().equals("mag")){
				if(page.equals("disp")){
					getServletContext().getRequestDispatcher("/getdisp?id="+user.getIdM()).include(request, response);
				}
			
			}
			if(user.getTipo().equals("admin")){
							
			}
		}
		
		
		
		
		
		
		
		getServletContext().getRequestDispatcher("/"+page+".jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}