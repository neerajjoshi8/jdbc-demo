package org.example.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.example.entity.Person;

public class PersonDao {
	private Connection con = null;

	private void init() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Properties properties = new Properties();
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			try (InputStream inputStream = loader.getResourceAsStream("db_credentials.properties")) {
				properties.load(inputStream);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		con = DriverManager.getConnection(properties.getProperty("dbUrl"), properties.getProperty("dbUsername"),
				properties.getProperty("dbPassword"));
	}

	private void close() throws SQLException {
		if (con != null) {
			con.close();
		}
	}

	public void insert(Person person) {
		try {
			init();
			PreparedStatement ps = con.prepareStatement("INSERT INTO person VALUES(?, ?, ?, ?)");
			ps.setLong(1, person.getId());
			ps.setString(2, person.getName());
			ps.setInt(3, person.getAge());
			ps.setString(4, person.getMobileNumber());
			ps.execute();
			System.out.println("Person " + person + " got successfully inserted into the table");
			close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public Optional<List<Person>> findAll() {
		List<Person> persons = new ArrayList<>();

		try {
			init();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM person");

			while (rs.next()) {
				Person person = new Person(rs.getLong(1), rs.getString(2), rs.getInt(3), rs.getString(4));
				persons.add(person);
			}
			close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(persons);
	}

	public void update(Person person) {
		try {
			init();
			PreparedStatement ps = con
					.prepareStatement("UPDATE person SET name = ?, age = ?, mobile_number = ? WHERE id = ?");
			ps.setString(1, person.getName());
			ps.setInt(2, person.getAge());
			ps.setString(3, person.getMobileNumber());
			ps.setLong(4, person.getId());
			ps.execute();
			System.out.println("Person " + person + " got updated");
			close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(Person person) {
		try {
			init();
			PreparedStatement ps = con.prepareStatement("DELETE FROM person WHERE id = ?");
			ps.setLong(1, person.getId());
			ps.execute();
			System.out.println("Person " + person + " got deleted");
			close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteExistingData() {
		try {
			init();
			Statement stmt = con.createStatement();
			stmt.execute("TRUNCATE TABLE person");
			System.out.println("Deleted all the data from person");
			close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}
