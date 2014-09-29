import java.util.Date;


public class Statistics {
	
	private String ip = "";
	private String uri = "";
	private Date timestamp = new Date();
	private int sentBytes = 0;
	private int recievedBytes = 0;
	private double speed = 0;
		
	Statistics(String ip, String uri, int sentBytes, int recievedBytes, double speed) {
		this.ip = ip;
		this.uri = uri;
		this.timestamp = new Date();
		this.sentBytes = sentBytes;
		this.recievedBytes = recievedBytes;
		this.speed = speed;
	}
	
	public String toString() {
		return "" + ip + " " + uri+ " " + timestamp + " " + sentBytes + " " + recievedBytes  + " " + speed ;
	  
	}
	
	public String getIP() {
		return ip + "";
	}
	public String getURI() {
		return uri + "";
	}
	public String getTimestamp() {
		return timestamp + "";
	}
	public String getSentBytes() {
		return sentBytes + "";
	}
	public String getRecieviedBytes() {
		return recievedBytes + "";
	}
	public String getSpeed() {
		return speed + "";
	}

}
