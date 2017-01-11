package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbOperations.StorageDB;

/**
 * Servlet implementation class CreateAccountServlet
 */
public class CreateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAccountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    StorageDB std;
    
    @Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		 std=new StorageDB();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String username = request.getParameter("uname");
		String password = request.getParameter("password");
		double balance = Double.parseDouble(request.getParameter("balance"));
		int success =0;
		try {
			 success= std.insertIntoTables(name, username, balance, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher rd = request.getRequestDispatcher("./BankLoginPage.html");
		if(success >= 1)
		{
			
			rd.forward(request, response);
		}
		else if(success < 0)
		{
			response.getWriter().println("<h4><center>Username already taken,</br> Please try a new one.</center></h4>");
			RequestDispatcher rdCPage = request.getRequestDispatcher("./AccountCreationPage.html");
			rdCPage.include(request, response);
		}
		else
		{
			response.getWriter().println("Unsuccessful");
			rd.include(request, response);
		}
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		try {
			std.getConnection().close();
			std = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	
	

}
