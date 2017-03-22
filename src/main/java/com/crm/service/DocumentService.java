package com.crm.service;

import com.crm.pojo.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Administrator on 2017/3/22.
 */
public interface DocumentService {
    List<Document> findAll();

    List<Document> findDocumentByFid(Integer fid);

    void saveDir(String name, Integer fid);

    void saveFile(Integer fid, MultipartFile file);

}
