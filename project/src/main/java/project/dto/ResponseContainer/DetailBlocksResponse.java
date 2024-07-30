package project.dto.ResponseContainer;

import lombok.Data;

@Data
public class DetailBlocksResponse {
	
	private int blockId;

	
	private String countLine;
	private String html;
	private String tag;
	
	private String imageUrl;
	private String blockStyle;
	private String parseContents;
	
}
