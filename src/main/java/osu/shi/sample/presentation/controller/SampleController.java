package osu.shi.sample.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import osu.shi.sample.application.shared.MyException;

import javax.servlet.http.HttpServletResponse;

// HTTPステータスコードをセットする方法
@RestController
public class SampleController {
    // ① 返り値の型にResponseEntityを使う方法
    @GetMapping("/response_entity/{status}")
    public ResponseEntity<String> responseEntitySample(@PathVariable String status) {
        if (status.equals("ok")) {
            // ↓みたいな書き方もできる（冗長か？）
            // return ResponseEntity.status(HttpStatus.OK).body("responseEntitySample");
            return ResponseEntity.ok("responseEntitySample");
        } else {
            // ↓みたいな書き方もできる
            // return new ResponseEntity<>("responseEntitySample", HttpStatus.I_AM_A_TEAPOT);
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("responseEntitySample");
        }
    }

    // ② 独自の例外を定義して@ExceptionHandler使ってステータスコードをセットする方法
    // 独自の例外の実装をする必要があり、面倒くさい？
    @GetMapping("/my_exception/{status}")
    public String myExceptionSample(@PathVariable String status) throws MyException {
        if (status.equals("ok")) {
            return "myExceptionSample";
        } else {
            throw new MyException("myExceptionSample");
        }
    }

    @ExceptionHandler(MyException.class)
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public String exceptionHandlerSample(MyException e) {
        return "exceptionHandlerSample";
    }

    // ③HttpServletResponse responseを引数に含めて、ステータスコードをセットする方法
    // 副作用を使うのは好みではない（あくまで個人的感想）
    @GetMapping("/http_servlet_response/{status}")
    public String httpServletResponseSampe(@PathVariable String status, HttpServletResponse response) {
        if (status.equals("ok")) {
            return "httpServletResponseSampe";
        } else {
            response.setStatus(HttpStatus.I_AM_A_TEAPOT.value());
            return "httpServletResponseSampe";
        }
    }
}
