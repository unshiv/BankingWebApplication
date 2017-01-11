package servlets;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbOperations.AccountTranscations;

/**
 * Servlet implementation class AccountOperationServelt
 */
public class AccountOperationServelt extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AccountTranscations ats;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountOperationServelt() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 HttpSession session = request.getSession(false);
		 ats = new AccountTranscations(Integer.parseInt(session.getAttribute("accNo").toString()));
		 PrintWriter out = response.getWriter();
		 RequestDispatcher rd = request.getRequestDispatcher("/MemberAccountPage.jsp");
		 
		 if(request.getParameter("operation").equals("Debit"))
		 {
			 int success = ats.debitMoney(Double.parseDouble(request.getParameter("debit")));
			 if(success >0)
			 {
				 out.println("Low Balance");
				 session.setAttribute("balance", ats.getBalance());
				 rd.forward(request, response);
			 }
			 else
			 {
				 session.setAttribute("balance", ats.getBalance());
				 session.setAttribute("transfered", 0);
				 rd.forward(request, response);
			 }
		 }
		 else if(request.getParameter("operation").equals("Credit"))
		 {
			 ats.creditMoney(Double.parseDouble(request.getParameter("credit")));
			 session.setAttribute("balance", ats.getBalance());
			 session.setAttribute("transfered", 0);
			 rd.forward(request, response);
		 }
		 else
		 {
			 if(0 > ats.transferMoney(Integer.parseInt(request.getParameter("transferAccountNo")), Double.parseDouble(request.getParameter("transferAmount"))))
			 {
				 out.println("Transfer Failed");
				 session.setAttribute("balance", ats.getBalance());
				 session.setAttribute("transfered", 1);
				 rd.forward(request, response); 
			 }
			 else
			 {
				 session.setAttribute("balance", ats.getBalance());
				 rd.forward(request, response);
			 }
		 }
	}

}
