package ins.core.web.multipart;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;

public class ContentsFileHandlerImpl implements ContentsFileHandler, ApplicationContextAware {

    protected final Log logger = LogFactory.getLog(getClass());

    private WebApplicationContext applicationContext;
    private String location;
    private String locationWeb;
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public void setLocationWeb(String locationWeb) {
        this.locationWeb = locationWeb;
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = (WebApplicationContext) applicationContext;
    }
    
    public File findFile(String filePath) {
      String path = "";
      try {
          if( StringUtils.isEmpty(locationWeb) ){
              path = applicationContext.getServletContext().getRealPath(location) + filePath;
          }else{
              path = locationWeb + filePath;
          }
      } catch (Exception e) {
          throw new RuntimeException("content file location is not good.");
      }
      
      return new File(path);
  }
    
    

    public String findFilePath(String filePath) {
      String path = "";
      try {
          if( StringUtils.isEmpty(locationWeb) ){
              path = applicationContext.getServletContext().getRealPath(location) + filePath;
          }else{
              path = locationWeb + filePath;
          }
      } catch (Exception e) {
          throw new RuntimeException("content file location is not good.");
      }
      
      return path;
  }
    
    
    
}