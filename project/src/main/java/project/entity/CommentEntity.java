package project.entity;

import java.time.LocalDateTime;

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

@Entity
@Table(name = "t_jpa_comment")
@NoArgsConstructor
@Data
public class CommentEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) // fk && pk = pk?
	private int commentIdx;
	
	@Column(nullable = false)
	private String contents;
	private int typeOfFeed;
	private LocalDateTime createdAt;
	

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private UserEntity author;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "noteIdx")
    private NoteEntity note;
}
