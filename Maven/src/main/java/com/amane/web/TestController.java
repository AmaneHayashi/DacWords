package com.amane.web;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.amane.dto.Execution;
import com.amane.dto.Result;
import com.amane.entity.Mark;
import com.amane.entity.Test;
import com.amane.entity.User;
import com.amane.enums.NormalStateEnum;
import com.amane.exception.DuplicateException;
import com.amane.exception.EmptyFieldException;
import com.amane.service.TestService;
import com.amane.utils.BeanTools;

@Controller
@RequestMapping("/amane/test") // url:/模块/资源/{id}/细分 /seckill/list
public class TestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TestService testService;

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


	@RequestMapping(value = "/creation/step3", method = RequestMethod.POST, produces = {
			"application/json; charset=utf-8" })
	@ResponseBody
	private Result<Execution<Integer>> setStep3(Test test, HttpSession session) throws Exception {
		Execution<Integer> execution = null;
		try {
			Test otest = (Test) session.getAttribute("creator");
			BeanUtils.copyProperties(otest, test, BeanTools.getFitPropertyNames(otest, test));
			// 完成注册
			execution = testService.register(test);
			System.out.println(test);
			session.removeAttribute("creator");
			return new Result<Execution<Integer>>(true, execution);
		} catch (Exception e) {
			//e.printStackTrace();
			execution = new Execution<Integer>(NormalStateEnum.INNER_ERROR);
		}
		return new Result<Execution<Integer>>(false, execution.getStateInfo());
	}

	/*成绩科*/
	@RequestMapping(value = "/testcenter/gradeDepart", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	private Result<Execution<Object>> gradeDeparter(@RequestParam("name") String name) throws Exception {
		Execution<Object> execution = null;
		try {
			execution = testService.getGradeDeparterMap(name);
			return new Result<Execution<Object>>(true, execution);
		} catch(EmptyFieldException e) {
			execution = new Execution<Object>(NormalStateEnum.EMPTY_FIELD);
		}
		catch (Exception e) {
			//e.printStackTrace();
			execution = new Execution<Object>(NormalStateEnum.INNER_ERROR);
		}
		return new Result<Execution<Object>>(false, execution.getStateInfo());
	}
	
	/*阅卷科*/
	@RequestMapping(value = "/testcenter/markDepart", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	private Result<Execution<Object>> markDeparter(@RequestParam("name") String name) throws Exception {
		Execution<Object> execution = null;
		try {
			execution = testService.getMarkDeparterMap(name);
			return new Result<Execution<Object>>(true, execution);
		} catch(EmptyFieldException e) {
			execution = new Execution<Object>(NormalStateEnum.EMPTY_FIELD);
		}
		catch (Exception e) {
			//e.printStackTrace();
			execution = new Execution<Object>(NormalStateEnum.INNER_ERROR);
		}
		return new Result<Execution<Object>>(false, execution.getStateInfo());
	}
	
	/*考试开始前的工作*/
	@RequestMapping(value = "/tester/init", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	private Result<Execution<Integer>> initTest(@RequestParam("id") String id, HttpSession session)
			throws Exception {
		Execution<Integer> execution = null;
		try {
			String uid = ((User) session.getAttribute("user")).getUid();
			Mark mark = new Mark(uid, id, -1);
			execution = testService.registerMark(mark);
			return new Result<Execution<Integer>>(true, execution);
		} catch (DuplicateException e1) {
			execution = new Execution<Integer>(NormalStateEnum.DUPLICATION);
		} catch (Exception e) {
			//e.printStackTrace();
			execution = new Execution<Integer>(NormalStateEnum.INNER_ERROR);
		}
		return new Result<Execution<Integer>>(false, execution.getStateInfo());
	}	
	
	/*阅卷*/
	@RequestMapping(value = "/tester/submitMark", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	private Result<Execution<Integer>> updateMark(Mark mark) throws Exception {
		Execution<Integer> execution = null;
		try {
			execution = testService.submitMark(mark);
			return new Result<Execution<Integer>>(true, execution);
		} catch (DuplicateException e1) {
			execution = new Execution<Integer>(NormalStateEnum.DUPLICATION);
		}  catch (Exception e) {
			execution = new Execution<Integer>(NormalStateEnum.INNER_ERROR);
		}
		return new Result<Execution<Integer>>(false, execution.getStateInfo());
	}
}
