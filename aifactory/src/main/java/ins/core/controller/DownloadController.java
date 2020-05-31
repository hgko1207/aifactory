package ins.core.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/download/*")
public class DownloadController {

    /*
     * 파일명으로 파일다운로드
     */
    @RequestMapping(value = "/byName/{domainName}/{realName}/{storedName}/", method = RequestMethod.GET)
    public String download(ModelMap modelMap, @PathVariable("domainName") String domainName, @PathVariable("realName") String realName, @PathVariable("storedName") String storedName) {
        Map<String, String> file = new HashMap<String, String>();
        file.put("orignlFileNm", realName);
        file.put("streFileNm", storedName);

        modelMap.addAttribute("domainName", domainName);
        modelMap.addAttribute("downloadFile", file);

        return "download";
    }

    /*
     * 파일명으로 파일 스트림
     */
    @RequestMapping(value = "/byNameStream/{domainName}/{storedName}/", method = RequestMethod.GET)
    public String stream(ModelMap modelMap, @PathVariable("domainName") String domainName, @PathVariable("storedName") String storedName) {
        Map<String, String> file = new HashMap<String, String>();
        file.put("orignlFileNm", storedName);
        file.put("streFileNm", storedName);

        modelMap.addAttribute("domainName", domainName);
        modelMap.addAttribute("downloadFile", file);

        return "stream";
    }
    
    /*
     * resources 파일 다운로드
     */
    @RequestMapping(value = "/resources/{filePath}/", method = RequestMethod.GET)
    public String downloadRes(ModelMap modelMap, @PathVariable("filePath") String filePath) {
        modelMap.addAttribute("downloadFile", filePath);

        return "downloadRes";
    }
}
