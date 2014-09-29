import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class HttpServer {

	private final int port;
	
	HttpServer(int port) {
			this.port = port;
		}
		
		public void run() throws Exception {
			//Create two event loop group for work with requests
			EventLoopGroup bossGroup = new NioEventLoopGroup();
			EventLoopGroup workerGroup = new NioEventLoopGroup();
			
			try {
				ServerBootstrap bootstrap = new ServerBootstrap()
					.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ServerInitialization());

				//Create a new Channel and bind it
				bootstrap.bind(port).sync().channel().closeFuture().sync();
			} finally {
				workerGroup.shutdownGracefully();
				bossGroup.shutdownGracefully();
			}
		}
		
		public static void main(String[] args) throws Exception {
			int port = 80;
			if(args.length > 0) {
				port = Integer.parseInt(args[0]);
			} else {
				System.out.println("Open the brawser with localhost:80/   ");
			}
			
			new HttpServer(port).run();
		}
		}
