package com.green1st.mandalartWeb.user;

import com.green1st.mandalartWeb.user.model.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    //중복체크
    DuplicateEmailRes checkEmailPw(String userId);
    DuplicateNickNameRes checkNickName(String nickName);
    UserUpdateRes checkPassWord(String userId);

    //회원가입
    int insUser(UserSignUpReq p);

    //이메일 코드 저장용 insert
    int insAuth(AuthKeyDto p);

    //이메일 코드 검증용 select
    EmailVerification selAuth(AuthKeyDto p);

    //이메일 코드 제거용 delete
    int delAuth(AuthKeyDto p);

    //코드 오류시 유저정보 삭제
    int delUserFirst(AuthKeyDto p);

    //로그인 전 인증했는지 체크
    int checkCode(String userId);




    //로그인
    UserSignInRes selUser(UserSignInReq p);
    //정보조회
    UserInfoGetRes selUserInfo(UserInfoGetReq p);

    //정보수정(미완성)
    int updUser(UserUpdateReq p);
    UserUpdateRes selUser2(String userId);
    //전체삭제(미완성)
    int delProjectLikeAndProjectComment(UserDeleteReq p);
    int delSharedProject(UserDeleteReq p);
    int delMandalart(UserDeleteReq p);
    int delProject(UserDeleteReq p);
    int delUser(UserDeleteReq p);

    //삭제를 위한 비밀번호 selet
    UserDeleteRes checkPassWord2(String userId);

    //내가 좋아요 한거, 댓글단거 삭제
    int delMyLikeAndComment(String userId);



// 임시 비밀번호 발급
    String checkPasswordId(String userId);

    int updTmpPassword(TempPasswordDto tempPasswordDto);

}
