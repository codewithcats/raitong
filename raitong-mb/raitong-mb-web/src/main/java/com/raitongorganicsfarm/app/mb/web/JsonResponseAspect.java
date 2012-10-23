package com.raitongorganicsfarm.app.mb.web;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Aspect
public class JsonResponseAspect {

	@Pointcut("execution(public org.springframework.http.ResponseEntity<String> com.raitongorganicsfarm.app.mb.web.*Controller.* (..))")
	public void jsonResponse() {
	}

	@Around("com.raitongorganicsfarm.app.mb.web.JsonResponseAspect.jsonResponse()")
	public ResponseEntity<String> setJsonResponseHeaders(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		ResponseEntity<String> r = (ResponseEntity<String>) proceedingJoinPoint.proceed();
		HttpHeaders h = new HttpHeaders();
		h.add("content-type", "application/json; charset=utf-8");
		if (r == null) {
			return new ResponseEntity<String>("", h, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		HttpStatus s = r.getStatusCode();
		String b = r.getBody();
		return new ResponseEntity<String>(b, h, s);
	}

}
