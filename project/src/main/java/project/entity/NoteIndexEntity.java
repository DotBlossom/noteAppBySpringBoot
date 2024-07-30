package project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "t_jpa_NoteIndex")
public class NoteIndexEntity {
   
	
	//service => if insert : rootNode set 
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int blockIdOfIndex;
	
	
	// rootBlockId..
	//Index -> 직접 순서별 지정. service에서 과거값 참조해서 매핑. 계쏙 순서가 유지될거면, 
	//rootblockid가 필요한가? 있음좋지.. 바뀌면 다시 mother찾고 렌더
	// property : id + hirerchy -> render flooredindex
	@Column
	private int rootBlockRocation; 
	@Column
	private int IndexBlockNoteIdx;
	@Column
	private int IndexBlockIdx;

	@Column
	private String tag;
	private String parseContents;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "noteIdx", nullable = false)
	private NoteEntity note;
	// hirerchy -> h1 h2 h2 h1 h2 h3 :  
	// how to render componento of NoteIndex?

	//noteidx && cnt join
	
}
