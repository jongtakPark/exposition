package com.exposition.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.exposition.dto.AnnouncementDto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Table(name="announcement")
@RequiredArgsConstructor
public class Announcement extends BaseEntity {
	
		// 글번호
			@Id
			@Column(name="announcement_id")
			@GeneratedValue(strategy = GenerationType.AUTO)
			private Long id;
			
		// 제목
			@NotEmpty(message = "제목을 적어주세요.")
			private String title;

		// 내용
			@Column(length = 2000)
			private String content; 
			

			@ManyToOne
			@JoinColumn(name = "admin_id")
			private Member admin;
			
			public static Announcement createannouncement(AnnouncementDto announcementDto) {
				Announcement announcement = new Announcement();
				announcement.setTitle(announcementDto.getTitle());
				announcement.setContent(announcementDto.getContent());
				announcement.setId(announcementDto.getId());
				return announcement;
			}

}
