package ins.core.web.multipart;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class MultipartFileHandlerImpl implements MultipartFileHandler // ,
                                                                      // ApplicationContextAware
{

    protected final Log logger = LogFactory.getLog(getClass());

    private String location;

    public void setLocation(String location) {
        this.location = location;
    }
    public String getLocation(){
        return this.location;
    }

    // ///////////////////////////////
    //
    // ///////////////////////////////
    private ContentFilePolicy defaultPolicy = new ContentFilePolicy() {
        @Override
        public String getContentFile(MultipartFile multipartFile, String nameKeyword) {
            // String fullName = multipartFile.getOriginalFilename();
            // String fileName = StringUtils.stripFilenameExtension(fullName);
            String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date()) + "_" + RandomStringUtils.randomAlphanumeric(4);
            // String fileExt = StringUtils.getFilenameExtension(fullName);

            return fileName;// + "." + fileExt;
        }

        @Override
        public String getContentFile() {
            return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date()) + "_" + RandomStringUtils.randomAlphanumeric(4);
        }
    };
    
    private ContentFilePolicy videoPolicy = new ContentFilePolicy() {
        @Override
        public String getContentFile(MultipartFile multipartFile, String nameKeyword) {
            String fullName = multipartFile.getOriginalFilename();
            String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date()) + "_" + RandomStringUtils.randomAlphanumeric(4);
            String fileExt = StringUtils.getFilenameExtension(fullName);

            return fileName + "." + fileExt;
        }

        @Override
        /**
         * not supported method.
         */
        public String getContentFile() {
            throw new RuntimeException("not supported method.");
        }
    };
    
    @Override
    public Map<String, String> saveFile(MultipartFile multipartFile, String domainName) throws IOException {
        ContentFilePolicy policy = getContentFilePolicy(domainName);
        return saveFile(multipartFile, domainName, (String) null, policy);
    }

    @Override
    public Map<String, String> saveFile(MultipartFile multipartFile, String domainName, String name) throws IOException {
        ContentFilePolicy policy = getContentFilePolicy(domainName);
        return saveFile(multipartFile, domainName, name, policy);
    }

    @Override
    public Map<String, String> saveFile(MultipartFile multipartFile, String domainName, String name, ContentFilePolicy policy) throws IOException {
        
        logger.debug("MultipartFile : " + multipartFile);
        logger.debug("MultipartFile Name: " + multipartFile.getName());
        logger.debug("MultipartFile Size : " + multipartFile.getSize());
        logger.debug("MultipartFile ContentType: " + multipartFile.getContentType());
        logger.debug("MultipartFile OriginalFilename: " + multipartFile.getOriginalFilename());

        String contentFile = policy.getContentFile(multipartFile, name);
        logger.debug("Stored Dir : " + domainName);
        logger.debug("Stored File Name : " + contentFile);
        File dest = findFile(contentFile, domainName);

        if (dest.exists()) {
            System.out.println("IOUtils.copyLarge start;");
            IOUtils.copyLarge(multipartFile.getInputStream(), new FileOutputStream(dest, false));
        } else {
            dest.getParentFile().mkdirs();
            if (dest.createNewFile()) {
                logger.debug("Saved File : " + dest.getAbsolutePath());
//                multipartFile.transferTo(dest);
                IOUtils.copyLarge(multipartFile.getInputStream(), new FileOutputStream(dest, false));
            } else {
                throw new IOException("cann't create file");
            }
        }
        
        return makeFileInfoMap(multipartFile, contentFile);
    }

    @Override
    public Map<String, String> saveFile(MultipartFile multipartFile, File target) throws IOException {
        File out = target;
        if (!out.isAbsolute()) {
            out = new File(getBaseDir(null), target.toString());
        }
        if (out.exists()) {
            System.out.println("IOUtils.copyLarge start;");
            IOUtils.copyLarge(multipartFile.getInputStream(), new FileOutputStream(out, false));
        } else {
            out.getParentFile().mkdirs();
//            multipartFile.transferTo(out);
            IOUtils.copyLarge(multipartFile.getInputStream(), new FileOutputStream(out, false));
        }
        
        return makeFileInfoMap(multipartFile, target.toString());
    }
    
    @Override
    public ContentFilePolicy getContentFilePolicy(String domainName) {
        return defaultPolicy;
    }

    @Override
    public File findFile(String contentFile, String domainName) throws IOException {
        logger.debug("contentFile : " + contentFile);
        File file = new File(getBaseDir(domainName), contentFile);
        logger.debug("Find File : " + file);
        return file;
    }

    @Override
    public void deleteFile(String contentFile, String domainName) throws IOException {

        if (contentFile.startsWith(File.separator)) {
            String[] paths = StringUtils.delimitedListToStringArray(contentFile, File.separator);
            for (int i = 0; i < paths.length; i++) {
                logger.debug(paths[i]);
            }
            for (int i = 0; i < paths.length - 1; i++) {

                StringBuilder buf = new StringBuilder();
                for (int c = 0; c < (paths.length - i); c++) {
                    if (StringUtils.hasText(paths[c])) {
                        buf.append(File.separator).append(paths[c]);
                    }
                }

                String key = buf.toString();
                File file = findFile(key, domainName);
                if (file.delete()) {
                    logger.debug("Deleted File : " + file);
                }
            }
        } else {
            File file = findFile(contentFile, domainName);
            if (file.delete()) {
                logger.debug("Deleted File : " + file);
            }
        }
    }

    // ///////////////////////////////
    //
    // //////////////////////////////
    private File dir;

    private File getBaseDir(String domainName) {
//        if (dir != null)
//            return dir;
        String path = "";
        try {
            // if (location.startsWith(File.separator)) {
            // path =
            // applicationContext.getServletContext().getRealPath(location);
            // } else {
            path = location;
            
            if( !StringUtils.isEmpty(domainName) ){
                path += File.separator + domainName;
            }
            
            // }
            
            boolean isMkdir = true;
            dir = new File(path);
            if (!dir.exists()) {
                isMkdir = dir.mkdir();
            }
            
            if( isMkdir )
                return dir;
        } catch (Exception e) {

        }
        try {
            dir = new File(System.getProperty("user.dir"), "upload");
            if (dir.exists()) {
                return dir;
            } else {
                boolean mkdir = dir.mkdir();
                if (mkdir) {
                    return dir;
                }
            }
        } catch (Exception e) {

        }
        throw new RuntimeException("upload location is not good.");
    }

    private Map<String, String> makeFileInfoMap(MultipartFile multipartFile, String streFileNm){
        Map<String, String> map = new HashMap<>();
        map.put("fileStreCours", "");
        map.put("streFileNm", streFileNm);
        map.put("orignlFileNm", multipartFile.getOriginalFilename());
        map.put("fileExtsn", FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
        map.put("fileSize", multipartFile.getSize()+"");
        
        return map;
        
    }
}
