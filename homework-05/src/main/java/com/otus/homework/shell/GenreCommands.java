package com.otus.homework.shell;

import com.otus.homework.domain.Genre;
import com.otus.homework.exception.DataLoadingException;
import com.otus.homework.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class GenreCommands {
    private final GenreService service;

    @ShellMethod(value = "count genres", key = {"cg", "count genres"})
    public String count() {
        return String.format("Количество жарнов: %d ", service.count());
    }

    @ShellMethod(value = "insert genre", key = {"ig", "insert genre"})
    public String insert(String name) {
        try {
            service.insert(name);
            return String.format("Жанр '%s' добавлен", name);
        } catch (DataLoadingException e) {
            return e.getMessage();
        }
    }


    @ShellMethod(value = "get genre", key = {"gg", "get genre"})
    public String getById(Long id) {
        try {
            return service.getById(id).toString();
        } catch (DataLoadingException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "get all genres", key = {"gag", "get all genres"})
    public String getAll() {

        StringBuilder genreList = new StringBuilder("Список жанров:\n");
        for (Genre genre : service.getAll()) {
            genreList.append(String.format("id = %d, название = %s\n", genre.getId(), genre.getName()));
        }
        return genreList.toString();
    }
}
