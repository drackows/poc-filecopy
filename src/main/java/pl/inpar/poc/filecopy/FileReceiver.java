package pl.inpar.poc.filecopy;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class FileReceiver implements Runnable {

	private Socket socket;
	private String name;

	public FileReceiver(String name, Socket socket) {
		this.name = name;
		this.socket = socket;
    }

	@Override
	public void run() {
		DataInputStream inputStream = null;
		DataOutputStream outputStream = null;
		FileOutputStream outFile = null;
		
		try {
	        inputStream = new DataInputStream(socket.getInputStream());
	        outputStream = new DataOutputStream(socket.getOutputStream());

	        String fileName = inputStream.readUTF();
        	System.out.println("Receiving file: "+fileName);
        	outFile = new FileOutputStream(new File("/tmp/"+fileName));
        	byte[] buffer = new byte[2048];
        	int read = 0;
        	long reveivedBytes = 0;
        	long startTime = System.currentTimeMillis();
	        while(true) {
	        	reveivedBytes += 2048;
	        	read = inputStream.read(buffer);
	        	if (read!=-1) {
	        		outFile.write(buffer, 0, read);
	        	} else {
	        		break;
	        	}
	        }
	        long tookTile = System.currentTimeMillis()-startTime;
	        System.out.println("Receiving file finished (size: "+reveivedBytes+" bytes, time: " + ((double)tookTile)/1000 + ", speed: " 
	        		+ (int)(((double)reveivedBytes)/1024)/(((double)tookTile)/1000) + "kB/s )");
	        
        } catch (IOException e) {
	        e.printStackTrace();
        } finally {
        	try {
        		if (inputStream!=null) inputStream.close();
        		if (outputStream!=null) outputStream.close();
	            socket.close();
            } catch (IOException e) {
	            e.printStackTrace();
            }
        }
		
		
		
	}

}
