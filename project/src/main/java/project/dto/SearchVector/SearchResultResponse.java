package project.dto.SearchVector;

import lombok.Data;
import project.dto.ResponseContainer.DetailBlocksResponse;

@Data
public class SearchResultResponse {
	
	private DetailBlocksResponse Qblock;
	private NoteIndexResponse Iblock;
}
