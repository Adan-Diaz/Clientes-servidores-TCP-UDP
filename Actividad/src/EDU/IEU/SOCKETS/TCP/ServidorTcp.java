package EDU.IEU.SOCKETS.TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTcp {
		
	public void iniciar() {
		ServerSocket servidor;
		boolean fin = false;
		try {
			servidor = new ServerSocket(6000);
			System.out.println("Servidor escuchando en el puerto 6000");
			Socket socketManejoCliente = servidor.accept();
			DataInputStream   in = new DataInputStream( socketManejoCliente.getInputStream() );
			DataOutputStream out = new DataOutputStream( socketManejoCliente.getOutputStream() );
			do {
				String mensaje = "";
				mensaje = in.readUTF();
				System.out.println("El servidor recibió del cliente: "+ mensaje);
				out.writeUTF("Gracias por tu mensaje"); 
				if(mensaje.equalsIgnoreCase("fin")) {
					fin = true;
					System.out.println("El servidor se apagara ya no recibe mas mensajes:");					
				}				
			}while(!fin);
			in.close();
			out.close();
			socketManejoCliente.close();
			servidor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	public static void main(String args[]) {
		ServidorTcp servidor = new ServidorTcp();
		servidor.iniciar();
	}
}
