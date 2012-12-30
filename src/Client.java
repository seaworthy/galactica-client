import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class Client {
    public static void main(String[] args) throws IOException {

	String ip = "50.116.15.78";
	Integer port = 9000;
	Socket socket = null;
	InputStream fromServer = null;
	OutputStream toServer = null;

	try {
	    socket = new Socket(ip, port);
	    toServer = socket.getOutputStream();
	    fromServer = socket.getInputStream();

	    System.out.println("Connection established");
	} catch (UnknownHostException e) {
	    System.err.println("Host not found");
	    System.exit(1);
	} catch (IOException e) {
	    System.err.println("I/O error");
	    System.exit(1);
	}

	byte[] operation_code = new byte[1];
	operation_code[0] = (byte) (0x01);
	String string = "GALACTICA";
	byte[] game_name = string.getBytes();
	byte[] protocol_version = new byte[1];
	protocol_version[0] = (byte) (0x01);

	ByteBuffer buffer = ByteBuffer.allocate(12);

	buffer.put(operation_code);
	buffer.put(game_name);
	buffer.put(protocol_version);
	buffer.put((byte) 0xff);

	buffer.rewind();

	while (buffer.hasRemaining()) {
	    toServer.write(buffer.get());
	}
	System.out.println("client: " + "command sent");

	byte[] message = new byte[1];
	fromServer.read(message);
	while (message[0] != (byte) 0xFF) {
	    fromServer.read(message);
	    System.out.println("server: " + " "
		    + Integer.toHexString(message[0]));
	}
	if (message[0] == (byte) 0xFF)
	    System.out.println("client: " + "disconnected");

	toServer.close();
	fromServer.close();
	socket.close();
    }
}