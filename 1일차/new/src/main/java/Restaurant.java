public class Restaurant extends Place{
    // 모든 변수명을 엮기 위한 enum 사용
    public enum Cuisine {
        KOREAN, JAPANESE, CHINESE, WESTERN
    }
    public enum PriceRange {
        UNDER_10000, FROM_10000_TO_15000, OVER_15000
    }
    protected Cuisine cuisine;
    protected PriceRange price;

    public Restaurant(String id, String name, String tags, Cuisine cuisine, PriceRange price){
        super (id, name, tags);
        this.cuisine = cuisine;
        this.price = price;
    }
    //데이터 무결성을 위한 getter 함수 클래스 내 선언
    public Cuisine getCuisine() { return cuisine; }
    public PriceRange getPrice() { return price; }
}