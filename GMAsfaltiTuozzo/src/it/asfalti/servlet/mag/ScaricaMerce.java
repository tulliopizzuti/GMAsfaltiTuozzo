package it.asfalti.servlet.mag;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import it.asfalti.db.DBInformation;
import it.asfalti.db.GetInformation;
import it.asfalti.javabean.ComposizioneBean;
import it.asfalti.javabean.MagazzinoBean;
import it.asfalti.javabean.OperazioneCompletataBean;
import it.asfalti.javabean.ProdottoBean;

/**
 * Servlet implementation class ScaricaMerce
 */
@WebServlet("/scaricaMerce")
public class ScaricaMerce extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static GetInformation information;
	static{
		information=new DBInformation();
	}  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScaricaMerce() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		MagazzinoBean user=(MagazzinoBean) session.getAttribute("user");
		OperazioneCompletataBean op=new OperazioneCompletataBean(" ", user.getIdM(), "client", new Date(System.currentTimeMillis()), " ",new ArrayList<ComposizioneBean>() );
		JSONArray obj=new JSONArray(request.getParameter("obj"));
		
		for(int i=0;i<obj.length();i++){
			op.getListaProdotti().add(new ComposizioneBean(" ",
					Float.parseFloat(obj.getJSONObject(i).getString("q")), 
					new ProdottoBean(obj.getJSONObject(i).getString("id"), "", "")
					));
		}
		String text=null;
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		if(information.registraScarico(op))
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
