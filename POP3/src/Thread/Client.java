package Thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException {
		BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Input: ");

		Socket socket = new Socket("127.0.0.3", 1000);

		BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

		while (true) {
			String line = userIn.readLine();
			out.println(line);
			System.out.println(serverIn.readLine());

		}
	}
}
