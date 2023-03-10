package com.exposition.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.exposition.dto.BoardMainDto;
import com.exposition.dto.TourBoardDto;
import com.exposition.service.FileService;
import com.exposition.service.TourBoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsBoardController {

	private final FileService fileService;
	private final TourBoardService tourBoardService;
	
	//주변관광지 페이지 이동
	@RequestMapping(value="/tour", method= {RequestMethod.GET, RequestMethod.POST})
	public String tourPage(Model model, TourBoardDto tourBoardDto, Optional<Integer> page) {
		
		Pageable pageable = PageRequest.of(page.isPresent()? page.get() : 0 , 6);
		Page<BoardMainDto> tourBoardList = tourBoardService.getBoardMainPage(tourBoardDto, pageable);
		model.addAttribute("tourboards", tourBoardList);
		int nowPage = tourBoardList.getPageable().getPageNumber() + 1 ;
	    int startPage =  Math.max(nowPage - 4, 1);
	    int endPage = Math.min(nowPage+9, tourBoardList.getTotalPages());
	    model.addAttribute("nowPage",nowPage);
	    model.addAttribute("startPage", startPage);
	    model.addAttribute("endPage", endPage);
	    
		return "news/tourboard";
	}
		
	//주변관광지 글 작성 페이지 이동
	@GetMapping(value="/tourwrite")
	public String tourWrite(Model model) {
		model.addAttribute("tourBoardDto", new TourBoardDto());
		return "news/tourboardwrite";
	}
	
	//주변관광지 글 등록
	@PostMapping(value="/toursave")
	public String tourSave(@RequestParam(value = "files", required = false) List<MultipartFile> files, Model model, @Valid TourBoardDto tourBoardDto, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "news/tourboardwrite";
		}
		if(files.get(0).isEmpty() && tourBoardDto.getId() == null) {
			model.addAttribute("errorMessage", "이미지는 필수 입니다.");
			return "news/tourboardwrite";
		}
		try {
			tourBoardService.saveTour(files, tourBoardDto);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "글 작성 중 에러가 발생했습니다.");
			return "news/tourboardwrite";
		}
		return "redirect:/news/tour";
	}
	
	//주변 관광지 상세 페이지 이동
	@GetMapping(value="view/{tourBoardId}")
	public String tourBoardDetail(@PathVariable("tourBoardId") Long tourBoardId, Model model) {
		try {
			TourBoardDto tourBoardDto = tourBoardService.getTourBoardDetail(tourBoardId);
			model.addAttribute("tourBoardDto",tourBoardDto);
		} catch (EntityNotFoundException e) {
			model.addAttribute("errorMessage","존재하지 않는 글 입니다");
			model.addAttribute("tourBoardDto", new TourBoardDto());
			return "news/tourboardwrite";
		}
		return "news/tourboardview";
	}
	
	//주변 관광지 수정 페이지로 이동
	@GetMapping(value="/modify/{id}")
	public String modifyBoard(@PathVariable("id") Long tourBoardId, Model model) {
		try {
			TourBoardDto tourBoardDto = tourBoardService.getTourBoardDetail(tourBoardId);
			model.addAttribute("tourBoardDto",tourBoardDto);
		} catch (EntityNotFoundException e) {
			model.addAttribute("errorMessage","존재하지 않는 글 입니다");
			model.addAttribute("tourBoardDto", new TourBoardDto());
			return "news/tourboardwrite";
		}
		return "news/updatewrite";
	}
	
	//주변 관광지 글 수정 등록
	@PostMapping(value="update/{id}")
	public String updatesucc(TourBoardDto tourBoardDto, Model model, @RequestParam("files") List<MultipartFile> fileList) {
		if(fileList.get(0).isEmpty()) {
			model.addAttribute("errorMessage", "첫번째 이미지는 필수입니다.");
			return "news/updatewrite";
		}
		try {
			tourBoardService.updateTourBoard(tourBoardDto, fileList);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "글 수정 중 에러가 발생하였습니다.");
			return "news/updatewrite";
		}
		
		return "redirect:/news/tour";
	}
	
	//주변 관광지 글 삭제하기(첨부파일이 있을 경우 첨부파일을 먼저 지우고 게시글을 지워야 한다)
	@DeleteMapping(value="delete/{id}")
	public String deleteBoard(@PathVariable("id") Long id) {
		tourBoardService.deleteBoard(id);
		return "redirect:/news/tour";
	}
}
