public class LegalDocument {

    private final int id;
    private final String title;
    private final String type;
    private final String content;

    public LegalDocument(int id, String title, String type, String content) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return title + "  •  " + type;
    }
}