package com.green1st.mandalartWeb.user;

import com.green1st.mandalartWeb.common.model.ResultResponse;
import com.green1st.mandalartWeb.user.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("user")
@ToString
@Tag(name = "회원", description = "sign-in / sign-out")
public class UserController {
    private final UserService userService;
    private final UserMessage userMessage;
    private final UserSignUpReq userSignUpReq;
    private final AuthKeyDto authKeyDto;
    @Autowired
    private MailSendService mss;

    //이메일 중복체크
    @GetMapping("email")
    @Operation(summary = "이메일 중복체크")
    public ResultResponse<Integer> emailChk(@ParameterObject @ModelAttribute @Valid DuplicateEmailReq p){
        DuplicateEmailRes res = userService.emailChk(p.getUserId());

        return ResultResponse.<Integer>builder()
                .statusCode(res.getCheck() == 1 ? "200" : "400")
                .resultMsg(res.getMessage())
                .resultData(res.getCheck())
                .build();
    }

    //닉네임 중복체크
    @GetMapping("nickName")
    @Operation(summary = "닉네임 중복체크")
    public ResultResponse<Integer> nickNameChk(@ParameterObject @ModelAttribute @Valid DuplicateNickNameReq p) {
        DuplicateNickNameRes res = userService.nickNameChk(p.getNickName());


        return ResultResponse.<Integer>builder()
                .statusCode(res.getCheck() == 1 ? "200" : "400")
                .resultMsg(res.getMessage())
                .resultData(res.getCheck())
                .build();
    }


    @PostMapping("signUp")
    @Operation(summary = "회원가입")
    public ResultResponse<Integer> signUpUser(@RequestPart(required = false) MultipartFile pic
                                              , @RequestPart @Valid UserSignUpReq p){
        int result = userService.postSignUp(pic, p); //db에 일단 insert


        //임의의 authKey 생성 & 이메일 발송
        String authKey = mss.sendAuthMail(p.getUserId());
        AuthKeyDto authKeyDto = new AuthKeyDto();
        authKeyDto.setEmail(p.getUserId());
        authKeyDto.setAuthKey(authKey);
        long expiryTime = System.currentTimeMillis() + 10 * 60 * 1000;  // 현재 시간 + 5분 (밀리초 단위)
        authKeyDto.setExpiryTime(expiryTime);

        //DB에 authKey insert
        userService.insAuthKey(authKeyDto);

        return ResultResponse.<Integer>builder()
                .statusCode(result == 1 ? "200" : "400")
                .resultMsg(userMessage.getMessage())
                .resultData(result)
                .build();
    }

    @GetMapping("signUpConfirm")
    @Operation(summary = "이메일 검증")
    public String confirmSignUp(@ParameterObject @ModelAttribute AuthKeyDto authKeyDto) {
        int isCodeValid = userService.selAuth(authKeyDto);

        if(isCodeValid ==2) {
            userService.delAuthKey(authKeyDto);
            userService.delUserFirst(authKeyDto);
            return "코드가 유효하지않습니다. 다시 요청해주세요.";
        }

        if (isCodeValid == 0) {
            userService.delAuthKey(authKeyDto); //만료되면 자동으로 삭제처리
            userService.delUserFirst(authKeyDto); //이메일 다시 요청을 위해 기존 유저정보 삭제
            return "이메일 인증 코드가 만료되었습니다. 다시 요청해주세요.";
        }

        // 인증코드 삭제
        userService.delAuthKey(authKeyDto);

        // 인증 코드가 유효하다면 회원가입 처리 로직 실행
        return "회원가입이 완료되었습니다.";
    }

    @GetMapping("signUpConfirm1")
    public ResultResponse<Integer> confirmSignUp1(@ModelAttribute AuthKeyDto p) {
        int isCodeValid = userService.selAuth(p);

        return ResultResponse.<Integer>builder()
                .statusCode(isCodeValid==1 ? "200" : "400")
                .resultMsg(isCodeValid==1 ? "회원가입이 완료되었습니다." : "이메일 인증 코드가 만료되었습니다. 다시 요청해주세요.")
                .resultData(isCodeValid==1 ? 1 : 0)
                .build();
    }




    @PostMapping("signIn")
    @Operation(summary = "로그인")
    public ResultResponse<UserSignInRes> signInUser(@RequestBody @Valid UserSignInReq p) {

        UserSignInRes res = userService.postSignIn(p);


        return ResultResponse.<UserSignInRes>builder()
                .statusCode(res.getUserId() != null ? "200" : "400")
                .resultMsg(res.getMessage())
                .resultData(res)
                .build();
    }

    @GetMapping()
    @Operation(summary = "유저정보조회")
    public ResultResponse<UserInfoGetRes> getUserInfo(@ParameterObject @ModelAttribute @Valid UserInfoGetReq p){

        UserInfoGetRes res = userService.getUserInfo(p);

        return ResultResponse.<UserInfoGetRes>builder()
                .statusCode(res != null ? "200" : "400")
                .resultMsg("조회완료")
                .resultData(res)
                .build();
    }

    @PatchMapping
    @Operation(summary = "유저정보수정")
    public ResultResponse<Integer> patchUser(@RequestBody @Valid UserUpdateReq p) {
        UserUpdateRes res = userService.patchUser(p);

        return ResultResponse.<Integer>builder()
                .statusCode(res.getResult() == 1 ? "200" : "400")
                .resultMsg(res.getMessage())
                .resultData(res.getResult())
                .build();
    }

    /*@DeleteMapping
    @Operation(summary = "회원탈퇴")
    public ResultResponse<Integer> deleteUser(@ParameterObject @ModelAttribute UserDeleteReq p) {
        int result = userService.deleteUser(p);
        UserDeleteRes res = new UserDeleteRes();
        return ResultResponse.<Integer>builder()
                .statusCode(result == 1 ? "200" : "400")
                .resultMsg(res.getMessage())
                .build();
    }*/

    @DeleteMapping()
    @Operation(summary = "나의 좋아요 댓글 삭제")
    public ResultResponse<Integer> deleteMyLikeComment(@ParameterObject @ModelAttribute @Valid UserDeleteReq p){
        UserDeleteRes res = userService.deleteLikeComment(p);
        return ResultResponse.<Integer>builder()
                .statusCode(res.getCheck() != 0 ? "200" : "400")
                .resultMsg(res.getMessage())
                .resultData(res.getCheck() != 0 ? 1 : 0)
                .build();
    }


    // 임시 비밀번호 발급
    @PostMapping("password")
    @Operation(summary = "임시 비밀번호 전송")
    public ResultResponse<Integer> findPassword(@RequestBody TempPasswordDto req) {
        try {
            int result = userService.tempPassword(req);
            return ResultResponse.<Integer>builder()
                    .statusCode("200")
                    .resultMsg("임시비밀번호변경완료")
                    .resultData(result)
                    .build();
        } catch (IllegalArgumentException e) {
            // 이메일이 잘못되었거나 아이디가 없는 경우
            return ResultResponse.<Integer>builder()
                    .statusCode("400")
                    .resultMsg("아이디가 존재하지 않습니다.")
                    .resultData(0)
                    .build();
        }
    }

}
