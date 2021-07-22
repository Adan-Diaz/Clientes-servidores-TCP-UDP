package EDU.IEU.SOCKETS.UDP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClienteUDP {
	private BufferedReader inTeclado;
	
	private DatagramSocket socket;
	private DatagramPacket paqueteRecibido;
	private DatagramPacket paqueteEnviado;
	private InetAddress address;
	private int port = 6000;
	private byte[] mensajeBytesRecibido = new byte[256];
	private byte[] mensajeBytesEnviado = new byte[256];
	private String MensajeTextoRecibido  = "";
	private String MensajeTextoEnviado = "";
	
	public ClienteUDP() {
		inTeclado = new BufferedReader( new InputStreamReader(System.in));
	}
	
	public static void main(String[] args) {
		ClienteUDP clienteUdp = new ClienteUDP();
		clienteUdp.enviarYRecibir();
	}
	
	public void enviarYRecibir() {
		try {
			socket = new DatagramSocket();
			address = InetAddress.getByName("localhost");
			
			do {
				System.out.println("Escriba un mensaje para el servidor: ");
				MensajeTextoEnviado = inTeclado.readLine();
				mensajeBytesEnviado = MensajeTextoEnviado.getBytes();
				
				paqueteEnviado = new DatagramPacket(mensajeBytesEnviado, 
						mensajeBytesEnviado.length,
						address,
						port);
				socket.send(paqueteEnviado);
				System.out.println("Enviado al servidor " + address.toString() 
				+ " puerto " + port + " mensaje " + MensajeTextoEnviado);
				
				paqueteRecibido = new DatagramPacket(mensajeBytesRecibido, 256);
				socket.receive(paqueteRecibido);
				MensajeTextoRecibido = new String(mensajeBytesRecibido).trim();
				System.out.println("Mensaje recibido del servidor " + 
						MensajeTextoRecibido);
			}while(!MensajeTextoRecibido.startsWith("Chauuuuu"));
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
}
