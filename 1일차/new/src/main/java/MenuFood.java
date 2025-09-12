import java.util.List;
// 우선 식당 메뉴 부터
public class MenuFood{
    // 불변의 값 설정, 가공 ㄴㄴ
    private final String name;
    private final Restaurant.Cuisine cuisine;
    private final Restaurant.PriceRange price;
    private final List<String> restaurantIds;

    public MenuFood(String name, Restaurant.Cuisine cuisine, Restaurant.PriceRange price, List<String> restaurantIds){
        this.name = name;
        this.cuisine = cuisine;
        this.price = price;
        this.restaurantIds = restaurantIds;

    }
    //데이터 무결성을 위한 getter 클래스 내 선언
    public String getName() { return name; }
    public Restaurant.Cuisine getCuisine() { return cuisine; }
    public Restaurant.PriceRange getPrice() { return price; }
    public List<String> getRestaurantIds() { return restaurantIds; }
}