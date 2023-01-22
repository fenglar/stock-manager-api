package pl.marcin.stockmanagerapi.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

    @GetMapping(path = "")
    public String redirectMainPageToSwagger() {
        return "redirect:/swagger-ui.html";
    }
}
