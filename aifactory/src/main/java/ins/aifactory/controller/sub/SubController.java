package ins.aifactory.controller.sub;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import ins.core.web.AbstractController;

@Controller
@RequestMapping("sub")
public class SubController extends AbstractController{

    /**
     * sub 화면
     * @param model
     * @return
     */
    @RequestMapping("overview")
    public String overview(ModelMap model) {
        return "tls/sub/" + this.urlbase + "/overview";
    }
    
    @RequestMapping("data")
    public String data(ModelMap model) {
        return "tls/sub/" + this.urlbase + "/data";
    }
    
    @RequestMapping("leaderboard")
    public String leaderboard(ModelMap model) {
        return "tls/sub/" + this.urlbase + "/leaderboard";
    }
    
}
