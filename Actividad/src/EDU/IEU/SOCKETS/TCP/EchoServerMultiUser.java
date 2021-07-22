package EDU.IEU.SOCKETS.TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServerMultiUser {
	private ServerSocket serverSocket;
	
	public void start(int port) {
		try {
			serverSocket = new ServerSocket(port);
			while(true)
				new EchoClienteHandler( serverSocket.accept() ).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int texto2Numero(String texto) {
		return Integer.parseInt(texto);
	}
	
	public String numero2Texto(int numero) {
		return String.valueOf(numero);
	}
	
	public void stop() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		EchoServerMultiUser echoServer = new EchoServerMultiUser();
		System.out.println("Servicio incializado...");
		echoServer.start(6000);
		System.out.println("Servicio finalizado....");
		
	}
	
	private static class EchoClienteHandler extends Thread{
		private Socket clientSocket;
		private PrintWriter out;
		private BufferedReader in;		
		
		public EchoClienteHandler(Socket socket) {
			this.clientSocket = socket;
		}

		@Override
		public void run() {			
			try {
				out = new PrintWriter( clientSocket.getOutputStream(), true );
				in = new BufferedReader(new InputStreamReader( clientSocket.getInputStream() ) );
				
				String inputLine;
				while( (inputLine = in.readLine() )  != null) {
					if(".".equals(inputLine)) {
						out.println("good bye");
						break;
					}
					out.println(inputLine);
				}
				in.close();
				out.close();
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}
}
