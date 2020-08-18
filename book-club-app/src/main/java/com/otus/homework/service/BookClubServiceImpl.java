package com.otus.homework.service;

import com.otus.homework.domain.BookClub;
import com.otus.homework.domain.User;
import com.otus.homework.exception.DataLoadingException;
import com.otus.homework.repository.BookClubRepository;
import com.otus.homework.repository.UserRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.otus.homework.security.AuthoritiesConstants.ADMIN;
import static com.otus.homework.security.AuthoritiesConstants.USER;

@Service
public class BookClubServiceImpl implements BookClubService {
    private static final String ERROR_INSERT = "При создании книжного клуба '%s' произошла ошибка";
    private static final String BOOK_CLUB_NOT_FOUND = "Книгжный клуб с id '%s' не найдена";
    private static final String CANT_DELETE_BOOK_CLUB_BECAUSE_NOT_ADMIN =
            "Вы не можете удалить клуб '%s', т.к. не являетесь его оранизатором.";
    private static final String YOU_HAVE_ALREADY_JOINED_THE_CLUB = "Вы уже вступили в клуб '%s'.";

    private final BookClubRepository bookClubRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public BookClubServiceImpl(BookClubRepository bookClubRepository,
                               UserRepository userRepository,
                               UserService userService) {
        this.bookClubRepository = bookClubRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public BookClub create(String name,
                           String mainTheme) throws DataLoadingException {
        User admin = userService.getCurrentUser();
        BookClub bookClub = new BookClub(name, admin, mainTheme, List.of(admin));

        try {
            return bookClubRepository.save(bookClub);
        } catch (Exception e) {
            throw new DataLoadingException(String.format(ERROR_INSERT, name), e.getCause());
        }
    }

    @Secured({USER, ADMIN})
    @Override
    public BookClub getById(String id) throws DataLoadingException {
        Optional<BookClub> book = bookClubRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new DataLoadingException(String.format(BOOK_CLUB_NOT_FOUND, id));
        }
    }

    @Secured({USER, ADMIN})
    @Override
    public void deleteById(String id) throws DataLoadingException {
        BookClub bookClub = getById(id);
        if (bookClub.getAdmin().equals(userService.getCurrentUser())) {
            bookClubRepository.deleteById(id);
        } else {
            throw new DataLoadingException(String.format(CANT_DELETE_BOOK_CLUB_BECAUSE_NOT_ADMIN, bookClub.getName()));
        }
    }

    @Secured({USER, ADMIN})
    @Override
    public List<BookClub> getAll() {
        return bookClubRepository.findAll();
    }

    @Secured({USER, ADMIN})
    @Override
    public List<BookClub> findAllByNameContaining(String fragment) {
        return bookClubRepository.findAllByNameContaining(fragment);
    }

    @Secured({USER, ADMIN})
    @Override
    public BookClub updateById(String id, String name, String mainTheme) throws DataLoadingException {
        BookClub bookClub = getById(id);
        bookClub.setName(name);
        bookClub.setMainTheme(mainTheme);
        return bookClubRepository.save(bookClub);
    }

    @Secured({USER, ADMIN})
    @Override
    public BookClub joinToClub(String id) throws DataLoadingException {
        BookClub bookClub = getById(id);
        User currentUser = userService.getCurrentUser();

        List<User> participantList = bookClub.getParticipantList();
        if (!participantList.contains(currentUser)) {
            participantList.add(currentUser);
        } else {
            throw new DataLoadingException(String.format(YOU_HAVE_ALREADY_JOINED_THE_CLUB, bookClub.getName()));
        }
        return bookClubRepository.save(bookClub);
    }
}
