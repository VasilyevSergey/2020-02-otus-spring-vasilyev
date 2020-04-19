package com.otus.homework.service;

import com.otus.homework.dao.CommentDao;
import com.otus.homework.domain.Comment;
import com.otus.homework.exception.DataLoadingException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private static final String ERROR_INSERT = "При добавлении комментария '%s' произошла ошибка";
    private static final String COMMENT_NOT_FOUND = "Комментарий с id '%d' не найден";
    private final CommentDao commentDao;
    private final BookService bookService;

    public CommentServiceImpl(CommentDao commentDao,
                              BookService bookService) {
        this.commentDao = commentDao;
        this.bookService = bookService;
    }

    @Override
    public int count() {
        return commentDao.count();
    }

    @Override
    public void add(String commentText, Long bookId, String commentator) throws DataLoadingException {
        Comment comment = new Comment(
                null,
                commentText,
                bookService.getById(bookId),
                ZonedDateTime.now().with(ChronoField.NANO_OF_SECOND, 0),
                commentator);

        try {
            commentDao.add(comment);
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            throw new DataLoadingException(String.format(ERROR_INSERT, commentText), e.getCause());
        }
    }

    @Override
    public void updateById(Long id, String commentText) throws DataLoadingException {
        Comment comment = getById(id);
        comment.setComment(commentText);

        int numberOfRowsAffected = commentDao.update(comment);
        if (numberOfRowsAffected == 0) {
            throw new DataLoadingException(String.format(COMMENT_NOT_FOUND, id));
        }
    }

    @Override
    public Comment getById(Long id) throws DataLoadingException {
        Optional<Comment> comment =  commentDao.getById(id);
        if (comment.isPresent()) {
            return comment.get();
        } else {
            throw new DataLoadingException(String.format(COMMENT_NOT_FOUND, id));
        }
    }

    @Override
    public List<Comment> getByBookId(Long id) throws DataLoadingException {
        return commentDao.getByBook(bookService.getById(id));
    }

    @Override
    public void deleteById(Long id) throws DataLoadingException {
        int numberOfRowsAffected = commentDao.deleteById(id);
        if (numberOfRowsAffected == 0) {
            throw new DataLoadingException(String.format(COMMENT_NOT_FOUND, id));
        }
    }

    @Override
    public List<Comment> getAll() {
        return commentDao.getAll();
    }
}
