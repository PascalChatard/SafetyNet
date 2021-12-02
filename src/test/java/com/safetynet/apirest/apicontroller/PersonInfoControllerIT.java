package com.safetynet.apirest.apicontroller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PersonInfoControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetNameAdrAgeEmailAndMedicalRecord() throws Exception {
		mockMvc.perform(get("/personInfo?firstName=Tony&lastName=Cooper")).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(1)).andExpect(jsonPath("$.[0].lastName").value("Cooper"))
				.andExpect(jsonPath("$.[0].address").value("112 Steppes Pl"))
				.andExpect(jsonPath("$.[0].age").value("27")).andExpect(jsonPath("$.[0].medications.size()").value("2"))
				.andExpect(jsonPath("$.[0].medications.[0]").value("hydrapermazol:300mg"))
				.andExpect(jsonPath("$.[0].medications.[1]").value("dodoxadin:30mg"))
				.andExpect(jsonPath("$.[0].allergies.size()").value("1"))
				.andExpect(jsonPath("$.[0].allergies").value("shellfish"));
	}

	@Test
	public void testGetNameAdrAgeEmailAndMedicalRecord_withLastNameNoMatch() throws Exception {
		mockMvc.perform(get("/personInfo?firstName=Tony&lastName=Blair")).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(0)).andExpect(jsonPath("$").isEmpty());
	}

	@Test
	public void testGetNameAdrAgeEmailAndMedicalRecord_withFirstNameNoMatch() throws Exception {
		mockMvc.perform(get("/personInfo?firstName=Olivia&lastName=Cooper")).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(0)).andExpect(jsonPath("$").isEmpty());
	}

}
