package it.asfalti.servlet.amm;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import it.asfalti.db.DBInformation;
import it.asfalti.db.GetInformation;
import it.asfalti.javabean.DisponibilitaBean;

/**
 * Servlet implementation class GetDispOf
 */
@WebServlet("/getDispOf")
public class GetDispOf extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static GetInformation information;
    static{
    	information=new DBInformation();
    }   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDispOf() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<DisponibilitaBean> disp=information.getDisponibilita((String)request.getParameter("id"));
		JSONArray array=new JSONArray();
		JSONObject obj=null;
		for(DisponibilitaBean d:disp){
			obj=new JSONObject();
			obj.put("id", d.getProd().getId());
			obj.put("desc", d.getProd().getDescrizione());
			obj.put("mis", d.getProd().getMisura());
			obj.put("q", d.getQ());
			array.put(obj);
		}
		response.getWriter().write(array.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
