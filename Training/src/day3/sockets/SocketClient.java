package day3.sockets;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {
	
	private Socket clientSocket;
	private DataOutputStream out;
	private FileInputStream fis;
	
	public void start(String address, int port, String filePath) {
		try {
			
			clientSocket = new Socket(address, port);
			
			System.out.println("Client started: " + port);
			
			out = new DataOutputStream(clientSocket.getOutputStream());
			
			File file = new File(filePath);
			fis = new FileInputStream(file);
			
			out.writeUTF(file.getName());
			out.writeLong(file.length());
			
			byte[] buffer = new byte[4096];
			int read;
			while((read = fis.read(buffer)) != -1) {
				out.write(buffer, 0, read);
			}
			
			System.out.println("=== [Success] " + file.getName() + " has sended ===");
			
		} catch (FileNotFoundException e) {
			System.out.println("=== [Error] No such file error ===");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("=== [Error] Error while writing ===");
			e.printStackTrace();
		} finally { 
			try {
				if (fis != null) fis.close();
				if (out != null) out.close();
				if (clientSocket != null) clientSocket.close();
			} catch (IOException e) {
				System.out.println("=== [Error] Error while closing ===");
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		SocketClient socketClient = new SocketClient();
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter source file path: ");
		String srcFilePath = sc.nextLine();
		
		socketClient.start("127.0.0.1", 7777, srcFilePath);
		
	}

}
