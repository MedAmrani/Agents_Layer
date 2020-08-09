package ma.fstt.models;

import java.io.Serializable;


public class ScrapRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String since_date ; 
	private String until_date ;
	private String text_query ;
	private String count ;
	
	public ScrapRequest() {}
	
	public String getSince_date() {
		return since_date;
	}

	public void setSince_date(String since_date) {
		this.since_date = since_date;
	}

	public String getUntil_date() {
		return until_date;
	}

	public void setUntil_date(String until_date) {
		this.until_date = until_date;
	}

	public String getText_query() {
		return text_query;
	}

	public void setText_query(String text_query) {
		this.text_query = text_query;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	

}
