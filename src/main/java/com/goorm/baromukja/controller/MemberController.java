package com.goorm.baromukja.controller;

import com.goorm.baromukja.baseUtil.config.service.JwtService;
import com.goorm.baromukja.baseUtil.response.dto.CommonResponse;
import com.goorm.baromukja.baseUtil.response.service.ResponseService;
import com.goorm.baromukja.dto.member.LoginReq;
import com.goorm.baromukja.dto.member.MemberSignupReq;
import com.goorm.baromukja.entity.MemberRole;
import com.goorm.baromukja.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "01. 회원")
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private final JwtService jwtService;
    private final ResponseService responseService;
    private final MemberService memberService;


    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "이메일, 비밀번호를 이용하여 로그인")
    public void login(
            @ApiParam(value = "로그인 객체", required = true) @RequestBody LoginReq loginReq) {
    }

    @PostMapping("/logout")
    @ApiOperation(value = "로그아웃", notes = "AccessToken & RefreshToken 헤더에 담아서 로그아웃 요청")
    public CommonResponse logout(HttpServletRequest request) {
        jwtService.logout(request);
        return responseService.successResult();
    }


    @ApiOperation(value = "회원 등급 조회", notes = "회원 등급을 반환합니다.")
    @GetMapping("/role")
    public CommonResponse findMemberRole(HttpServletRequest httpServletRequest) {
        String username = jwtService.decode(httpServletRequest.getHeader("Authorization"));
        return responseService.singleResult(memberService.findByUsername(username).getRole());
    }



    @ApiOperation(value = "회원 가입", notes = "아이디, 패스워드를 받아서 저장합니다.")
    @PostMapping
    public CommonResponse saveUserMember(
            @ApiParam(value = "회원 객체", required = true)
            @RequestBody MemberSignupReq memberSignupReq) {
        memberService.signUp(memberSignupReq, MemberRole.USER);
        return responseService.successResult();
    }

    @ApiOperation(value = "ADMIN 회원 가입", notes = "관리자 회원의 아이디, 패스워드를 받아서 저장합니다.")
    @PostMapping("/admin")
    public CommonResponse saveAdminMember(
            @ApiParam(value = "Admin 회원 객체", required = true)
            @RequestBody MemberSignupReq memberSignupReq) {
        memberService.signUp(memberSignupReq, MemberRole.ADMIN);
        return responseService.successResult();
    }
}
