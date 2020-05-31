package ins.aifactory.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import ins.core.web.AbstractController;

@Controller
@RequestMapping("login")
public class LoginController extends AbstractController{

    /**
     * 로그인 화면
     * @param model
     * @return
     */
    @RequestMapping("login.do")
    public String main(ModelMap model) {
        return this.urlbase + "/login";
    }
}
