package com.amane.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amane.dto.Execution;
import com.amane.dto.Result;
import com.amane.entity.Grade;
import com.amane.entity.Question;
import com.amane.entity.User;
import com.amane.enums.NormalStateEnum;
import com.amane.exception.DuplicateException;
import com.amane.service.QuestionService;
import com.amane.utils.BeanTools;

@Controller
@RequestMapping("/amane/question") // url:/模块/资源/{id}/细分 /seckill/list
public class QuestionController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private QuestionService questionService;
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
	@RequestMapping(value = "/batchInsert", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	private Result<Execution<Integer>> batchInsert(MultipartHttpServletRequest request, HttpSession session) throws Exception {
		Execution<Integer> execution = null;
		try {
			Object obj = session.getAttribute("creator");
			String basePath = session.getServletContext().getRealPath("/");
			Map<String, MultipartFile> fileMap = request.getFileMap();
			System.out.println(fileMap);
			Map<String, String> dataMap = BeanTools.mappedRequestParams(request);
			//一个bean实例化list: Collections.nCopies
			List<Question> qList = dataMap	.entrySet()
											.parallelStream()
											.map(m -> JSON.parseObject(m.getValue()).toJavaObject(Question.class))
											.collect(Collectors.toList());
			qList.forEach(q -> {
				BeanUtils.copyProperties(obj, q, BeanTools.getFitPropertyNames(obj, q));
				q.setPicpath(questionService.modifyPic(basePath, fileMap.get(q.getId())).getData());
			});
			qList.forEach(System.out::println);
			execution = questionService.batchInsert(qList);
			return new Result<Execution<Integer>>(true, execution);
		} catch(Exception e) {
			//e.printStackTrace();
			execution = new Execution<Integer>(NormalStateEnum.INNER_ERROR);
		}
		return new Result<Execution<Integer>>(false, execution.getStateInfo());
	}

	@RequestMapping(value = "/creation/step3", method = RequestMethod.POST, produces = {
		"application/json; charset=utf-8" })
	@ResponseBody
	private Result<Execution<Integer>> setStep3(HttpSession session) throws Exception {
		Execution<Integer> execution = null;
		try {
			session.removeAttribute("creator");
			return new Result<Execution<Integer>>(true, execution);
		} catch (Exception e) {
			//e.printStackTrace();
			execution = new Execution<Integer>(NormalStateEnum.INNER_ERROR);
		}
		return new Result<Execution<Integer>>(false, execution.getStateInfo());
	}
	
	@RequestMapping(value = "/getQs", method = RequestMethod.POST, produces = {
	"application/json; charset=utf-8" })
	@ResponseBody
	private Result<Execution<Map<String, Integer>>> getQs(@RequestParam("oid") String oid, @RequestParam("kind") String kind) throws Exception {
		Execution<Map<String, Integer>> execution = null;
		try {
			execution = questionService.getQs(oid, kind);
			return new Result<Execution<Map<String, Integer>>>(true, execution);
		} catch (Exception e) {
			//e.printStackTrace();
			execution = new Execution<Map<String, Integer>>(NormalStateEnum.INNER_ERROR);
		}
		return new Result<Execution<Map<String, Integer>>>(false, execution.getStateInfo());
	}

	@RequestMapping(value = "/preview")
	private ModelAndView getFirstList(Question question) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("qe", question);
		System.out.println(question);
		mav.setViewName("modals/previewModal");
		return mav;
	}
	
	@RequestMapping(value = "/tester")
	private ModelAndView getTester(@RequestParam("id") String id) throws Exception {
		ModelAndView mav = getQuestions(id);
		mav.setViewName("dtl/testpaper");
		return mav;
	}
	
	@RequestMapping(value = "/submitTest", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	private Result<Execution<Integer>> submitTest(@RequestParam Map<String, String> params, HttpSession session) throws Exception {
		Execution<Integer> execution = null;
		try {
			String uid = ((User) session.getAttribute("user")).getUid();
			execution = questionService.submitTest(uid, params);
			System.out.println(params);
			return new Result<Execution<Integer>>(true, execution);
		} catch (DuplicateException e1) {
			execution = new Execution<Integer>(NormalStateEnum.DUPLICATION);
		}  catch (Exception e) {
			execution = new Execution<Integer>(NormalStateEnum.INNER_ERROR);
		}
		return new Result<Execution<Integer>>(false, execution.getStateInfo());
	}
	
	@RequestMapping(value = "/testjudge")
	private ModelAndView getTestJudge(@RequestParam("uid") String uid, @RequestParam("id") String id) throws Exception {
		ModelAndView mav = getQuestions(id);
		List<Map<String, String>> ansList = questionService.getQuestionJudgeById(uid, id);
		mav.addObject("ansList", JSON.toJSON(ansList));
		mav.setViewName("dtl/testpaper");
		return mav;
	}
	
	@RequestMapping(value = "/submitJudge", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	private Result<Execution<Integer>> submitJudge(@RequestParam("form") String form, 
			@RequestParam("juid") String uid) throws Exception {
		Execution<Integer> execution = null;
		try {
			JSONObject jsonForm = JSON.parseObject(form);
			List<Grade> gradeList = new ArrayList<Grade>();
			jsonForm.keySet().forEach(k -> {
				Grade grade = new Grade();
				grade.setUid(uid);
				grade.setQid(k);
				grade.setGrade(jsonForm.getInteger(k));
				gradeList.add(grade);
			});
			execution = questionService.submitJudge(gradeList);
			return new Result<Execution<Integer>>(true, execution);
		} catch (DuplicateException e1) {
			execution = new Execution<Integer>(NormalStateEnum.DUPLICATION);
		}  catch (Exception e) {
			execution = new Execution<Integer>(NormalStateEnum.INNER_ERROR);
		}
		return new Result<Execution<Integer>>(false, execution.getStateInfo());
	}
	
	/**分别获得填空题与选择题*/
	private ModelAndView getQuestions(String id) {
		ModelAndView mav = new ModelAndView();
		List<Question> qList = questionService.getQuestionsById(id);
		mav.addObject("qList", qList);
		return mav;
	}
}
