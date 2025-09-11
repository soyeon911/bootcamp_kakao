
import practice.model.*;
import practice.model.Restaurant.Cuisine;
import practice.model.Restaurant.PriceRange;
import practice.model.Cafe.CafeCat;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;



// 안내문구
public class Test {
    public static void main(String[] args){
        System.out.println("----------------------------");
        System.out.println("안녕하세요 당신의 소중한 식사/카페 시간!");
        System.out.println("메뉴를 고르기 망설여진다면 제가 추천해 드릴게요");
        System.out.println("----------------------------");

        Scanner sc = new Scanner(System.in);
        boolean isFood = askIsFoodOrCafe(sc);

        if(isFood){
            Cuisine cuisine = askCuisine(sc);
            PriceRange priceRange = askFoodPrice(sc);

            // 음식 종류 && 가격대 의 조건을 모두 만족하는 메뉴 가져오기 -> filter
            List<MenuFood> candidates = dataMenuFoods().stream()
                    .filter(m -> m.getCuisine() == cuisine && m.getPrice == price).toList();

            if(candidates.isEmpty()){
                System.out.println("조건에 맞는 음식이 없어요. 다시 시도해주세요!");
                return;
            }
        }
    }


    // 숫자 입력받는 툴 (정해진 숫자 이외의 값은 오류 처리)
    private static int askInt(Scanner sc, int min, int max){
        while(true){
            String s = sc.nextLine().trim;
            try{
                int n = Integer.parseInt(s);
                if (n >= min && n <= max){
                    return n;
                }
            }catch (NumberFormatException e){
                System.out.println("잘못된 입력입니다." + min + " ~ " + max + "사이의 숫자를 입력해주세요.");
            }
        }
    }

    // 어떤 거 선택할래? (1. 식당, 2. 카페)
    private static boolean askIsFoodOrCafe(Scanner sc){
        System.out.println("무엇을 찾고 계신가요?");
        System.out.println("1. 식당");
        System.out.println("2. 카페");
        int n = askInt(sc, "번호를 입력해주세요: ", 1, 2);
        return n == 1;
    }

    //case 1 : cuisine 고르기 (한식, 중식, 일식 etc)
    private static Cuisine askCuisine(Scanner sc){
        System.out.println("어떤 종류의 음식을 드시고 싶으세요?");
        System.out.println("1. 한식");
        System.out.println("2. 중식");
        System.out.println("3. 일식");
        int n = askInt(sc, "번호를 입력해주세요: ", 1, 3);
        return switch (n){
            case 1 -> Cuisine.KOREAN;
            case 2 -> Cuisine.JAPANESE;
            case 3 -> Cuisine.CHINESE;
            default -> Cuisine.KOREAN;
        };
    }

    // 가격대 선택하기
    private static PriceRange askFoodPrice(Scanner sc){
        System.out.println("가격대를 선택해주세요.");
        System.out.println("1. 10,000원 이하");
        System.out.println("2. 10,000 ~ 15,000원");
        System.out.println("3. 15,000원 이상");
        int n = askInt(sc, "번호를 입력해주세요: ", 1, 3);
        return switch (n) {
            case 1 -> PriceRange.UNDER_10000;
            case 2 -> PriceRange.FROM_10000_TO_15000;
            case 3 -> PriceRange.OVER_15000;
            default -> PriceRange.FROM_10000_TO_15000;
        };
    }

    // case 2 : cuisine 고르기 (베이커리, 디저트, 음료 etc)
    private static CafeCat askCafeCategory(Scanner sc){
        System.out.println("어떤 종류의 음식을 드시고 싶으세요?");
        System.out.println("1. 베이커리");
        System.out.println("2. 디저트");
        System.out.println("3. 커피");
        int n = askInt(sc, "번호를 입력해주세요: ", 1, 3);
        return switch (n){
            case 1 -> CafeCat.BAKERY;
            case 2 -> CafeCat.DESSERT;
            case 3 -> CafeCat.COFFEE;
            default -> CafeCat.BAKERY;
        };
    }
    // 가격대 선택하기
    private static Cafe.PriceRange askCafePrice(Scanner sc){
        System.out.println("가격대를 선택해주세요.");
        System.out.println("1. 10,000원 이하");
        System.out.println("2. 10,000 ~ 15,000원");
        System.out.println("3. 15,000원 이상");
        int n = askInt(sc, "번호를 입력해주세요: ", 1, 3);
        return switch (n) {
            case 1 -> PriceRange.UNDER_10000;
            case 2 -> PriceRange.FROM_10000_TO_15000;
            case 3 -> PriceRange.OVER_15000;
            default -> PriceRange.FROM_10000_TO_15000;
        };
    }
// 콘솔 락을 위한 spinner
    public static Spinner extends Thread{
        private final AtomicBoolean stop;
        private final long interval;
        private final SharedPrinter p;
        private static final char frames[] = {'|', '/', '-', '\\'};
        Spinner(AtomicBoolean stop, long interval, SharedPrinter p){
            setDaemon(true);
            this.stop = stop;
            this.interval = interval;
            this.p = p;
        }
        @Override
        public void run(){
            int i = 0;
            while(!stop) {
                p.print("\r추천을 준비중입니다. 잠시만 기다려주세요." + FRAMES[i++ % FRAMES.length]);
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {}
            }
            p.print("\r" + " ".repeat(50) + "\r");
        }
    }



//그 선택지에 따른 랜덤 추첨 (메뉴를 추첨하는 거)


// 추첨 이후 그 메뉴를 파는 식당 리스트도 show


    // --- 시드 데이터 ---
    private static List<Restaurant> seedRestaurants() {
        return new ArrayList<>(List.of(
                new Restaurant("r001","진수정","", Cuisine.KOREAN, PriceRange.FROM_10000_TO_15000),
                new Restaurant("r002","백반천국","", Cuisine.KOREAN, PriceRange.UNDER_10000),
                new FranchiseRestaurant("r003","맘스터치 판교점","", Cuisine.SNACK_BURGER, PriceRange.UNDER_10000, "맘스터치","PG-01"),
                new Restaurant("r004","파스타팩토리 정문점","", Cuisine.WESTERN, PriceRange.FROM_10000_TO_15000),
                new FranchiseRestaurant("r005","스시오 판교점","", Cuisine.JAPANESE, PriceRange.OVER_15000, "스시오","PG-02"),
                new Restaurant("r006","짜장명가","", Cuisine.CHINESE, PriceRange.UNDER_10000)
        ));
    }

    private static List<Cafe> seedCafes() {
        return new ArrayList<>(List.of(
                new Cafe("c001","블루보틀","", CafeCat.COFFEE, Cafe.PriceRange.OVER_15000),
                new Cafe("c002","폴바셋","", CafeCat.COFFEE, Cafe.PriceRange.FROM_10000_TO_15000),
                new Cafe("c003","파리바게뜨","", CafeCat.BAKERY, Cafe.PriceRange.FROM_10000_TO_15000),
                new Cafe("c004","뚜레쥬르","", CafeCat.BAKERY, Cafe.PriceRange.FROM_10000_TO_15000),
                new Cafe("c005","치즈케이크샵","", CafeCat.DESSERT, Cafe.PriceRange.OVER_15000)
        ));
    }

    private static List<MenuFood> seedMenuFoods() {
        return new ArrayList<>(List.of(
                new MenuFood("김치찌개", Cuisine.KOREAN, PriceRange.FROM_10000_TO_15000, List.of("r001","r002")),
                new MenuFood("초밥세트", Cuisine.JAPANESE, PriceRange.OVER_15000, List.of("r005")),
                new MenuFood("파스타", Cuisine.WESTERN, PriceRange.FROM_10000_TO_15000, List.of("r004")),
                new MenuFood("짜장면", Cuisine.CHINESE, PriceRange.UNDER_10000, List.of("r006")),
                new MenuFood("치킨버거세트", Cuisine.SNACK_BURGER, PriceRange.UNDER_10000, List.of("r003"))
        ));
    }

    private static List<MenuCafe> seedMenuCafes() {
        return new ArrayList<>(List.of(
                new MenuCafe("아메리카노", CafeCat.COFFEE, Cafe.PriceRange.FROM_10000_TO_15000, List.of("c001","c002")),
                new MenuCafe("크루아상", CafeCat.BAKERY, Cafe.PriceRange.FROM_10000_TO_15000, List.of("c003","c004")),
                new MenuCafe("치즈케이크", CafeCat.DESSERT, Cafe.PriceRange.OVER_15000, List.of("c005"))
        ));
    }
}

