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
import com.safetynet.apirest.model.Firestation;

class FirestationRepositoryTests {

	private DataSrc dataSrc;

	FirestationRepository classUnderTest;

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
		classUnderTest = new FirestationRepository(null);

		// WHEN
		assertThrows(NullDataSrcException.class, () -> classUnderTest.deleteById(0));
	}

	@Test
	final void testDeleteById_ThrowIndexOutOfBoundsException() {
		// GIVEN
		classUnderTest = new FirestationRepository(dataSrc);

		// WHEN
		assertThrows(IndexOutOfBoundsException.class, () -> classUnderTest.deleteById(5));
	}

	@Test
	final void testDeleteById() {
		// GIVEN
		classUnderTest = new FirestationRepository(dataSrc);
		Firestation firestation = Firestation.builder().address("1509 Culver St").station("3").build();

		// WHEN
		Firestation detetedFirestation = classUnderTest.deleteById(0);

		// THEN
		assertThat(firestation).isEqualTo(detetedFirestation);
	}

	@Test
	final void testSave_ThrowNullDataSrcException() {
		// GIVEN
		classUnderTest = new FirestationRepository(null);
		Firestation firestation = new Firestation();

		// WHEN
		assertThrows(NullDataSrcException.class, () -> classUnderTest.save(firestation));
	}

	@Test
	final void testSave_NullFirestation() {
		// GIVEN
		classUnderTest = new FirestationRepository(dataSrc);

		// WHEN
		Firestation firestation = classUnderTest.save(null);

		// THEN
		assertThat(firestation).isNull();
	}

	@Test
	final void testSave() {
		// GIVEN
		classUnderTest = new FirestationRepository(dataSrc);
		Firestation firestation = Firestation.builder().address("892 Downing Ct").station("2").build();

		// WHEN
		Firestation addedFirestation = classUnderTest.save(firestation);

		// THEN
		assertThat(addedFirestation).isEqualTo(firestation);
	}

	@Test
	final void testUpdate_ThrowNullDataSrcException() {
		// GIVEN
		classUnderTest = new FirestationRepository(null);
		Firestation firestation = new Firestation();

		// WHEN
		assertThrows(NullDataSrcException.class, () -> classUnderTest.update(0, firestation));
	}

	@Test
	final void testUpdate_ThrowIndexOutOfBoundsException() {
		// GIVEN
		classUnderTest = new FirestationRepository(null);
		Firestation firestation = new Firestation();

		// WHEN
		assertThrows(NullDataSrcException.class, () -> classUnderTest.update(5, firestation));
	}

	@Test
	final void testUpdate() {
		// GIVEN
		classUnderTest = new FirestationRepository(dataSrc);
		Firestation firestation = Firestation.builder().address("1509 Culver St").station("4").build();

		// WHEN
		Firestation modifiedFirestation = classUnderTest.update(0, firestation);

		// THEN
		assertThat(modifiedFirestation).isNotNull();
		assertThat(modifiedFirestation.getAddress()).isEqualTo("1509 Culver St");
		assertThat(modifiedFirestation.getStation()).isEqualTo("3");

	}

}
