package com.amane.web;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.amane.entity.Test;
import com.amane.utils.BeanTools;

@Controller
@RequestMapping("/")
public class DispatcherController {

	public static boolean isOnline(HttpSession session) {
		session.setMaxInactiveInterval(90 * 60);
		try {
			session.getAttribute("user").toString();
			return true;
		} catch(NullPointerException e) {
			return false;
		}
	}
	
	/** LOGIN AREA */
	@RequestMapping(value = "/register" , method = RequestMethod.GET)
	public String register(HttpSession session) {
		return isOnline(session) ? "redirect:/dorest" : "welcome/register";
	}
	
	@RequestMapping(value = "/login" , method = RequestMethod.GET)
	public String login(HttpSession session) {
		return isOnline(session) ? "redirect:/dorest" : "welcome/login";
	}
	
	@RequestMapping(value = "/logout" , method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
	
	/** MAIN AREA */
	@RequestMapping(value = "/dorest" , method = RequestMethod.GET)
	public String dorest(HttpSession session) throws IOException {
		return isOnline(session) ? "main/dorest" : "redirect:/login";
	}
	
	@RequestMapping(value = "/dorest/start_test" , method = RequestMethod.GET)
	public String startTest(HttpSession session, Test test, Model model) throws IOException {
		model.addAttribute("test", JSON.toJSON(test));
		System.out.println(model);
		return isOnline(session) ? "dtl/tester" : "redirect:/login";
	}
	
	/** DETAIL AREA */
	@RequestMapping(value = "/dorest/list/{templateType}")
	public String list(HttpServletRequest request, HttpSession session, Model model, 
			@PathVariable("templateType") String templateType) throws IOException {
		if(isOnline(session)) {
			model.addAllAttributes(BeanTools.mappedRequestParams(request));
			model.addAttribute("templateType", templateType);
			return "dtl/list";
		}
		else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/dorest/{templateType}/detail/{id}" , method = RequestMethod.GET)
	public String detail(HttpServletRequest request, HttpSession session, Model model, 
			@PathVariable("templateType") String templateType, @PathVariable("id") String id) throws IOException {
		if(isOnline(session)) {
			model.addAttribute(BeanTools.mappedRequestParams(request));
			model.addAttribute("templateType", templateType);
			model.addAttribute("id", id);
			return "dtl/dtl";
		}
		else {
			return "redirect:/login";
		}
	}
	
	/** CREATE AREA */
	@RequestMapping(value = "/creation/warmup" , method = RequestMethod.GET)
	public String creation(HttpSession session) throws IOException {
		if(isOnline(session)) {
			session.removeAttribute("creator");
			return "creation/step1";
		}
		else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/creation/infos" , method = RequestMethod.GET)
	public String creation2() throws IOException {
		return "creation/step2";
	}
	
	@RequestMapping(value = "/creation/contents" , method = RequestMethod.GET)
	public String creation3() throws IOException {
		return "creation/step3";
	}
	
	@RequestMapping(value = "/creation/contents/form" , method = RequestMethod.GET)
	public String creation3Pretask() throws IOException {
		return "creation/step3_template/test";
	}
	
	/** PART AREA */
	@RequestMapping(value = "/nav" , method = RequestMethod.GET)
	public String nav() {
		return "part/nav";
	}
	
	@RequestMapping(value = "/footer" , method = RequestMethod.GET)
	public String footer() {
		return "part/footer";
	}
	
	@RequestMapping(value = "/sidebar" , method = RequestMethod.GET)
	public String sidebar(HttpServletRequest request, Model model) throws IOException {
		model.addAllAttributes(BeanTools.mappedRequestParams(request));
		return "part/sidebar";
	}
	
	@RequestMapping(value = "/creation_nav" , method = RequestMethod.GET)
	public String creationNav() {
		return "part/creation_nav";
	}
	
	@RequestMapping(value = "/modals/addModal" , method = RequestMethod.GET)
	public String addModal() {
		return "modals/addModal";
	}
	
	@RequestMapping(value = "/modals/previewModal" , method = RequestMethod.GET)
	public String previewModal() {
		return "modals/previewModal";
	}
	
	@RequestMapping(value = "/modals/createModal" , method = RequestMethod.GET)
	public String creatorModal() {
		return "modals/creatorModal";
	}
	
	@RequestMapping(value = "/user/{path}" , method = RequestMethod.GET)
	public String user(HttpSession session, Model model, @PathVariable("path") String path) throws IOException {
		if(isOnline(session)) {
			model.addAttribute("path", path);
			return "my/mode";
		}
		else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/user/{path}/dtl" , method = RequestMethod.GET)
	public String myPath(@PathVariable("path") String path) {
		return "my/items/" + path;
	}

	@RequestMapping(value = "/user/testcenter/judge" , method = RequestMethod.GET)
	public String user(HttpServletRequest request, HttpSession session, Model model) throws IOException {
		if(isOnline(session)) {
			model.addAllAttributes(BeanTools.mappedRequestParams(request));
			return "dtl/testjudge";
		}
		else {
			return "redirect:/login";
		}
	}
}
