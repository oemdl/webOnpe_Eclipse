package svl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class svlActas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public svlActas() {
        super();
    }

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		dao.OnpeDAO daoOnpe = new dao.OnpeDAO();
					
		String id = request.getParameter("id");
		String nroMesa = request.getParameter("nroMesa");
		String cboDepartamento = request.getParameter("cboDepartamento");
		
		Object data = null, departamentos = null;
		
		if ( id == null ) {
			departamentos = daoOnpe.getDepartamentos(1,25);
			session.setAttribute("departamentos", departamentos);
		}
		if ( nroMesa != null ) {
			id = nroMesa;
			data = daoOnpe.getGrupoVotacion(nroMesa);
		}
		
		session.setAttribute("id", id);
		session.setAttribute("data", data);
		
		response.sendRedirect("actas.jsp");
	}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}