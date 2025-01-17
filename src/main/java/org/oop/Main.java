package org.oop;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scanner;
    static boolean isLoggedIn = false;
    static boolean isAdministrator = false;
    static long loggedInUserId = 0;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        D database = new D();
        database.initializeDatabase();
        String chosenOption;

        while (true) {
            if (!isLoggedIn) {
                System.out.println("Главное меню:");
                System.out.println("1. Авторизоваться");
                System.out.println("2. Зарегистрироваться");
                System.out.println("3. Выйти");
                System.out.print("Выберите опцию: ");
                chosenOption = scanner.nextLine();
                switch (chosenOption) {
                    case "1":
                        login(database);
                        break;
                    case "2":
                        register(database);
                        break;
                    case "3":
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Неверная опция.");
                        break;
                }
            } else {
                System.out.println("Меню действий:");
                System.out.println("1. Разлогиниться");
                System.out.println("2. Создать статью");
                System.out.println("3. Посмотреть все статьи");
                System.out.println("4. Управление пользователем");
                System.out.println("5. Добавить пользователя");
                System.out.println("6. Удалить пользователя");
                System.out.println("7. Изменить пароль пользователя");
                System.out.println("8. Посмотреть всех пользователей");
                System.out.println("9. Добавить статью");
                System.out.println("10. Посмотреть все статьи");
                System.out.println("11. Удалить статью");
                System.out.println("12. Искать статью по заголовку");
                System.out.println("13. Добавить комментарий");
                System.out.println("14. Посмотреть комментарии");
                System.out.println("15. Удалить комментарий");
                System.out.println("16. Выйти");
                System.out.print("Выберите опцию: ");
                chosenOption = scanner.nextLine();
                switch (chosenOption) {
                    case "1":
                        logout();
                        break;
                    case "2":
                        createArticle(database);
                        break;
                    case "3":
                        viewAllArticles(database);
                        break;
                    case "4":
                        manageUsers(database);
                        break;
                    case "5":
                        addUser(database);
                        break;
                    case "6":
                        deleteUser(database);
                        break;
                    case "7":
                        changeUserPassword(database);
                        break;
                    case "8":
                        listAllUsers(database);
                        break;
                    case "9":
                        addArticle(database);
                        break;
                    case "10":
                        listAllArticles(database);
                        break;
                    case "11":
                        deleteArticle(database);
                        break;
                    case "12":
                        searchArticlesByTitle(database);
                        break;
                    case "13":
                        addComment(database);
                        break;
                    case "14":
                        viewComments(database);
                        break;
                    case "15":
                        deleteComment(database);
                        break;
                    case "16":
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Неверная опция.");
                        break;
                }
            }
        }
    }

    static void printMenu(String title, List<String> options) {
        System.out.println(title);
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
    }

    static int getChoice(List<String> options) {
        while (true) {
            try {
                System.out.print("Введите опцию: ");
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= options.size()) {
                    return choice;
                } else {
                    System.out.println("Неверная опция. Попробуйте снова.");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Неверный ввод. Попробуйте снова.");
            }
        }
    }

    static void login(D database) {
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        User user = database.gubu(username);

        if (user != null && BCrypt.checkpw(password, user.password)) {
            isLoggedIn = true;
            loggedInUserId = user.id;
            isAdministrator = user.role == Role.ADMIN;
            System.out.println("Успешный вход в систему.");
        } else {
            System.out.println("Неверные учетные данные.");
        }
    }

    static void logout() {
        isLoggedIn = false;
        loggedInUserId = 0;
        isAdministrator = false;
        System.out.println("Вы вышли из системы.");
    }

    static void register(D database) {
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        Role role = Role.USER;

        User newUser = new User(0, username, password, email, role);
        if (database.cu(newUser) != null) {
            System.out.println("Регистрация прошла успешно.");
        } else {
            System.out.println("Ошибка при регистрации.");
        }
    }

    static void createArticle(D database) {
        System.out.print("Введите заголовок статьи: ");
        String title = scanner.nextLine();
        System.out.print("Введите содержимое статьи: ");
        String content = scanner.nextLine();

        Article article = new Article(0L, title, content, loggedInUserId);
        if (database.ca(article) != null) {
            System.out.println("Статья успешно создана.");
        } else {
            System.out.println("Ошибка при создании статьи.");
        }
    }

    static void viewAllArticles(D database) {
        List<Article> articles = database.ga();
        if (articles.isEmpty()) {
            System.out.println("Статьи не найдены.");
        } else {
            for (Article article : articles) {
                System.out.println("ID: " + article.id + ", Заголовок: " + article.title);
            }
        }
    }

    static void addUser(D database) {
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        System.out.print("Введите email: ");
        String email = scanner.nextLine();

        User newUser = new User(0, username, password, email, Role.USER);
        if (database.cu(newUser) != null) {
            System.out.println("Пользователь успешно добавлен.");
        } else {
            System.out.println("Ошибка при добавлении пользователя.");
        }
    }

    static void deleteUser(D database) {
        System.out.print("Введите ID пользователя для удаления: ");
        int userId = Integer.parseInt(scanner.nextLine());
        if (database.du(userId)) {
            System.out.println("Пользователь успешно удален.");
        } else {
            System.out.println("Ошибка при удалении пользователя.");
        }
    }

    static void changeUserPassword(D database) {
        System.out.print("Введите ID пользователя: ");
        int userId = Integer.parseInt(scanner.nextLine());
        System.out.print("Введите новый пароль: ");
        String newPassword = scanner.nextLine();
        if (database.cp(userId, newPassword)) {
            System.out.println("Пароль успешно изменен.");
        } else {
            System.out.println("Ошибка при изменении пароля.");
        }
    }

    static void listAllUsers(D database) {
        List<User> users = database.gau();
        if (users.isEmpty()) {
            System.out.println("Пользователи не найдены.");
        } else {
            for (User user : users) {
                System.out.println("ID: " + user.id + ", Имя пользователя: " + user.username);
            }
        }
    }

    static void addArticle(D database) {
        System.out.print("Введите заголовок статьи: ");
        String title = scanner.nextLine();
        System.out.print("Введите содержимое статьи: ");
        String content = scanner.nextLine();

        Article article = new Article(0L, title, content, loggedInUserId);
        if (database.ca(article) != null) {
            System.out.println("Статья успешно добавлена.");
        } else {
            System.out.println("Ошибка при добавлении статьи.");
        }
    }

    static void listAllArticles(D database) {
        List<Article> articles = database.ga();
        if (articles.isEmpty()) {
            System.out.println("Статьи не найдены.");
        } else {
            for (Article article : articles) {
                System.out.println("ID: " + article.id + ", Заголовок: " + article.title);
            }
        }
    }

    static void deleteArticle(D database) {
        System.out.print("Введите ID статьи для удаления: ");
        long articleId = Long.parseLong(scanner.nextLine());
        if (database.da(articleId)) {
            System.out.println("Статья успешно удалена.");
        } else {
            System.out.println("Ошибка при удалении статьи.");
        }
    }

    static void searchArticlesByTitle(D database) {
        System.out.print("Введите заголовок статьи для поиска: ");
        String title = scanner.nextLine();
        List<Article> articles = database.ga(title);
        if (articles.isEmpty()) {
            System.out.println("Статьи не найдены.");
        } else {
            for (Article article : articles) {
                System.out.println("ID: " + article.id + ", Заголовок: " + article.title);
            }
        }
    }

    static void addComment(D database) {
        System.out.print("Введите ID статьи: ");
        long articleId = Long.parseLong(scanner.nextLine());
        System.out.print("Введите текст комментария: ");
        String commentText = scanner.nextLine();

        Comment comment = new Comment(null, articleId, loggedInUserId, commentText);
        if (database.createComment(comment) != null) {
            System.out.println("Комментарий успешно добавлен.");
        } else {
            System.out.println("Ошибка при добавлении комментария.");
        }
    }

    static void viewComments(D database) {
        System.out.print("Введите ID статьи: ");
        long articleId = Long.parseLong(scanner.nextLine());
        List<Comment> comments = database.getCommentsByArticleId(articleId);
        if (comments.isEmpty()) {
            System.out.println("Комментарии не найдены.");
        } else {
            for (Comment comment : comments) {
                System.out.println(comment);
            }
        }
    }

    static void deleteComment(D database) {
        System.out.print("Введите ID комментария для удаления: ");
        long commentId = Long.parseLong(scanner.nextLine());
        if (database.deleteComment(commentId)) {
            System.out.println("Комментарий успешно удален.");
        } else {
            System.out.println("Ошибка при удалении комментария.");
        }
    }

    static void manageUsers(D database) {
        printMenu("Управление пользователями", Arrays.asList(
                "Добавить пользователя",
                "Изменить пароль пользователя",
                "Удалить пользователя",
                "Просмотреть всех пользователей",
                "Вернуться в главное меню"
        ));
        int choice = getChoice(Arrays.asList(
                "Добавить пользователя",
                "Изменить пароль пользователя",
                "Удалить пользователя",
                "Просмотреть всех пользователей",
                "Вернуться в главное меню"
        ));
        switch (choice) {
            case 1:
                addUser(database);
                break;
            case 2:
                changeUserPassword(database);
                break;
            case 3:
                deleteUser(database);
                break;
            case 4:
                listAllUsers(database);
                break;
            case 5:
                return; // Возврат в главное меню
            default:
                System.out.println("Неверная опция.");
                break;
        }
    }
}