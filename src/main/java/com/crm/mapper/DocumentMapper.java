package com.crm.mapper;

import com.crm.pojo.Document;

import java.util.List;

/**
 * Created by Administrator on 2017/3/22.
 */
public interface DocumentMapper {
    List<Document> findAll();

    List<Document> findDocumentByFid(Integer fid);

    void save(Document document);

    Document findDocumentById(Integer id);
}
