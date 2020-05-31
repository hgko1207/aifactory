package ins.aifactory.controller.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import ins.core.web.AbstractController;

@Controller
@RequestMapping("main")
public class MainController extends AbstractController{

    /**
     * 메인 화면
     * @param model
     * @return
     */
    @RequestMapping("main.do")
    public String main(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return "tls/main/" + this.urlbase +"/main";
    }
    
}
