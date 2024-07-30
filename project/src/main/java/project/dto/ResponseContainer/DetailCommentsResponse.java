package project.dto.ResponseContainer;

import java.time.LocalDateTime;

import lombok.Data;
@Data

public class DetailCommentsResponse {
	private String contents;
	private int typeOfFeed;
	private LocalDateTime createdAt;
}
