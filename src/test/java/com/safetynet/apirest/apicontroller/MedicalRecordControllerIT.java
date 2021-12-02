package com.safetynet.apirest.apicontroller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import com.safetynet.apirest.model.MedicalRecord;
import com.safetynet.apirest.utils.JsonUtils;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@DirtiesContext
	@Test
	final void testDeleteMedicalRecord() throws Exception {

		mockMvc.perform(delete("/medicalRecord/{id}", 1)).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("Jacob")).andExpect(jsonPath("$.lastName").value("Boyd"))
				.andExpect(jsonPath("$.birthdate").value("03/06/1989"))
				.andExpect(jsonPath("$.medications[0]").value("pharmacol:5000mg"))
				.andExpect(jsonPath("$.medications[1]").value("terazine:10mg"))
				.andExpect(jsonPath("$.medications[2]").value("noznazol:250mg"))
				.andExpect(jsonPath("$.allergies").isEmpty());

	}

	@DirtiesContext
	@Test
	final void testDeleteMedicalRecord_withIndexOutOfBounds() throws Exception {

		mockMvc.perform(delete("/medicalRecord/{id}", 100)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error").value("Bad Request"))
				.andExpect(jsonPath("$.path").value("/medicalRecord/100"))
				.andExpect(jsonPath("$.exception").value("java.lang.IndexOutOfBoundsException"))
				.andExpect(jsonPath("$.message").value("Index 100 out of bounds for length 24"));
	}

	@Test
	final void testAddMedicalRecord() throws Exception {

		MedicalRecord newMedicalRecord = MedicalRecord.builder().firstName("Thierry").lastName("Henri")
				.birthdate("03/08/1982").allergies(null).medications(null).build();

		mockMvc.perform(post("/medicalRecord").content(JsonUtils.asJsonString(newMedicalRecord))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.firstName").value("Thierry"))
				.andExpect(jsonPath("$.lastName").value("Henri")).andExpect(jsonPath("$.birthdate").value("03/08/1982"))
				.andExpect(jsonPath("$.medications").isEmpty()).andExpect(jsonPath("$.allergies").isEmpty());

	}

	@Test
	public void testUpdateMedicalRecord() throws Exception {
		MedicalRecord upMedicalRecord = new MedicalRecord();
		upMedicalRecord.setFirstName("John");
		upMedicalRecord.setLastName("Boyd");
		upMedicalRecord.setBirthdate("03/06/1984");
		upMedicalRecord.setMedications(Arrays.asList("aznol:350mg", "hydrapermazol:1300mg"));
		upMedicalRecord.setAllergies(Arrays.asList("nillacilan", "pollens"));

		mockMvc.perform(put("/medicalRecord/{id}", 0).content(JsonUtils.asJsonString(upMedicalRecord))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("John")).andExpect(jsonPath("$.lastName").value("Boyd"))
				.andExpect(jsonPath("$.birthdate").value("03/06/1984"))
				.andExpect(jsonPath("$.medications[0]").value("aznol:350mg"))
				.andExpect(jsonPath("$.medications[1]").value("hydrapermazol:100mg"))
				.andExpect(jsonPath("$.allergies[0]").value("nillacilan"));
	}

	@Test
	public void testUpdateMedicalRecord_withIndexOutOfBounds() throws Exception {
		MedicalRecord upMedicalRecord = new MedicalRecord();
		upMedicalRecord.setFirstName("John");
		upMedicalRecord.setLastName("Boyd");
		upMedicalRecord.setBirthdate("03/06/1984");
		upMedicalRecord.setMedications(Arrays.asList("aznol:350mg", "hydrapermazol:1300mg"));
		upMedicalRecord.setAllergies(Arrays.asList("nillacilan", "pollens"));

		mockMvc.perform(put("/medicalRecord/{id}", 100).content(JsonUtils.asJsonString(upMedicalRecord))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.error").value("Bad Request"))
				.andExpect(jsonPath("$.path").value("/medicalRecord/100"))
				.andExpect(jsonPath("$.exception").value("java.lang.IndexOutOfBoundsException"))
				.andExpect(jsonPath("$.message").value("Index 100 out of bounds for length 23"));
	}
}
