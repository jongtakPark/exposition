package com.exposition.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.exposition.dto.ReservationDto;
import com.exposition.entity.Company;
import com.exposition.service.CompanyService;
import com.exposition.service.ReservationService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value="/lease")
@RequiredArgsConstructor
public class LeaseController {

	private final CompanyService companyService;
	private final ReservationService reservationService;
	@GetMapping(value="/")
	public String Lease(Model model) {
		model.addAttribute("reservationDto", new ReservationDto());
		return "lease/lease";
	}
	//DB에서 endDay 가져올때 +1 해줘야함
	@GetMapping(value="/calendar")
<<<<<<< HEAD
<<<<<<< HEAD
	   public String caldendar() {
	      return "lease/calendar";
	   }
=======
	public String caldendar(Model model, HttpServletRequest request) {
=======
	public String caldendar(Model model, HttpServletRequest request, ReservationDto reservationDto) throws Exception {
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
>>>>>>> origin/main
		HttpSession session = request.getSession();
		session.setAttribute("location", reservationDto);
		List<ReservationDto> list = reservationService.getSameLocationReservation(reservationDto);
		for(int i=0; i<list.size(); i++) {
			Date dt = dtFormat.parse(list.get(i).getEndDay());
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			cal.add(Calendar.DATE, +1);
			String endDay = dtFormat.format(cal.getTime());
			list.get(i).setEndDay(endDay);
		}
		model.addAttribute("list", list);
		return "lease/calendar";
	}
	
	@ResponseBody
	@PostMapping(value="/reservation")
	public String reservation(@RequestBody Map<String, Object> reserve_date, HttpServletRequest request, Model model) throws Exception  {
		String startDay = (String) reserve_date.get("startStr");
		String endDay = (String) reserve_date.get("end");
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = dtFormat.parse(endDay);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		endDay = dtFormat.format(cal.getTime());
		HttpSession session = request.getSession();
		if(endDay==null && startDay==null) {
			model.addAttribute("errorMessage", "등록 기간을 드래그해주세요.");
			return "lease/calendar";
		} else {
			session.setAttribute("endDay", endDay);
			session.setAttribute("startDay", startDay);
		}
		
		return "success";
	}
	//예약 신청
	@PostMapping(value="/new")
	public String newReservation(@RequestParam("content") String content, HttpServletRequest request, @RequestParam(value = "files", required = false) List<MultipartFile> files, Model model, ReservationDto reservationDto, Principal principal) throws Exception {
		if(files.get(0).isEmpty()) {
			model.addAttribute("errorMessage", "이미지는 필수 입니다.");
			return "lease/calendar";
		}
		Company company = companyService.findByCom(principal.getName());
		company.setApproval("예약신청중");
		companyService.updateCompany(company);
		HttpSession session = request.getSession();
		String endDay = (String) session.getAttribute("endDay");
		String startDay =(String) session.getAttribute("startDay");
		reservationDto = (ReservationDto) session.getAttribute("location");
		reservationDto.setLocation(reservationDto.getLocation());
		reservationDto.setContent(content);
		reservationDto.setStartDay(startDay);
		reservationDto.setEndDay(endDay);
		reservationDto.setCompany(company);
		reservationService.saveReservation(files, reservationDto);
		return "lease/lease";
	}
>>>>>>> origin/main
}
