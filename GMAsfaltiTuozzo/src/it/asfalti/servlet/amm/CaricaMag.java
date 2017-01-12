package it.asfalti.servlet.amm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import it.asfalti.db.DBInformation;
import it.asfalti.db.GetInformation;

/**
 * Servlet implementation class CaricaMag
 */
@WebServlet("/caricaMag")
public class CaricaMag extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static GetInformation information;
    static{
    	information=new DBInformation();
    }    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CaricaMag() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject obj=new JSONObject(request.getParameter("obj"));
		JSONArray prods=(JSONArray) obj.get("prods");
		JSONArray mags=(JSONArray) obj.get("mags");
		JSONArray q=(JSONArray) obj.get("q");
		String idOp=(String) obj.get("idOp");
		if(prods.length()==mags.length() && mags.length()==q.length() ){
			for(int i=0;i<prods.length();i++){
				System.out.println(prods.getString(i)+" "+mags.getString(i)+" "+q.getDouble(i));
			}
			
			
			
		}		
		/*String text=null;
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		if(information.creaMag(mag))
			text="true";
		else
			text="false";
		

		response.getWriter().write(text);*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
