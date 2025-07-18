package com.metaverse.communiy_app.board.service;

import com.metaverse.communiy_app.board.domain.Board;
import com.metaverse.communiy_app.board.dto.BoardRequestDto;
import com.metaverse.communiy_app.board.dto.BoardResponseDto;
import com.metaverse.communiy_app.board.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto) {
        Board board = new Board(boardRequestDto);
        Board savedBoard = boardRepository.save(board);
        BoardResponseDto boardResponseDto = new BoardResponseDto(savedBoard);
        return boardResponseDto;
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getBoards() {
        List<BoardResponseDto> responseList = boardRepository.findAllByOrderByCreatedAtDesc().stream().map(BoardResponseDto::new).toList();
        return responseList;
    }

    @Transactional
    public Long updateBoard(Long id, BoardRequestDto boardRequestDto) {
        Board board = findBoard(id);
        board.update(boardRequestDto);
        return id;
    }

    @Transactional
    public Long deleteBoard(Long id) {
        Board board = findBoard(id);
        boardRepository.delete(board);
        return id;
    }

    private Board findBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글은 존재하지 않습니다.")
        );
    }
}
