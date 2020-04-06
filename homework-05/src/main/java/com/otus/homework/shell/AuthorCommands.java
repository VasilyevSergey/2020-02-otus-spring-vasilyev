package com.otus.homework.shell;

import com.otus.homework.exception.DataLoadingException;
import com.otus.homework.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class AuthorCommands {
    private final AuthorService service;

    @ShellMethod(value = "count authors", key = {"ca", "count authors"})
    public String count() {
        return String.format("Количество авторов: %d ", service.count());
    }

    @ShellMethod(value = "insert author", key = {"ia", "insert author"})
    public String insert(String name) {
        try {
            service.insert(name);
            return String.format("Автор '%s' добавлен", name);
        } catch (DataLoadingException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "get author", key = {"ga", "get author"})
    public String getById(Long id) {
        try {
            return service.getById(id).toString();
        } catch (DataLoadingException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "get all authors", key = {"gaa", "get all authors"})
    public String getAll() {
        return service.getAll().toString();
    }

    @ShellMethod(value = "delete author", key = {"da", "delete author"})
    public String delete(Long id) {
        try {
            service.deleteById(id);
            return String.format("Автор с id '%d' удален", id);
        } catch (DataLoadingException e) {
            return e.getMessage();
        }
    }
}
