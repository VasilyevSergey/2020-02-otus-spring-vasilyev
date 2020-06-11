package com.otus.homework.shell;

import com.otus.homework.domain.Comment;
import com.otus.homework.exception.DataLoadingException;
import com.otus.homework.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class CommentCommands {
    private final CommentService service;

    @ShellMethod(value = "count comments", key = {"cc", "count comments"})
    public String countComments() {
        return String.format("Количество комментариев: %s ", service.count());
    }

    @ShellMethod(value = "add comment", key = {"ac", "add comment"})
    public String add(String commentText, String bookId, String commentator) {
        try {
            service.add(commentText, bookId, commentator);
            return String.format("Комментарий '%s' добавлен", commentText);
        } catch (DataLoadingException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "update comment", key = {"uc", "update comment"})
    public String updateById(String id, String commentText) {
        try {
            service.updateById(id, commentText);
            return String.format("Комментарий с id = %s обновлен", id);
        } catch (DataLoadingException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "get comment", key = {"gc", "get comment"})
    public String getById(String id) {
        try {
            return service.getById(id).toString();
        } catch (DataLoadingException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "get all comments", key = {"gac", "get all comments"})
    public String getAll() {
        StringBuilder commentList = new StringBuilder("Список комментариев:\n");
        for (Comment comment : service.getAll()) {
            commentList.append(String.format(
                    "id = %s, комментарий = %s, комментатор = %s, дата = %s\n",
                    comment.getId(),
                    comment.getComment(),
                    comment.getCommentator(),
                    comment.getDatetime().toString()));
        }
        return commentList.toString();
    }

    @ShellMethod(value = "get comments by book id", key = {"gcbb", "get comments by book id"})
    public String getCommentsByBookId(String bookId) {
        List<Comment> commentListByBookId;
        try {
            commentListByBookId = service.getByBookId(bookId);
        } catch (DataLoadingException e) {
            return e.getMessage();
        }

        StringBuilder commentList = new StringBuilder("Список комментариев:\n");
        for (Comment comment : commentListByBookId) {
            commentList.append(String.format(
                    "id = %s, комментарий = %s, книга = %s, комментатор = %s, дата = %s\n",
                    comment.getId(),
                    comment.getComment(),
                    comment.getBook().getTitle(),
                    comment.getCommentator(),
                    comment.getDatetime().toString()));
        }
        return commentList.toString();
    }

    @ShellMethod(value = "delete comment", key = {"dc", "delete comment"})
    public String delete(String id) {
        try {
            service.deleteById(id);
            return String.format("Комментарий с id '%s' удален", id);
        } catch (DataLoadingException e) {
            return e.getMessage();
        }
    }
}
