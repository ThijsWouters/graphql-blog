package comment_microservice;

public class Comment {
    private final String id;
    private String postId;
    private String authorId;
    private String contents;

    public Comment(String id, String postId, String authorId, String contents) {
        this.id = id;
        this.postId = postId;
        this.authorId = authorId;
        this.contents = contents;
    }

    public String getId() {
        return id;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
