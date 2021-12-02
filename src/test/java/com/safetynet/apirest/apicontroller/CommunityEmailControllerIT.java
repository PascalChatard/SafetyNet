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
class CommunityEmailControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testEmailListOfCityHabitants() throws Exception {
		mockMvc.perform(get("/communityEmail?city=Culver")).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(23))
				.andExpect(jsonPath("$.[0]").value("jaboyd@email.com"))
				.andExpect(jsonPath("$.[1]").value("drk@email.com"))
				.andExpect(jsonPath("$.[2]").value("tenz@email.com"))
				.andExpect(jsonPath("$.[3]").value("jaboyd@email.com"))
				.andExpect(jsonPath("$.[4]").value("jaboyd@email.com"))
				.andExpect(jsonPath("$.[5]").value("drk@email.com"))

				.andExpect(jsonPath("$.[17]").value("aly@imail.com"))
				.andExpect(jsonPath("$.[18]").value("bstel@email.com"))
				.andExpect(jsonPath("$.[19]").value("ssanw@email.com"))
				.andExpect(jsonPath("$.[20]").value("bstel@email.com"))
				.andExpect(jsonPath("$.[21]").value("clivfd@ymail.com"))
				.andExpect(jsonPath("$.[22]").value("gramps@email.com"));
	}

	@Test
	public void testEmailListOfCityHabitants_withNotFoundCity() throws Exception {
		mockMvc.perform(get("/communityEmail?city=Lille")).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(0)).andExpect(jsonPath("$").isEmpty());
	}

}
