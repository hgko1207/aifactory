package ins.core.web.multipart;

import java.io.File;

public interface ContentsFileHandler {
    public void setLocation(String location);
    public File findFile(String fileName);
    public String findFilePath(String fileName);

}