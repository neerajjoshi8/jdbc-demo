package org.example.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person {
	private long id;
	private String name;
	private int age;
	private String mobileNumber;

	public static List<Person> getDummyPersons() {
		List<Person> dummyPersons = new ArrayList<>();
		for (int i = 1; i < 20; i++) {
			Person person = new Person(Long.valueOf(i), "abc" + i, 12 + i, "123432121" + i);
			dummyPersons.add(person);
		}
		return dummyPersons;
	}
}
