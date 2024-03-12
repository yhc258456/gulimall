package com.rachel.gulimall.product.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {


    @GetMapping({"/", "/index.html"})
    public String indexPage() {


        return "index";
    }

}
