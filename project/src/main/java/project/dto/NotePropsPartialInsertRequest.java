package project.dto;

import java.time.LocalDateTime;


import lombok.Data;


@Data
public class NotePropsPartialInsertRequest {

	//noteProps
	private String title;
	private String contents;
	private int noteIdx;
	private LocalDateTime createdAt;
	private boolean isUpdated = false;
	
	//Linker && Validation: UserEntity
	private String authorString;	
	
	//Front : formValidation
	//private List<CommentEntity> comments;
}
