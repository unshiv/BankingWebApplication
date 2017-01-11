package beans;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;

import dbOperations.StorageDB;
import supportClasses.Transaction;

public class TransactionBean {
	
	int accountNumber;
	List<Transaction> transList;
	
	

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public List<Transaction> getTransList() {
		StorageDB sdb = new StorageDB();
		try {
			transList =  sdb.txListC(accountNumber);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Collections.reverse(transList);
		return transList;
	}

	public void setTransList(List<Transaction> transList) {
		this.transList = transList;
	}
	
	

}
