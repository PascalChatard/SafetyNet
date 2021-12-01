package com.safetynet.apirest.apicontroller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import com.safetynet.apirest.model.Person;
import com.safetynet.apirest.utils.JsonUtils;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@DirtiesContext
	@Test
	final void testDeletePerson() throws Exception {

		// GIVEN WHEN THEN
		mockMvc.perform(delete("/person/{id}", 1)).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("Jacob")).andExpect(jsonPath("$.lastName").value("Boyd"))
				.andExpect(jsonPath("$.address").value("1509 Culver St"))
				.andExpect(jsonPath("$.city").value("Culver")).andExpect(jsonPath("$.zip").value("97451"))
				.andExpect(jsonPath("$.phone").value("841-874-6513"))
				.andExpect(jsonPath("$.email").value("drk@email.com"));
	}

	@DirtiesContext
	@Test
	final void testDeletePerson_withIndexOutOfBounds() throws Exception {

		// GIVEN WHEN THEN
		mockMvc.perform(delete("/person/{id}", 100)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error").value("Bad Request")).andExpect(jsonPath("$.path").value("/person/100"))
				.andExpect(jsonPath("$.exception").value("java.lang.IndexOutOfBoundsException"))
				.andExpect(jsonPath("$.message").value("Index 100 out of bounds for length 23"));
	}

	@Test
	final void  testAddPerson() throws Exception {
		
		// GIVEN
		Person newPerson = new Person();
		newPerson.setFirstName("Thierry");
		newPerson.setLastName("Henri");
		newPerson.setAddress("17 Lot. Lou Campo");
		newPerson.setCity("Aix-en-provence");
		newPerson.setZip("13120");
		newPerson.setPhone("0442782588");
		newPerson.setEmail("thenri@gmail.com");
		
		// WHEN THEN
		mockMvc.perform(post("/person").content(JsonUtils.asJsonString(newPerson))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.firstName").value("Thierry"))
				.andExpect(jsonPath("$.lastName").value("Henri"))
				.andExpect(jsonPath("$.address").value("17 Lot. Lou Campo"))
				.andExpect(jsonPath("$.city").value("Aix-en-provence")).andExpect(jsonPath("$.zip").value("13120"))
				.andExpect(jsonPath("$.phone").value("0442782588"))
				.andExpect(jsonPath("$.email").value("thenri@gmail.com"));
	}

	@Test
	public void testUpdatePerson() throws Exception {

		// GIVEN
		Person upPerson = new Person();
		upPerson.setFirstName("John");
		upPerson.setLastName("Boyd");
		upPerson.setAddress("1509 Culver St");
		upPerson.setCity("Culver");
		upPerson.setZip("97451");
		upPerson.setPhone("841-874-6512");
		upPerson.setEmail("john.boyd@outlook.com");

		// WHEN THEN
		mockMvc.perform(put("/person/{id}", 0).content(JsonUtils.asJsonString(upPerson))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("John")).andExpect(jsonPath("$.lastName").value("Boyd"))
				.andExpect(jsonPath("$.address").value("1509 Culver St")).andExpect(jsonPath("$.city").value("Culver"))
				.andExpect(jsonPath("$.zip").value("97451")).andExpect(jsonPath("$.phone").value("841-874-6512"))
				.andExpect(jsonPath("$.email").value("jaboyd@email.com"));
	}

	@Test
	public void testUpdatePerson_withIndexOutOfBounds() throws Exception {

		// GIVEN
		Person upPerson = new Person();
		upPerson.setFirstName("John");
		upPerson.setLastName("Boyd");
		upPerson.setAddress("1509 Culver St");
		upPerson.setCity("Culver");
		upPerson.setZip("97451");
		upPerson.setPhone("841-874-6512");
		upPerson.setEmail("john.boyd@outlook.com");

		// WHEN THEN
		mockMvc.perform(put("/person/{id}", 100).content(JsonUtils.asJsonString(upPerson))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error").value("Bad Request")).andExpect(jsonPath("$.path").value("/person/100"))
				.andExpect(jsonPath("$.exception").value("java.lang.IndexOutOfBoundsException"))
				.andExpect(jsonPath("$.message").value("Index 100 out of bounds for length 23"));
	}

}
