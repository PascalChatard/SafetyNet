package com.safetynet.apirest.apiservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.safetynet.apirest.apirepository.PersonRepository;
import com.safetynet.apirest.model.Person;

class PersonServiceTests {

	@Mock
	private PersonRepository personRepository;

	@InjectMocks
	private PersonService personSrv;

	Person person;

	@BeforeEach
	public void init() {

		MockitoAnnotations.openMocks(this);
		person = Person.builder().firstName("John").lastName("Boyd").address("1509 Culver St").city("Culver")
				.zip("97451").phone("841-874-6512").email("jaboyd@email.com").build();
	}

	@Test
	final void testServiceDeleById() {

		// GIVEN
		when(personRepository.deleteById(0)).thenReturn(person);

		// WHEN
		Person deletedPerson = personSrv.serviceDeleteById(0);

		// THEN
		assertThat(deletedPerson).isEqualTo(person);
		verify(personRepository, times(1)).deleteById(0);

	}

	@Test
	final void testServiceSave() {

		// GIVEN
		when(personRepository.save(person)).thenReturn(person);

		// WHEN
		Person newPerson = personSrv.serviceSave(person);

		// THEN
		assertThat(newPerson).isEqualTo(person);
		verify(personRepository, times(1)).save(person);
	}

	@Test
	final void testServiceUpdate() {

		// GIVEN
		when(personRepository.update(0, person)).thenReturn(person);

		// WHEN
		Person newPerson = personSrv.serviceUpdate(0, person);

		// THEN
		assertThat(newPerson).isEqualTo(person);
		verify(personRepository, times(1)).update(0, person);
	}

}
