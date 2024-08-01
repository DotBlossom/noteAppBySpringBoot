package project.dto.ResponseContainer;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import project.entity.NoteIndexEntity;



@Data
public class NoteDetailResponse {

	private String noteTitle;
	private String noteContents;
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	
	//temp
	//private String author;	
	//private List<CommentEntity> comments;
	
	private List<DetailBlocksResponse> blocks;
	private List<NoteIndexEntity> indexes;
}
