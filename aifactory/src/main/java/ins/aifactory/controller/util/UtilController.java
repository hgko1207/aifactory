package ins.aifactory.controller.util;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UtilController {

	/**
	 * validato rule dynamic Javascript
	 */
	@RequestMapping("/validator.do")
	public String validate() {
		return "util/validator";
	}

}