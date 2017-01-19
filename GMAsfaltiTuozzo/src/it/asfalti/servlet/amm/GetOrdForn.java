package it.asfalti.servlet.amm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.asfalti.db.DBInformation;
import it.asfalti.db.GetInformation;

/**
 * Preleva tutti gli oridni fornitore dal database e li registra sulla richiesta in modo da poter costruire la pagina jsp che li richiede
 */
@WebServlet("/getOrdForn")
public class GetOrdForn extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static GetInformation information;
    static{
    	information=new DBInformation();
    }   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetOrdForn() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("ops", information.getAllOrdForn());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
