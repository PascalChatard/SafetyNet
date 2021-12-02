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
class FireControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testListHabitantsOfAddress() throws Exception {
		mockMvc.perform(get("/fire?address=892 Downing Ct")).andExpect(status().isOk())
				.andExpect(jsonPath("$.stationNumber").value("2")).andExpect(jsonPath("$.habitants.size()").value(3))
				.andExpect(jsonPath("$.habitants[0].firstName").value("Sophia"))
				.andExpect(jsonPath("$.habitants[0].lastName").value("Zemicks"))
				.andExpect(jsonPath("$.habitants[0].age").value("33"))
				.andExpect(jsonPath("$.habitants[0].phone").value("841-874-7878"))
				.andExpect(jsonPath("$.habitants[0].medications.size()").value("4"))
				.andExpect(jsonPath("$.habitants[0].medications.[0]").value("aznol:60mg"))
				.andExpect(jsonPath("$.habitants[0].medications.[1]").value("hydrapermazol:900mg"))
				.andExpect(jsonPath("$.habitants[0].medications.[2]").value("pharmacol:5000mg"))
				.andExpect(jsonPath("$.habitants[0].medications.[3]").value("terazine:500mg"))
				.andExpect(jsonPath("$.habitants[0].allergies.size()").value("3"))
				.andExpect(jsonPath("$.habitants[0].allergies.[0]").value("peanut"))
				.andExpect(jsonPath("$.habitants[0].allergies.[1]").value("shellfish"))
				.andExpect(jsonPath("$.habitants[0].allergies.[2]").value("aznol"))

				.andExpect(jsonPath("$.habitants[1].firstName").value("Warren"))
				.andExpect(jsonPath("$.habitants[1].lastName").value("Zemicks"))
				.andExpect(jsonPath("$.habitants[1].age").value("36"))
				.andExpect(jsonPath("$.habitants[1].phone").value("841-874-7512"))
				.andExpect(jsonPath("$.habitants[1].medications.size()").value("0"))
				.andExpect(jsonPath("$.habitants[1].allergies.size()").value("0"))

				.andExpect(jsonPath("$.habitants[2].firstName").value("Zach"))
				.andExpect(jsonPath("$.habitants[2].lastName").value("Zemicks"))
				.andExpect(jsonPath("$.habitants[2].age").value("4"))
				.andExpect(jsonPath("$.habitants[2].phone").value("841-874-7512"))
				.andExpect(jsonPath("$.habitants[2].medications.size()").value("0"))
				.andExpect(jsonPath("$.habitants[2].allergies.size()").value("0"));
	}

	@Test
	public void testListHabitantsOfAddress_withNotFoundAddress() throws Exception {
		mockMvc.perform(get("/fire?address=15 rue du champs-de-mars")).andExpect(status().isOk())
				.andExpect(jsonPath("$.stationNumber").isEmpty())
				.andExpect(jsonPath("$.habitants.size()").value(0)).andExpect(jsonPath("$.habitants").isEmpty());

	}

}
