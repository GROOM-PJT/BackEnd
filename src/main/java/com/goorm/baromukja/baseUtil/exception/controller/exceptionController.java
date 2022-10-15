package com.goorm.baromukja.baseUtil.exception.controller;

import com.goorm.baromukja.baseUtil.exception.AccessDeniedException;
import com.goorm.baromukja.baseUtil.exception.BussinessException;
import com.goorm.baromukja.baseUtil.exception.ExMessage;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Api(tags = "999. Exception Test")
@RestController
@RequestMapping("/exception")
public class exceptionController {

	@GetMapping("/entrypoint")
	public void entrypointException() {
		throw new BussinessException(ExMessage.JWT_ERROR_FORMAT);
	}

	@GetMapping("/accessDenied")
	public void accessDeniedException(HttpServletResponse response) {
		throw new AccessDeniedException(ExMessage.JWT_ACCESS_DENIED);
	}
}
