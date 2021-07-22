package EDU.IEU.SOCKETS.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class ServidorUDP {
	private DatagramSocket socket;
	private DatagramPacket paqueteRecibido;
	private DatagramPacket paqueteEnviado;
	
	public static void main(String[] args) {
		ServidorUDP servidorUdp = new ServidorUDP();
		System.out.println("Iniciando servidor en el puerto 6000....");
		servidorUdp.enviarYRecibir();
	}
	
	public void enviarYRecibir() {
		try {
			socket = new DatagramSocket(6000);
			byte[] mensajeBytesRecibido = new byte[256];
			byte[] mensajeBytesEnviado = new byte[256];
			String mensajeTexto = "";
			mensajeTexto = new String(mensajeBytesRecibido);
			String mensajeRespuesta = "";
			
			paqueteRecibido = new DatagramPacket(mensajeBytesRecibido,256);
			paqueteEnviado = new DatagramPacket(mensajeBytesEnviado,256); 
			
			int port;
			InetAddress address;
			byte[] mensaje2_bytes = new byte[256];
			
			do {
				socket.receive(paqueteRecibido);
				mensajeTexto = new String(mensajeBytesRecibido).trim();
				
				System.out.println("Mensaje recibido del cliente: " +
						mensajeTexto);
				port = paqueteRecibido.getPort();
				address = paqueteRecibido.getAddress();				
				System.out.println("del puerto: " + port + 
						", direccion ip: " + address.toString() );
				if(mensajeTexto.startsWith("fin")) {
					mensajeRespuesta = "Chauuuuu cliente";
				}
				if(mensajeTexto.startsWith("hola")) {
					mensajeRespuesta = "hola cliente";
				}
				mensajeBytesEnviado = mensajeRespuesta.getBytes();
				paqueteEnviado = new DatagramPacket(mensajeBytesEnviado,
						mensajeBytesEnviado.length,
						address,
						port
						);
				socket.send(paqueteEnviado);
			}while(true);
		}catch( IOException ex) {
			ex.printStackTrace();
		}
	}
}
