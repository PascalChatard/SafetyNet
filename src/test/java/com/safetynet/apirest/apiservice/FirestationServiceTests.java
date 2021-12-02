package com.safetynet.apirest.apiservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.apirest.apirepository.FirestationRepository;
import com.safetynet.apirest.model.DataSrc;
import com.safetynet.apirest.model.Firestation;
import com.safetynet.apirest.model.ListPersonsByFirestation;

class FirestationServiceTests {

	@Mock
	private FirestationRepository repositoryFirestation;

	DataSrc dataSrc;

	@InjectMocks
	private FirestationService srvFirestation;

	Firestation firestation;

	String dataSrcString = "{" + "    \"persons\": ["
			+ "        { \"firstName\":\"John\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"jaboyd@email.com\" },"
			+ "        { \"firstName\":\"Jacob\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6513\", \"email\":\"drk@email.com\" },"
			+ "        { \"firstName\":\"Tenley\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"tenz@email.com\" },"
			+ "        { \"firstName\":\"Roger\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"jaboyd@email.com\" },"
			+ "        { \"firstName\":\"Felicia\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6544\", \"email\":\"jaboyd@email.com\" },"
			+ "        { \"firstName\":\"Eric\", \"lastName\":\"Cadigan\", \"address\":\"951 LoneTree Rd\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-7458\", \"email\":\"gramps@email.com\" }"
			+ "	], " + "    \"firestations\": [" + "	{ \"address\":\"1509 Culver St\", \"station\":\"3\" },"
			+ "        { \"address\":\"29 15th St\", \"station\":\"2\" },"
			+ "        { \"address\":\"834 Binoc Ave\", \"station\":\"3\" },"
			+ "        { \"address\":\"748 Townings Dr\", \"station\":\"3\" },"
			+ "        { \"address\":\"951 LoneTree Rd\", \"station\":\"2\" }" + "	]," + "    \"medicalrecords\": ["
			+ "        { \"firstName\":\"John\", \"lastName\":\"Boyd\", \"birthdate\":\"03/06/1984\", \"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"], \"allergies\":[\"nillacilan\"] },"
			+ "        { \"firstName\":\"Jacob\", \"lastName\":\"Boyd\", \"birthdate\":\"03/06/1989\", \"medications\":[\"pharmacol:5000mg\", \"terazine:10mg\", \"noznazol:250mg\"], \"allergies\":[] },"
			+ "        { \"firstName\":\"Tenley\", \"lastName\":\"Boyd\", \"birthdate\":\"02/18/2012\", \"medications\":[], \"allergies\":[\"peanut\"] },"
			+ "        { \"firstName\":\"Roger\", \"lastName\":\"Boyd\", \"birthdate\":\"09/06/2017\", \"medications\":[], \"allergies\":[] },"
			+ "        { \"firstName\":\"Felicia\", \"lastName\":\"Boyd\",\"birthdate\":\"01/08/1986\", \"medications\":[\"tetracyclaz:650mg\"], \"allergies\":[\"xilliathal\"] },"
			+ "        { \"firstName\":\"Eric\", \"lastName\":\"Cadigan\", \"birthdate\":\"08/06/1945\", \"medications\":[\"tradoxidine:400mg\"], \"allergies\":[] }"
			+ "        ] " + "}";

	@BeforeEach
	public void init() throws JsonMappingException, JsonProcessingException {

		firestation = Firestation.builder().address("1509 Culver St").station("3").build();

		ObjectMapper objectMapper = new ObjectMapper();
		// mapping des donnees contenues dans la String dans le mock
		// DataSrc
		dataSrc = objectMapper.readValue(dataSrcString, DataSrc.class);

		srvFirestation = new FirestationService(dataSrc);
		MockitoAnnotations.openMocks(this);
	}

	@Test
	final void testServiceDeleById() {

		// GIVEN
		when(repositoryFirestation.deleteById(0)).thenReturn(firestation);

		// WHEN
		Firestation deletedFirestation = srvFirestation.serviceDeleteById(0);

		// THEN
		assertThat(deletedFirestation).isEqualTo(firestation);
		verify(repositoryFirestation, times(1)).deleteById(0);
	}

	@Test
	final void testServiceSave() {

		// GIVEN
		when(repositoryFirestation.save(firestation)).thenReturn(firestation);

		// WHEN
		Firestation newFirestation = srvFirestation.serviceSave(firestation);

		// THEN
		assertThat(newFirestation).isEqualTo(firestation);
		verify(repositoryFirestation, times(1)).save(firestation);
	}

	@Test
	final void testServiceUpdate() {
		when(repositoryFirestation.update(0, firestation)).thenReturn(firestation);

		// WHEN
		Firestation newFirestation = srvFirestation.serviceUpdate(0, firestation);

		// THEN
		assertThat(newFirestation).isEqualTo(firestation);
		verify(repositoryFirestation, times(1)).update(0, firestation);
	}

	@Test
	final void testListOfPersonsCoverByFirestation() {

		// WHEN
		ListPersonsByFirestation listPersons = srvFirestation.listOfPersonsCoverByFirestation("3");

		// THEN
		assertThat(listPersons.getPersons()).isNotEmpty();
		assertThat(listPersons.getChildrenNumber()).isEqualTo("2");
		assertThat(listPersons.getAdultsNumber()).isEqualTo("3");
		assertThat(listPersons.getPersons().get(0).getFirstName()).isEqualTo("John");
		assertThat(listPersons.getPersons().get(0).getLastName()).isEqualTo("Boyd");
		assertThat(listPersons.getPersons().get(0).getAddress()).isEqualTo("1509 Culver St");
		assertThat(listPersons.getPersons().get(0).getPhone()).isEqualTo("841-874-6512");

		assertThat(listPersons.getPersons().get(1).getFirstName()).isEqualTo("Jacob");
		assertThat(listPersons.getPersons().get(1).getLastName()).isEqualTo("Boyd");
		assertThat(listPersons.getPersons().get(1).getAddress()).isEqualTo("1509 Culver St");
		assertThat(listPersons.getPersons().get(1).getPhone()).isEqualTo("841-874-6513");

		assertThat(listPersons.getPersons().get(2).getFirstName()).isEqualTo("Tenley");
		assertThat(listPersons.getPersons().get(2).getLastName()).isEqualTo("Boyd");
		assertThat(listPersons.getPersons().get(2).getAddress()).isEqualTo("1509 Culver St");
		assertThat(listPersons.getPersons().get(2).getPhone()).isEqualTo("841-874-6512");

		assertThat(listPersons.getPersons().get(3).getFirstName()).isEqualTo("Roger");
		assertThat(listPersons.getPersons().get(3).getLastName()).isEqualTo("Boyd");
		assertThat(listPersons.getPersons().get(3).getAddress()).isEqualTo("1509 Culver St");
		assertThat(listPersons.getPersons().get(3).getPhone()).isEqualTo("841-874-6512");

		assertThat(listPersons.getPersons().get(4).getFirstName()).isEqualTo("Felicia");
		assertThat(listPersons.getPersons().get(4).getLastName()).isEqualTo("Boyd");
		assertThat(listPersons.getPersons().get(4).getAddress()).isEqualTo("1509 Culver St");
		assertThat(listPersons.getPersons().get(4).getPhone()).isEqualTo("841-874-6544");
	}

	@Test
	final void testListOfPersonsCoverByFirestation_forNoFoundStation() {

		// WHEN
		ListPersonsByFirestation listPersons = srvFirestation.listOfPersonsCoverByFirestation("15");

		// THEN
		assertThat(listPersons.getPersons()).isEmpty();
		assertThat(listPersons.getChildrenNumber()).isEqualTo("0");
		assertThat(listPersons.getAdultsNumber()).isEqualTo("0");
	}

	@Test
	final void testListOfPersonsCoverByFirestation_forEmptyStation() {

		// WHEN
		ListPersonsByFirestation listPersons = srvFirestation.listOfPersonsCoverByFirestation("");

		// THEN
		assertThat(listPersons.getPersons()).isEmpty();
		assertThat(listPersons.getChildrenNumber()).isEqualTo("0");
		assertThat(listPersons.getAdultsNumber()).isEqualTo("0");
	}

}
