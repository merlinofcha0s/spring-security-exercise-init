package fr.plb.springsecuritydemo.controller;

import fr.plb.springsecuritydemo.service.VinylService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VinylController {

    private final VinylService vinylService;

    public VinylController(VinylService vinylService) {
        this.vinylService = vinylService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/all";
    }

    @GetMapping("/all")
    public ModelAndView all() {
        ModelAndView model = new ModelAndView();
        model.addObject("vinyls", vinylService.findAll());
        return model;
    }
}
