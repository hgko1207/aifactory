package ins.core.web.multipart;

import org.springframework.web.multipart.MultipartFile;

public interface ContentFilePolicy {

    public String getContentFile(MultipartFile multipartFile, String name);
    public String getContentFile();

}
