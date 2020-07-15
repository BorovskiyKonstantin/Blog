package main.web.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DefaultController {
    @RequestMapping(value = {"/",
            "/edit/*",
            "/calendar/*",
            "/my/*",
            "/login",
            "/login/*",
            "/moderator/*",
            "/moderation/*",
            "/post/*",
            "/posts/*",
            "/profile",
            "/settings",
            "/stat",
            "/login/change-password/*",
            "/404"})
    public String index(Model model){
        return "forward:/index.html";
    }
}
