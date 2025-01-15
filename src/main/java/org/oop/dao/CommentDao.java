package org.oop.dao;

import org.oop.api.dao.ICommentDao;
import org.oop.model.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDao extends Dao implements ICommentDao {
    @Override
    public Comment createComment(Comment comment) {
        String query = "INSERT INTO comments (article_id, user_id, comment_text) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, comment.getArticleId());
            preparedStatement.setInt(2, comment.getUserId());
            preparedStatement.setString(3, comment.getCommentText());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating comment failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    comment.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating comment failed, no ID obtained.");
                }
            }

            return comment;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Comment> getCommentsByArticleId(int articleId) {
        List<Comment> comments = new ArrayList<>();
        String query = "SELECT id, article_id, user_id, comment_text FROM comments WHERE article_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, articleId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Comment comment = new Comment(
                            resultSet.getInt("article_id"),
                            resultSet.getInt("user_id"),
                            resultSet.getString("comment_text")
                    );
                    comment.setId(resultSet.getInt("id"));
                    comments.add(comment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    @Override
    public boolean deleteComment(int commentId) {
        String query = "DELETE FROM comments WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, commentId);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}