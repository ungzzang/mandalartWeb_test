package com.green1st.mandalartWeb.common.excprion;

import com.green1st.mandalartWeb.common.model.ResultResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. 일반적인 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultResponse<Integer>> handleGeneralException(Exception ex) {

        ResultResponse<Integer> response = new ResultResponse<Integer>(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())
        , ex.getMessage(), 0);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 2. 특정 예외 처리 (예: NullPointerException)
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ResultResponse<Integer>> handleNullPointerException(NullPointerException ex) {
        ResultResponse<Integer> response = new ResultResponse<Integer>(String.valueOf(HttpStatus.BAD_REQUEST.value())
                , "Null 값이 발견되었습니다: " + ex.getMessage(), 0);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 3. Bean Validation 예외 처리 (예: @Valid 관련 에러)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultResponse<List<String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> messages = new ArrayList<>();
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
                    errors.put(error.getField(), error.getDefaultMessage());
                    messages.add("속성명: " + error.getField() + ", 메세지: " + error.getDefaultMessage());
                }
        );

        ResultResponse<List<String>> response = new ResultResponse<List<String>>(
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                "속성 유효성 오류",
                messages
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
