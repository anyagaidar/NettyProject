import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;


public final class RequestStatistics {

	//The uri 'instance' hold a reference to a single instance
	private static RequestStatistics instance = null;

	private List<Statistics> listIP = new ArrayList<>();
	private Set<String> uniqueIP = new HashSet<>();
			
	//Save and numerate url
	private Map<String, Integer> countURL = new TreeMap<>();
	private long numberQuery = 0;
	private long numberActive = 0;
	//exception if listIP is empty
	private String firstIP;
			
	private RequestStatistics() {
		
	}
			
	//The only way how to take link to this instance
	public synchronized static RequestStatistics getInstance() {
		
		if (instance == null) {
			instance = new RequestStatistics();
				}
			return instance;
		}
			
	public synchronized String getReport() {
		String lastTimestamp = (String) (listIP.size() > 0 ? listIP.get(listIP.size()-1).getTimestamp(): new Date()+"");
		String lastIP = getFirstIP();
		if(listIP.size() >= 1) {
			lastIP = listIP.get(listIP.size()-1).getIP();
		}
		String result = "<html><head><center><font size=10, UTF-8, font color=\"green\"><P><I>Statistics: </I></P></font></center></head>"
				
				.concat("<table border = 4><tbody><tr><th>All connections</th><th>Active connections</th><th>Unique connections</th><th>IP</th>")
				.concat("<th>Last Connection</th></tr></tbody><tr><th>" + numberQuery + "</th><th>" + numberActive + "</th>"+"<th>" +  uniqueIP.size() +"</th>")
				.concat("</th><th>"+  lastIP +"</th><th>"+  lastTimestamp + "</th></tr></table><br>");
		

		result += ("<table border = 4><tbody><tr><th>IP</th><th>URI</th><th>Timestamp</th><th>Sent bytes</th><th>Recieved bytes</th>")
				.concat("<th>Speed(bytes/sec)</th></tr></tbody>");
			for(Statistics cip: listIP) {
				result +="<tr><th>" + cip.getIP() + "</th>" +
						"<th>" + cip.getURI() + "</th>" +
							  "<th>" + cip.getTimestamp()+ "</th>" +
								  "<th>" + cip.getSentBytes() + "</th>" +
								     "<th>" + cip.getRecieviedBytes() + "</th>" +
								   "<th>" + cip.getSpeed() + "</th>";
				
		}result.concat("</table>");
			
			
			
			
			result+=("<table border = 4><br><tbody><tr><th>URL</th><th>URL Counter</th></tr></tbody><tr>");
			for(Entry<String, Integer> k : countURL.entrySet()) {
				result += "<tr><th>" + k.getKey() + "</th>" +
							 "<th>" + k.getValue() + "</th></tr>";
			}result.concat("</tbody></table></html>");
			
		return result;
	}		
	public synchronized void addConnection(Statistics cip) {
		if(listIP.size() > 15)
			listIP.remove(0);
			listIP.add(cip);
	}
			
	public synchronized void setCount() {
		numberQuery++;
	}
			
	public synchronized void setFirstIP(String ip) {
		firstIP = ip;
	}
			
	public synchronized String getFirstIP() {
		return firstIP;
	}
			
	public synchronized void putURLandCountHim(String url) {
		if(countURL.containsKey(url)) {
			countURL.put(url, new Integer(countURL.get(url)+1));
	}
		else {
			countURL.put(url, new Integer(1));
		}
	}
			
	// Unique connection is considered by the unique query
			
	public synchronized void setCountUniqueConnection(String s) {
		if(!s.equals("/favicon.ico"))
			uniqueIP.add(s);
	}
			
	public synchronized void setNumberAcvite() {
		numberActive++;
	}
			
	public synchronized void dropNumberActive() {
		numberActive--;
	}
}
