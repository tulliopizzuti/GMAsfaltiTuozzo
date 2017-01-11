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
import it.asfalti.javabean.ComposizioneBean;
import it.asfalti.javabean.OperazioneCompletataBean;

/**
 * Servlet implementation class GetOpOf
 */
@WebServlet("/getOpOf")
public class GetOpOf extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static GetInformation information;
    static{
    	information=new DBInformation();
    } 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetOpOf() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<OperazioneCompletataBean> ops=information.getOperazioniComp((String)request.getParameter("id"));
		JSONArray array=new JSONArray();
		JSONObject obj=null;
		for(OperazioneCompletataBean op:ops){
			obj=new JSONObject();
			obj.put("idO", op.getIdOp());
			obj.put("idM", op.getIdM());
			obj.put("data", op.getData());
			obj.put("da_a", op.getDa_a());
			obj.put("tipo", op.getTipo());
			JSONArray prods=new JSONArray();
			JSONObject p=null;
			for(ComposizioneBean c: op.getListaProdotti()){
				p=new JSONObject();
				p.put("idP", c.getProdotto().getId());
				p.put("pDesc", c.getProdotto().getDescrizione());
				p.put("pMis", c.getProdotto().getMisura());
				p.put("pQ", c.getQuantita());
				prods.put(p);
			}
			obj.put("prods",prods);		
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
