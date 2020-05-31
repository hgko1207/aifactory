package ins.aifactory.controller.lapAdhrnc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Principal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ins.aifactory.service.cmmnCode.CmmnCode;
import ins.aifactory.service.lap.Lap;
import ins.aifactory.service.lap.LapCriterion;
import ins.aifactory.service.lap.LapService;
import ins.aifactory.service.lapAdhrnc.LapAdhrnc;
import ins.aifactory.service.lapAdhrnc.LapAdhrncService;
import ins.aifactory.service.task.Task;
import ins.aifactory.service.task.TaskService;
import ins.aifactory.service.user.User;
import ins.core.exception.ActiveLapNoExistException;
import ins.core.web.AbstractController;

@Controller
@RequestMapping("lapAdhrnc")
public class LapAdhrncController extends AbstractController{
    
    private static final Logger PY_LOGGER = LoggerFactory.getLogger("python");
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private LapAdhrncService lapAdhrncService;
    
    @Autowired
    private LapService lapService;
    
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public String insert(HttpServletRequest request, HttpServletResponse response, LapAdhrnc entity, BindingResult result, Principal principal) {
        // 현재 진행중인 Lap 조회
        LapCriterion lapCriterion = new LapCriterion();
        lapCriterion.setTaskId(entity.getLap().getTask().getTaskId());
        lapCriterion.setActvtyYn("Y");
        List<Lap> lapList = lapService.list(lapCriterion);
        
        // 현재 진행중인 Lap가 없으면 오류 반환
		if (lapList.size() > 0) {
			entity.setLap(lapList.get(0));
            
            Task task = entity.getLap().getTask();
            task = taskService.detail(task);
            entity.setResultSbmisnMthdCode(new CmmnCode("1002","0000"));    // public
            
            // 참여자를 로그인한 사용자로 입력
            User user = new User();
            user.setUserId(principal.getName());
            entity.setUser(user);
            
            this.lapAdhrncService.insert(entity);
            
            // Python Call
            // Arg : adhrncSn, resultSbmisnMthdCode, filePath
            // /var/lib/tomcat8/upload/SUBMISSION
            String cmd[] = new String[10];
            cmd[0] = "sudo";
            cmd[1] = "sh";
            cmd[2] = "/home/aifactory/sendan/send.sh";
            cmd[3] = "/usr/bin/python3";
            cmd[4] = "/root/mf/aifactory/src" + task.getGrdngFileCours(); //채점코드 위치
            cmd[5] = entity.getAdhrncSn()+"";
            cmd[6] = "0000";
            cmd[7] = "/var/lib/tomcat8/upload/SUBMISSION/"+entity.getCmmnFile().getFiles().get(0).getStreFileNm(); //채점해야할 파일 위치
            //callShell(cmd);
            //callPython(cmd);
            
            // private insert
            entity.setResultSbmisnMthdCode(new CmmnCode("1002","0001"));
            this.lapAdhrncService.insert(entity);
            
            cmd[8] = entity.getAdhrncSn()+"";
			cmd[9] = "0001";
			callShell(cmd);
		} else {
			throw new ActiveLapNoExistException();
		}
        
        return "jsonView";
    }
    
    private void callShell(String[] cmd){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    PY_LOGGER.debug(cmd[6] + " Start Call Command ***********************************************************");
                    PY_LOGGER.debug(cmd[6] + " Command : " + cmd[0] + " " + cmd[1] + " " + cmd[2] + " " + cmd[3] + " " + cmd[4] + " " + cmd[5] + " " + cmd[6] + " " + cmd[7] + " " + cmd[8] + " " + cmd[9]);
                    
                    Process process = new ProcessBuilder(cmd).start();
                    
                    BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                    
                    // Read the output from the command
                    PY_LOGGER.debug(cmd[6] + " start standard output");
                    String s = null;
                    while ((s = stdInput.readLine()) != null) {
                        PY_LOGGER.debug(cmd[6] +  " " + s);
                    }
                    PY_LOGGER.debug(cmd[6] + " end standard output");
                    
                    // Read any errors from the attempted command
                    PY_LOGGER.debug(cmd[6] + " start error output");
                    while ((s = stdError.readLine()) != null) {
                        PY_LOGGER.debug(cmd[6] +  " " + s);
                    }
                    PY_LOGGER.debug(cmd[6] + " end error output");
                    PY_LOGGER.debug(cmd[6] + " End Call Command ***********************************************************");
                } catch (IOException e) {
                    PY_LOGGER.debug(e.getMessage(),e);  
                }
            }
        });
    }
    
    private void callPython(String[] cmd){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    PY_LOGGER.debug(cmd[3] + " Start Call Command ***********************************************************");
                    PY_LOGGER.debug(cmd[3] + " Command : " + cmd[0] + " " + cmd[1] + " " + cmd[2] + " " + cmd[3] + " " + cmd[4]);
                    
                    Process process = new ProcessBuilder(cmd).start();
                    
                    BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                    
                    // Read the output from the command
                    PY_LOGGER.debug(cmd[3] + " start standard output");
                    String s = null;
                    while ((s = stdInput.readLine()) != null) {
                        PY_LOGGER.debug(cmd[3] +  " " + s);
                    }
                    PY_LOGGER.debug(cmd[3] + " end standard output");
                    
                    // Read any errors from the attempted command
                    PY_LOGGER.debug(cmd[3] + " start error output");
                    while ((s = stdError.readLine()) != null) {
                        PY_LOGGER.debug(cmd[3] +  " " + s);
                    }
                    PY_LOGGER.debug(cmd[3] + " end error output");
                    PY_LOGGER.debug(cmd[3] + " End Call Command ***********************************************************");
                } catch (IOException e) {
                    PY_LOGGER.debug(e.getMessage(),e);  
                }
            }
        });
    }
}
