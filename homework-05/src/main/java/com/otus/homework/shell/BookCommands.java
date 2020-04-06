package com.otus.homework.shell;

import com.otus.homework.exception.DataLoadingException;
import com.otus.homework.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class BookCommands {
    private final BookService service;

    @ShellMethod(value = "count books", key = {"cb", "count books"})
    public String countBooks() {
        return String.format("Количество книг: %d ", service.count());
    }

    @ShellMethod(value = "insert book", key = {"ib", "insert book"})
    public String insert(String title, Long authorId, Long genreId) {
        try {
            service.insert(title, authorId, genreId);
            return String.format("Книга '%s' добавлена", title);
        } catch (DataLoadingException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "update book", key = {"ub", "update book"})
    public String updateById(Long id, String newTitle, Long newAuthorId, Long newGenreId) {
        try {
            service.updateById(id, newTitle, newAuthorId, newGenreId);
            return String.format("Книга с id = %d обновлена", id);
        } catch (DataLoadingException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "get book", key = {"gb", "get book"})
    public String getById(Long id) {
        try {
            return service.getById(id).toString();
        } catch (DataLoadingException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "get all books", key = {"gab", "get all books"})
    public String getAll() {
        return service.getAll().toString();
    }

    @ShellMethod(value = "delete book", key = {"db", "delete book"})
    public String delete(Long id) {
        try {
            service.deleteById(id);
            return String.format("Книга с id '%d' удалена", id);
        } catch (DataLoadingException e) {
            return e.getMessage();
        }
    }
}
