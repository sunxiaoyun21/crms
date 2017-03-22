package com.crm.service;


import com.crm.pojo.Notice;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface NoticeService {
    Notice findNoticeById(Integer id);

    List<Notice> findByParam(Map<String, Object> param);

    Long count();

    String saveImage(InputStream inputStream, String originalFilename) throws FileNotFoundException, IOException;

    void saveNotice(Notice notice);
}
