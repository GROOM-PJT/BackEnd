package com.goorm.baromukja.controller.user;

import com.goorm.baromukja.baseUtil.config.JwtProperties;
import com.goorm.baromukja.baseUtil.config.service.JwtService;
import com.goorm.baromukja.baseUtil.response.dto.CommonResponse;
import com.goorm.baromukja.baseUtil.response.service.ResponseService;
import com.goorm.baromukja.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/15
 */

@Api(tags = "01-2. 회원 - user")
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member/user")
public class MemberControllerForUser {

    private final ResponseService responseService;
    private final MemberService memberService;
    private final JwtService jwtService;

    @PostMapping("/logout")
    @ApiOperation(value = "로그아웃", notes = "AccessToken & RefreshToken 헤더에 담아서 로그아웃 요청")
    public CommonResponse logout(HttpServletRequest request) {
        jwtService.logout(request);
        return responseService.successResult();
    }


    @ApiOperation(value = "회원 등급 조회", notes = "회원 등급을 반환합니다.(삭제 예정 - 불필요한 듯)")
    @GetMapping("/role")
    public CommonResponse findMemberRole(HttpServletRequest httpServletRequest) {
        String username = jwtService.decode(httpServletRequest.getHeader("Authorization").replace(JwtProperties.TOKEN_PREFIX, ""));
        return responseService.singleResult(memberService.findByUsername(username).getRole());
    }

}

