package ins.aifactory.controller.notebook;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import ins.core.web.AbstractController;

@Controller
@RequestMapping("notebook")
public class NotebookController extends AbstractController{

    /**
     * 목록 화면
     * @param model
     * @return
     */
    @RequestMapping("search.do")
    public String main(ModelMap model) {
        return "tls/" + this.urlbase + "/search";
    }
    
    /**
     * 상세 화면
     * @param model
     * @return
     */
    @RequestMapping("detail.do")
    public String detail(ModelMap model) {
        return "tls/" + this.urlbase + "/detail";
    }
    
}
