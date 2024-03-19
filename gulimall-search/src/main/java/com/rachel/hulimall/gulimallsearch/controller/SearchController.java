package com.rachel.hulimall.gulimallsearch.controller;

import com.rachel.hulimall.gulimallsearch.service.MallSearchService;
import com.rachel.hulimall.gulimallsearch.vo.SearchParam;
import com.rachel.hulimall.gulimallsearch.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SearchController {


    @Autowired
    private MallSearchService mallSearchService;

    @GetMapping("/list.html")
    public String list(SearchParam searchParam, Model model, HttpServletRequest request) {
        searchParam.set_queryString(request.getQueryString());
        SearchResult result = mallSearchService.search(searchParam);
        model.addAttribute("result", result);
        return "list";
    }

    @GetMapping("/test")
    @ResponseBody
    public String list() {

        return "Hello World";
    }

}
