package ins.core.web.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import ins.core.web.multipart.MultipartFileHandler;

public class MultipartFileView extends AbstractView {

    private MultipartFileHandler multipartFileHandler;
    private boolean isDownload = false;

    public void setMultipartFileHandler(MultipartFileHandler multipartFileHandler) {
        this.multipartFileHandler = multipartFileHandler;
    }

    public void setDownload(boolean isDownload) {
        this.isDownload = isDownload;
    }

    protected boolean generatesDownloadContent() {
        return isDownload;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (generatesDownloadContent()) {
            download(request, response, model);
        } else {
            stream(request, response, model);
        }

    }

    protected void download(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws Exception {

        Map<String, String> map = (Map<String, String>) model.get("downloadFile");
        String domainName = (String) model.get("domainName");
        
        String contentFile = map.get("streFileNm");
        String contentName = map.get("orignlFileNm");
        String contentType = map.get("fileExtsn");
//        long contentSize = Long.parseLong(map.get("fileSize"));
        
        File file = multipartFileHandler.findFile(contentFile, domainName);
        logger.debug("contentFile : " + contentFile);
        logger.debug("contentName : " + contentName);
        logger.debug("contentType : " + contentType);
//        logger.debug("contentSize : " + contentSize);
        logger.debug("file : " + file);

        String filename = new String(contentName.getBytes("euc-kr"), "8859_1");

        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        // response.setContentType(contentType);
        // response.setContentLength(contentSize);

        this.copy(file, response.getOutputStream());
    }

    protected void stream(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws Exception {

        Map<String, String> map = (Map<String, String>) model.get("downloadFile");
        String domainName = (String) model.get("domainName");
        
        String contentFile = map.get("streFileNm");
        String contentName = map.get("orignlFileNm");
        String contentType = map.get("fileExtsn");

        File file = multipartFileHandler.findFile(contentFile, domainName);
        logger.debug("contentFile : " + contentFile);
        logger.debug("contentName : " + contentName);
        logger.debug("contentType : " + contentType);
        logger.debug("file : " + file);

        // response.setContentType(contentType);
        // response.setContentLength((int)contentSize);
        copy(file, response.getOutputStream());
    }

    protected void copy(File file, OutputStream out) throws Exception {

        try {
            FileCopyUtils.copy(new FileInputStream(file), out);
        } catch (Exception e) {
        }
        /*
         * FileInputStream fis = null; try { fis = new FileInputStream(file);
         * FileCopyUtils.copy(fis, out); } catch(Exception e){
         * e.printStackTrace(); }finally{ if(fis != null){ try{ fis.close();
         * }catch(Exception e){ } } } out.flush();
         */
    }
}
