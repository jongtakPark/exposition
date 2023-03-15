package com.exposition.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.exposition.constant.Role;
import com.exposition.dto.AnnouncementDto;
import com.exposition.entity.Announcement;
import com.exposition.entity.Member;
import com.exposition.service.AnnouncementService;
import com.exposition.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value="/ment")
@RequiredArgsConstructor
public class AnnouncementController {
	
	public final AnnouncementService announcementService;
	public final MemberService memberService;
	


	//공지사항
		@GetMapping(value="/announcement")
		public String announcement(Model model) {
			List list = announcementService.announcement();
			model.addAttribute("announcement",list);
			return "ment/announcement";
		}
		
		// 공지사항 글쓰기 페이지로 이동
		@GetMapping(value="/announcementwrite")
		public String announcewrite(Model model) {
			model.addAttribute("announcementDto", new AnnouncementDto());
			return "ment/annwrite";
		}			
		
		
		// 공지사항글쓰기
		@PostMapping(value="/ann")
		public String write(AnnouncementDto announcementDto, Model model) {
			Announcement announcement = Announcement.createannouncement(announcementDto);
			announcementService.saveAnnouncement(announcement);
			return "redirect:/ment/announcement";
			}	
		
		// 공지사항글 상세보기
		@GetMapping(value="/annview/{id}")
		public String mentView(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
			Optional<Announcement> view = announcementService.findAnnouncement(id);
			HttpSession session = request.getSession();
			session.setAttribute("title", view.get().getTitle());
			session.setAttribute("content", view.get().getContent());
			session.setAttribute("id", view.get().getId());
			model.addAttribute("title", view.get().getTitle());
			model.addAttribute("content", view.get().getContent());
			model.addAttribute("created", view.get().getCreatedBy());
			model.addAttribute("session",session);
			return "ment/annview";
		}
		
		//공지사항글 수정창으로 이동
		@GetMapping(value="/modify")
		public String modifyView(Model model) {
			model.addAttribute("announcementDto", new AnnouncementDto());
			return "ment/annupdatewrite";
		}
		
		//공지사항글 수정등록
		@PutMapping(value="/modcomplete/{id}")
		public String modComplete(@PathVariable("id") Long id, @RequestParam("title") String title, @RequestParam("content") String content, Announcement announcement, Model model) {
			announcement = announcementService.updateAnnouncement(id);
			AnnouncementDto announcementDto = new AnnouncementDto();
			announcementDto.setTitle(title);
			announcementDto.setContent(content);
			announcementDto.setId(id);
			announcement = Announcement.createannouncement(announcementDto);
			announcementService.saveAnnouncement(announcement);
			// model.addAttribute("freeboard",boardService.boardList()));
			return "redirect:/ment/announcement";
		}
		
		//문제 없이 잘 작동하지만 member테이블의 id를 가져오려면 id가 바인딩 되어 있어야 함.
		//설문게시판 페이지 넘어갈 때 session에 member id를 바인딩 해야함.
		//권환 변경 USER -> VOLUNTEER
		@GetMapping(value="/change/{id}")
		public String changeRole(@PathVariable("id") Long id, Model model, Optional<Member> member) {
			member = memberService.findById(id);
			member.get().setRole(Role.VOLUNTEER);
			memberService.updateMember(member.get());
			return "redirect:/ment/announcement";
		}

}
