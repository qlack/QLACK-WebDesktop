package com.eurodyn.qlack.webdesktop.app.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MyErrorController implements ErrorController {

  @RequestMapping(path = "/error", method = RequestMethod.GET)
  public String handleError() {
    //do something like logging
    return "error.html";
  }

  @Override
  public String getErrorPath() {
    return "/error";
  }
}
