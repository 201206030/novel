package xyz.zinglizingli.search.web;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.zinglizingli.search.utils.ContentFactory;

import java.util.Set;

@Controller
@RequestMapping
public class IndexController {



    @RequestMapping(value = {"/index.html","/"})
    public String index(ModelMap modelMap){
        modelMap.put("hotBookList", ContentFactory.giveRandomContent());
        return "index";
    }
}
