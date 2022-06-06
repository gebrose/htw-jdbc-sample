package de.htw_berlin.imi.db.web;

import de.htw_berlin.imi.db.services.BueroRaumEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping(path = "/ui/bueros")
public class BueroRaumMvcController {

    @Autowired
    BueroRaumEntityService bueroRaumEntityService;

    @GetMapping
    String findAll(final Model model) {
        model.addAttribute("bueros", bueroRaumEntityService.findAll());
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

}
