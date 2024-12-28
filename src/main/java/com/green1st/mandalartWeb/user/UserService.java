package com.green1st.mandalartWeb.user;

import com.green1st.mandalartWeb.common.MyFileUtils;
import com.green1st.mandalartWeb.user.model.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final MyFileUtils myFileUtils;
    private final UserMessage userMessage;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private static String FROM_ADDRESS;


    //이메일 중복체크
    public DuplicateEmailRes emailChk(String userId){
        DuplicateEmailRes res = userMapper.checkEmailPw(userId);

        if(res == null){
            res = new DuplicateEmailRes();
            res.setCheck(1); //중복되는 이메일없을때
            res.setMessage("사용가능한 이메일입니다.");
            return res;
        }else {
            res.setCheck(0); //중복되는 이메일있을때
            res.setMessage("중복된 이메일입니다.");
            return res;
        }
    }

    //닉네임 중복체크
    public DuplicateNickNameRes nickNameChk(String nickName){
        DuplicateNickNameRes res = userMapper.checkNickName(nickName);

        if(res == null){
            res = new DuplicateNickNameRes();
            res.setCheck(1); //중복되는 닉네임없을때
            res.setMessage("사용가능한 닉네임입니다.");
            return res;
        }else {
            res.setCheck(0); //중복되는 닉네임있을때
            res.setMessage("중복된 닉네임입니다.");
            return res;
        }
    }


    //회원가입
    public int postSignUp(MultipartFile pic, UserSignUpReq p){
        System.out.println(pic);
        if(emailChk(p.getUserId()).getCheck() == 0){
            userMessage.setMessage("이메일중복체크를 해주세요.");
            return 0;
        } else if(nickNameChk(p.getNickName()).getCheck() == 0){
            userMessage.setMessage("닉네임중복체크를 해주세요.");
            return 0;
        }

        String savedPicName = (pic != null ? myFileUtils.makeRandomFileName(pic) : null);
        String hashedPassWord = BCrypt.hashpw(p.getUpw(), BCrypt.gensalt());

        p.setPic(savedPicName);
        p.setUpw(hashedPassWord);

        int result = userMapper.insUser(p);

        if(pic == null){
            userMessage.setMessage("회원가입이 완료되었습니다.");
            return result;
        }

        String userId = p.getUserId();
        String middlePath = String.format("user/%s", userId); //userId = 이메일
        myFileUtils.makeFolders(middlePath);
        String filePath = String.format("%s/%s", middlePath, savedPicName);

        try{
            myFileUtils.transferTo(pic, filePath);
        }catch (IOException e){
            e.printStackTrace();
        }
        userMessage.setMessage("이메일 인증 링크를 전송했습니다.");
        return result;

    }

    //데이터베이스내 인증코드 저장
    public void insAuthKey(AuthKeyDto p) {

        userMapper.insAuth(p);
    }

    // 인증 코드 검증
    public int selAuth(AuthKeyDto p) {
        // DB에서 인증 코드 조회
        EmailVerification record = userMapper.selAuth(p);

        if (record == null) {
            return 2;  // 해당 이메일로 저장된 인증 코드가 없으면 유효하지 않음
        }

        // 인증 코드가 일치하고, 만료 시간이 지나지 않았는지 확인
        if (record.getAuthKey().equals(p.getAuthKey()) && record.getExpiryTime() > System.currentTimeMillis()) {
            return 1;  // 인증 코드와 만료 시간 모두 유효
        }

        return 0;  // 인증 코드가 일치하지 않거나 만료된 경우
    }

    // 인증 코드 삭제
    public void delAuthKey(AuthKeyDto p) {
        userMapper.delAuth(p);
    }

    // 인증 오류시 먼저 저장된 유저정보 삭제
    public void delUserFirst(AuthKeyDto p) {
        userMapper.delUserFirst(p);
    }



    //로그인
    public UserSignInRes postSignIn(UserSignInReq p){
        int emailVerification = userMapper.checkCode(p.getUserId());

        if(emailVerification == 1){
            UserSignInRes res = new UserSignInRes();
            res.setMessage("이메일인증을 해주세요.");
            return res;
        }

        UserSignInRes res = userMapper.selUser(p);
        log.info("조회된 회원정보: {}", res);
        if(res == null || !BCrypt.checkpw(p.getUpw(), res.getUpw())){
            res = new UserSignInRes();
            res.setMessage("아이디 혹은 비밀번호를 확인해 주십시오.");
            return res;
        }

        res.setMessage("로그인성공");
        return res;
    }

    //회원정보조회
    public UserInfoGetRes getUserInfo(UserInfoGetReq p){
        return userMapper.selUserInfo(p);
    }


    //회원정보수정
    public UserUpdateRes patchUser(UserUpdateReq p){
        // 이메일, 비밀번호 일치 여부 확인
        UserUpdateRes res = userMapper.checkPassWord(p.getUserId());
        if(res == null || !BCrypt.checkpw(p.getUpw(), res.getUpw())){
            res = new UserUpdateRes();
            res.setMessage("이메일 혹은 비밀번호 재확인필요");
            res.setResult(0);
            return res;
        }

        // 비밀번호 바꿀시
        if(p.getNewUpw() != null && p.getCheckUpw() != null) {
            if(p.getNewUpw().equals(p.getCheckUpw())) {
                String hashedPassWord = BCrypt.hashpw(p.getNewUpw(), BCrypt.gensalt());
                p.setNewUpw(hashedPassWord);
            }
            else {
                res.setMessage("비밀번호를 다시 입력해주십시오.");
                res.setResult(0);
                return res;
            }
        }

        // 닉네임 바꿀시
        if(p.getNickName() != null) { //*여기검토*
            int check = nickNameChk(p.getNickName()).getCheck();
            if(check == 0){
                res.setMessage("중복된 닉네임입니다.");
                res.setResult(0);
                return res;
            }
        }

        //저장할 파일명(랜덤명 파일명) 생성
        String savedPicName = (p.getPic() != null ? myFileUtils.makeRandomFileName(p.getPic()) : null);
        p.setPicName(savedPicName);

        if(p.getPic() != null) {
            //폴더 생성
            String folderPath = String.format("user/%s", p.getUserId());
            myFileUtils.makeFolders(folderPath);

            //기존 파일 삭제
            String deletePath = String.format("%s/user/%s", myFileUtils.getUploadPath(), p.getUserId());
            myFileUtils.deleteFolder(deletePath, false);

            //원하는 위치에 저장할 파일명으로 파일을 이동(transferTo)
            String userId = p.getUserId();
            String filePath = String.format("user/%s/%s", userId, savedPicName);

            try {
                myFileUtils.transferTo(p.getPic(), filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // DB에 튜플을 수정(Update)
        int result = userMapper.updUser(p);

        res.setMessage("회원수정이 완료되었습니다.");
        res.setResult(result);
        return res;
    }

    //내가 좋아요한거, 댓글삭제
    public UserDeleteRes deleteLikeComment(UserDeleteReq p){
        UserDeleteRes userDeleteRes = userMapper.checkPassWord2(p.getUserId());
        if(p.getUserId() != userDeleteRes.getUserId() || !BCrypt.checkpw(p.getUpw(), userDeleteRes.getUpw())){
            userDeleteRes.setMessage("이메일 혹은 비밀번호를 확인해주세요");
            userDeleteRes.setCheck(0);
            return userDeleteRes;
        }

        int result = userMapper.delMyLikeAndComment(p.getUserId());
        userDeleteRes.setMessage("좋아요, 댓글 삭제 성공");
        userDeleteRes.setCheck(1);
        return userDeleteRes;
    }


    //회원삭제(미완성)
    public int deleteUser(UserDeleteReq p){
        UserSignInReq req = new UserSignInReq();
        UserSignInRes res = userMapper.selUser(req);
        UserDeleteRes userDeleteRes = new UserDeleteRes();
        if(res == null || !BCrypt.checkpw(p.getUpw(), res.getUpw())){
            userDeleteRes.setMessage("아이디 혹은 비밀번호를 확인해 주십시오.");
            return 0;
        }

        //구성요소들 삭제
        int deleteLikeComment = userMapper.delProjectLikeAndProjectComment(p);
        log.info("deleteLikeComment: {}", deleteLikeComment);
        int deleteSharedProject = userMapper.delSharedProject(p);
        log.info("deleteSharedProject: {}", deleteSharedProject);
        int deleteMandalart = userMapper.delMandalart(p);
        log.info("deleteMandalart: {}", deleteMandalart);
        int deleteProject = userMapper.delProject(p);
        log.info("deleteProject: {}", deleteProject);
        int deleteUser = userMapper.delUser(p);
        log.info("deleteUser: {}", deleteUser);

        //사진 삭제 (폴더 삭제)
        String deletePath = String.format("%s/user/%s", myFileUtils.getUploadPath(), p.getUserId());
        myFileUtils.deleteFolder(deletePath, true);

        userDeleteRes.setMessage("회원삭제가 완료되었습니다.");
        return 1;
    }
    //데이터 내 정보로 할때 객체 선언하면 새로운거라서 다시 수정필요.


    // -------------------------------------------------------
    // 임시 비밀번호 발급
    public int tempPassword(TempPasswordDto tempPasswordDto) {
        String userId = userMapper.checkPasswordId(tempPasswordDto.getUserId());
        if (userId == null || userId.isEmpty() ||
                !userId.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("유효하지 않은 이메일 주소: " + userId);
        }
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        StringBuilder tmpPasswordBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int idx = (int) (charSet.length * Math.random());
            tmpPasswordBuilder.append(charSet[idx]);
        }
        tempPasswordDto.setTmpPassword(tmpPasswordBuilder.toString());
        String tmpPasswordOriginal = tempPasswordDto.getTmpPassword();

        String hashedPassWord = BCrypt.hashpw(tempPasswordDto.getTmpPassword(), BCrypt.gensalt());
        tempPasswordDto.setTmpPassword(hashedPassWord);
        int result1 = userMapper.updTmpPassword(tempPasswordDto);

        //int result = userMapper.insPassword(tempPasswordDto);

        if(result1 == 1) {
            MimeMessage message = javaMailSender.createMimeMessage();

            try {
                message.setFrom(FROM_ADDRESS);
                message.setRecipients(MimeMessage.RecipientType.TO, userId);
                message.setSubject("임시 비밀번호 안내입니다.");
                String body = "";
                body += "<h3>" + "안녕하세요." + "</h3>";
                body += "<h3>" + "요청하신 임시 비밀번호가 생성되었습니다." + "</h3>";
                body += "<h3>" + "아래의 임시 비밀번호로 로그인하세요." + "</h3>";
                body += "<h1>" + tmpPasswordOriginal + "</h1>";
                body += "<h3>" + "감사합니다." + "</h3>";
                message.setText(body,"UTF-8", "html");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            javaMailSender.send(message);
        }
        return result1;
    }
}
