package org.oop.commands;

import org.oop.api.IArticleService;
import org.oop.api.ICommand;
import org.oop.commands.menu.BaseCommand;
import org.oop.commands.menu.ArticleMenu;
import org.oop.di.Injector;
import org.oop.model.Comment;

import java.util.List;

public class ViewCommentsCommand extends BaseCommand {
    private final IArticleService articleService;

    public ViewCommentsCommand() {
        this.articleService = Injector.getInstance().getService(IArticleService.class);
    }

    @Override
    public ICommand execute() {
        long articleId = Long.parseLong(ioService.prompt("Введите ID статьи: "));
        List<Comment> comments = articleService.getCommentsByArticleId((int) articleId);

        if (comments.isEmpty()) {
            ioService.printLine("Комментарии отсутствуют.");
        } else {
            ioService.printLine("Комментарии:");
            comments.forEach(comment -> ioService.printLine(comment.toString()));
        }

        return new ArticleMenu();
    }

    @Override
    public String getDescription() {
        return "Просмотреть комментарии";
    }
}