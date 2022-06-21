package de.htw_berlin.imi.db.web;

import de.htw_berlin.imi.db.services.BueroRaumEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller()
@RequestMapping(path = "/ui/bueros")
public class BueroRaumMvcController {

    @Autowired
    BueroRaumEntityService bueroRaumEntityService;

    @GetMapping
    String findAll(final Model model) {
        model.addAttribute("bueros", bueroRaumEntityService.findAll());
        // empty template object that accepts fiel values from
        // the HTML form when new office room objetcs are created
        model.addAttribute("bueroTemplate", new BueroDto());
        return "bueros";
    }

    @GetMapping("/{id}")
    String find(final Model model,
                @PathVariable("id") final long id) {
        model.addAttribute("buero",
                bueroRaumEntityService
                        .findById(id)
                        .orElseThrow(IllegalArgumentException::new));
        return "buero-detail";
    }

    @PostMapping("")
    String createBuero(@ModelAttribute("bueroTemplate") final BueroDto bueroTemplate) {
        bueroRaumEntityService.createFrom(bueroTemplate);
        // causes a page reload
        return "redirect:/ui/bueros";
    }

    @DeleteMapping("/{id}")
    String deleteBuero(@PathVariable("id") final long id) {
        bueroRaumEntityService
                .findById(id)
                .ifPresent(b -> bueroRaumEntityService.delete(b));
        // causes a page reload
        return "redirect:/ui/bueros";
    }

}
