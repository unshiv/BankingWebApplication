package dbOperations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import supportClasses.Transaction;

public class StorageDB {

	Connection conn = null;
	PreparedStatement pst = null;

	public Connection getConnection() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/imcs", "root", "root");
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return conn;
	}

	public int checkAllUsernames(String username) {
		int pass = 1;
		conn = getConnection();
		ResultSet rs;
		try {
			pst = conn.prepareStatement("select username from cred");
			rs = pst.executeQuery();
			while (rs.next()) {
				if (rs.getString("username").equals(username)) {
					pass = -1;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pass;

	}

	public int insertIntoTables(String name, String username, double balance,
			String password) throws SQLException {
		conn = getConnection();

		if (checkAllUsernames(username) == -1) {
			return -1;
		} else {
			pst = conn
					.prepareStatement("insert into accounts values(null,?,?,500,?)");
			pst.setString(1, name);
			pst.setDouble(2, balance);
			pst.setString(3, username);

			pst.executeUpdate();

			pst = conn
					.prepareStatement("insert into cred values(?,?,LAST_INSERT_ID())");

			pst.setString(1, username);
			pst.setString(2, password);

			return pst.executeUpdate();
		}

	}

	// public int insertIntoTransactionTables(int txid, int tx_type, Date date,
	// Double amount, int accnt_id) throws SQLException
	// {
	// conn = getConnection();
	// pst = conn.prepareStatement("insert into accounts values(null,?,?,?,?)");
	// pst.setInt(1, tx_type);
	// pst.setDate(2, (java.sql.Date) date);
	// pst.setDouble(3, amount);
	// pst.setInt(4, accnt_id);
	// pst.executeUpdate();
	//
	// pst = conn.prepareStatement("insert into txs
	// values(?,?,LAST_INSERT_ID())");
	//
	//
	// return pst.executeUpdate();
	// }

	public int validateUser(String username, String password) {
		conn = getConnection();
		int accNo = -1;
		try {
			pst = conn.prepareStatement("select * from cred where username=?");
			pst.setString(1, username);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				if (password.equals(rs.getString("password"))) {
					accNo = rs.getInt("ACCNT_ID");
				} else {
					accNo = -1;
				}
			} else {
				accNo = -1;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return accNo;
	}

	public TreeMap<String, String> findName(int accountNo) {
		TreeMap<String, String> accMap = new TreeMap<String, String>();
		try {
			pst = conn
					.prepareStatement("select * from accounts where ACCNT_ID=?");
			pst.setInt(1, accountNo);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				accMap.put("name", rs.getString("name"));
				accMap.put("balance", rs.getString("Balance"));
				accMap.put("username", rs.getString("username"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accMap;
	}

	public List<Transaction> txListC(int AccountNumber)
			throws NumberFormatException, ParseException {
		conn = getConnection();
		List<Transaction> transList = new ArrayList<Transaction>();
		try {
			pst = conn.prepareStatement("select * from txs where ACCNT_ID=?");
			pst.setInt(1, AccountNumber);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				transList.add(new Transaction(rs.getInt("TX_ID"), rs
						.getString("TX_TYPE"), Double.parseDouble(rs
						.getString("AMOUNT")), new SimpleDateFormat(
						"yyyy-mm-dd").parse((rs.getString("TX_DATE")))));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return transList;
	}
	
}
