package com.metaverse.community_app.board.controller;

import com.metaverse.community_app.board.dto.BoardRequestDto;
import com.metaverse.community_app.board.dto.BoardResponseDto;
import com.metaverse.community_app.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PreAuthorize("hasRole('ADMIN')")
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
    public ResponseEntity<BoardResponseDto> getBoardById(@PathVariable Long id) {
        BoardResponseDto boardResponseDto = boardService.getBoardById(id);
        return ResponseEntity.ok(boardResponseDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/boards/{id}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto) {
        BoardResponseDto updatedBoard = boardService.updateBoard(id, boardRequestDto);
        return ResponseEntity.ok(updatedBoard);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/boards/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }
}