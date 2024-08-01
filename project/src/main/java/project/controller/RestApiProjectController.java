package project.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import project.dto.InsertRefBlockRequest;
import project.dto.NoteJoinUserDto;
import project.dto.NoteListResponse;
import project.dto.NotePropsPartialInsertRequest;
import project.dto.ResponseContainer.DetailBlocksResponse;
import project.dto.ResponseContainer.NoteDetailResponse;
import project.dto.projection.BlockDetailProjection;
import project.dto.projection.BlockPageDto;
import project.dto.updater.NotePropsUpdate;
import project.entity.BlockEntity;
import project.entity.BlockFileEntity;
import project.entity.NoteEntity;
import project.entity.NoteIndexEntity;
import project.service.BlockService;
import project.service.NoteService;

@Slf4j
@RestController
@RequestMapping("/jpa")
public class RestApiProjectController {
	
	@Autowired
	private NoteService noteService;
	@Autowired
	private BlockService blockService;
	
	@GetMapping("/notes")
	public ResponseEntity<Object> openNoteList() throws Exception{
		List<NoteEntity> querySet = noteService.selectNoteList();
		if (querySet == null) {
			Map<String, String> result = new HashMap<>();
			result.put("code", HttpStatus.NOT_FOUND.toString());
			result.put("message" , "노트 목록 조회 실패");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
		} else {
		List<NoteListResponse> noteListResponse = new ArrayList<>();
		
		querySet.forEach(query -> {
			noteListResponse.add(new ModelMapper().map(query, NoteListResponse.class));
		});
		
		return ResponseEntity.status(HttpStatus.OK).body(noteListResponse);
		}	
	}

	@GetMapping("/notes/{noteIdx}")
	public ResponseEntity<Object> openNoteDetail(@PathVariable("noteIdx") Integer noteIdx) throws Exception {
		
		Map<String, String> result = new HashMap<>();
		
		
		NoteEntity querySet = noteService.selectNoteDetailWithGraph(noteIdx, "note-with-blocks");

		
		if (querySet == null) {
			result.put("code", HttpStatus.NOT_FOUND.toString());
			result.put("message" , "Fetch Failed");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
		}
		
		NoteDetailResponse res = new ModelMapper().map(querySet, NoteDetailResponse.class);
		
		List<DetailBlocksResponse> blocks = new ArrayList<>();
		List<NoteIndexEntity> indexes = new ArrayList<>();
		querySet.getBlocks().forEach(qs -> {
			blocks.add(new ModelMapper().map(qs, DetailBlocksResponse.class));
		});
		querySet.getIndexes().forEach(qs -> {
			indexes.add(new ModelMapper().map(qs, NoteIndexEntity.class));
		});
		
		
		res.setIndexes(indexes);
		res.setBlocks(blocks);
		
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
	
	//block-> map.swithch type is 
	// 일단 사진 한장 먼저 테스트.x
	// 아니면 그냥 파일로 퉁치든가. o
	// formdata에선 처리를 해야겠지. 그리고 뭐 url만 일단 여기에 담아도 되겠찌..
	// react에서는 blocks에 url 동시에 줘야함.
	// login tokenValid 거치고, 리액트 insert페이지에 왔으니, 여기 백엔드는 믿어야지 .. 
	@PostMapping("/notes/write")
	public ResponseEntity<Object> insertNote (
			@RequestPart(value="blocks", required=true) List<BlockEntity> blocks,
			@RequestPart(value="noteProps", required=true) NotePropsPartialInsertRequest noteProps,
			@RequestPart(value="files", required=false) MultipartFile[] files) throws Exception{
				
			Map<String, String> result = new HashMap<>();
			
			if(blocks == null) {
				result.put("code", HttpStatus.NOT_FOUND.toString());
				result.put("message" , "Fetch Failed");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
				
			} else {
				String authorName = noteProps.getAuthorString();
				NoteEntity note = new ModelMapper().map(noteProps, NoteEntity.class);
				// userProps had been made already, only set to FK
				
				noteService.insertNote(blocks, note, files, authorName);
				// if not -> 500 arise
				
				result.put("code", HttpStatus.OK.toString());
				result.put("message" , "ok");
				return ResponseEntity.status(HttpStatus.OK).body(result);
			}
		
	}
	
	
	@GetMapping("/ref/{blockId}")
	public BlockDetailProjection getBlockToRef(@PathVariable("blockId") int blockId) {
		
		return blockService.selectBlock(blockId);
	}
	
	
	// 프론트상에서 존재하는 noteIdx에서 호출하는 것.
	// blockId/Idx -> QueryVector / noteIdx : destinationResponseQ
	@GetMapping("/notes/file/{noteIdx}/{blockId}/{idx}")
	public void downloadBoardFile(@PathVariable("idx") int idx, @PathVariable("blockId") int blockId, @PathVariable("noteIdx") int noteIdx, 
			HttpServletResponse response) throws Exception {
		// validNote == blockSet
		
		BlockFileEntity blockFileEntity = blockService.selectBlockFileInfo(idx, blockId);
		if (ObjectUtils.isEmpty(blockFileEntity)) {
			return;
		}
		
		Path path = Paths.get(blockFileEntity.getStoredFilePath());
	
		byte[] file = Files.readAllBytes(path); 
		
		response.setContentType("application/octet-stream");
		response.setContentLength(file.length);
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(blockFileEntity.getOriginalFileName(), "UTF-8") + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		response.getOutputStream().write(file);
		response.getOutputStream().flush();
		response.getOutputStream().close();

	}
	// InPage(in fetchAction) -> setStatus -> in ModalComponentView(dataPut.statusRender)
	@GetMapping("/ref/search")
	public Page<BlockPageDto> SearchParseContents(
			@RequestParam("kw") String kw,
			@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		Page<BlockPageDto> qs = blockService.searchParseContents(kw, pageable);
		
		return qs;
	}
	
	// service call -> getTargetBlocks -> postRefBlock(dumpTarget)
	// post : data passing to serverProps (dtos..map..)
	// imguri is true? 
	// wired -> go to graph.
	@PostMapping("ref/insertRef")
	public ResponseEntity<Object> insertRefBlock(@RequestBody InsertRefBlockRequest req) throws Exception{
		
		Map<String, String> result = new HashMap<>();
		BlockDetailProjection ref = blockService.selectBlock(req.getRefBlockId());
		// no realfile, just StoredurlMapper
		if(ref == null) {
			result.put("code", HttpStatus.NOT_FOUND.toString());
			result.put("message" , "Fetch Failed");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
			
		} else {
			
			//selectBlockEntity 일단 그냥 가져와서 해보고 안되면 entity.. 
			// block 가지고 바로 insertRef.. geneJoinkey해서 
			
			blockService.insertRefBlock(req.getWiredBlockId(),req.getWiredNoteIdx(), req.getHasRefCnt(), ref);
			
			result.put("code", HttpStatus.OK.toString());
			result.put("message" , "ok");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
			
			
		}
	}
	
	@GetMapping("notes/{username}")
	public List<NoteJoinUserDto> getNoteByUsername(@PathVariable("username") String username) {
		return noteService.selectAllNoteInfoProjectionByUserId(username);
		
	}
	
	// file은 insert와 동일.
	// delete는 note 삭제하면 다날아감 ㅋㅋ 

	@PutMapping("update/{noteIdx}")
	public void updateNoteProps(@RequestPart(value="blocks", required=true) List<BlockEntity> blocks,
			@RequestPart(value="noteProps", required=true) NotePropsUpdate noteProps,
			@RequestPart(value="files", required=false) MultipartFile[] files,
			@PathVariable("noteIdx") int noteIdx) throws Exception{
		
		noteService.updateNote(blocks, noteProps, files, noteIdx);
	
		
	}
	@DeleteMapping("image/{blockId}/{imageUrl}")
	public void deleteBlockFile(@PathVariable("blockId") int blockId, @PathVariable("imageUrl") String imageUrl) throws IOException {
		blockService.deleteFile(imageUrl);
	}
}
