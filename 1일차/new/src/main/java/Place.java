public class Place {
    protected String id;
    protected String name;
    protected String tags;

    public Place (String id, String name, String tags){
        this.id = id;
        this.name = name;
        this.tags = tags;
    }
    //데이터 무결성을 위한 getter 함수 클래스 내 선언
    public String getId() { return id; }
    public String getName() { return name; }
    public String getTags() { return tags; }
}