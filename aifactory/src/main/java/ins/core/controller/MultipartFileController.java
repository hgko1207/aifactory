package ins.core.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ins.core.web.multipart.ContentFilePolicy;
import ins.core.web.multipart.MultipartFileHandler;

@Controller
@RequestMapping("/file/*")
public class MultipartFileController {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    MultipartFileHandler handler;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(HttpServletRequest request, HttpServletResponse response, @RequestParam("files") MultipartFile[] multipartFile,
            @RequestParam(value = "contentFile", required = false) String name, Model model) throws Exception {

        List<Map<String, String>> result = new ArrayList<Map<String, String>>();

        for (MultipartFile f : multipartFile) {
            Map<String, String> entityFile = handler.saveFile(f, "TEMP", name, new ContentFilePolicy() {
                @Override
                public String getContentFile(MultipartFile multipartFile, String nameKeyword) {
                    String fullName = multipartFile.getOriginalFilename();
                    String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date()) + "_" + RandomStringUtils.randomAlphanumeric(4);
                    String fileExt = StringUtils.getFilenameExtension(fullName);

                    return fileName;
                }

                @Override
                public String getContentFile() {
                    return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date()) + "_" + RandomStringUtils.randomAlphanumeric(4);
                }
            });

            result.add(entityFile);
        }
        model.addAttribute("upload", result);

        return "jsonView";
    }
    
    @RequestMapping(value = "/uploadImageAndGetUrl", method = RequestMethod.POST)
    public String uploadImageAndGetUrl(HttpServletRequest request, HttpServletResponse response, @RequestParam("files") MultipartFile[] multipartFile, Model model) throws Exception {

        List<Map<String, String>> result = new ArrayList<Map<String, String>>();

        for (MultipartFile f : multipartFile) {
            Map<String, String> entityFile = handler.saveFile(f, "DOC_IMG", "", new ContentFilePolicy() {
                @Override
                public String getContentFile(MultipartFile multipartFile, String nameKeyword) {
                    String fullName = multipartFile.getOriginalFilename();
                    String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date()) + "_" + RandomStringUtils.randomAlphanumeric(4);
                    String fileExt = StringUtils.getFilenameExtension(fullName);

                    return fileName;
                }

                @Override
                public String getContentFile() {
                    return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date()) + "_" + RandomStringUtils.randomAlphanumeric(4);
                }
            });

            result.add(entityFile);
        }
        String path = "";
        String orignlFileNm = "";
        if( result.size() >= 1 ){
            path = request.getContextPath() + "/download/byNameStream/DOC_IMG/" + result.get(0).get("streFileNm") + "/";
            orignlFileNm = result.get(0).get("orignlFileNm");
        }
        model.addAttribute("fileName", orignlFileNm);
        model.addAttribute("uploadedImageURL", path);

        return "jsonView";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(HttpServletRequest request, HttpServletResponse response, @RequestParam("multipartFile") String[] multipartFile, Model model)
            throws Exception {

        List<Map<String, String>> result = new ArrayList<>();

        for (String f : multipartFile) {

            handler.deleteFile(f, null);

            String contentFile = f;
            
            Map<String, String> map = new HashMap<>();
            map.put("streFileNm", contentFile);
            
            result.add(map);
        }
        model.addAttribute("delete", result.size() != 1 ? result : result.get(0));

        return "";
    }
}