package org.oop.commands;

import org.oop.api.IArticleService;
import org.oop.api.ICommand;
import org.oop.commands.menu.BaseCommand;
import org.oop.commands.menu.ArticleMenu;
import org.oop.di.Injector;

public class AddCommentCommand extends BaseCommand {
    private final IArticleService articleService;

    public AddCommentCommand() {
        this.articleService = Injector.getInstance().getService(IArticleService.class);
    }

    @Override
    public ICommand execute() {
        long articleId = Long.parseLong(ioService.prompt("Введите ID статьи: "));
        String commentText = ioService.prompt("Введите текст комментария: ");

        articleService.addCommentToArticle((int) articleId, commentText);
        ioService.printLine("Комментарий успешно добавлен.");

        return new ArticleMenu();
    }

    @Override
    public String getDescription() {
        return "Добавить комментарий";
    }
}