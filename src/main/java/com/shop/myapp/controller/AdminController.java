package com.shop.myapp.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.shop.myapp.dto.Member;
import com.shop.myapp.interceptor.Auth;
import com.shop.myapp.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
//@Auth(role = Auth.Role.ADMIN)
@RequestMapping("/admin")
public class AdminController {
	private final MemberService memberService;

	public AdminController(MemberService memberService) {
        this.memberService = memberService;
    }

	@PostMapping("/updateMemberInfo")
	public String updateMemberInfo(String memberId) {
		log.info("updateMemberInfo");

		return "관리자 메인페이지";
	}
	
	//@Auth(role = Auth.Role.ADMIN)
	@GetMapping("/list")
	public String memberListForm() {
		return "/members/memberListForm";
	}

	@PostMapping("/list")
	public ModelAndView getMemberList(@RequestParam String chkInfo, @RequestParam String condition) {
		List<Member> members = memberService.getMembers(chkInfo, condition);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("members/memberTable");
		mv.addObject("members", members);

		return mv;
	}
	
	@GetMapping("/{memberId}/detail")
	public ModelAndView detail(@PathVariable String memberId,Model model) {
		log.info("member detail!!!");
		Member member = memberService.getMember(memberId);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("modal/memberDetail");
		mv.addObject("member", member);
		System.out.println(member.getMemberLevel());
		/*model.addAttribute("member", member);

		return "/modal/memberDetail";*/
		return mv;
	}
	
	@PostMapping("/memberUpdate")
	public String memberUpdate(@ModelAttribute Member member) {
		log.info("member update!!!");
		
		memberService.updateByAdmin(member);
		
		return "redirect:/admin/list";
	}
}
