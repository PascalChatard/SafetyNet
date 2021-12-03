package com.safetynet.apirest.apirepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.apirest.exception.NullDataSrcException;
import com.safetynet.apirest.model.DataSrc;
import com.safetynet.apirest.model.Person;


class PersonRepositoryTests {


	private DataSrc dataSrc;

	PersonRepository classUnderTest;

	String dataSrcString = "{" + "    \"persons\": ["
			+ "        { \"firstName\":\"John\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"jaboyd@email.com\" },"
			+ "        { \"firstName\":\"Eric\", \"lastName\":\"Cadigan\", \"address\":\"951 LoneTree Rd\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-7458\", \"email\":\"gramps@email.com\" }"
			+ "	], " + "    \"firestations\": [" + "	{ \"address\":\"1509 Culver St\", \"station\":\"3\" },"
			+ "        { \"address\":\"951 LoneTree Rd\", \"station\":\"2\" }" + "	]," + "    \"medicalrecords\": ["
			+ "        { \"firstName\":\"John\", \"lastName\":\"Boyd\", \"birthdate\":\"03/06/1984\", \"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"], \"allergies\":[\"nillacilan\"] },"
			+ "        { \"firstName\":\"Eric\", \"lastName\":\"Cadigan\", \"birthdate\":\"08/06/1945\", \"medications\":[\"tradoxidine:400mg\"], \"allergies\":[] }"
			+ "        ] " + "}";


	@BeforeEach
	public void init() throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		// mapping des donnees contenues dans la String dans le mock
		// DataSrc
		dataSrc = objectMapper.readValue(dataSrcString, DataSrc.class);
	}

	@Test
	final void testDeleteById_ThrowNullDataSrcException() {
		// GIVEN
		classUnderTest = new PersonRepository(null);

		// WHEN
		assertThrows(NullDataSrcException.class, () -> classUnderTest.deleteById(0));
	}

	@Test
	final void testDeleteById_ThrowNullDataSrcException2() {

		// GIVEN
		classUnderTest = new PersonRepository(null);

		// WHEN
		try {
			classUnderTest.deleteById(0);
		}
		// THEN
		catch (NullDataSrcException e) {
			assertThat(e.getMessage()).contains("  *** Reference dataSrc == null ***  ");
		}
	}

	@Test
	final void testDeleteById() {

		// GIVEN
		classUnderTest = new PersonRepository(dataSrc);
		Person person = Person.builder().firstName("John").lastName("Boyd").address("1509 Culver St").city("Culver")
				.zip("97451").phone("841-874-6512").email("jaboyd@email.com").build();

		// WHEN
		Person deletedPerson = classUnderTest.deleteById(0);

		// TTHEN
		assertThat(deletedPerson).isEqualTo(person);
	}

	@Test
	final void testDeleteById_ThrowIndexOutOfBoundsException() {

		// GIVEN
		classUnderTest = new PersonRepository(dataSrc);

		// WHEN
		assertThrows(IndexOutOfBoundsException.class, () -> classUnderTest.deleteById(5));
	}

	@Test
	final void testSave_ThrowNullDataSrcException() {

		// GIVEN
		classUnderTest = new PersonRepository(null);
		Person person = new Person();

		// WHEN
		assertThrows(NullDataSrcException.class, () -> classUnderTest.save(person));
	}

	@Test
	final void testSave_NullPerson() {
		// GIVEN
		classUnderTest = new PersonRepository(dataSrc);
		// WHEN
		Person addedPerson = classUnderTest.save(null);
		// TTHEN
		assertThat(addedPerson).isNull();
	}

	@Test
	final void testSave() {

		// GIVEN
		classUnderTest = new PersonRepository(dataSrc);
		Person newPerson = Person.builder().firstName("Thierry").lastName("Thierry").address("17 Lot. Lou Campo")
				.city("Pertuis").zip("84120").phone("0490782588").email("thenri@gmail.com").build();

		// WHEN
		Person addedPerson = classUnderTest.save(newPerson);

		// TTHEN
		assertThat(addedPerson).isEqualTo(newPerson);
	}

	@Test
	final void testUpdate_ThrowNullDataSrcException() {

		// GIVEN
		classUnderTest = new PersonRepository(null);
		Person person = new Person();

		// WHEN
		assertThrows(NullDataSrcException.class, () -> classUnderTest.update(0, person));
	}

	@Test
	final void testUpdate() {
		// GIVEN
		classUnderTest = new PersonRepository(dataSrc);
		Person modifiedPerson = Person.builder().firstName("John").lastName("Boyd").address("13 Bvd Louvain")
				.city("Marseille").zip("13001").phone("0491795601").email("john.boyd@business.com").build();

		// WHEN
		Person oldPerson = classUnderTest.update(0, modifiedPerson);

		// TTHEN
		assertThat(modifiedPerson.getFirstName()).isEqualTo(oldPerson.getFirstName());
		assertThat(modifiedPerson.getLastName()).isEqualTo(oldPerson.getLastName());
		assertThat(modifiedPerson.getFirstName()).isEqualTo(oldPerson.getFirstName());
		assertThat(oldPerson.getAddress()).isEqualTo("1509 Culver St");
		assertThat(oldPerson.getCity()).isEqualTo("Culver");
		assertThat(oldPerson.getZip()).isEqualTo("97451");
		assertThat(oldPerson.getPhone()).isEqualTo("841-874-6512");
		assertThat(oldPerson.getEmail()).isEqualTo("jaboyd@email.com");
	}

	@Test
	final void testUpdate_ThrowIndexOutOfBoundsException() {

		// GIVEN
		classUnderTest = new PersonRepository(dataSrc);
		Person person = new Person();

		// TTHEN
		assertThrows(IndexOutOfBoundsException.class, () -> classUnderTest.update(5, person));
	}

}
