package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Student;
import Model.User;

public class DAO {
	Connection connection = DBUtills.connectDb();
	public ArrayList<User> getAllUsers() {
		ArrayList<User> users = new ArrayList<>();
		try {
			PreparedStatement stm = connection.prepareStatement("select * from User");
			ResultSet rs = stm.executeQuery();
			while(rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("password");
				User user = new User(username, password);
				users.add(user);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return users;
	}

	public ArrayList<Student> getAllStudents() {
		
		PreparedStatement stm;
		ArrayList<Student> students = new ArrayList<>();
		try {
			stm = connection.prepareStatement("select * from Student");
			ResultSet rs = stm.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id_student");
				String name = rs.getNString("name");
				int year = rs.getInt("year");
				double avg = rs.getInt("avg");
				Student student = new Student(id, name, year, avg);
				students.add(student);
			}
			stm.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return students;
		
	}

	public boolean checkUsername(String username) {
		for (User user : getAllUsers()) {
			if (user.getUsername().equals(username))
				return true;
		}
		return false;
	}
	public boolean checkLogin(String username, String password) {
		for (User user : getAllUsers()) {
			if (user.getUsername().equals(username) && user.getPassword().equals(password))
				return true;
		}
		return false;
	}
	public Student searchById(String id){
		for (Student student : getAllStudents()) {
			if((student.getId()+"").equals(id))
				return student;
		}
		return null;
	}
	public ArrayList<Student> searchByName(String name){
		ArrayList<Student> students = new ArrayList<Student>();
		for (Student student : getAllStudents()) {
			if(student.getName().endsWith(name)) {
				students.add(student);
			}
		}
		return students;
	}
	public static void main(String[] args) {
		System.out.println(new DAO().getAllUsers());
	}
}
