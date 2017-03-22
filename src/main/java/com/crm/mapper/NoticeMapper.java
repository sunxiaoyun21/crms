package com.crm.mapper;


import com.crm.pojo.Notice;
import com.crm.pojo.Reader;

import java.util.List;
import java.util.Map;

public interface NoticeMapper {
    Notice findByid(Integer id);

    List<Notice> findByParam(Map<String, Object> param);

    Long count();

    void save(Notice notice);

    void saveReadMan(String username);

    List<Reader> findReaderMan();


    List<String> findReader();

}
