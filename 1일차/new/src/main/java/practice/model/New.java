// 갖고 있는 엔티티 (장소, 식당, 카페, 프렌차이즈 식당, 메뉴)
public class Place {
    protected id id;
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

public class Restaurant extends Place{
    // 모든 변수명을 엮기 위한 enum 사용
    public enum Cuisine {
        KOREAN, JAPANESE, CHINESE
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

public class Cafe extends Place{
    // 모든 변수명을 엮기 위한 enum 사용
    public enum CafeCat{
        DESSERT, BAKERY, COFFEE
    }
    public enum PriceRange{
        UNDER_10000, FROM_10000_TO_15000, OVER_15000
    }
    private CafeCat cafecat;
    private PriceRange priceRange;
    // 데이터 무결성을 위한 getter 함수 클래스 내 선언
    public CafeCat getCafeCat() { return cafecat; }
    public PriceRange getPriceRange() { return priceRange; }
}

public class FranchiseRestaurant extends Restaurant{
    private String brandName;
    private String branchCode;
    public FranchiseRestaurant(String id, String name, String tags,
                               Cuisine cuisine, PriceRange price,
                               brandName, String branchCode){
        super (id, name, tags, cuisine, price);
        this.brandName = brandName;
        this.branchCode = branchCode;
    }
    // 데이터 무결성을 위한 getter 클래스 내 선언
    public String getBrandName() { return brandName; }
    public String getBranchCode() { return branchCode; }
}

// 우선 식당 메뉴 부터
public class MenuFood{
    // 불변의 값 설정, 가공 ㄴㄴ
    private final String name;
    private final Restaurant.Cuisine cuisine;
    private final Restaurant.PriceRange priceRange;
    private final List<String> restaurantIds;

    public MenuFood(String name, Restaurant.Cuisine cuisine, Restaurant.PriceRange priceRange, List<String> restaurantIds){
        this.name = name;
        this.cuisine = cuisine;
        this.priceRange = priceRange;
        this.restaurantIds = restaurantIds;
    }
    //데이터 무결성을 위한 getter 클래스 내 선언
    public String getName() { return name; }
    public Restaurant.Cuisine getCuisine() { return cuisine; }
    public Restaurant.PriceRange getPrice() { return price; }
    public List<String> getRestaurantIds() { return restaurantIds; }
}
