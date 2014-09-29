import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class MainServerHandler extends ChannelInboundHandlerAdapter{
	private String ip;
	private String uri;
	private int sentBytes;
	private int recievedBytes;
	private double speed;
			
		MainServerHandler(String ip) {
			this.ip = ip;
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
					
			recievedBytes += msg.toString().length();
			if(!(msg instanceof HttpRequest)) 
				return;
				
			uri = ((HttpRequest) msg).getUri();
			FullHttpResponse response = new ServHandler().checkuri((uri));
					
			if(uri.contains("%3C")) {//here we are checking if there any %3C symbols,if contains we take it
				this.uri = uri.substring(17, uri.length()-3);
			} 
			ServerInitialization.rs.setCountUniqueConnection(uri);//unique connections
					
			if(response != null) {
				this.sentBytes = response.content().writerIndex();
						
				ctx.write(response).addListener(ChannelFutureListener.CLOSE);
			}
				}
		
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		speed = System.currentTimeMillis();//get the time
		ServerInitialization.rs.setCount();// count open connections
		ServerInitialization.rs.setNumberAcvite();//count active connections			
		RequestStatistics.getInstance().setFirstIP(ip);//gets first IP for the first request
	}

	

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		
		speed = (System.currentTimeMillis() - speed) / 1000;
		speed = (recievedBytes + sentBytes) / speed;
		speed = new BigDecimal(speed).setScale(2, RoundingMode.UP).doubleValue();//to round the digits 

		Statistics cip = new Statistics(ip, uri, sentBytes, recievedBytes, speed);
		ServerInitialization.rs.addConnection(cip);
				
		ServerInitialization.rs.dropNumberActive();//active connections
				
		ctx.flush();
			}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		
		cause.printStackTrace();
		ctx.close();
		}
}
