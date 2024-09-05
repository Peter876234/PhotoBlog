package hkmu.comps380f.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "redirect:/PhotoBlog/list";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/guest")
    public String guest() {
        return "redirect:/PhotoBlog/list";
    }
}


