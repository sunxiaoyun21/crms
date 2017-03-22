package com.crm.mapper;


import com.crm.pojo.UserLog;

import java.util.List;
import java.util.Map;

public interface UserLogMapper {

    List<UserLog> findByParam(Map<String, Object> param);

    Long countByParam(Map<String, Object> param);

    void save(UserLog userLog);
}
