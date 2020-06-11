package com.otus.homework.shell;

import com.otus.homework.domain.Book;
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
        return String.format("Количество книг: %s ", service.count());
    }

    @ShellMethod(value = "insert book", key = {"ib", "insert book"})
    public String insert(String title, String authorId, String genreId) {
        try {
            service.insert(title, authorId, genreId);
            return String.format("Книга '%s' добавлена", title);
        } catch (DataLoadingException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "update book", key = {"ub", "update book"})
    public String updateById(String id, String newTitle, String newAuthorId, String newGenreId) {
        try {
            service.updateById(id, newTitle, newAuthorId, newGenreId);
            return String.format("Книга с id = %s обновлена", id);
        } catch (DataLoadingException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "get book", key = {"gb", "get book"})
    public String getById(String id) {
        try {
            return service.getById(id).toString();
        } catch (DataLoadingException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "get all books", key = {"gab", "get all books"})
    public String getAll() {
        StringBuilder bookList = new StringBuilder("Список книг:\n");
        for (Book book : service.getAll()) {
            bookList.append(String.format(
                    "id = %s, название = %s, автор = %s, жанр = %s\n",
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor().getName(),
                    book.getGenre().getName()));
        }
        return bookList.toString();
    }

    @ShellMethod(value = "delete book", key = {"db", "delete book"})
    public String delete(String id) {
        try {
            service.deleteById(id);
            return String.format("Книга с id '%s' удалена", id);
        } catch (DataLoadingException e) {
            return e.getMessage();
        }
    }
}
