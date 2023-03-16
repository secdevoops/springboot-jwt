package es.secdevoops.springboot.jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@GetMapping(value="/secdevoops/admin/hello")
	public String helloAdmin() {
		return "Hello Admin!!!!";
	}

	@GetMapping(value="/secdevoops/user/hello")
	public String helloUser() {
		return "Hello User!!!!";
	}

}