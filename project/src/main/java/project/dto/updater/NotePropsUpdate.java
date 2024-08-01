package project.dto.updater;

import lombok.Data;

@Data
public class NotePropsUpdate {
	private String noteTitle;
	private String noteContents;
	private boolean isUpdated;
	
}
