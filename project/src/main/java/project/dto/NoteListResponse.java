package project.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NoteListResponse {
	
	private int noteIdx;
	private String title;
	private LocalDateTime createdAt;
}
