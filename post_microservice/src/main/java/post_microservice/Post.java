package post_microservice;

public class Post {
    private final String id;
    private String authorId;
    private String title;
    private String contents;

    public Post(String id, String authorId, String title, String contents) {
        this.id = id;
        this.authorId = authorId;
        this.title = title;
        this.contents = contents;
    }

    public String getId() {
        return id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
