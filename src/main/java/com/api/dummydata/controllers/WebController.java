package com.api.dummydata.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class WebController {

  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("name", "Marcelo");
    return "index";
  }
}