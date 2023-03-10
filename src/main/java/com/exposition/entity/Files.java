package com.exposition.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.exposition.dto.FileDto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Table(name="files")
@RequiredArgsConstructor
public class Files extends BaseEntity {

	@Id
	@Column(name="files_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//처음 파일 이름을 바꾼 이름
	private String img;

	//처음 파일 이름
	private String oriImg;
	
	//썸네일
	private String thumbnail;
	
	//저장위치
	private String savePath;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="tourboard_id")
	private TourBoard tourboard;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="freeboard_id")
	private FreeBoard freeBoard;
	
	public static Files createFile(FileDto fileDto) {
		Files file = new Files();
		file.setId(fileDto.getId());
		file.setImg(fileDto.getImg());
		file.setOriImg(fileDto.getOriImg());
		file.setThumbnail(fileDto.getThumbnail());
		file.setSavePath(fileDto.getSavePath());
		return file;
	}
	
	public void updateFile(String img, String oriImg, String savePath) {
		this.img = img;
		this.oriImg = oriImg;
		this.savePath = savePath;
	}


	
}
