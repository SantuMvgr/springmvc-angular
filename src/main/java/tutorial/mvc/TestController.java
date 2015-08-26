package tutorial.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Sandy on 8/26/2015.
 */

@Controller
public class TestController {
    @RequestMapping("/test")
    public String test() {
        return "view";
    }
}
