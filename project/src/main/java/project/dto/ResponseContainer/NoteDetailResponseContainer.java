package project.dto.ResponseContainer;

import java.util.List;

import lombok.Data;

@Data
public class NoteDetailResponseContainer {
	DetailNotePropsResponse note;
	
	List<DetailCommentsResponse> comments;
	
	//imgUrlMake.
	List<DetailBlocksResponse> blocks;

}
