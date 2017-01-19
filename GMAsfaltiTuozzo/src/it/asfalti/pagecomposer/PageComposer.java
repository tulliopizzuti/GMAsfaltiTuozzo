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
 * Servlet principale utilizzata per costruire le pagine jsp
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
				if(page.equals("ordscarico")){
					getServletContext().getRequestDispatcher("/getOrdScarico?id="+user.getIdM()).include(request, response);
				}
				if(page.equals("opconcl")){
					getServletContext().getRequestDispatcher("/getOpConcl?id="+user.getIdM()).include(request, response);
				}
				if(page.equals("opsosp")){
					getServletContext().getRequestDispatcher("/getOpSosp?id="+user.getIdM()).include(request, response);
				}
				if(page.equals("scarico")){
					getServletContext().getRequestDispatcher("/getdisp?id="+user.getIdM()).include(request, response);
				}
				if(page.equals("carico")){
					getServletContext().getRequestDispatcher("/getProds").include(request, response);
				}
			}
			if(user.getTipo().equals("admin")){
				if(page.equals("alldisp")){
					getServletContext().getRequestDispatcher("/getAllMag").include(request, response);
				}	
				if(page.equals("allopconcl")){
					getServletContext().getRequestDispatcher("/getAllMag").include(request, response);
				}
				if(page.equals("delprod")){
					getServletContext().getRequestDispatcher("/getProdsElim").include(request, response);
				}
				if(page.equals("modprod")){
					getServletContext().getRequestDispatcher("/getProds").include(request, response);
				}
				if(page.equals("modmag")){
					getServletContext().getRequestDispatcher("/getAllMag").include(request, response);
				}
				if(page.equals("delmag")){
					getServletContext().getRequestDispatcher("/getAllMag").include(request, response);
				}
				if(page.equals("ordforn")){
					getServletContext().getRequestDispatcher("/getOrdForn").include(request, response);
				}
				if(page.equals("ordcarico")){
					getServletContext().getRequestDispatcher("/getOrdCar").include(request, response);
				}
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
