package it.asfalti.servlet.amm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import it.asfalti.db.DBInformation;
import it.asfalti.db.GetInformation;

/**
 * La servlet riceve un oggetto json contenente gli id dei prodotti da cancellare.
 */
@WebServlet("/eliminaProdotti")
public class EliminaProdotti extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static GetInformation information;
    static{
    	information=new DBInformation();
    }   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EliminaProdotti() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONArray array=new JSONArray(request.getParameter("obj"));
		String text=null;
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		for(int i=0;i<array.length();i++){
			if(information.eliminaProd(array.getJSONObject(i).getString("id"))){
				text="true";
			}else{
				text="false";
				break;
			}
		}
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
