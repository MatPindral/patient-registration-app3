package pl.sda.patient_registration_app.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ErrorController {

    @GetMapping("/error")
    public String showErrorPage() {


        return "error";
    }

    @PostMapping("/error")
    public String showPostErrorPage() {


        return "error";
    }

}
