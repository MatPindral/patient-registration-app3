package pl.sda.patient_registration_app.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.patient_registration_app.bo.*;
import pl.sda.patient_registration_app.dto.NewDoctorRegistrationDto;
import pl.sda.patient_registration_app.type.DoctorSpecializationType;

import javax.validation.Valid;

@Controller
public class DoctorsController {
    private final DoctorsService doctorsService;
    private final DoctorsFinder doctorsFinder;
    private final UtilsService utilsService;
    private TimetablesService timetablesService;

    @Autowired
    public DoctorsController(DoctorsService doctorsService, DoctorsFinder doctorsFinder, UtilsService utilsService, TimetablesService timetablesService) {
        this.doctorsService = doctorsService;
        this.doctorsFinder = doctorsFinder;
        this.utilsService = utilsService;
        this.timetablesService = timetablesService;
    }

    @GetMapping("/doktorzy")
    public ModelAndView showDoctorsPage() {

        ModelAndView mav = new ModelAndView("doktorzy");

        mav.addObject("doctors", doctorsFinder.showAllDoctors());

//                timetablesService.addPredefinedTimetablesToDB();


        return mav;
    }

    @GetMapping("admin/dodajLekarza")
    public String addDoctorPage(Model model) {
        model.addAttribute("newDoctor", new NewDoctorRegistrationDto());
        model.addAttribute("docSpecEnum", utilsService.convertDoctorSpecializationTypesToString());
        return "dodawanieLekarza";
    }

    @PostMapping("admin/dodajLekarza")
    public ModelAndView addDoctorToDB(@ModelAttribute("newDoctor") @Valid NewDoctorRegistrationDto newDoctorRegistrationDto,
                                      @RequestParam("specType") String specType) {
        DoctorSpecializationType doctorSpecializationType = DoctorSpecializationType.findByName(specType);

        newDoctorRegistrationDto.setSpecialization(doctorSpecializationType);

        doctorsService.saveNewDoctorToDB(newDoctorRegistrationDto);

        ModelAndView mav = new ModelAndView("dodawanieLekarzaWynik");

        mav.addObject("addedDoctor", doctorsFinder.findByLogin(newDoctorRegistrationDto.getLogin()));
        return mav;
    }


}
