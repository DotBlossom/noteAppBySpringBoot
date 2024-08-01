package project.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

// note blocks - block - blockfile
@NamedEntityGraphs({
	@NamedEntityGraph(
		name = "note-with-blocks",
		attributeNodes = {
			@NamedAttributeNode("blocks"),
			@NamedAttributeNode("indexes")
		}
		
	),
	
	@NamedEntityGraph(
		name = "note-with-blocks-and-blockFiles",
		attributeNodes = {
			@NamedAttributeNode(value = "blocks", subgraph = "block-files")
		},
		
		subgraphs = {@NamedSubgraph(
			name = "block-files",
			attributeNodes = {
				@NamedAttributeNode("blockFiles")
			}
				
		)}
	)

})


@Entity
@Table(name = "t_jpa_note")
@NoArgsConstructor
@Data
public class NoteEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int noteIdx;
	
	@Column(nullable = true)
	private String noteTitle;
	private String noteContents;
	
	
	@Column(nullable = true)
	private LocalDateTime createdAt;
	private boolean isUpdated = false;
	
	@Column(nullable = true)
	private LocalDateTime updatedAt;
	
	// inputDTO(String) -> Controller(Mapping Real Entity)
	// outPutDTO(String or UserProfilePropsDTO)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "username")
	private UserEntity author;	

	
	
	// Lazy GET FETCH
	/*
	@OneToMany(mappedBy="note" ,fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CommentEntity> comments = new ArrayList<>();
	*/
	
	
	@OneToMany(mappedBy="note", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<NoteIndexEntity> indexes = new ArrayList<>();
	
	
	// EntityGraph oneQueryJoin
	@OneToMany(mappedBy="note",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BlockEntity> blocks = new ArrayList<>();

}
