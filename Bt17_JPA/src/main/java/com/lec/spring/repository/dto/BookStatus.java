package com.lec.spring.repository.dto;

import com.lec.spring.domain.Book;
import lombok.Data;

@Data
public class BookStatus {
    private int code;
    private String description;

    public BookStatus(int code){
        this.code = code;
        this.description = parseDescription(code);

    }
    // DB 의 정수값 컬럼으로부터 받아서 BookStatus 로 반환하는 생성자
    private String parseDescription(int code) {
        return switch (code){
            case 100 -> "판매종료";
            case 200 -> "판매중";
            case 300 -> "판매보류";
            default -> "미지원";
        };
    }

    public boolean isDisplayed(){
        return code == 200;
    }
}

