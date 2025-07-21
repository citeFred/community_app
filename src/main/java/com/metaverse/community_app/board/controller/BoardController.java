package com.metaverse.community_app.board.controller;

import com.metaverse.community_app.board.dto.BoardRequestDto;
import com.metaverse.community_app.board.dto.BoardResponseDto;
import com.metaverse.community_app.board.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }
    
    @PostMapping("/boards")
    public ResponseEntity<BoardResponseDto> createBoard(@RequestBody BoardRequestDto boardRequestDto) {
        BoardResponseDto boardResponseDto = boardService.createBoard(boardRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(boardResponseDto);
    }

    @GetMapping("/boards")
    public ResponseEntity<List<BoardResponseDto>> getBoards() {
        List<BoardResponseDto> boardResponseDtoList = boardService.getBoards();
        return ResponseEntity.ok(boardResponseDtoList);
    }

    @GetMapping("/boards/{id}")
    public ResponseEntity<BoardResponseDto> getBoardsById(@PathVariable Long id) {
        BoardResponseDto boardResponseDto = boardService.getBoardById(id);
        return ResponseEntity.ok(boardResponseDto);
    }

    @PutMapping("/boards/{id}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto) { // 반환 타입 변경
        BoardResponseDto updatedBoard = boardService.updateBoard(id, boardRequestDto);
        return ResponseEntity.ok(updatedBoard);
    }

    @DeleteMapping("/boards/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }
}
