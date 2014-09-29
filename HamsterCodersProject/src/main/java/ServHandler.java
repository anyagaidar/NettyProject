import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;


public class ServHandler {
	
	public FullHttpResponse checkuri(String uri) throws InterruptedException {

	String url = "";		
	if(uri.contains("%3C")) {
		url = uri.substring(17, uri.length()-3);
			uri = uri.substring(0, 9);
			RequestStatistics.getInstance().putURLandCountHim(url);
		}
								
		switch(uri) {
			case "/hello":
				return uriHelloWorld();
			case "/redirect":
				return uriRedirect(url);
			case "/status":
				return uriStatus();							   	
			default:
				return notFounduri();
			}
		}
			
	private FullHttpResponse uriHelloWorld() throws InterruptedException {// Hello world! in the browser
		String hello = "<head><center><font color=\"green\",font size = \"10\"><br><I>Hello world!</I></font></center></br></head>";
		FullHttpResponse uri = new DefaultFullHttpResponse(HTTP_1_1, OK,
				Unpooled.copiedBuffer(hello, CharsetUtil.US_ASCII));
			
	Thread.sleep(10000L);// delay for 10 seconds
	
		return uri;
			}
	// provides an answer to the status query "/status"
		private FullHttpResponse uriStatus() {
					
			FullHttpResponse uri = new DefaultFullHttpResponse(HTTP_1_1, OK,
							Unpooled.copiedBuffer(ServerInitialization.rs.getReport(), CharsetUtil.US_ASCII));	
					return uri;
				}
			
	// provides an answer to the Redirect query "/redirect<url>"
	private FullHttpResponse uriRedirect(String url) {
				
		FullHttpResponse uri = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.FOUND);
		uri.headers().set(HttpHeaders.Names.LOCATION, url);
		return uri;
	}
			
			
			//If the page is not found
	private FullHttpResponse notFounduri() {
		String notFound = "<head><tr>Sorry, the page is not available <br> 404 Not Found</tr></br></head>";
		DefaultFullHttpResponse uri = new DefaultFullHttpResponse(HTTP_1_1, OK, 
		Unpooled.copiedBuffer(notFound, CharsetUtil.US_ASCII));
			
	return uri;
	}
}
