package com.safetynet.apirest.apirepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.apirest.exception.NullDataSrcException;
import com.safetynet.apirest.model.DataSrc;
import com.safetynet.apirest.model.MedicalRecord;

class MedicalRecordRepositoryTests {

	private DataSrc dataSrc;

	MedicalRecordRepository classUnderTest;


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
		classUnderTest = new MedicalRecordRepository(null);

		// WHEN
		assertThrows(NullDataSrcException.class, () -> classUnderTest.deleteById(0));
	}

	@Test
	final void testDeleteById_ThrowIndexOutOfBoundsException() {

		// GIVEN
		classUnderTest = new MedicalRecordRepository(dataSrc);

		// WHEN
		assertThrows(IndexOutOfBoundsException.class, () -> classUnderTest.deleteById(5));
	}

	@Test
	final void testDeleteById() {

		// GIVEN
		classUnderTest = new MedicalRecordRepository(dataSrc);
		MedicalRecord medicalRecord = MedicalRecord.builder().firstName("John").lastName("Boyd").birthdate("03/06/1984")
				.medications(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")).allergies(Arrays.asList("nillacilan"))
				.build();

		// WHEN
		MedicalRecord deletedMedicalRecord = classUnderTest.deleteById(0);

		// TTHEN
		assertThat(deletedMedicalRecord).isEqualTo(medicalRecord);
	}

	@Test
	final void testSave_ThrowNullDataSrcException() {

		// GIVEN
		classUnderTest = new MedicalRecordRepository(null);
		MedicalRecord medicalRecord = new MedicalRecord();

		// WHEN
		assertThrows(NullDataSrcException.class, () -> classUnderTest.save(medicalRecord));
	}

	@Test
	final void testSave_NullMedicalRecord() {

		// GIVEN
		classUnderTest = new MedicalRecordRepository(dataSrc);

		// WHEN
		MedicalRecord newMedicalRecord = classUnderTest.save(null);

		// TTHEN
		assertThat(newMedicalRecord).isNull();
	}

	@Test
	final void testSave() {

		// GIVEN
		classUnderTest = new MedicalRecordRepository(dataSrc);
		MedicalRecord medicalRecord = MedicalRecord.builder().firstName("Thierry").lastName("Henri")
				.birthdate("03/08/1982").medications(Arrays.asList("aznol:350mg", "hydrapermazol:100mg"))
				.allergies(Arrays.asList("nillacilan")).build();

		// WHEN
		MedicalRecord newMedicalRecord = classUnderTest.save(medicalRecord);

		// THEN
		assertThat(newMedicalRecord).isEqualTo(medicalRecord);
	}

	@Test
	final void testUpdate_ThrowNullDataSrcException() {

		// GIVEN
		classUnderTest = new MedicalRecordRepository(null);
		MedicalRecord medicalRecord = new MedicalRecord();

		// WHEN
		assertThrows(NullDataSrcException.class, () -> classUnderTest.update(0, medicalRecord));
	}

	@Test
	final void testUpdate_ThrowIndexOutOfBoundsException() {

		// GIVEN
		classUnderTest = new MedicalRecordRepository(dataSrc);
		MedicalRecord medicalRecord = new MedicalRecord();

		// WHEN
		assertThrows(IndexOutOfBoundsException.class, () -> classUnderTest.update(5, medicalRecord));
	}

	@Test
	final void testUpdate() {

		// GIVEN
		classUnderTest = new MedicalRecordRepository(dataSrc);
		MedicalRecord medicalRecord = MedicalRecord.builder().firstName("John").lastName("Boyd").birthdate("03/06/1984")
				.medications(Arrays.asList("aznol:1350mg", "hydrapermazol:1100mg"))
				.allergies(Arrays.asList("nillacilan", "pollens")).build();

		// WHEN
		MedicalRecord oldMedicalRecord = classUnderTest.update(0, medicalRecord);

		// THEN
		assertThat(oldMedicalRecord).isNotNull();
		assertThat(oldMedicalRecord.getFirstName()).isEqualTo("John");
		assertThat(oldMedicalRecord.getLastName()).isEqualTo("Boyd");
		assertThat(oldMedicalRecord.getBirthdate()).isEqualTo("03/06/1984");
		assertThat(oldMedicalRecord.getMedications().size()).isEqualTo(2);
		assertThat(oldMedicalRecord.getMedications().get(0)).isEqualTo("aznol:350mg");
		assertThat(oldMedicalRecord.getMedications().get(1)).isEqualTo("hydrapermazol:100mg");
		assertThat(oldMedicalRecord.getAllergies().size()).isEqualTo(1);
		assertThat(oldMedicalRecord.getAllergies().get(0)).isEqualTo("nillacilan");
	}

}
