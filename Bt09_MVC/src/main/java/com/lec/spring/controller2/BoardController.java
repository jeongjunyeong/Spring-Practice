package com.lec.spring.controller2;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/board")
public class BoardController {

    @RequestMapping("/list")
    public String list(HttpServletRequest request) {
        return "board/list";
    }

    @RequestMapping("/write")
    public String write(HttpServletRequest request) {
        return "board/write";
    }

    @RequestMapping("/detail")
    public String detail(HttpServletRequest request) {
        return "board/detail";
    }

    @RequestMapping("/update")
    public String update(HttpServletRequest request) {
        return "board/update";
    }
    @RequestMapping("/delete")
    public String delete(HttpServletRequest request) {
        return "board/delete";
    }

}
