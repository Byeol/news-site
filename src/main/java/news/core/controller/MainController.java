package news.core.controller;

import news.util.constants.IConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(IConstants.APPLICATION_PATH)
public class MainController {

    @RequestMapping
    public String getMain() {
        return "main";
    }
}
