package com.eurodyn.qlack.webdesktop.app.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyErrorController implements ErrorController {

  @GetMapping(path = "/error")
  public String handleError() {
    //do something like logging
    return "error.html";
  }

  @Override
  public String getErrorPath() {
    return "/error";
  }
}
