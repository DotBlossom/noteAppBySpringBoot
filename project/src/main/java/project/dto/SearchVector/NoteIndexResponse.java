package project.dto.SearchVector;



import lombok.Data;



@Data
public class NoteIndexResponse {
   

    private int noteIdx;
    private int blockId;
    private String parseContents;
    private String tag;
    
}