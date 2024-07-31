package project.dto.projection;

public interface NoteInfoProjection {
	int getNoteIdx();
	String getNoteContents();
	String getNoteTitle();
	
	// link href..

	default String getNoteIdAndNoteContentsAndNoteTitle() {
		return getNoteIdx() + " " + getNoteContents()  + " " + getNoteTitle();
	}
	
}
