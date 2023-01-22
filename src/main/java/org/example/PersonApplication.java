package org.example;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dao.PersonDao;
import org.example.entity.Person;

public class PersonApplication {
	private static final Logger logger = LogManager.getLogger(PersonApplication.class);

	public static void main(String[] args) throws InterruptedException {
		
		PersonDao personDao = new PersonDao();

		logger.info("Delete existing data present in the table");
		personDao.deleteExistingData();

		Thread.sleep(5000);

		logger.info("Inserting persons into table");
		List<Person> dummyPersons = Person.getDummyPersons();
		dummyPersons.forEach(personDao::insert);

		Thread.sleep(5000);

		logger.info("Printing all persons");
		List<Person> persons = personDao.findAll().orElse(new ArrayList<>());
		persons.forEach(System.out::println);

		if (!persons.isEmpty()) {
			Thread.sleep(5000);

			logger.info("Updating person {}", persons.get(0));
			persons.get(0).setAge(100);
			personDao.update(persons.get(0));

			Thread.sleep(5000);

			logger.info("Deleting the person {}", persons.get(persons.size() - 1));
			personDao.delete(persons.get(persons.size() - 1));

		}
	}

}
