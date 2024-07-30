package project.dto;

import lombok.Data;

@Data
public class InsertRefBlockRequest {
	
	private int refBlockId;
	private int refNoteIdx;
	
	//graphContext Mapping
	private int wiredBlockId;
	private int wiredNoteIdx;
}
