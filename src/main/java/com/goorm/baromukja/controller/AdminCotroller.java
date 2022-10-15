package com.goorm.baromukja.controller;

import com.goorm.baromukja.baseUtil.response.dto.ListResponse;
import com.goorm.baromukja.baseUtil.response.service.ResponseService;
import com.goorm.baromukja.dto.member.MemberRes;
import com.goorm.baromukja.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/15
 */

@Api(tags = "00. 관리자")
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/admin")
public class AdminCotroller {

    private final ResponseService responseService;
    private final MemberService memberService;

    @ApiOperation(value = "모든 회원 조회", notes = "모든 회원을 조회합니다.")
    @GetMapping("/all")
    public ListResponse<MemberRes> findAllMember() {
        List<MemberRes> allByName = memberService.findAll();
        return responseService.listResult(allByName);
    }
}
