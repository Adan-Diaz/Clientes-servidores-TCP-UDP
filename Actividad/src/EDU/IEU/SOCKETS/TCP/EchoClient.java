package EDU.IEU.SOCKETS.TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import EDU.IEU.SOCKETS.UDP.ClienteUDP;

public class EchoClient {
	private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    

    public void startConnection(String ip, int port) {
    	try {
	        clientSocket = new Socket(ip, port);
	        out = new PrintWriter(clientSocket.getOutputStream(), true);
	        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    	}catch(IOException ex) {
    		ex.printStackTrace();
    	}
    }

    public String sendMessage(String msg) {
    	try {
	        out.println(msg);
	        System.out.println("Enviado " + msg);
	        String resp = in.readLine();
	        System.out.println("Respuesta " + resp);
	        return resp;
    	}catch(IOException ex) {
    		ex.printStackTrace();
    		return null;
    	}
    }

    public void stopConnection() {
        try {
			in.close();
			out.close();
		    clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void main(String[] args) {
    	BufferedReader inTeclado = new BufferedReader( new InputStreamReader(System.in));
    	
    	EchoClient echoClient = new EchoClient();    	
    	echoClient.startConnection("localhost", 6000);
    	try {
	    	while(true){
	    		System.out.print("Escriba un mensaje al servidor: ");   		
				String mensaje = inTeclado.readLine();
				
				System.out.println("Mensaje para el servidor " + mensaje);
				String respuesta = echoClient.sendMessage(mensaje);
				System.out.println("Respuesta : " + respuesta);
				if(respuesta.equals("good bye")) {
					System.out.println("Conexion finalizada");
					break;
				}
	    	}
	    	echoClient.stopConnection();
    	} catch (IOException e) {
			e.printStackTrace();
		}

    }
    
}
