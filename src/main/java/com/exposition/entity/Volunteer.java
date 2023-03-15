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

import com.exposition.dto.VolunteerDto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Table(name="volunteer")
@RequiredArgsConstructor
public class Volunteer extends BaseEntity {
	
	// 글번호
		@Id
		@Column(name="volunteer_id")
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long id;
		
		// 제목
		@NotEmpty(message = "제목을 적어주세요.")
		private String title;

		// 내용
		@Column(length = 2000)
		private String content; 
		

		@ManyToOne
		@JoinColumn(name = "member_id")
		private Member member;
		
		public static Volunteer createvolunteer(VolunteerDto volunteerDto) {
			Volunteer volunteer = new Volunteer();
			volunteer.setTitle(volunteerDto.getTitle());
			volunteer.setContent(volunteerDto.getContent());
			volunteer.setId(volunteerDto.getId());
			return volunteer;
		}

}
