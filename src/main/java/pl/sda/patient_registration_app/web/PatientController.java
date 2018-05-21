package pl.sda.patient_registration_app.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.patient_registration_app.bo.VisitsFinder;
import pl.sda.patient_registration_app.dto.MyUserPrincipalDto;

@Controller

public class PatientController {

    private final VisitsFinder visitsFinder;

    @Autowired
    public PatientController(VisitsFinder visitsFinder) {
        this.visitsFinder = visitsFinder;
    }

    @GetMapping(value = "/wizytyPacjenta")
    public ModelAndView showPatientVisits() {
        ModelAndView mav = new ModelAndView("wizytyPacjenta");
        MyUserPrincipalDto myUserPrincipalDto = (MyUserPrincipalDto) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();


        mav.addObject("wizyty", visitsFinder.findVisitsByPatientId(myUserPrincipalDto.getId()));
        return mav;

    }


}
