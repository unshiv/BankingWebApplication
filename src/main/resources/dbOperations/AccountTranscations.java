package dbOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountTranscations {

	int accountNo;
	StorageDB sdb;
	Connection conn;
	PreparedStatement pst;

	public AccountTranscations(int accountNo) {
		this.accountNo = accountNo;
		sdb = new StorageDB();
		conn = sdb.getConnection();
	}

	public double getBalance() {
		double balance = 0;
		try {
			pst = conn.prepareStatement("select * from accounts where ACCNT_ID=?");
			pst.setInt(1, accountNo);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				balance = rs.getDouble("balance");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return balance;
	}

	public void creditMoney(double amountCredit) {
		double totalAnmount = getBalance() + amountCredit;
		try {
			pst = conn.prepareStatement("update accounts SET balance = ? where ACCNT_ID=?");
			pst.setDouble(1, totalAnmount);
			pst.setInt(2, accountNo);
			pst.executeUpdate();
			// --
			pst = conn.prepareStatement("insert into txs values(null,'Credit',?,?,?)");
			java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
			pst.setTimestamp(1, date);
			pst.setInt(2, (int) amountCredit);
			pst.setInt(3, accountNo);
			pst.executeUpdate();
			// --

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int debitMoney(double amountDebit) {
		if ((getBalance() - amountDebit) < 500) {
			return -1;
		} else {
			double totalAnmount = getBalance() - amountDebit;
			try {
				pst = conn.prepareStatement("update accounts SET balance = ? where ACCNT_ID=?");
				pst.setDouble(1, totalAnmount);
				pst.setInt(2, accountNo);
				pst.executeUpdate();
				// --
				pst = conn.prepareStatement("insert into txs values(null,'Debit',?,?,?)");
				java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
				pst.setTimestamp(1, date);
				pst.setInt(2, (int) amountDebit);
				pst.setInt(3, accountNo);

				pst.executeUpdate();
				// --
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return 1;
		}
	}

	public int transferMoney(int accountNo, double transferBalance) {
		int success = -1;
		conn = sdb.getConnection();
		AccountTranscations newAccount = new AccountTranscations(accountNo);
		double total = newAccount.getBalance() + transferBalance;
		success = this.debitMoney(transferBalance);
		if (success > 0) {
			try {
				if (conn.createStatement().executeQuery("select * from accounts where ACCNT_ID=" + accountNo).next()) {
					pst = conn.prepareStatement("update accounts set balance=? where ACCNT_ID=?");
					pst.setDouble(1, total);
					pst.setInt(2, accountNo);
					pst.executeUpdate();
				}
				else
				{
					success =-1;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return success;
	}
}
