package com.goorm.baromukja.controller.admin;

import com.goorm.baromukja.baseUtil.config.JwtProperties;
import com.goorm.baromukja.baseUtil.config.service.JwtService;
import com.goorm.baromukja.baseUtil.exception.ExMessage;
import com.goorm.baromukja.baseUtil.response.dto.CommonResponse;
import com.goorm.baromukja.baseUtil.response.service.ResponseService;
import com.goorm.baromukja.dto.member.MemberResponse;
import com.goorm.baromukja.dto.restaurant.*;
import com.goorm.baromukja.service.MemberService;
import com.goorm.baromukja.service.RestaurantServiceImpl;
import com.goorm.baromukja.service.awsS3.AwsS3Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Api(tags = "04-1. 식당 - admin")
@CrossOrigin
@RequestMapping("api/v1/restaurant/admin")
@RestController
@RequiredArgsConstructor
public class RestaurantControllerForAdmin {
    private final MemberService memberService;
    private final RestaurantServiceImpl restaurantService;
    private final ResponseService responseService;
    private final JwtService jwtService;
    private final AwsS3Service awsS3Service;

    @ApiOperation(value = "식당 정보 추가", notes = "식당 정보를 추가")
    @PostMapping("/add")
    public CommonResponse addRestaurant(HttpServletRequest request,
                                        @DateTimeFormat(pattern = "HH:mm:ss")
                                        @RequestBody RestaurantRequest restaurant) {
        // TODO: 이후에 api version을 3로 변경하면, Admin만 접근 가능하도록 설정됨
        String userName = jwtService.decode(request.getHeader("Authorization").replace(JwtProperties.TOKEN_PREFIX, ""));
        MemberResponse memberResponse = memberService.findByUsername(userName);
        log.info("ADD Restaurant: " + memberResponse.toString());
        RestaurantDto restaurantDto = restaurantService.save(restaurant, memberResponse);
        return responseService.singleResult(restaurantDto);
    }

    @ApiOperation(value = "식당 이미지 추가", notes = "식당 이미지를 추가")
    @PostMapping(value = "/addImage/{restaurantId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResponse addImage(HttpServletRequest request,
                                        @PathVariable Long restaurantId,
                                        @RequestPart("image") MultipartFile image) {
        RestaurantResponseWithMember restaurantResponse = restaurantService.findByIdWithMember(restaurantId);
        String userName = jwtService.decode(request.getHeader("Authorization").replace(JwtProperties.TOKEN_PREFIX, ""));
        if (restaurantResponse.getUsername().equals(userName)) {
            String imageUrl = awsS3Service.uploadFile(image);
            log.info("save Image");
            restaurantService.addImageUrl(restaurantId, imageUrl);
            return responseService.successResult();
        }
        return responseService.failResult(ExMessage.NO_AUTHORITY.getMessage());
    }


    @ApiOperation(value = "식당 삭제", notes = "식당 id를 받아 해당 식당을 삭제한다.")
    @DeleteMapping("/delete/{restaurantId}")
    public CommonResponse delete(HttpServletRequest request,
                                 @PathVariable Long restaurantId) {
        RestaurantResponseWithMember restaurantResponse = restaurantService.findByIdWithMember(restaurantId);
        String userName = jwtService.decode(request.getHeader("Authorization").replace(JwtProperties.TOKEN_PREFIX, ""));
        if (restaurantResponse.getUsername().equals(userName)) {
            restaurantService.delete(restaurantId);
            return responseService.successResult();
        }
        return responseService.failResult(ExMessage.NO_AUTHORITY.getMessage());
    }
}
