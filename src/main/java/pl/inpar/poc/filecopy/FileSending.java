package pl.inpar.poc.filecopy;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileSending implements Runnable {

	public static void main(String[] args) {
		
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(5);
		
		try {
			FileSending fileSending = new FileSending(new Socket("localhost", FileReceiverServer.PORT));
			newFixedThreadPool.execute(fileSending);
        } catch (IOException e) {
	        e.printStackTrace();
        }
		
	}

	private Socket socket;
	
	public FileSending(Socket socket) {
		this.socket = socket;
	}
	
	@Override
    public void run() {
		
		DataInputStream inputStream = null;
		DataOutputStream outputStream = null;
		FileInputStream fileInputStream = null;
		
		int number;
		byte[] buffer = new byte[2048];
		
		try {
	        inputStream = new DataInputStream(socket.getInputStream());
	        outputStream = new DataOutputStream(socket.getOutputStream());
	        
//	        File file = new File("src/main/resources/scala-2.10.2.tgz");
	        File file = new File("/home/drackowski/Pobrane/Windows 7 Starter (32 Bit) by (oldBen)/windows 7 starter/Windows 7 Starter (32 Bit).iso");
			fileInputStream = new FileInputStream(file);
	        outputStream.writeUTF(file.getName());
	        while ((number = fileInputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, number);
			}
	        

//	        int messages = 5+new Random().nextInt(20);
//	        while(messages>0) {
//	        	String ans = inputStream.readUTF();
//	        	System.out.println(ans);
//	        	outputStream.writeUTF(name + "Ping "+messages);
//	        	messages--;
//	        }
//	        outputStream.writeUTF("CU");
	        System.out.println("Talking finished.");
	        
        } catch (IOException e) {
	        e.printStackTrace();
        } finally {
        	try {
        		fileInputStream.close();
        		inputStream.close();
        		outputStream.close();
	            socket.close();
            } catch (IOException e) {
	            e.printStackTrace();
            }
        }
		

		
    }
	
	
	

}
