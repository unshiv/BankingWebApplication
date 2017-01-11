<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="./resources/cssFiles/accountpage.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="./resources/jsFiles/accountPage.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<title>Applications Page</title>
</head>

<body>

	<div class="container-fluid">
		<div id="top">
			<form action="./BankLoginPage.html">
				<input type="submit" class="btn btn-danger" value="LogOut"
					style="float: right;" onclick="logout" />
			</form>
			<h1 class="text-center">
				<%
					String st = (String) session.getAttribute("accountName");
					out.println("<center><h1>Hi " + st + "</h1></center>");
				%>

			</h1>
		</div>
		<form action="./AccountOperationServelt" method="post">
			<div id="left">
				<ul id="leftList" class="nav nav-pill nav-stacked">
					<li id="homeLink"><label><u>Home</u></label></li>
					<li id="creditLink"><label><u>Credit balance</u></label></li>
					<li id="debitLink"><label><u>Debit balance</u></label></li>
					<li id="transferLink"><label><u>Transfer amount</u></label></li>
					<li id="transLink"><label><u>Resent transactions</u></label></li>
					<li></li>
				</ul>
			</div>

			<div id="content">
				<div id="homeid">
					<label>Account Number: <%=session.getAttribute("accNo")%>  Your Account balance is <%=session.getAttribute("balance")%></label>
					<br /> <label>You can perform any actions by selecting the
						appropriate menu item</label><br/>
						<c:if test="<%= session.getAttribute(\"transfered\") == (Object)1%>">
						<div><h2 class="text-center">Account Transfer Failed, The specified account number does'nt exist.</h2></div>
						</c:if>
				</div>
				<div id="creditid">
					<fieldset>
						<legend> Credit Balance into Account</legend>
						Credit Balance: <input type="text" name="credit" /> <input
							type="submit" name="operation" value="Credit" />
					</fieldset>
				</div>
				<div id="debitid">
					<fieldset>
						<legend> Debit Balance into Account</legend>
						Debit Balance: <input type="text" name="debit" /> <input
							type="submit" name="operation" value="Debit" />
					</fieldset>
				</div>
				
				<div id="transferid">
					<fieldset>
						<legend> Transfer balance from one account to other account</legend>
						Account Number: <input type="text" name="transferAccountNo" />
						Amount: <input type="text" name="transferAmount" />
						 <input	type="submit" name="operation" value="Transfer" />
					</fieldset>
				</div>
				
				<div id="transid">
					<h1>
						<center>Recent Transactions</center>
					</h1>
					<table class="table table-bordered">
						<tr>
							<th><u>Transaction Number</u></th>
							<th><u>Transaction type</u>
							<th><u>Amount</u>
						</tr>
						<% Integer accNo = (Integer) session.getAttribute("accNo"); %>
						<jsp:useBean id="transactions" class="beans.TransactionBean">
						</jsp:useBean>
						<jsp:setProperty property="accountNumber" name="transactions"
							value="${accNo}" />

						<c:forEach var="trans" items="${transactions.transList}">
							<tr>
								<td>${trans.transId}</td>
								<td>${trans.type}</td>
								<td>${trans.amount}</td>
							</tr>
						</c:forEach>

					</table>
					<table class="table" width="20%">
						<tr>
							<td colspan="2"><b> Total Balance</b></td>
							<td><b><%=session.getAttribute("balance")%></b></td>
						</tr>
					</table>
				</div>
			</div>
		</form>
	</div>
</body>
</html>