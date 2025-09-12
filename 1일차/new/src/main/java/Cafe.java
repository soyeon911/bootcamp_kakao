public class Cafe extends Place{
    // 모든 변수명을 엮기 위한 enum 사용
    public enum CafeCat{
        DESSERT, BAKERY, COFFEE
    }
    public enum PriceRange{
        UNDER_10000, FROM_10000_TO_15000, OVER_15000
    }
    private CafeCat cafecat;
    private PriceRange price;

    public Cafe(String id, String name, String tags, CafeCat cafecat, PriceRange price) {
        super(id, name, tags);      // Place 에서 가져올 속성들
        this.cafecat = cafecat;
        this.price = price;
    }
    // 데이터 무결성을 위한 getter 함수 클래스 내 선언
    public CafeCat getCafeCat() { return cafecat; }
    public PriceRange getPriceRange() { return price; }
}