package com.example.chocolate.controller;

import com.example.chocolate.model.Chocolate;
import com.example.chocolate.model.Place;
import com.example.chocolate.repository.ChocolateRepository;
import com.example.chocolate.repository.PlaceRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ChocolateController {

    private final ChocolateRepository chocolates;
    private final PlaceRepository places;

    public ChocolateController(ChocolateRepository chocolates, PlaceRepository places) {
        this.chocolates = chocolates;
        this.places = places;
    }

    // Home page: all chocolates, or only one type (/?type=Dark)
    @GetMapping("/")
    public String home(@RequestParam(required = false) String type, Model model) {
        List<Chocolate> list = (type == null || type.isBlank())
                ? chocolates.findAll()
                : chocolates.findByType(type);
        model.addAttribute("chocolates", list);
        model.addAttribute("places", places.findAll());
        model.addAttribute("selectedType", type);
        return "index";
    }

    // One place and its chocolates
    @GetMapping("/places/{id}")
    public String place(@PathVariable Long id, Model model) {
        Place place = places.findById(id).orElseThrow();
        model.addAttribute("place", place);
        model.addAttribute("places", places.findAll());
        return "place";
    }

    // Show the "add chocolate" form
    @GetMapping("/chocolates/new")
    public String newForm(Model model) {
        model.addAttribute("chocolate", new Chocolate());
        model.addAttribute("places", places.findAll());
        return "add";
    }

    // Save the new chocolate
    @PostMapping("/chocolates")
    public String save(@ModelAttribute Chocolate chocolate,
                       @RequestParam Long placeId,
                       RedirectAttributes redirectAttributes) {
        chocolate.setPlace(places.findById(placeId).orElseThrow());
        if (chocolate.getImageUrl() == null || chocolate.getImageUrl().isBlank()) {
            String t = chocolate.getType() != null ? chocolate.getType().toLowerCase() : "dark";
            chocolate.setImageUrl("/images/default_" + t + ".jpg");
        }
        chocolates.save(chocolate);
        redirectAttributes.addFlashAttribute("success", "Added '" + chocolate.getName() + "' successfully!");
        return "redirect:/";
    }

    // Delete a chocolate
    @PostMapping("/chocolates/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        chocolates.findById(id).ifPresent(c -> {
            chocolates.deleteById(id);
            redirectAttributes.addFlashAttribute("info", "Deleted '" + c.getName() + "'.");
        });
        return "redirect:/";
    }
}
