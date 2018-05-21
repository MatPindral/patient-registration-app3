package pl.sda.patient_registration_app.bo;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import pl.sda.patient_registration_app.type.DoctorSpecializationType;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static pl.sda.patient_registration_app.bo.UtilsService.CLOSING_HOUR;
import static pl.sda.patient_registration_app.bo.UtilsService.OPENING_HOUR;

public class UtilsServiceTest {

    private UtilsService utilsService = new UtilsService();

    @Test
    public void getOpenHoursOfTheClinicShouldReturnAListOfLocalTimesCorrespondingToOpeninHourAndClosingHour() {

        LocalTime openingHour = LocalTime.of(OPENING_HOUR, 0);
        LocalTime closingHour = LocalTime.of(CLOSING_HOUR, 0);

        List<LocalTime> result = utilsService.getOpenHoursOfTheClinic();

        Assert.assertTrue(result.get(0).equals(openingHour)
                && result.get(result.size() - 1).equals(closingHour));

    }

    @Test
    public void convertDoctorSpecializationTypesToStringShouldReturnSortedListOfSpecializationNamesInString() {


        List<String> sortedSpecializationNames = new ArrayList<>();
        sortedSpecializationNames.add("dentysta");
        sortedSpecializationNames.add("dermatolog");
        sortedSpecializationNames.add("hematolog");
        sortedSpecializationNames.add("kardiolog");

        List<String> resultList = utilsService.convertDoctorSpecializationTypesToString();

        Assert.assertEquals(resultList, sortedSpecializationNames);

    }





}