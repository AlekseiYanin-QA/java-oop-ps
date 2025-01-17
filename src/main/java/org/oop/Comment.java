package org.oop;

public class Comment {
    public Long id;
    public Long articleId;
    public Long userId;
    public String commentText;

    public Comment(Long id, Long articleId, Long userId, String commentText) {
        this.id = id;
        this.articleId = articleId;
        this.userId = userId;
        this.commentText = commentText;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Статья ID: " + articleId + " | Пользователь ID: " + userId + "\nКомментарий: " + commentText;
    }
}
