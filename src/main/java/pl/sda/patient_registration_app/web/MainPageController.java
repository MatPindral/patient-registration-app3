package pl.sda.patient_registration_app.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.sda.patient_registration_app.bo.TimetablesService;

@Controller
public class MainPageController {


    @GetMapping(value = "/main")
    public String mainPage() {


        return "main";
    }

    @PostMapping(value = "/main")
    public String mainPostPage() {
        return "main";
    }


}
