package pl.sda.patient_registration_app.web;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.patient_registration_app.exceptions.EmailExistsException;
import pl.sda.patient_registration_app.bo.NewPatientRegistrationService;
import pl.sda.patient_registration_app.dto.NewUserRegistrationDto;
import pl.sda.patient_registration_app.entity.Patient;

import javax.validation.Valid;

@Controller
public class NewUserRegistrationController {

    private final NewPatientRegistrationService newPatientRegistrationService;

    public NewUserRegistrationController(NewPatientRegistrationService newPatientRegistrationService) {
        this.newPatientRegistrationService = newPatientRegistrationService;
    }

    @GetMapping(value = "/nowyUzytkownik")
    public ModelAndView showNewUserPage() {

        ModelAndView mav = new ModelAndView("nowyUzytkownik");
        mav.addObject("newUser", new NewUserRegistrationDto());


        return mav;
    }

    @PostMapping(value = "/nowyUzytkownik/zarejestruj")
    public ModelAndView registerUserAccount(
            @ModelAttribute("newUser") @Valid NewUserRegistrationDto newUserRegistrationDto,
            BindingResult result,
            Errors errors) {

        Patient registered = null;

        if (!result.hasErrors()) {
            registered = createUserAccount(newUserRegistrationDto, result);
        }
        if (registered == null) {
            result.rejectValue("email", "message.regError");
        }
        if (result.hasErrors()) {
            return new ModelAndView("bladRejestracji");
        } else {
            return new ModelAndView("rejestracjaWynik", "newUser", newUserRegistrationDto);
        }
    }

    private Patient createUserAccount(NewUserRegistrationDto newUserRegistrationDto, BindingResult result) {
        Patient registered = null;

        try {
            registered = newPatientRegistrationService.saveNewPatientToDB(newUserRegistrationDto);
        } catch (EmailExistsException e) {
            return null;
        }
        return registered;


    }
}
