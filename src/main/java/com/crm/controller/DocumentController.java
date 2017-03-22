package com.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2017/3/22.
 */
@Controller
@RequestMapping("/doc")
public class DocumentController {

    @GetMapping
    public String list(){
        return "document/list";
    }
}
