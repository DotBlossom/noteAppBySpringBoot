package project.dto.ResponseContainer;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DetailNotePropsResponse {
	private String title;
	private String contents;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String AuthorName;
}
