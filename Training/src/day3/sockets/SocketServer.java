package day3.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SocketServer {

	private ServerSocket serverSocket;
	private Socket clientSocket;
	private DataInputStream in;
	private DataOutputStream out;
	
	public void start(int port, String dstFilePath) {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server started: " + port);
			
			// 클라이언트 연결 끊겨도 계속 기다리기
			while (true) {
				try {
					clientSocket = serverSocket.accept();
					System.out.println("Client " + clientSocket.getPort() + " connected");
					
					in = new DataInputStream(clientSocket.getInputStream());
					out = new DataOutputStream(clientSocket.getOutputStream());
					
					String fileName = in.readUTF();
					long fileSize = in.readLong();
					
				
					FileOutputStream fos = new FileOutputStream(new File(dstFilePath, fileName));
					byte[] buffer = new byte[4096];
					int read = 0;
					long totalRead = 0;
					
					while (totalRead < fileSize && (read = in.read(buffer)) != -1) {
						fos.write(buffer, 0, read);
						totalRead += read;
					}
					
					System.out.println("=== [Success] Receiving " + fileName + " && Server keep runningn ===");
					System.out.println();
					
				} catch(FileNotFoundException e) {
					System.out.println("=== [Error] File not found ===");
				} catch (IOException e) {
					System.out.println("=== [Error] Data cannot be read ===");
				}
			}
			
		} catch (IOException e) {
			System.out.println("=== [Error] IO error occured while waiting for connection ===");
			e.printStackTrace();
		} finally {
			try {
				if (serverSocket != null) serverSocket.close();
				if (clientSocket != null) clientSocket.close();
				if (in != null) in.close();
				if(out != null) out.close();
			} catch (IOException e) {
				System.out.println("=== [Error] IO error occured while closing connection ===");
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		SocketServer socketServer = new SocketServer();
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter dir path where to download: ");
		String dstFilePath = sc.nextLine();
		socketServer.start(7777, dstFilePath);
	}

}
