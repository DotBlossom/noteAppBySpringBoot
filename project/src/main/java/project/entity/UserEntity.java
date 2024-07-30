package project.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;

import jakarta.persistence.Table;
import lombok.Data;



@Table(name = "t_jpa_user")
@Entity
@Data
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int seq;
	
	@Column(unique = true)
	private String username; //id
	private String email;
	private String password;
	
	private String role;
	
	// profile field
	@Column(unique = true, nullable = false)
	private String name;
	
	@Column(nullable= true)
	private String bio;
	private int age;
	private char gender;
	
	// onetoone 연결법? 
	/*
	@OneToOne
	@JoinColumn(name = "profileFileIdx", unique = true)
	private UserProfileFileEntity profileFile; 
	*/
	
	@OneToMany(mappedBy = "author", fetch = FetchType.EAGER, cascade= CascadeType.ALL, orphanRemoval = true) // orphan?
	private List<NoteEntity> notes = new ArrayList<>();
	
	/*
	@OneToMany(mappedBy = "author", fetch = FetchType.EAGER, cascade= CascadeType.ALL, orphanRemoval = true) 
	private List<CommentEntity> comments = new ArrayList<>();
	*/
	//orphan : 회원 삭제 염두.
	//ArrayList constuct?
}
