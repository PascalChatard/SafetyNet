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
class ChildAlertControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testListOfChildrenLivingAtAddress() throws Exception {
		mockMvc.perform(get("/childAlert?address=1509 Culver St")).andExpect(status().isOk())
				.andExpect(jsonPath("$.children.size()").value(2))
				.andExpect(jsonPath("$.children.[0].firstName").value("Tenley"))
				.andExpect(jsonPath("$.children.[0].lastName").value("Boyd"))
				.andExpect(jsonPath("$.children.[0].age").value("9"))
				.andExpect(jsonPath("$.children.[1].firstName").value("Roger"))
				.andExpect(jsonPath("$.children.[1].lastName").value("Boyd"))
				.andExpect(jsonPath("$.children.[1].age").value("4"))

				.andExpect(jsonPath("$.adults.size()").value(3))
				.andExpect(jsonPath("$.adults.[0].firstName").value("John"))
				.andExpect(jsonPath("$.adults.[0].lastName").value("Boyd"))
				.andExpect(jsonPath("$.adults.[1].firstName").value("Jacob"))
				.andExpect(jsonPath("$.adults.[1].lastName").value("Boyd"))
				.andExpect(jsonPath("$.adults.[2].firstName").value("Felicia"))
				.andExpect(jsonPath("$.adults.[2].lastName").value("Boyd"))
				.andExpect(jsonPath("$.children.size()").value(2));
	}

	@Test
	public void testListOfChildrenLivingAtAddress_withNotFoundAddress() throws Exception {
		mockMvc.perform(get("/childAlert?address=15 rue du champs-de-mars")).andExpect(status().isOk())
				.andExpect(jsonPath("$.children.size()").value(0))
				.andExpect(jsonPath("$.children").isEmpty());

	}

}
