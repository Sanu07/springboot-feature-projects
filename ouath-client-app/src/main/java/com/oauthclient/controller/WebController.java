package com.oauthclient.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @GetMapping("/securedPage")
    public String securedPage(Model model, Principal principal) {
        return "securedPage";
    }

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        return "index";
    }
}