package com.safetynet.apirest.apiservice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.apirest.model.DataSrc;
import com.safetynet.apirest.model.ListChildByAddress;

class ChildAlertServiceTests {

	@Mock
	DataSrc dataSrc;

	@InjectMocks
	private ChildAlertService childAlertSrv;

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

		ObjectMapper objectMapper = new ObjectMapper();
		// mapping des donnees contenues dans la String dans le mock
		// DataSrc
		dataSrc = objectMapper.readValue(dataSrcString, DataSrc.class);
		childAlertSrv = new ChildAlertService(dataSrc);
	}

	@Test
	final void testListOfChildrenLivingAtAddress() {

		ListChildByAddress childs = childAlertSrv.listOfChildrenLivingAtAddress("1509 Culver St");
		assertThat(childs.getChildren().size()).isEqualTo(2);
		assertThat(childs.getChildren().get(0).getFirstName()).isEqualTo("Tenley");
		assertThat(childs.getChildren().get(0).getLastName()).isEqualTo("Boyd");
		assertThat(childs.getChildren().get(0).getAge()).isEqualTo("9");
		assertThat(childs.getChildren().get(1).getFirstName()).isEqualTo("Roger");
		assertThat(childs.getChildren().get(1).getLastName()).isEqualTo("Boyd");
		assertThat(childs.getChildren().get(1).getAge()).isEqualTo("4");

		assertThat(childs.getAdults().size()).isEqualTo(3);
		assertThat(childs.getAdults().get(0).getFirstName()).isEqualTo("John");
		assertThat(childs.getAdults().get(0).getLastName()).isEqualTo("Boyd");
		assertThat(childs.getAdults().get(1).getFirstName()).isEqualTo("Jacob");
		assertThat(childs.getAdults().get(1).getLastName()).isEqualTo("Boyd");
		assertThat(childs.getAdults().get(2).getFirstName()).isEqualTo("Felicia");
		assertThat(childs.getAdults().get(2).getLastName()).isEqualTo("Boyd");
		
	}

	@Test
	final void testListOfChildrenLivingAtAddress_forNotFoundAddress() {

		ListChildByAddress childs = childAlertSrv.listOfChildrenLivingAtAddress("102 Rue du Commerce");
		assertThat(childs.getChildren().size()).isEqualTo(0);
		assertThat(childs.getAdults().size()).isEqualTo(0);
		assertThat(childs.getChildren()).isEmpty();
		assertThat(childs.getAdults()).isEmpty();
	}

	@Test
	final void testListOfChildrenLivingAtAddress_forEmptyAddress() {

		ListChildByAddress childs = childAlertSrv.listOfChildrenLivingAtAddress("");
		assertThat(childs.getChildren().size()).isEqualTo(0);
		assertThat(childs.getAdults().size()).isEqualTo(0);
		assertThat(childs.getChildren()).isEmpty();
		assertThat(childs.getAdults()).isEmpty();
	}

}
