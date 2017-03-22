package com.crm.mapper;


import com.crm.pojo.Notice;

import java.util.List;
import java.util.Map;

public interface NoticeMapper {
    Notice findByid(Integer id);

    List<Notice> findByParam(Map<String, Object> param);

    Long count();

    void save(Notice notice);
}
