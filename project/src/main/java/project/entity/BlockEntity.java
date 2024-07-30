package project.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "t_jpa_block")
@NoArgsConstructor
public class BlockEntity {
	
	@Id
	private int blockId;
	
	@Column(nullable = false)
	private String countLine;
	private String html;
	private String tag;
	private String parseContents;
	
	@Column(nullable = true)
	private String imageUrl;
	private String blockStyle;
	
	@Column(nullable = false)
	private int fileNum = 0;
	
	@Column 
	private int noteIdxForQ;
	// ------------- blockProps -------------------
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ParentId")
	private BlockEntity parentBlock;
	
	@Column
	private int hasRefCnt = 0;
	private char blockType = 'n';
	// ------------- RefblockProps -------------------
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "noteIdx")
	private NoteEntity note;
	
	//block -> collect.blockfiles 접근 가능
	@OneToMany(mappedBy="block" ,fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BlockFileEntity> blockFiles;
	
	// ------------- blockRelatedByPageProps -------------------
	
}	
