package com.lec.spring.controller2;

import com.lec.spring.common.U;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;


// @ResponseBody
//   자바 객체를 그대로 response 함 (view 를 사용하지 않음)
//   String 을 리턴하면 문자열 텍스트로 response
//   Java 객체를 리턴하면 JSON 으로 response
//       List<>, Set<>, 배열 => JSON array 로 변환
//       Map<> => JSON object 로 변환
//       Java Object => JSON object 로 변환  (Property 사용!)
//

@Controller
public class BaseController {
    @RequestMapping("/member/search")
    @ResponseBody
    public String searchMember(){
        System.out.println("/member/search  searchMember() 호출");
        return "searchMember() 호출";
    }

    // ModelAndView 객체 사용
    // '뷰(View)' 와 '데이터(Model)' 을 둘다 -> ModelAndView 에 세팅
    @RequestMapping(value = "/member/find")
    public ModelAndView findMember(){
        ModelAndView mv = new ModelAndView();

        mv.addObject("mbName", "삼성라이온즈");
        mv.addObject("mbDate", LocalDateTime.now());
        mv.setViewName("member/find");

        return mv;
    }

    // wildcard *, ** 사용가능
    @RequestMapping("/member/action/*")
    @ResponseBody
    public String memberAction1(HttpServletRequest request) {
        return U.requestInfo(request);
    }

    @RequestMapping("/member/action/*/*")
    @ResponseBody
    public String memberAction2(HttpServletRequest request) {
        return U.requestInfo(request);
    }

    @RequestMapping("/member/action/*/*/**")
    @ResponseBody
    public String memberAction3(HttpServletRequest request) {
        return U.requestInfo(request);
    }

    // 확장자 패턴 사용 가능
    @RequestMapping("/member/*.do")
    @ResponseBody
    public String memberAction4(HttpServletRequest request) {
        return U.requestInfo(request);
    }


}


