import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;


public class ServerInitialization extends ChannelInitializer<SocketChannel>{
	
public static RequestStatistics rs = RequestStatistics.getInstance();
	
@Override
protected void initChannel(SocketChannel sc) throws Exception {
	
String ip = sc.remoteAddress().getHostString();//receive IP 
		
ChannelPipeline pipeline = sc.pipeline();
		
pipeline.addLast("decoder", new HttpRequestDecoder());
pipeline.addLast("encoder", new HttpResponseEncoder());
pipeline.addLast("handler", new MainServerHandler(ip));
	}

}
