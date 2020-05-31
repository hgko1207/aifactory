package ins.core.web.multipart;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface MultipartFileHandler {

    public Map<String, String> saveFile(MultipartFile multipartFile, String domainName) throws IOException;

    public Map<String, String> saveFile(MultipartFile multipartFile, String domainName, String fileName) throws IOException;

    public Map<String, String> saveFile(MultipartFile multipartFile, String domainName, String name, ContentFilePolicy policy) throws IOException;

    public Map<String, String> saveFile(MultipartFile multipartFile, File target) throws IOException;

    public File findFile(String contentFile, String domainName) throws IOException;

    public void deleteFile(String contentFile, String domainName) throws IOException;
    
    public ContentFilePolicy getContentFilePolicy(String domainName);
    
    public String getLocation();

}