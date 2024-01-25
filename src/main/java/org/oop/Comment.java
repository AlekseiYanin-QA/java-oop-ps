package org.oop.model;

public class Comment {
    private int id;
    private int articleId;
    private int userId;
    private String commentText;

    public Comment() {}

    public Comment(int articleId, int userId, String commentText) {
        this.articleId = articleId;
        this.userId = userId;
        this.commentText = commentText;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", userId=" + userId +
                ", commentText='" + commentText + '\'' +
                '}';
    }
}