package EDU.IEU.SOCKETS.TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	private Socket clienteSocket;
	private ServerSocket serverSocket;
	private PrintWriter out;
	private BufferedReader in;
 	
	public void start(int port) {
		
		try {
			
			serverSocket = new ServerSocket(port);
			System.out.println("Servidor iniciado en el puerto " + port);
			
			clienteSocket = serverSocket.accept();
			System.out.println("Se conecto un cliente " + clienteSocket.getInetAddress().toString());

			out = new PrintWriter( clienteSocket.getOutputStream(), true );
			in = new BufferedReader( new InputStreamReader( clienteSocket.getInputStream() ) );
			
			String inputLine = "";
			while( (inputLine = in.readLine() ) != null ) {
				System.out.println("Recibimos del cliente:" + inputLine);
				if(".".contentEquals(inputLine)) {
					out.println("good bye");
					System.out.println("Servidor recibio orden de apagado");
					break;
				}
				out.println(inputLine);
			}
			out.close();
			in.close();
			clienteSocket.close();
			serverSocket.close();			
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public static void main(String[] args) {
		EchoServer echoServer = new EchoServer();
		echoServer.start(6000);
		System.out.println("Servicio finalizado....");
		
	}
}
