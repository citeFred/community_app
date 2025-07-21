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
        Board board = new Board(
                boardRequestDto.getTitle()
        );
        Board savedBoard = boardRepository.save(board);
        BoardResponseDto boardResponseDto = new BoardResponseDto(savedBoard);
        return boardResponseDto;
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getBoards() {
        List<BoardResponseDto> boardResponseDtoList = boardRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(BoardResponseDto::new)
                .toList();
        return boardResponseDtoList;
    }

    @Transactional(readOnly = true)
    public BoardResponseDto getBoardById(Long id) {
        Board board = findBoard(id);
        return new BoardResponseDto(board);
    }

    @Transactional
    public BoardResponseDto updateBoard(Long id, BoardRequestDto boardRequestDto) {
        Board board = findBoard(id);
        board.update(
                boardRequestDto.getTitle()
        );
        return new BoardResponseDto(board);
    }

    @Transactional
    public void deleteBoard(Long id) {
        Board board = findBoard(id);
        boardRepository.delete(board);
    }

    private Board findBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시판은 존재하지 않습니다.")
        );
    }
}
