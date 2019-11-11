package xyz.zinglizingli.common.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class IndexController {



    @RequestMapping(value = {"/index.html","/"})
    public String index(){
        return "redirect:/books";
    }
}
