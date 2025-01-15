package org.oop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.oop.api.IArticleService;
import org.oop.api.ICommentService;
import org.oop.api.IAuthService;
import org.oop.api.IUserService;
import org.oop.di.Injector;
import org.oop.model.Article;
import org.oop.model.Comment;
import org.oop.model.Role;
import org.oop.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentIntegrationTest {

    private IArticleService articleService;
    private ICommentService commentService;
    private IAuthService authService;
    private IUserService userService;

    @BeforeEach
    void setUp() {
        // Инициализация сервисов через Injector
        articleService = Injector.getInstance().getService(IArticleService.class);
        commentService = Injector.getInstance().getService(ICommentService.class);
        authService = Injector.getInstance().getService(IAuthService.class);
        userService = Injector.getInstance().getService(IUserService.class);
    }

    @Test
    void testCommentFunctionality() {
        // 1. Регистрация пользователя
        userService.register("testuser", "password123", "testuser@example.com", Role.USER);

        // 2. Авторизация пользователя
        authService.login("testuser", "password123");

        // 3. Создание статьи
        Article article = articleService.createArticle("Тестовая статья", "Это тестовая статья.");
        assertThat(article).isNotNull();
        assertThat(article.getId()).isPositive();

        // 4. Добавление комментария
        long userId = authService.getCurrentUserId();
        Comment comment = commentService.createComment(
                article.getId(),
                (int) userId,
                "Это тестовый комментарий."
        );
        assertThat(comment).isNotNull();
        assertThat(comment.getId()).isPositive();
        assertThat(comment.getCommentText()).isEqualTo("Это тестовый комментарий.");

        // 5. Получение комментариев для статьи
        List<Comment> comments = commentService.getCommentsByArticleId(article.getId());
        assertThat(comments).hasSize(1);
        assertThat(comments.get(0).getCommentText()).isEqualTo("Это тестовый комментарий.");

        // 6. Проверка, что комментарий принадлежит статье и пользователю
        assertThat(comments.get(0).getArticleId()).isEqualTo(article.getId());
        assertThat(comments.get(0).getUserId()).isEqualTo((int) userId);
    }
}