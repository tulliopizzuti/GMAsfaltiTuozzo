package it.asfalti.servlet.amm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import it.asfalti.db.DBInformation;
import it.asfalti.db.GetInformation;
import it.asfalti.javabean.MagazzinoBean;

/**
 * Crea un nuovo magazzino, i dati sono inviati alla servlet da javascript sotto forma di JSON object
 */
@WebServlet("/creaMag")
public class CreaMag extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static GetInformation information;
    static{
    	information=new DBInformation();
    }     
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreaMag() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject obj=new JSONObject(request.getParameter("obj"));
		MagazzinoBean mag=new MagazzinoBean(obj.getString("idM"),
				obj.getString("desc"),
				obj.getString("cit"), 
				obj.getString("via"),
				obj.getString("cap"),
				obj.getString("nC"),
				obj.getString("pass"),
				"");
		String text=null;
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		if(information.creaMag(mag))
			text="true";
		else
			text="false";
		

		response.getWriter().write(text);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
