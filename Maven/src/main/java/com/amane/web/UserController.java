package com.amane.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.amane.dto.Execution;
import com.amane.dto.Result;
import com.amane.entity.User;
import com.amane.enums.NormalStateEnum;
import com.amane.exception.DuplicateException;
import com.amane.exception.WrongInputException;
import com.amane.service.UserService;

@Controller
@RequestMapping("/amane/user") // url:/模块/资源/{id}/细分 /seckill/list
public class UserController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;
	/*
	 * @RequestMapping 来映射请求，也就是通过它来指定控制器可以处理哪些URL请求,类似于struts的action请求
	 * RequestMapping注解有六个属性，下面我们把她分成三类进行说明。 1、 value， method；
	 * value：指定请求的实际地址，指定的地址可以是URI Template 模式[/a为常数，/{a}中a为参数]； method：
	 * 指定请求的method类型，GET、POST、PUT、DELETE等；
	 * 
	 * 2、 consumes，produces；
	 * consumes：指定处理请求的提交内容类型（Content-Type），例如application/json, text/html;
	 * produces:指定返回的内容类型，仅当request请求头中的(Accept)类型中包含该指定类型才返回；
	 * 
	 * 3、 params，headers； params： 指定request中必须包含某些参数值是，才让该方法处理请求。
	 * headers：指定request中必须包含某些指定的header值，才能让该方法处理请求。
	 * 
	 * @ResponseBody表示该方法的返回结果直接写入HTTP response body中
	 * 
	 * @PathVariable是用来获得请求url中的动态参数的(方法GET)
	 * 
	 * @RequestParam相当于request.getParameter()(方法POST)
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	private Result<Execution<User>> register(HttpSession session, User user) {
		Execution<User> execution = null;
		try {
			execution = userService.register(user);
			session.setAttribute("user", user);
			return new Result<Execution<User>>(true, execution);
		} catch (DuplicateException e1) {
			execution = new Execution<User>(NormalStateEnum.DUPLICATION);
		} catch (Exception e) {
			execution = new Execution<User>(NormalStateEnum.INNER_ERROR);
		}
		return new Result<Execution<User>>(false, execution.getStateInfo());
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	private Result<Execution<User>> login(HttpSession session, User user) {
		Execution<User> execution = null;
		try {
			execution = userService.login(user);
			session.setAttribute("user", execution.getData());
			return new Result<Execution<User>>(true, execution);
		} catch (WrongInputException e1) {
			execution = new Execution<User>(NormalStateEnum.WRONG_INPUT);
		} catch (Exception e) {
			execution = new Execution<User>(NormalStateEnum.INNER_ERROR);
		}
		return new Result<Execution<User>>(false, execution.getStateInfo());
	}
	
	@RequestMapping(value = "/getUid", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	private Result<Execution<String>> getUid() throws IOException {
		Execution<String> execution = null;
		try {
			execution = userService.getUid();
			return new Result<Execution<String>>(true, execution);
		} catch (Exception e) {
			execution = new Execution<String>(NormalStateEnum.INNER_ERROR);
		}
		return new Result<Execution<String>>(false, execution.getStateInfo());
	}
	
	@RequestMapping(value = "/ckUid", method = RequestMethod.POST,  produces = { "application/json; charset=utf-8" })
	@ResponseBody
	private Result<Execution<User>> ckUid(@RequestParam(value="username") String username) throws IOException {
		Execution<User> execution = null;
		try {
			execution = userService.queryUserByName(username);
			return new Result<Execution<User>>(true, execution);
		} catch(DuplicateException e1) {
			execution = new Execution<User>(NormalStateEnum.DUPLICATION);
		} catch(Exception e) {
			execution = new Execution<User>(NormalStateEnum.INNER_ERROR);
		}
		return new Result<Execution<User>>(false, execution.getStateInfo());
	}
	
	@RequestMapping(value = "/getParams", method = RequestMethod.GET,  produces = { "application/json; charset=utf-8" })
	private ModelAndView getParams(@RequestParam("uid") String uid) throws IOException {
		ModelAndView mav = new ModelAndView();
		Map<String, Integer> paramMap = userService.getParams(uid);
		mav.addAllObjects(paramMap);
		mav.setViewName("/my/items/myinfo");
		return mav;
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.POST,  produces = { "application/json; charset=utf-8" })
	@ResponseBody
	private Result<Execution<Integer>> test() throws IOException {
		Execution<Integer> execution = null;
		try {
			userService.test();
			execution = new Execution<Integer>(NormalStateEnum.SUCCESS, 1);
			return new Result<Execution<Integer>>(true, execution);
		} catch(Exception e) {
			execution = new Execution<Integer>(NormalStateEnum.INNER_ERROR);
		}
		return new Result<Execution<Integer>>(false, execution.getStateInfo());	
	}
}
