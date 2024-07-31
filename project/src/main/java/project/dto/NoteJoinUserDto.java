package project.dto;

public class NoteJoinUserDto {
	private int noteIdx;
	private String noteContents;
	private String noteTitle;
	
	public NoteJoinUserDto(int noteIdx,  String noteContents,  String noteTitle) {
		this.noteIdx = noteIdx;
		this.noteContents = noteContents;
		this.noteTitle = noteTitle;
	}
	
	public int getNoteIdx() {
		return this.noteIdx;
	}
	public String getNoteContents() {
		return this.noteContents;
		
	};
	public String getNoteTitle() {
		return this.noteTitle;
	};
}
