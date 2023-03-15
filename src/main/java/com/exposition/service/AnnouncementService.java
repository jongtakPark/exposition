package com.exposition.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exposition.entity.Announcement;
import com.exposition.repository.AnnouncementRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AnnouncementService {
	
	private final AnnouncementRepository announcementRepository;
	
			//게시판 글 작성
			public Announcement saveAnnouncement(Announcement announcement) {
				return announcementRepository.save(announcement);
			}
			
			//게시판 리스트 출력(페이징)
			public List announcement(){
				return announcementRepository.findAll();
			}
			
		
			//게시판 상세보기 출력
			public Optional<Announcement> findAnnouncement(Long id) {
				return announcementRepository.findById(id);

			}
			//게시글 수정하기
			public Announcement updateAnnouncement(Long id) {
				return announcementRepository.findById(id).get();

			
			}


}
