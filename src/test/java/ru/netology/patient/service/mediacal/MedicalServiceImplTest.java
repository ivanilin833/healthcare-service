package ru.netology.patient.service.mediacal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyString;

public class MedicalServiceImplTest {

    @Test
    public void checkBloodPressureMessageTest() {
        //arrange
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);
        Mockito.when(patientInfoRepository.getById(anyString()))
                .thenReturn(new PatientInfo("Иван", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        //act
        medicalService.checkBloodPressure("", new BloodPressure(200, 110));
        Mockito.verify(sendAlertService).send(argumentCaptor.capture());

        //assert
        Assertions.assertEquals(argumentCaptor.getValue(), "Warning, patient with id: null, need help");
    }

    @Test
    public void checkBloodPressureNonMessageTest() {
        //act
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);
        Mockito.when(patientInfoRepository.getById(anyString()))
                .thenReturn(new PatientInfo("Иван", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        medicalService.checkBloodPressure("", new BloodPressure(120, 80));

        //assert
        Mockito.verify(sendAlertService, Mockito.times(0)).send(anyString());
    }

    @Test
    public void checkTemperatureMessageTest() {
        //arrange
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);
        Mockito.when(patientInfoRepository.getById(anyString()))
                .thenReturn(new PatientInfo("Иван", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));
        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        //act
        medicalService.checkTemperature("", new BigDecimal("0"));
        Mockito.verify(sendAlertService).send(argumentCaptor.capture());

        //assert
        Assertions.assertEquals(argumentCaptor.getValue(), "Warning, patient with id: null, need help");
    }

    @Test
    public void checkTemperatureNonMessageTest() {
        //act
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);
        Mockito.when(patientInfoRepository.getById(anyString()))
                .thenReturn(new PatientInfo("Иван", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        medicalService.checkTemperature("", new BigDecimal("37.8"));

        //assert
        Mockito.verify(sendAlertService, Mockito.times(0)).send(anyString());
    }
}
