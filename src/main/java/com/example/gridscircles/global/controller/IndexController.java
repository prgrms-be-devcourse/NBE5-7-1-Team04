package com.example.gridscircles.global.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String redirectToOrders() {
        return "redirect:/orders";
    }

    @RequestMapping("/admin")
    public String redirectToAdmin() {
        return "redirect:/admin/products/list";
    }
}