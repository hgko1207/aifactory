package ins.aifactory.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import ins.aifactory.service.lap.LapService;

public class LapProcJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(LapProcJob.class);
    
    @Autowired
    private LapService lapService;

    @Override
    protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
        LOGGER.debug("# Run LapProcJob.... ####################################################");
        
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        
        // 종료된 랩 상태 업데이트
        LOGGER.debug("# Lap End Proc.... ");
        lapService.updateEndProc();
        
        // 랩 시작 상태 업데이트
        LOGGER.debug("# Lap Start Proc.... ");
        lapService.updateStartProc();
        
        // 현재 시간이 체크 타임이고 랩이 활성화 중인 랩의 랭킹 업데이트
        LOGGER.debug("# Lap Rank Proc.... ");
        lapService.updateRankProc();
        
    }
}
