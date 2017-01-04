package it.asfalti.servlet.mag;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import it.asfalti.db.DBInformation;
import it.asfalti.db.GetInformation;
import it.asfalti.javabean.DisponibilitaBean;

/**
 * Servlet implementation class GetDisp
 */
@WebServlet("/getdisp")
public class GetDisp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static GetInformation information;
    static{
    	information=new DBInformation();
    } 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDisp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<DisponibilitaBean> disp=information.getDisponibilita((String)request.getParameter("id"));
		request.setAttribute("disp", disp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
