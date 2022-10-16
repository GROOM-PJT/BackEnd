package com.goorm.baromukja.controller.none;

import com.goorm.baromukja.baseUtil.config.service.JwtService;
import com.goorm.baromukja.baseUtil.response.dto.CommonResponse;
import com.goorm.baromukja.baseUtil.response.service.ResponseService;
import com.goorm.baromukja.dto.member.LoginRequest;
import com.goorm.baromukja.dto.member.MemberSignupRequest;
import com.goorm.baromukja.entity.MemberRole;
import com.goorm.baromukja.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


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
            @ApiParam(value = "로그인 객체", required = true) @RequestBody LoginRequest loginReq) {
    }


    @ApiOperation(value = "회원 가입", notes = "아이디, 패스워드를 받아서 저장합니다.")
    @PostMapping
    public CommonResponse saveUserMember(
            @ApiParam(value = "회원 객체", required = true)
            @RequestBody MemberSignupRequest memberSignupReq) {
        memberService.signUp(memberSignupReq, MemberRole.USER);
        return responseService.successResult();
    }

    @ApiOperation(value = "ADMIN 회원 가입", notes = "관리자 회원의 아이디, 패스워드를 받아서 저장합니다.")
    @PostMapping("/admin")
    public CommonResponse saveAdminMember(
            @ApiParam(value = "Admin 회원 객체", required = true)
            @RequestBody MemberSignupRequest memberSignupReq) {
        memberService.signUp(memberSignupReq, MemberRole.ADMIN);
        return responseService.successResult();
    }
}
