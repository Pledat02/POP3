package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

import DAO.DAO;
import Model.Student;

public class ServerThread extends Thread {
	private Socket socket;
	BufferedReader reader;
	PrintWriter out;
	boolean isLogin = false;

	public ServerThread(Socket socket) {
		this.socket = socket;
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String respone = null, command = null, param = null;
		String username = null;

		try {
			out.println("Welcome to my server");
			while (!isLogin) {
				String line = reader.readLine();
				if (line.equals("QUIT"))
					break;
				StringTokenizer stringTokenizer = new StringTokenizer(line, " ");
				if (stringTokenizer.countTokens() < 2) {
					respone = "echo: " + line;
				} else if (stringTokenizer.countTokens() > 2) {
					respone = "Lenh khong hop le";
				} else {
					command = stringTokenizer.nextToken().toUpperCase();
					param = stringTokenizer.nextToken();
					switch (command) {
					case "USER":
						if (!new DAO().checkUsername(param)) {
							respone = "khong tim  thay user name";
						} else {
							respone = "chao user " + param + "! hay nhap mat khau cua ban";
							username = param;
						}
						break;
					case "PASS":
						if (new DAO().checkLogin(username, param)) {
							respone = "Dang nhap thanh cong";
							isLogin = true;
						} else {
							respone = "Sai mat khau";
						}
						break;

					default:
						respone = "Lenh khong hop le";
						break;
					}
				}
				out.println(respone);

			}
			while (isLogin) {
				String line = reader.readLine();
				if (line.equals("QUIT"))
					break;
				StringTokenizer stringTokenizer = new StringTokenizer(line, " ");
				if (stringTokenizer.countTokens() < 2) {
					respone = "echo: " + line;
				} else if (stringTokenizer.countTokens() > 2) {
					respone = "Lenh khong hop le";
				} else {
					command = stringTokenizer.nextToken().toUpperCase();
					param = stringTokenizer.nextToken();
					switch (command) {
					case "SEARCHBYID":
						Student student = new DAO().searchById(param);
						if (new DAO().searchById(param)!=null) {
							respone = student.toString();
						} else {
							respone = "Khong tim thay student co id la: " + param;
							
						}
						break;
					case "SEARCHBYNAME":
						ArrayList<Student> students = new DAO().searchByName(param);
						if (students!=null) {
							respone = students.toString();
						} else {
							respone = "Khong tim thay student co name la: " + param;
						}
						break;

					default:
						respone = "Lenh khong hop le";
						break;
					}
				}
				out.println(respone);

			}

			socket.close();
		} catch (IOException e) {
			e.getStackTrace();
		}
	}

}
