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
class PhoneAlertControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testListPhoneOfPersonsCoverByFirestation() throws Exception {

		mockMvc.perform(get("/phoneAlert?firestation=2")).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(5)).andExpect(jsonPath("$.[0]").value("841-874-6513"))
				.andExpect(jsonPath("$.[1]").value("841-874-7878")).andExpect(jsonPath("$.[2]").value("841-874-7512"))
				.andExpect(jsonPath("$.[3]").value("841-874-7512")).andExpect(jsonPath("$.[4]").value("841-874-7458"));
	}

	@Test
	public void testListPhoneOfPersonsCoverByFirestation_withNoMatchingFirestation() throws Exception {

		mockMvc.perform(get("/phoneAlert?firestation=100")).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(0)).andExpect(jsonPath("$").isEmpty());
	}

}
