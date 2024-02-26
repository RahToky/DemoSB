package mg.mahatoky.demosb.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mtk_ext
 */
@RestController
@RequestMapping("/api")
public class HomeRestController {

    @GetMapping("")
    public String index(){
        return "welcome to the jungle";
    }

}
