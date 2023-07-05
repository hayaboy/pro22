package com.spring.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.spring.member.service.MemberService;
import com.spring.member.vo.MemberVO;

public class MemberControllerImpl  extends MultiActionController  implements MemberController{
	private MemberService memberService;
	
	
	
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}




	@Override
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName=getViewName(request);
		List<MemberVO> membersList = memberService.listMembers();
		
		ModelAndView mav= new ModelAndView(viewName);
		
		mav.addObject("membersList", membersList);
		
		
		System.out.println(viewName);
		
		
		return mav;
	}

	
	
	public ModelAndView memberForm(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String viewName=getViewName(request);
		System.out.println(viewName);
		ModelAndView mav= new ModelAndView(viewName);
		
		return mav;
	}
	
	public ModelAndView addMember(HttpServletRequest request, HttpServletResponse response) throws Exception{
		System.out.println("회원 추가 코드 동작");
		
		ModelAndView mav= new ModelAndView();
		
		
		String id=request.getParameter("id");
		String pwd=request.getParameter("pwd");
		String name=request.getParameter("name");
		String email=request.getParameter("email");
		System.out.println(id + ","  + pwd + "," + name + "," +email );
		MemberVO memberVO = new MemberVO(id,pwd, name,email  );
		
		memberService.addMember(memberVO);  //회원 추가
		
		List<MemberVO> membersList = memberService.listMembers();
		mav.addObject("membersList", membersList);
		mav.setViewName("/listMembers");
		return mav;
	}
	
	
	
	
	
	public ModelAndView delMember(HttpServletRequest request, HttpServletResponse response) throws Exception{
		System.out.println("회원 삭제 코드 동작");
		String id=request.getParameter("id");
		
		memberService.delMember(id);
		
		ModelAndView mav= new ModelAndView();
		List<MemberVO> membersList = memberService.listMembers();
		mav.addObject("membersList", membersList);
		mav.setViewName("/listMembers");
		return mav;
	}
	
	
	
	private String getViewName(HttpServletRequest request) throws Exception{
		String contextPath = request.getContextPath();
		
		System.out.println("컨텍스트 경로 : " + contextPath);
		String uri=(String) request.getAttribute("javax.servlet.include.request_uri");
		System.out.println(uri);
		if(uri ==null || uri.trim().equals("")) {
			uri=request.getRequestURI();
			System.out.println("요청하는 uri:" + uri);
		}
		
		System.out.println("컨텍스트패스 길이:" + contextPath.length());
		
		int begin=0;  // 시작 위치
		
		if((contextPath != null) && ( ! ("".equals(contextPath)))) {
			begin=contextPath.length();
			System.out.println("시작위치:" + begin);
		} 
		
		System.out.println();
		
		int end;
		if(uri.indexOf(";") != -1) {
			end=uri.indexOf(";");
			System.out.println(end);
		}else if(uri.indexOf("?") != -1) {
			end=uri.indexOf("?");
			System.out.println(end);
		}else {
			end=uri.length();
			System.out.println("uri의 길이 :" + end);
		}
		
		String fileName=uri.substring(begin, end);
		System.out.println("중간 파일명: "+ fileName);
		
		
		if(fileName.indexOf(".")  !=-1) {
			fileName=fileName.substring(0, fileName.lastIndexOf("."));
			System.out.println(fileName);
		}
		
		if(fileName.lastIndexOf("/") != -1) {
			fileName=fileName.substring(fileName.lastIndexOf("/"),fileName.length() );
			System.out.println("최종 파일명 : " + fileName);
		}
		
		return fileName;
	}
	
}
