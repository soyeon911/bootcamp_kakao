public class FranchiseRestaurant extends Restaurant{
    private String brandName;
    private String branchCode;
    public FranchiseRestaurant(String id, String name, String tags,
                               Cuisine cuisine, PriceRange price,
                               String brandName, String branchCode){
        super (id, name, tags, cuisine, price);
        this.brandName = brandName;
        this.branchCode = branchCode;
    }
    // 데이터 무결성을 위한 getter 클래스 내 선언
    public String getBrandName() { return brandName; }
    public String getBranchCode() { return branchCode; }
}