package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbOperations.StorageDB;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	StorageDB sdb = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		sdb = new StorageDB();
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		try {
			sdb.getConnection().close();
			sdb = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		int accNo = sdb.validateUser(username, password);
		TreeMap<String, String> values = sdb.findName(accNo);
		PrintWriter out = response.getWriter();
		if(accNo <=0 )
		{
			out.println("<h4><center>Invalid Credentials, Try again</center></h4>");
			RequestDispatcher rd = request.getRequestDispatcher("/BankLoginPage.html");
			rd.include(request, response);
		}
		else
		{
			HttpSession session = request.getSession();
			session.setAttribute("accNo", accNo);
			session.setAttribute("accountName", values.get("name"));
			session.setAttribute("balance", values.get("balance"));
			//session.setAttribute("transactions", transactions);
			RequestDispatcher rd = request.getRequestDispatcher("/MemberAccountPage.jsp");
			rd.forward(request, response);
		}
	}

}
