package pl.inpar.poc.filecopy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FileReceiverServer {

	static final int PORT = 10101;

	public static void main(String[] args) throws IOException {
		
		/* Socket server on port PORT */
		ServerSocket serverSocket = new ServerSocket(PORT);
		
		ExecutorService executor = Executors.newFixedThreadPool(10);
		
		System.out.println("Server started: waiting for 5 clients");
		
		int clientsLimit = 5;
		while(clientsLimit>0) {
			Socket socket = null;
            try {
	            socket = serverSocket.accept();
	            System.out.println("Client connected! "+clientsLimit);
	            FileReceiver fileReceiver = new FileReceiver(clientsLimit+"#", socket);
	            executor.execute(fileReceiver);
            } catch (IOException e) {
	            e.printStackTrace();
            }
            clientsLimit--;
		}
		
		System.out.println("Stop accepting connections");
		
		try {
			executor.awaitTermination(10000, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
	        e.printStackTrace();
        } finally {
	        try {
	            serverSocket.close();
            } catch (IOException e) {
	            e.printStackTrace();
            }
        }
		
		byte[] buffer = new byte[65536];
/*
		InputStream socketStream= clientSocket.getInputStream();
		File f=new File("C:\\output.dat");

		OutputStream fileStream=new FileOutputStream(f);
		
		int number;
		while ((number = socketStream.read(buffer)) != -1) {
		    fileStream.write(buffer,0,number);
		}

		fileStream.close();
		socketStream.close();
*/
		
		
	}

}
