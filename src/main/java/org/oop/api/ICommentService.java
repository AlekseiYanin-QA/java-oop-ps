package org.oop.api;

import org.oop.model.Comment;

import java.util.List;

public interface ICommentService {
    Comment createComment(int articleId, int userId, String commentText);
    List<Comment> getCommentsByArticleId(int articleId);
    boolean deleteComment(int commentId);
}