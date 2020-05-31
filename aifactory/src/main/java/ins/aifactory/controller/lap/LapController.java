package ins.aifactory.controller.lap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ins.aifactory.service.lap.Lap;
import ins.aifactory.service.lap.LapService;
import ins.aifactory.service.lapRank.LapRank;
import ins.aifactory.service.lapRank.LapRankService;
import ins.core.web.AbstractController;

@Controller
@RequestMapping("lap")
public class LapController extends AbstractController{
    
    @Autowired
    private LapService lapService;
    
    @Autowired
    private LapRankService lapRankService;
    
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String delete(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Lap entity, BindingResult result) {
        // Rank 삭제
        LapRank lapRank = new LapRank();
        lapRank.setLap(entity);
        lapRankService.deleteByLap(lapRank);
        
        // Lap 삭제
        lapService.delete(entity);
        
        return "jsonView";
    }
}
