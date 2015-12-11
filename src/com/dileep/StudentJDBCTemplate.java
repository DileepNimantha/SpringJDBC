package com.dileep;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class StudentJDBCTemplate implements StudentDAO {
	@SuppressWarnings("unused")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public void create(String name, Integer age) {
		if (!checkForExistance(name)) {
			String SQL = "insert into STUDENT (name, age) values (?, ?)";
			jdbcTemplateObject.update(SQL, name, age);
			System.out.println("Created record: Name = " + name + ", Age = " + age);
			return;
		}
		return;
	}

	private boolean checkForExistance(String name) {
		String SQL = "select * from STUDENT where name = ? LIMIT 1";
		List<Student> student = jdbcTemplateObject.query(SQL, new Object[] { name }, new StudentMapper());
		if (!student.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public Student getStudent(Integer id) {
		String SQL = "select * from STUDENT where id = ?";
		Student student = jdbcTemplateObject.queryForObject(SQL, new Object[] { id }, new StudentMapper());
		return student;
	}

	@Override
	public List<Student> listStudents() {
		String SQL = "select * from Student";
		List<Student> students = jdbcTemplateObject.query(SQL, new StudentMapper());
		return students;
	}

	@Override
	public void delete(Integer id) {
		String SQL = "delete from STUDENT where id=?";
		jdbcTemplateObject.update(SQL, id);
		System.out.println("Deleted record with ID = " + id);
		return;
	}

	@Override
	public void update(Integer id, Integer age) {
		String SQL = "update STUDENT set age = ? where id = ?";
		jdbcTemplateObject.update(SQL, age, id);
		System.out.println("Updated record with ID = " + id);
		return;
	}

}
