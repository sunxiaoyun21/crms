package com.crm.service.impl;

import com.crm.exception.ServiceException;
import com.crm.mapper.DocumentMapper;
import com.crm.pojo.Document;
import com.crm.service.DocumentService;
import com.crm.shiro.ShiroUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/3/22.
 */
@Service
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    private DocumentMapper documentMapper;

    @Value("${savePath}")
    private String savePath;

    @Override
    public List<Document> findAll() {
        return documentMapper.findAll();
    }

    @Override
    public List<Document> findDocumentByFid(Integer fid) {
        return documentMapper.findDocumentByFid(fid);
    }

    @Override
    public void saveDir(String name, Integer fid) {
        Document document=new Document();
        document.setFid(fid);
        document.setCreateuser(ShiroUtil.getCurrentRealName());
        document.setName(name);
        document.setType(Document.TYPE_DIR);
        documentMapper.save(document);

    }

    @Override
    @Transactional
    public void saveFile(Integer fid, MultipartFile file) {
        //存文件到磁盘
        String name=file.getOriginalFilename();
        String fileName= UUID.randomUUID().toString();
        Long size=file.getSize();
        if(name.lastIndexOf(".") !=-1){
          fileName+=name.substring(name.lastIndexOf("."));
        }

        try {
            File saveFile=new File(new File(savePath),fileName);
            OutputStream outputStream=new FileOutputStream(saveFile);
            InputStream inputStream=file.getInputStream();
            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }catch (IOException ex){
            throw new ServiceException();
        }

        //保存数据库
        Document document=new Document();
        document.setFid(fid);
        document.setName(name);
        document.setFilename(fileName);
        document.setCreateuser(ShiroUtil.getCurrentRealName());
        document.setType(Document.TYPE_DOC);
        document.setSize(FileUtils.byteCountToDisplaySize(size));

        documentMapper.save(document);
    }

    /**
     * 下载文件
     * @param id
     * @return
     */
    @Override
    public InputStream downLoadFile(Integer id) throws FileNotFoundException {
        Document document=documentMapper.findDocumentById(id);
        if(document == null || Document.TYPE_DIR==document.getType()){
            return null;
        }else {
            File file=new File(savePath+"/"+document.getFilename());
            return new FileInputStream(file);
        }

    }

    @Override
    public Document findDocumentById(Integer id) {
        return documentMapper.findDocumentById(id);
    }


}
