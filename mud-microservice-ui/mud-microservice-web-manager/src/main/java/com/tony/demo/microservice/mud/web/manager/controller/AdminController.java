package com.tony.demo.microservice.mud.web.manager.controller;

import java.util.Map;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tony.demo.microservice.mud.common.AbstractController;

/**
 * 访问权限测试
 * 
 * @author Tony
 *
 */
@RestController
@RequestMapping("admin")
@Secured("ROLE_ADMIN")
public class AdminController extends AbstractController {

	@GetMapping("info")
	public Map<String, Object> getInfo() {
		return data("Information for administrater");
	}

}
