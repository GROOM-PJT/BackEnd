package com.goorm.baromukja.baseUtil.exception;

import com.goorm.baromukja.baseUtil.response.dto.CommonResponse;
import com.goorm.baromukja.baseUtil.response.service.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

	private final ResponseService responseService;

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public CommonResponse undefinedExceptionHandler(HttpServletRequest request, Exception e) {
		String exception = request.getHeader("exception");

		log.error("Exception : " + exception);
		log.error("미정의 에러");
		log.error(e.getMessage() + "\n\n");
		return responseService.failResult(e.getMessage());
	}

	@ExceptionHandler(BussinessException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public CommonResponse bussinessExceptionHandler(HttpServletRequest request, Exception e) {
		log.error("\n==========================================================================================\n"
				+ e.getMessage()
				+ "\n==========================================================================================");
		return responseService.failResult(e.getMessage());
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public CommonResponse accessDeniedExceptionHandler(HttpServletRequest request, Exception e) {
		log.error("\n==========================================================================================\n"
				+ e.getMessage()
				+ "\n==========================================================================================");
		return responseService.failResult(e.getMessage());
	}

}
