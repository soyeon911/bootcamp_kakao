import java.util.List;
// 카페 메뉴
public class MenuCafe{
    // 불변의 값 설정, 가공 ㄴㄴ
    private final String name;
    private final Cafe.CafeCat category;
    private final Cafe.PriceRange price;
    private final List<String> cafeIds;

    public MenuCafe(String name, Cafe.CafeCat category, Cafe.PriceRange price, List<String> cafeIds) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.cafeIds = cafeIds;
    }
    //데이터 무결성을 위한 getter 클래스 내 선언
    public String getName() { return name; }
    public Cafe.CafeCat getCategory() { return category; }
    public Cafe.PriceRange getPrice() { return price; }
    public List<String> getCafeIds() { return cafeIds; }
}