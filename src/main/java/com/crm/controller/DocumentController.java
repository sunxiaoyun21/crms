package com.crm.controller;

import com.crm.dto.AjaxResult;
import com.crm.exception.ServiceException;
import com.crm.pojo.Document;
import com.crm.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

/**
 * Created by Administrator on 2017/3/22.
 */
@Controller
@RequestMapping("/doc")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @GetMapping
    public String list(Model model,
                      @RequestParam(required = false,defaultValue = "0") Integer fid){

        List<Document> documentList=documentService.findDocumentByFid(fid);
        model.addAttribute("documentList",documentList);
        model.addAttribute("fid",fid);
        return "document/list";
    }

    /**
     * 新建文件夹
     * @return
     */
    @PostMapping("/dir/new")
    public String newDocument(String name,Integer fid){
        documentService.saveDir(name,fid);
        return "redirect:/doc?fid="+fid;
    }

    @PostMapping("/file/upload")
    public AjaxResult uploadDocument(Integer fid, MultipartFile file){
       documentService.saveFile(fid,file);
       try {
           return new AjaxResult(AjaxResult.SUCCESS);
       }catch (ServiceException ex){
           return new AjaxResult(AjaxResult.ERROR,ex.getMessage());
       }


    }
}
