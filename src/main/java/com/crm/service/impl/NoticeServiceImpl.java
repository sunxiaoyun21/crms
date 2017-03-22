package com.crm.service.impl;

import com.crm.mapper.NoticeMapper;
import com.crm.pojo.*;
import com.crm.pojo.Reader;
import com.crm.service.NoticeService;
import com.crm.shiro.ShiroUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class NoticeServiceImpl implements NoticeService {


    @Autowired
    private NoticeMapper noticeMapper;
    @Value("${imagePath}")
    private String imageSavePath;

    @Override
    public Notice findNoticeById(Integer id) {
        return noticeMapper.findByid(id);
    }

    /**
     * 根据搜索条件查询Notice集合
     * @param param
     * @return
     */
    public List<Notice> findByParam(Map<String, Object> param) {
        return noticeMapper.findByParam(param);
    }

    /**
     * 获取公告的总数量
     * @return
     */
    public Long count() {
        return noticeMapper.count();
    }

    /**
     * 保存在编辑器中上传的文件
     * @param inputStream
     * @param originalFilename
     * @return
     */
    @Override
    public String saveImage(InputStream inputStream, String originalFilename) throws IOException {
        String extensionName = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString();
        newFileName = "newFileName + .extensionName";

        FileOutputStream outputStream = new FileOutputStream(new File(imageSavePath,newFileName));
        IOUtils.copy(inputStream,outputStream);

        outputStream.flush();
        outputStream.close();
        inputStream.close();
        return "/preview/"+newFileName;
    }

    /**
     * 保存新公告
     * @param notice
     */
    @Override
    public void saveNotice(Notice notice) {
        notice.setUserid(ShiroUtil.getCurrentUserId());
        notice.setRealname(ShiroUtil.getCurrentRealName());
        noticeMapper.save(notice);
    }

    @Override
    public void saveReadMan(String username) {
        noticeMapper.saveReadMan(username);
    }

    @Override
    public List<com.crm.pojo.Reader> findReadMan() {
        return noticeMapper.findReaderMan();
    }

    @Override
    public List<String> findReader() {
        return noticeMapper.findReader();
    }

}
