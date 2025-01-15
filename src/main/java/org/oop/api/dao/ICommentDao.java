package org.oop.api.dao;

import org.oop.model.Comment;

import java.util.List;

public interface ICommentDao {
    Comment createComment(Comment comment);
    List<Comment> getCommentsByArticleId(int articleId);
    boolean deleteComment(int commentId);
}