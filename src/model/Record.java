package model;

/**
 * @author Katy
 *
 */
public class Record {
	
	private String date;
	private String reason;

	public Record(String date, String reason) {
		this.date = date;
		this.reason = reason;
	}

	public String getDate() {
		return date;
	}

	public String getReason() {
		return reason;
	}
}
