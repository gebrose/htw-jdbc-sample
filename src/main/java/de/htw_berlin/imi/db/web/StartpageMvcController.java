package de.htw_berlin.imi.db.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class StartpageMvcController {

    @GetMapping("/ui")
    String findAll() {
        return "start";
    }

    @GetMapping
    String root() {
        return "redirect:ui";
    }

}
