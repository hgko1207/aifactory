package ins.core.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

@Aspect
@Component
public class ApiAspect {

	@Around(value = "execution(* ins..*Controller.*(..)) or execution(* ins..*Api.*(..))")
	public Object doApiDefaultAround(ProceedingJoinPoint pjp) throws Throwable {

		Object[] obj = pjp.getArgs();
		ModelMap modelMap = getModeMap(obj);
		Object retVal = null;

		if (modelMap != null) {
			modelMap.addAttribute(ApiConstants.CODE, ApiConstants.CODE_SUCCESS);
			modelMap.addAttribute(ApiConstants.MESSAGE, "Success");
		}
		retVal = pjp.proceed();
		return retVal;
	}

	private static ModelMap getModeMap(Object[] obj) {
		ModelMap modelMap = null;
		for (int i = 0; i < obj.length; i++) {
			if (obj[i] instanceof ModelMap)
				modelMap = (ModelMap) obj[i];
		}
		return modelMap;
	}

}
