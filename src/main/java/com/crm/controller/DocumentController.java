package com.crm.controller;

import com.crm.dto.AjaxResult;
import com.crm.exception.NotFoundException;
import com.crm.exception.ServiceException;
import com.crm.pojo.Document;
import com.crm.service.DocumentService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    /**
     * 上传文件
     * @param fid
     * @param file
     * @return
     */
    @PostMapping("/file/upload")
    @ResponseBody
    public AjaxResult uploadDocument(Integer fid, MultipartFile file){
       documentService.saveFile(fid,file);
       try {
           return new AjaxResult(AjaxResult.SUCCESS);
       }catch (ServiceException ex){
           return new AjaxResult(AjaxResult.ERROR,ex.getMessage());
       }
    }

    /**
     * 文件下载
     * @param id
     * @param response
     * @throws IOException
     */
    @GetMapping("/download/{id:\\d+}")
    public void downLoadFile(@PathVariable Integer id, HttpServletResponse response) throws IOException{
        InputStream inputStream=documentService.downLoadFile(id);
        if(inputStream==null){
            throw new NotFoundException();
        }else {
            Document document=documentService.findDocumentById(id);
            OutputStream outputStream=response.getOutputStream();
            //将文件下载标记为二进制
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
            String fileName=document.getName();
            fileName=new String(fileName.getBytes("UTF-8"),"ISO8859-1");
            response.setHeader("Content-Disposition","attachment;filename=\""+fileName+"\"");
            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }
    }
}
