package com.lec.spring.controller;

import com.lec.spring.domain.Post;
import com.lec.spring.domain.PostValidator;
import com.lec.spring.dto.PostDto;
import com.lec.spring.service.BoardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    public BoardController() {
        System.out.println("BoardController() 생성");
    }

    @GetMapping("/write")
    public void write() {}

    @PostMapping("/write")
    public String writeOk(@Valid PostDto post
            , BindingResult result
            , Model model  // 매개변수 선언시 BindingResult 보다 Model 을 뒤에 두어야 한다.
            , RedirectAttributes redirectAttributes // redirect 시 넘져줄 값들을 다믄 객체
    ){

        //만약 에러가 있다면 redirect 한다. -- 밑에 코드를 실행 안한다.
        if(result.hasErrors()) {

            // addAttribute
            //    request parameters로 값을 전달.  redirect URL에 query string 으로 값이 담김
            //    request.getParameter에서 해당 값에 액세스 가능
            // addFlashAttribute
            //    일회성. 한번 사용하면 Redirect후 값이 소멸
            //    request parameters로 값을 전달하지않음
            //    '객체'로 값을 그대로 전달

            // redirect 시, 기존에 입려갰던 값들은 보이게 하기
            redirectAttributes.addFlashAttribute("user", post.getUser());
            redirectAttributes.addFlashAttribute("subject", post.getSubject());
            redirectAttributes.addFlashAttribute("content", post.getContent());

            List<FieldError> errList = result.getFieldErrors();
            for(FieldError err : errList) {
                redirectAttributes.addFlashAttribute("error_" + err.getField(), err.getCode() );
            }

            return "redirect:/board/write";
        }

        model.addAttribute("result", boardService.write(post));
        return "board/writeOk";
    }


    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {

        model.addAttribute("post", boardService.detail(id));


        return "board/detail";
    }

    @GetMapping("/list")
    public void list(Model model) {

        model.addAttribute("list", boardService.list());

    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("post", boardService.selectById(id));
        return "board/update";
    }

    @PostMapping("/update")
    public String updateOk(@Valid Post post
            , BindingResult result
            , Model model
            , RedirectAttributes redirectAttributes

    ) {

        if(result.hasErrors()) {
            redirectAttributes.addFlashAttribute("subject", post.getSubject());
            redirectAttributes.addFlashAttribute("content", post.getContent());

            List<FieldError> errList = result.getFieldErrors();
            for(FieldError err : errList) {
                redirectAttributes.addFlashAttribute("error_" + err.getField(), err.getCode() );
            }

            return "redirect:/board/update/" + post.getId();
        }

        model.addAttribute("result",boardService.update(post));
        return "board/updateOk";
    }

    @PostMapping("/delete")
    public String deleteOk(Long id, Model model){
        model.addAttribute("result", boardService.deleteById(id));
        return "board/deleteOk";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        System.out.println("initBinder(호출)");
        binder.setValidator(new PostValidator());
    }


} //end controller
