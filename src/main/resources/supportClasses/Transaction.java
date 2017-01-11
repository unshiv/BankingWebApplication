package supportClasses;

import java.util.Date;

public class Transaction {
	
	int transId;
	String type;
	double amount;
	Date date;
	
	
	
	public Transaction(int transId, String type, double amount, Date date) {
		super();
		this.transId = transId;
		this.type = type;
		this.amount = amount;
		this.date = date;
	}
	public int getTransId() {
		return transId;
	}
	public void setTransId(int transId) {
		this.transId = transId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	

}
