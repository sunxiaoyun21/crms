package com.crm.controller;

import com.crm.dto.DataTablesResult;
import com.crm.exception.NotFoundException;
import com.crm.pojo.Notice;
import com.crm.service.NoticeService;
import com.crm.service.UserService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private UserService userService;
    @Autowired
    private NoticeService noticeService;

    @GetMapping
    public String list() {
        return "notice/list";
    }

    /**
     * 发表公告
     */
    @GetMapping("/new")
    public String newNotice() {
        return "notice/new";
    }

    @PostMapping("/new")
    public String newNotice(Notice notice, RedirectAttributes redirectAttributes) {
        noticeService.saveNotice(notice);
        redirectAttributes.addFlashAttribute("message", "发表成功");
        return "redirect:/notice";
    }

    /**
     * 查询notice
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/load", method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult load(HttpServletRequest request) {
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        String draw = request.getParameter("draw");

        Map<String, Object> param = Maps.newHashMap();
        param.put("start", start);
        param.put("length", length);

        List<Notice> noticeList = noticeService.findByParam(param);
        Long count = noticeService.count();

        return new DataTablesResult<>(draw, noticeList, count, count);
    }

    /**
     * 根据ID显示具体的公告内容
     */
    @GetMapping("/{id:\\d+}")
    public String viewNotice(@PathVariable Integer id, Model model) {
        Notice notice = noticeService.findNoticeById(id);
        if (notice == null) {
            throw new NotFoundException();
        }
        model.addAttribute("notice", notice);
        return "notice/view";
    }

    /**
     * 编辑器上传文件
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/img/upload")
    @ResponseBody
    public Map<String, Object> upload(MultipartFile file) throws IOException {
        Map<String, Object> result = Maps.newHashMap();
        if (!file.isEmpty()) {
            String path = noticeService.saveImage(file.getInputStream(), file.getOriginalFilename());

            result.put("success", true);
            result.put("file_path", path);
        } else {
            result.put("error", false);
            result.put("msg", "请选择文件");
        }

        return result;
    }
}
