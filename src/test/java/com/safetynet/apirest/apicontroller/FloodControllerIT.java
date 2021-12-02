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
class FloodControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testListOfHousholdServedByStation() throws Exception {
		mockMvc.perform(get("/flood/stations?stations=2")).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(1)).andExpect(jsonPath("$.[0].firestationNumber").value("2"))
				.andExpect(jsonPath("$[0].households.size()").value(3))
				.andExpect(jsonPath("$.[0].households.[0].address").value("29 15th St"))
				.andExpect(jsonPath("$.[0].households.[0].habitants.size()").value("1"))
				.andExpect(jsonPath("$.[0].households.[0].habitants[0].firstName").value("Jonanathan"))
				.andExpect(jsonPath("$.[0].households.[0].habitants[0].lastName").value("Marrack"))
				.andExpect(jsonPath("$.[0].households.[0].habitants[0].age").value("32"))
				.andExpect(jsonPath("$.[0].households.[0].habitants[0].phone").value("841-874-6513"))
				.andExpect(jsonPath("$.[0].households.[0].habitants[0].medications.size()").value("0"))
				.andExpect(jsonPath("$.[0].households.[0].habitants[0].allergies.size()").value("0"))

				.andExpect(jsonPath("$.[0].households.[1].address").value("892 Downing Ct"))
				.andExpect(jsonPath("$.[0].households.[1].habitants.size()").value("3"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[0].firstName").value("Sophia"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[0].lastName").value("Zemicks"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[0].age").value("33"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[0].phone").value("841-874-7878"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[0].medications.size()").value("4"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[0].medications[0]").value("aznol:60mg"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[0].medications[1]").value("hydrapermazol:900mg"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[0].medications[2]").value("pharmacol:5000mg"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[0].medications[3]").value("terazine:500mg"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[0].allergies.size()").value("3"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[0].allergies[0]").value("peanut"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[0].allergies[1]").value("shellfish"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[0].allergies[2]").value("aznol"))

				.andExpect(jsonPath("$.[0].households.[1].habitants[1].firstName").value("Warren"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[1].lastName").value("Zemicks"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[1].age").value("36"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[1].phone").value("841-874-7512"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[1].medications.size()").value("0"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[1].allergies.size()").value("0"))

				.andExpect(jsonPath("$.[0].households.[1].habitants[2].firstName").value("Zach"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[2].lastName").value("Zemicks"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[2].age").value("4"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[2].phone").value("841-874-7512"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[2].medications.size()").value("0"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[2].allergies.size()").value("0"))

				.andExpect(jsonPath("$.[0].households.[2].address").value("951 LoneTree Rd"))
				.andExpect(jsonPath("$.[0].households.[2].habitants.size()").value("1"))
				.andExpect(jsonPath("$.[0].households.[2].habitants[0].firstName").value("Eric"))
				.andExpect(jsonPath("$.[0].households.[2].habitants[0].lastName").value("Cadigan"))
				.andExpect(jsonPath("$.[0].households.[2].habitants[0].age").value("76"))
				.andExpect(jsonPath("$.[0].households.[2].habitants[0].phone").value("841-874-7458"))
				.andExpect(jsonPath("$.[0].households.[2].habitants[0].medications.size()").value("1"))
				.andExpect(jsonPath("$.[0].households.[2].habitants[0].medications[0]").value("tradoxidine:400mg"))
				.andExpect(jsonPath("$.[0].households.[2].habitants[0].allergies.size()").value("0"));
	}

	@Test
	public void testListOfHousholdServedByStation_for2Stations() throws Exception {
		mockMvc.perform(get("/flood/stations?stations=2,1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(2)).andExpect(jsonPath("$.[0].firestationNumber").value("2"))
				.andExpect(jsonPath("$[0].households.size()").value(3))
				.andExpect(jsonPath("$.[0].households.[0].address").value("29 15th St"))
				.andExpect(jsonPath("$.[0].households.[0].habitants.size()").value("1"))
				.andExpect(jsonPath("$.[0].households.[0].habitants[0].firstName").value("Jonanathan"))
				.andExpect(jsonPath("$.[0].households.[0].habitants[0].lastName").value("Marrack"))
				.andExpect(jsonPath("$.[0].households.[0].habitants[0].age").value("32"))
				.andExpect(jsonPath("$.[0].households.[0].habitants[0].phone").value("841-874-6513"))
				.andExpect(jsonPath("$.[0].households.[0].habitants[0].medications.size()").value("0"))
				.andExpect(jsonPath("$.[0].households.[0].habitants[0].allergies.size()").value("0"))

				.andExpect(jsonPath("$.[0].households.[1].address").value("892 Downing Ct"))
				.andExpect(jsonPath("$.[0].households.[1].habitants.size()").value("3"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[0].firstName").value("Sophia"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[0].lastName").value("Zemicks"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[0].age").value("33"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[0].phone").value("841-874-7878"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[0].medications.size()").value("4"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[0].medications[0]").value("aznol:60mg"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[0].medications[1]").value("hydrapermazol:900mg"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[0].medications[2]").value("pharmacol:5000mg"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[0].medications[3]").value("terazine:500mg"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[0].allergies.size()").value("3"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[0].allergies[0]").value("peanut"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[0].allergies[1]").value("shellfish"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[0].allergies[2]").value("aznol"))

				.andExpect(jsonPath("$.[0].households.[1].habitants[1].firstName").value("Warren"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[1].lastName").value("Zemicks"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[1].age").value("36"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[1].phone").value("841-874-7512"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[1].medications.size()").value("0"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[1].allergies.size()").value("0"))

				.andExpect(jsonPath("$.[0].households.[1].habitants[2].firstName").value("Zach"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[2].lastName").value("Zemicks"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[2].age").value("4"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[2].phone").value("841-874-7512"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[2].medications.size()").value("0"))
				.andExpect(jsonPath("$.[0].households.[1].habitants[2].allergies.size()").value("0"))

				.andExpect(jsonPath("$.[0].households.[2].address").value("951 LoneTree Rd"))
				.andExpect(jsonPath("$.[0].households.[2].habitants.size()").value("1"))
				.andExpect(jsonPath("$.[0].households.[2].habitants[0].firstName").value("Eric"))
				.andExpect(jsonPath("$.[0].households.[2].habitants[0].lastName").value("Cadigan"))
				.andExpect(jsonPath("$.[0].households.[2].habitants[0].age").value("76"))
				.andExpect(jsonPath("$.[0].households.[2].habitants[0].phone").value("841-874-7458"))
				.andExpect(jsonPath("$.[0].households.[2].habitants[0].medications.size()").value("1"))
				.andExpect(jsonPath("$.[0].households.[2].habitants[0].medications[0]").value("tradoxidine:400mg"))
				.andExpect(jsonPath("$.[0].households.[2].habitants[0].allergies.size()").value("0"))

				.andExpect(jsonPath("$.[1].firestationNumber").value("1"))
				.andExpect(jsonPath("$.[1].households.size()").value(3))
				.andExpect(jsonPath("$.[1].households.[0].address").value("644 Gershwin Cir"))
				.andExpect(jsonPath("$.[1].households.[0].habitants.size()").value("1"))
				.andExpect(jsonPath("$.[1].households.[0].habitants[0].firstName").value("Peter"))
				.andExpect(jsonPath("$.[1].households.[0].habitants[0].lastName").value("Duncan"))
				.andExpect(jsonPath("$.[1].households.[0].habitants[0].age").value("21"))
				.andExpect(jsonPath("$.[1].households.[0].habitants[0].phone").value("841-874-6512"))
				.andExpect(jsonPath("$.[1].households.[0].habitants[0].medications.size()").value("0"))
				.andExpect(jsonPath("$.[1].households.[0].habitants[0].allergies.size()").value("1"))
				.andExpect(jsonPath("$.[1].households.[0].habitants[0].allergies[0]").value("shellfish"))

				.andExpect(jsonPath("$.[1].households.[1].address").value("908 73rd St"))
				.andExpect(jsonPath("$.[1].households.[1].habitants.size()").value("2"))
				.andExpect(jsonPath("$.[1].households.[1].habitants[0].firstName").value("Reginold"))
				.andExpect(jsonPath("$.[1].households.[1].habitants[0].lastName").value("Walker"))
				.andExpect(jsonPath("$.[1].households.[1].habitants[0].age").value("42"))
				.andExpect(jsonPath("$.[1].households.[1].habitants[0].phone").value("841-874-8547"))
				.andExpect(jsonPath("$.[1].households.[1].habitants[0].medications.size()").value("1"))
				.andExpect(jsonPath("$.[1].households.[1].habitants[0].medications.[0]").value("thradox:700mg"))
				.andExpect(jsonPath("$.[1].households.[1].habitants[0].allergies.size()").value("1"))
				.andExpect(jsonPath("$.[1].households.[1].habitants[0].allergies[0]").value("illisoxian"))

				.andExpect(jsonPath("$.[1].households.[1].habitants[1].firstName").value("Jamie"))
				.andExpect(jsonPath("$.[1].households.[1].habitants[1].lastName").value("Peters"))
				.andExpect(jsonPath("$.[1].households.[1].habitants[1].age").value("39"))
				.andExpect(jsonPath("$.[1].households.[1].habitants[1].phone").value("841-874-7462"))
				.andExpect(jsonPath("$.[1].households.[1].habitants[1].medications.size()").value("0"))
				.andExpect(jsonPath("$.[1].households.[1].habitants[1].allergies.size()").value("0"))

				.andExpect(jsonPath("$.[1].households.[2].address").value("947 E. Rose Dr"))
				.andExpect(jsonPath("$.[1].households.[2].habitants.size()").value("3"))
				.andExpect(jsonPath("$.[1].households.[2].habitants[0].firstName").value("Brian"))
				.andExpect(jsonPath("$.[1].households.[2].habitants[0].lastName").value("Stelzer"))
				.andExpect(jsonPath("$.[1].households.[2].habitants[0].age").value("45"))
				.andExpect(jsonPath("$.[1].households.[2].habitants[0].phone").value("841-874-7784"))
				.andExpect(jsonPath("$.[1].households.[2].habitants[0].medications.size()").value("2"))
				.andExpect(jsonPath("$.[1].households.[2].habitants[0].medications.[0]").value("ibupurin:200mg"))
				.andExpect(jsonPath("$.[1].households.[2].habitants[0].medications.[1]").value("hydrapermazol:400mg"))
				.andExpect(jsonPath("$.[1].households.[2].habitants[0].allergies.size()").value("1"))
				.andExpect(jsonPath("$.[1].households.[2].habitants[0].allergies[0]").value("nillacilan"))

				.andExpect(jsonPath("$.[1].households.[2].habitants[1].firstName").value("Shawna"))
				.andExpect(jsonPath("$.[1].households.[2].habitants[1].lastName").value("Stelzer"))
				.andExpect(jsonPath("$.[1].households.[2].habitants[1].age").value("41"))
				.andExpect(jsonPath("$.[1].households.[2].habitants[1].phone").value("841-874-7784"))
				.andExpect(jsonPath("$.[1].households.[2].habitants[1].medications.size()").value("0"))
				.andExpect(jsonPath("$.[1].households.[2].habitants[1].allergies.size()").value("0"))

				.andExpect(jsonPath("$.[1].households.[2].habitants[2].firstName").value("Kendrik"))
				.andExpect(jsonPath("$.[1].households.[2].habitants[2].lastName").value("Stelzer"))
				.andExpect(jsonPath("$.[1].households.[2].habitants[2].age").value("7"))
				.andExpect(jsonPath("$.[1].households.[2].habitants[2].phone").value("841-874-7784"))
				.andExpect(jsonPath("$.[1].households.[2].habitants[2].medications.size()").value("2"))
				.andExpect(jsonPath("$.[1].households.[2].habitants[2].medications.[0]").value("noxidian:100mg"))
				.andExpect(jsonPath("$.[1].households.[2].habitants[2].medications.[1]").value("pharmacol:2500mg"))
				.andExpect(jsonPath("$.[1].households.[2].habitants[2].allergies.size()").value("0"));
	}

	@Test
	public void testListOfHousholdServedByStation_withNotFoundStation() throws Exception {
		mockMvc.perform(get("/flood/stations?stations=100")).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(1)).andExpect(jsonPath("$.[0].firestationNumber").value("100"))
				.andExpect(jsonPath("$[0].households.size()").value(0))
				.andExpect(jsonPath("$.[0].households").isEmpty());
	}

	
}
