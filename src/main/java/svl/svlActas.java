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
		String idDepartamento = request.getParameter("cboDepartamento");
		String idProvincia = request.getParameter("cboProvincia");
		String idDistrito = request.getParameter("cboDistrito");
		String idLocalVotacion = request.getParameter("cboLocalVotacion");
		Object data = null;

		if ( idDepartamento != null && idDepartamento.equals("-1") )  {
			idProvincia = "-1";
			session.setAttribute("provincias", null);
		}
		if ( idProvincia != null && idProvincia.equals("-1") ) {
			idDistrito = "-1";
			session.setAttribute("distritos", null);
		}
		if ( idDistrito != null && idDistrito.equals("-1") ) {
			idLocalVotacion = "-1";
			session.setAttribute("locales", null);
		}
		
		if ( idDepartamento == null ) idDepartamento = "-1";
		if ( idProvincia == null ) idProvincia = "-1";
		if ( idDistrito == null ) idDistrito = "-1";
		if ( idLocalVotacion == null ) idLocalVotacion = "-1";
		
		if ( id == null && session.getAttribute("departamentos") == null )
			session.setAttribute("departamentos", daoOnpe.getDepartamentos(1,25) );
		
		if ( idDepartamento != null && !idDepartamento.equals("-1") )
			session.setAttribute("provincias", daoOnpe.getProvincias(idDepartamento) );
		
		if ( idProvincia != null && !idProvincia.equals("-1") )
			session.setAttribute("distritos", daoOnpe.getDistritos(idProvincia) );
		
		if ( idDistrito != null && !idDistrito.equals("-1") )
			session.setAttribute("locales", daoOnpe.getLocalesVotacion(idDistrito) );
		
		if ( nroMesa != null ) {
			id = nroMesa;
			data = daoOnpe.getGrupoVotacion(nroMesa);
		}

		
		String sDPD = "";
		if ( idDepartamento != null ) sDPD = idDepartamento;
		if ( idProvincia != null ) sDPD += "," + idProvincia;
		if ( idDistrito != null ) sDPD += "," + idDistrito;
		if ( idLocalVotacion != null ) sDPD += "," + idLocalVotacion;
		
		session.setAttribute("id", id);
		session.setAttribute("data", data);
		session.setAttribute("dpd", sDPD);
		
		response.sendRedirect("actas.jsp");
	}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}