package com.safetynet.apirest.apicontroller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.safetynet.apirest.model.Firestation;
import com.safetynet.apirest.utils.JsonUtils;

@SpringBootTest
@AutoConfigureMockMvc
public class FirestationControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@DirtiesContext
	@Test
	final void testDeleteFirestation() throws Exception {

		// GIVEN WHEN THEN
		mockMvc.perform(delete("/firestation/{id}", 1)).andExpect(status().isOk())
				.andExpect(jsonPath("$.address").value("29 15th St")).andExpect(jsonPath("$.station").value("2"));

	}

	@DirtiesContext
	@Test
	final void testDeleteFirestation__withIndexOutOfBounds() throws Exception {

		mockMvc.perform(delete("/firestation/{id}", 100)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error").value("Bad Request"))
				.andExpect(jsonPath("$.path").value("/firestation/100"))
				.andExpect(jsonPath("$.exception").value("java.lang.IndexOutOfBoundsException"))
				.andExpect(jsonPath("$.message").value("Index 100 out of bounds for length 14"));

	}

	@Test
	final void testAddFirestation() throws Exception {

		Firestation firestation = Firestation.builder().address("111 Lot. Capemundo").station("4").build();

		mockMvc.perform(post("/firestation").content(JsonUtils.asJsonString(firestation))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.address").value("111 Lot. Capemundo"))
				.andExpect(jsonPath("$.station").value("4"));
	}

	@Test
	public void testUpdateFirestation() throws Exception {
		Firestation upFirestation = Firestation.builder().address("1509 Culver St").station("4").build();

		mockMvc.perform(put("/firestation/{id}", 0).content(JsonUtils.asJsonString(upFirestation))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.address").value("1509 Culver St"))
				.andExpect(jsonPath("$.station").value("3"));
	}

	@Test
	public void testUpdateFirestation__withIndexOutOfBounds() throws Exception {
		Firestation upFirestation = Firestation.builder().address("1509 Culver St").station("4").build();

		mockMvc.perform(put("/firestation/{id}", 100).content(JsonUtils.asJsonString(upFirestation))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error").value("Bad Request"))
				.andExpect(jsonPath("$.path").value("/firestation/100"))
				.andExpect(jsonPath("$.exception").value("java.lang.IndexOutOfBoundsException"))
				.andExpect(jsonPath("$.message").value("Index 100 out of bounds for length 13"));
	}

	@Test
	public void testdisplayListPersonsCoverByFirestation() throws Exception {
		mockMvc.perform(get("/firestation?stationNumber=2")).andExpect(status().isOk())
				.andExpect(jsonPath("$.childrenNumber").value("1")).andExpect(jsonPath("$.adultsNumber").value("4"))
				.andExpect(jsonPath("$.persons.[0].firstName").value("Jonanathan"))
				.andExpect(jsonPath("$.persons.[0].lastName").value("Marrack"))
				.andExpect(jsonPath("$.persons.[0].address").value("29 15th St"))
				.andExpect(jsonPath("$.persons.[0].phone").value("841-874-6513"))
				.andExpect(jsonPath("$.persons.[1].firstName").value("Sophia"))
				.andExpect(jsonPath("$.persons.[1].lastName").value("Zemicks"))
				.andExpect(jsonPath("$.persons.[1].address").value("892 Downing Ct"))
				.andExpect(jsonPath("$.persons.[1].phone").value("841-874-7878"))
				.andExpect(jsonPath("$.persons.[2].firstName").value("Warren"))
				.andExpect(jsonPath("$.persons.[2].lastName").value("Zemicks"))
				.andExpect(jsonPath("$.persons.[2].address").value("892 Downing Ct"))
				.andExpect(jsonPath("$.persons.[2].phone").value("841-874-7512"))
				.andExpect(jsonPath("$.persons.[3].firstName").value("Zach"))
				.andExpect(jsonPath("$.persons.[3].lastName").value("Zemicks"))
				.andExpect(jsonPath("$.persons.[3].address").value("892 Downing Ct"))
				.andExpect(jsonPath("$.persons.[3].phone").value("841-874-7512"))
				.andExpect(jsonPath("$.persons.[4].firstName").value("Eric"))
				.andExpect(jsonPath("$.persons.[4].lastName").value("Cadigan"))
				.andExpect(jsonPath("$.persons.[4].address").value("951 LoneTree Rd"))
				.andExpect(jsonPath("$.persons.[4].phone").value("841-874-7458"));
	}

	@Test
	public void testdisplayListPersonsCoverByFirestation_withNotFoundStation() throws Exception {
		mockMvc.perform(get("/firestation?stationNumber=100")).andExpect(status().isOk())
				.andExpect(jsonPath("$.childrenNumber").value("0")).andExpect(jsonPath("$.adultsNumber").value("0"))
				.andExpect(jsonPath("$.persons").isEmpty());
	}


}
