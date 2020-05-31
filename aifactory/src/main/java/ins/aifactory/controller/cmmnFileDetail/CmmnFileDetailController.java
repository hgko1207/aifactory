package ins.aifactory.controller.cmmnFileDetail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;

import ins.aifactory.service.cmmnFileDetail.CmmnFileDetail;
import ins.aifactory.service.cmmnFileDetail.CmmnFileDetailCriterion;
import ins.aifactory.service.cmmnFileDetail.CmmnFileDetailService;
import ins.core.util.ZipUtils;
import ins.core.web.AbstractController;
import ins.core.web.multipart.MultipartFileHandler;

@Controller
@RequestMapping("cmmnFileDetail")
public class CmmnFileDetailController extends AbstractController{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CmmnFileDetailController.class);
    
    @Autowired
    private CmmnFileDetailService service;
    
    @Autowired
    private MultipartFileHandler handler;
    
    @Resource protected Validator validator;
    
    @RequestMapping(value = "deleteFile")
    public String deleteFile(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, CmmnFileDetail entity, BindingResult result) {
        service.delete(entity);
        return "jsonView";
    }
    
    @RequestMapping(value = "downloadAll")
    public String downloadAll(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, String atchFileId) {
        CmmnFileDetailCriterion criterion = new CmmnFileDetailCriterion();
        criterion.setAtchFileId(atchFileId);
        List<CmmnFileDetail> fileList = service.list(criterion);
        
        if(fileList != null && fileList.size() > 0){
            String dirName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date()) + "_" + RandomStringUtils.randomAlphanumeric(4);
            String targetDirPath = handler.getLocation() + File.separator + "TEMP" + File.separator + dirName;
            for(CmmnFileDetail detailFile : fileList){
                File s = new File(handler.getLocation() + File.separator + detailFile.getFileStreCours() + File.separator + detailFile.getStreFileNm());
                File t = new File(targetDirPath + File.separator + detailFile.getOrignlFileNm());
                
                try{
                    if(!t.getParentFile().exists()){
                        t.getParentFile().mkdirs();
                    }
                    IOUtils.copyLarge(new FileInputStream(s), new FileOutputStream(t));
                }catch(Exception e){
                    e.printStackTrace();
                    throw new RuntimeException();
                }
            }
            try {
                ZipUtils.zip(new File(targetDirPath));
                
                Map<String, String> file = new HashMap<String, String>();
                file.put("orignlFileNm", dirName+".zip");
                file.put("streFileNm", dirName+".zip");
        
                modelMap.addAttribute("domainName", "TEMP");
                modelMap.addAttribute("downloadFile", file);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
        
        return "download";
    }
    
}
