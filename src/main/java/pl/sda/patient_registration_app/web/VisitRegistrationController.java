package pl.sda.patient_registration_app.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.patient_registration_app.bo.*;
import pl.sda.patient_registration_app.dto.MyUserPrincipalDto;
import pl.sda.patient_registration_app.dto.RegisterDto;
import pl.sda.patient_registration_app.type.DoctorSpecializationType;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

@Controller
public class VisitRegistrationController {

    private final DoctorDayService doctorDayService;
    private final UtilsService utilsService;
    private final VisitRegistrationService visitRegistrationService;

    @Autowired
    public VisitRegistrationController(DoctorDayService doctorDayService,
                                       UtilsService utilsService, VisitRegistrationService visitRegistrationService) {
        this.doctorDayService = doctorDayService;
        this.utilsService = utilsService;
        this.visitRegistrationService = visitRegistrationService;
    }

    @GetMapping("/rejestracja")
    public ModelAndView showRegistrationPage() {


        ModelAndView mav = new ModelAndView("rejestracja");

        mav.addObject("docSpecEnum", utilsService.convertDoctorSpecializationTypesToString());
        return mav;
    }

    @GetMapping("/rejestracja/specjalista")
    public ModelAndView showDoctorsSchedule(@RequestParam(name = "specType", required = false) String specName,
                                            @RequestParam(name = "date", required = false)
                                            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        ModelAndView mav = new ModelAndView("tabelaWizyt");
        if (date == null) {
            date = LocalDate.now();
        }
        DoctorSpecializationType doctorSpecializationType = DoctorSpecializationType.findByName(specName);

        mav.addObject("doctorDayDtoList",
                doctorDayService.createDoctorDayDtosForChosenDayAndSpecialization(date, doctorSpecializationType));
        mav.addObject("hours", utilsService.getOpenHoursOfTheClinic());
        mav.addObject("dateOfVisits", date);
        mav.addObject("weekDayName", date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()));
        mav.addObject("registerDto", new RegisterDto());
        mav.addObject("specType", specName);
        return mav;
    }

    @PostMapping("/rejestracja/specjalista")
    public ModelAndView registerPatientToVisit(@ModelAttribute("registerDto") RegisterDto registerDto) {
        ModelAndView mav = new ModelAndView("podsumowanieRejestracji");

        MyUserPrincipalDto myUserPrincipalDto = (MyUserPrincipalDto) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        visitRegistrationService.registerPatientToAVisit(registerDto, myUserPrincipalDto.getId());


        return mav;
    }
}
