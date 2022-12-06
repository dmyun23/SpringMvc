package com.uno.controller;


import com.uno.exception.GeneralException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {

    @GetMapping("/")
    public String root() throws Exception{
//        throw new GeneralException("Test");
        return "index";
    }


}
