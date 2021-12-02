package com.safetynet.apirest.apiservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.safetynet.apirest.apirepository.MedicalRecordRepository;
import com.safetynet.apirest.model.MedicalRecord;

class MedicalRecordServiceTests {

	@Mock
	private MedicalRecordRepository repositoryMedicalRecord;

	@InjectMocks
	private MedicalRecordService srvMedicalRecord;

	MedicalRecord medicalRecord;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		medicalRecord = MedicalRecord.builder().firstName("Thierry").lastName("Henri").birthdate("03/08/1982")
				.medications(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")).allergies(Arrays.asList("nillacilan"))
				.build();
	}

	@Test
	final void testServiceDeleById() {
		when(repositoryMedicalRecord.deleteById(0)).thenReturn(medicalRecord);
		MedicalRecord deletedMedicalRecord = srvMedicalRecord.serviceDeleteById(0);
		assertThat(deletedMedicalRecord).isEqualTo(medicalRecord);
		verify(repositoryMedicalRecord, times(1)).deleteById(0);
	}

	@Test
	final void testServiceSave() {
		when(repositoryMedicalRecord.save(medicalRecord)).thenReturn(medicalRecord);
		MedicalRecord newMedicalRecord = srvMedicalRecord.serviceSave(medicalRecord);
		assertThat(newMedicalRecord).isEqualTo(medicalRecord);
		verify(repositoryMedicalRecord, times(1)).save(medicalRecord);
	}

	@Test
	final void testServiceUpdate() {
		when(repositoryMedicalRecord.update(0, medicalRecord)).thenReturn(medicalRecord);
		MedicalRecord newMedicalRecord = srvMedicalRecord.serviceUpdate(0, medicalRecord);
		assertThat(newMedicalRecord).isEqualTo(medicalRecord);
		verify(repositoryMedicalRecord, times(1)).update(0, medicalRecord);
	}

}
