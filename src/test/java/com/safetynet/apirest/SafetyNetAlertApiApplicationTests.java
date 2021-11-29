package com.safetynet.apirest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.apirest.config.DataLoader;

@SpringBootTest
class SafetyNetAlertApiApplicationTests {

	@Autowired
	private DataLoader dataLoader;

	@Test
	void contextLoads() {
	}

	@Test
	public void dataSourceTest() {
		String firstNameExpected = "John";
		String lastNameExpected = "Boyd";
		String addressExpected = "1509 Culver St";
		String cityExpected = "Culver";
		String zipExpected = "97451";
		String phoneExpected = "841-874-6512";
		String emailExpected = "jaboyd@email.com";

		assertEquals(firstNameExpected, dataLoader.getDataSrc().getPersons().get(0).getFirstName());
		assertEquals(lastNameExpected, dataLoader.getDataSrc().getPersons().get(0).getLastName());
		assertEquals(addressExpected, dataLoader.getDataSrc().getPersons().get(0).getAddress());
		assertEquals(cityExpected, dataLoader.getDataSrc().getPersons().get(0).getCity());
		assertEquals(zipExpected, dataLoader.getDataSrc().getPersons().get(0).getZip());
		assertEquals(phoneExpected, dataLoader.getDataSrc().getPersons().get(0).getPhone());
		assertEquals(emailExpected, dataLoader.getDataSrc().getPersons().get(0).getEmail());

		String addressFireStationExpected = "1509 Culver St";
		String stationExpected = "3";

		assertEquals(addressFireStationExpected, dataLoader.getDataSrc().getFirestations().get(0).getAddress());
		assertEquals(stationExpected, dataLoader.getDataSrc().getFirestations().get(0).getStation());

		String firstName2Expected = "John";
		String lastName2Expected = "Boyd";
		String birthdateExpected = "03/06/1984";
		String drug1Expected = "aznol:350mg";
		String drug2Expected = "hydrapermazol:100mg";
		String allergieExpected = "nillacilan";

		assertEquals(firstName2Expected, dataLoader.getDataSrc().getMedicalrecords().get(0).getFirstName());
		assertEquals(lastName2Expected, dataLoader.getDataSrc().getMedicalrecords().get(0).getLastName());
		assertEquals(birthdateExpected, dataLoader.getDataSrc().getMedicalrecords().get(0).getBirthdate());
		assertEquals(drug1Expected, dataLoader.getDataSrc().getMedicalrecords().get(0).getMedications().get(0));
		assertEquals(drug2Expected, dataLoader.getDataSrc().getMedicalrecords().get(0).getMedications().get(1));
		assertEquals(allergieExpected, dataLoader.getDataSrc().getMedicalrecords().get(0).getAllergies().get(0));

		assertNotNull(dataLoader.getDataSrc());

	}
}
