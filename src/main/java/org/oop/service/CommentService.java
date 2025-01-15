package org.oop.service;

import org.oop.api.ICommentService;
import org.oop.api.dao.ICommentDao;
import org.oop.di.Injector;
import org.oop.model.Comment;

import java.util.List;

public class CommentService implements ICommentService {
    private final ICommentDao commentDao;

    public CommentService() {
        this.commentDao = Injector.getInstance().getService(ICommentDao.class);
    }

    @Override
    public Comment createComment(int articleId, int userId, String commentText) {
        Comment comment = new Comment(articleId, userId, commentText);
        return commentDao.createComment(comment);
    }

    @Override
    public List<Comment> getCommentsByArticleId(int articleId) {
        return commentDao.getCommentsByArticleId(articleId);
    }

    @Override
    public boolean deleteComment(int commentId) {
        return commentDao.deleteComment(commentId);
    }
}