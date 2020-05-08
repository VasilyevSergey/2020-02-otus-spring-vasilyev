package com.otus.homework.service;

import com.otus.homework.dao.CommentRepository;
import com.otus.homework.domain.Comment;
import com.otus.homework.exception.DataLoadingException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private static final String ERROR_INSERT = "При добавлении комментария '%s' произошла ошибка";
    private static final String COMMENT_NOT_FOUND = "Комментарий с id '%s' не найден";
    private final CommentRepository commentRepository;
    private final BookService bookService;

    public CommentServiceImpl(CommentRepository commentRepository,
                              BookService bookService) {
        this.commentRepository = commentRepository;
        this.bookService = bookService;
    }

    @Override
    public long count() {
        return commentRepository.count();
    }

    @Override
    public void add(String commentText, String bookId, String commentator) throws DataLoadingException {
        Comment comment = new Comment(
                null,
                commentText,
                bookService.getById(bookId),
                LocalDateTime.now().with(ChronoField.NANO_OF_SECOND, 0),
                commentator);

        try {
            commentRepository.save(comment);
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            throw new DataLoadingException(String.format(ERROR_INSERT, commentText), e.getCause());
        }
    }

    @Override
    public void updateById(String id, String commentText) throws DataLoadingException {
        Comment comment = getById(id);
        comment.setComment(commentText);
        if (!commentRepository.existsById(id)) {
            throw new DataLoadingException(String.format(COMMENT_NOT_FOUND, id));
        }
        commentRepository.save(comment);
    }

    @Override
    public Comment getById(String id) throws DataLoadingException {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            return comment.get();
        } else {
            throw new DataLoadingException(String.format(COMMENT_NOT_FOUND, id));
        }
    }

    @Override
    public List<Comment> getByBookId(String id) throws DataLoadingException {
        return commentRepository.findAllByBook(bookService.getById(id));
    }

    @Override
    public void deleteById(String id) throws DataLoadingException {
        if (!commentRepository.existsById(id)) {
            throw new DataLoadingException(String.format(COMMENT_NOT_FOUND, id));
        }
        commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }
}
