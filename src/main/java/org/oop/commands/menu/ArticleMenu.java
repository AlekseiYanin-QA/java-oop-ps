package org.oop.commands.menu;

import org.oop.commands.*;
import org.oop.api.ICommand;

public class ArticleMenu extends BaseCommand {

    public ArticleMenu() {
        initializeMenu();
    }

    private void initializeMenu() {
        commandSuppliers.put(1, CreateArticleCommand::new); // Создать статью
        commandSuppliers.put(2, DeleteArticleCommand::new); // Удалить статью
        commandSuppliers.put(3, AddCommentCommand::new);    // Добавить комментарий
        commandSuppliers.put(4, ViewCommentsCommand::new);  // Просмотреть комментарии
        commandSuppliers.put(5, MainMenu::new);             // Вернуться в главное меню
    }

    @Override
    public ICommand execute() {
        return selectMenu();
    }

    @Override
    public String getDescription() {
        return "Управление статьями";
    }
}