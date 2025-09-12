import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Test {
    private static final SharedPrinter PRINTER = new SharedPrinter();

    // 숫자 입력받는 툴 (정해진 숫자 이외의 값은 오류 처리)
    private static int askInt(Scanner sc, String a, int min, int max){
        while(true){
            String s = sc.nextLine().trim();
            try{
                int n = Integer.parseInt(s);
                if (n >= min && n <= max) return n;
            }catch (NumberFormatException e){}
            System.out.println("잘못된 입력입니다. " + min + " ~ " + max + " 사이의 숫자를 입력해주세요.");
        }
    }
    // 어떤 거 선택할래? (1. 식당, 2. 카페)
    // 카페는 추후에..
    private static boolean askIsFoodOrCafe(Scanner sc){
        System.out.println("무엇을 찾고 계신가요?");
        System.out.println();
        System.out.println("1. 식당");
        System.out.println("2. 카페");
        System.out.println();
        int n = askInt(sc, "번호를 입력해주세요: ", 1, 2);
        return n == 1;
    }
    //case 1 : cuisine 고르기 (한식, 중식, 일식 etc)
    private static Restaurant.Cuisine askCuisine(Scanner sc){
        System.out.println("어떤 종류의 음식을 드시고 싶으세요?");
        System.out.println();
        System.out.println("1. 한식");
        System.out.println("2. 중식");
        System.out.println("3. 일식");
        System.out.println("4. 양식");
        System.out.println();
        int n = askInt(sc,"번호를 입력해주세요: ", 1, 3);
        return switch (n){
            case 1 -> Restaurant.Cuisine.KOREAN;
            case 2 -> Restaurant.Cuisine.CHINESE;
            case 3 -> Restaurant.Cuisine.JAPANESE;
            case 4 -> Restaurant.Cuisine.WESTERN;
            default -> Restaurant.Cuisine.KOREAN;
        };
    }
    // 가격대 선택하기
    private static Restaurant.PriceRange askFoodPrice(Scanner sc){
        System.out.println("가격대를 선택해주세요.");
        System.out.println();
        System.out.println("1. 10,000원 이하");
        System.out.println("2. 10,000 ~ 15,000원");
        System.out.println("3. 15,000원 이상");
        System.out.println();
        int n = askInt(sc,"번호를 입력해주세요: ", 1, 3);
        return switch (n) {
            case 1 -> Restaurant.PriceRange.UNDER_10000;
            case 2 -> Restaurant.PriceRange.FROM_10000_TO_15000;
            case 3 -> Restaurant.PriceRange.OVER_15000;
            default -> Restaurant.PriceRange.FROM_10000_TO_15000;
        };
    }
    // case 2 : cuisine 고르기 (베이커리, 디저트, 음료 etc)
    private static Cafe.CafeCat askCafeCategory(Scanner sc){
        System.out.println("어떤 종류를 원하시나요?");
        System.out.println();
        System.out.println("1. 베이커리");
        System.out.println("2. 디저트");
        System.out.println("3. 커피");
        System.out.println();
        int n = askInt(sc,"번호를 입력해주세요: ", 1, 3);
        return switch (n){
            case 1 -> Cafe.CafeCat.BAKERY;
            case 2 -> Cafe.CafeCat.DESSERT;
            case 3 -> Cafe.CafeCat.COFFEE;
            default -> Cafe.CafeCat.BAKERY;
        };
    }
    // 가격대 선택
    private static Cafe.PriceRange askCafePrice(Scanner sc){
        System.out.println("가격대를 선택해주세요.");
        System.out.println();
        System.out.println("1. 10,000원 이하");
        System.out.println("2. 10,000 ~ 15,000원");
        System.out.println("3. 15,000원 이상");
        System.out.println();
        int n = askInt(sc,"번호를 입력해주세요: ", 1, 3);
        return switch (n) {
            case 1 -> Cafe.PriceRange.UNDER_10000;
            case 2 -> Cafe.PriceRange.FROM_10000_TO_15000;
            case 3 -> Cafe.PriceRange.OVER_15000;
            default -> Cafe.PriceRange.FROM_10000_TO_15000;
        };
    }

    // 스피너
    static class Spinner extends Thread {
        private final AtomicBoolean stop;       // 다른 쓰레드에서 true로 바뀌면 스피너 종료하는 신호
        private final long interval;            // 스피너 모양 바뀌는 시간
        private final SharedPrinter p;          // 출력 동기화를 위한 프린터 (출력이 섞이는 걸 방지)
        private static final char[] FRAMES = {'/', '-', '\\', '_'};

        // 스피너 생성 [주 쓰레드가 종료되면 같이 종료되는 쓰레드 = 데몬 쓰레드]
        Spinner(AtomicBoolean stop, long interval, SharedPrinter p){
            setDaemon(true);
            this.stop = stop;
            this.interval = interval;
            this.p = p;
        }
        // 쓰레드 실행 (프레임의 인덱스 세는 거, stop 플래그가 false가 될 때까지는 지속적인 실행(while), frame내 인덱스 반복해야하니까 mod 연산자 사용)
        public void run(){
            int i = 0;
            System.out.println("추천을 준비중입니다. 잠시만 기다려주세요.");
            while(!stop.get()) {
                System.out.print(FRAMES[i++ % FRAMES.length]);
                // interval 시간동안 대기, 외부에서 쓰레드가 깨우는 경우도 생각
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {}
            }
            p.print("\n");
        }
    }

    // 시드 데이터 (데이터 만드는 건 ai 썼습니다... 😭)
    private static List<MenuFood> seedMenuFoods() {
        return List.of(
                new MenuFood("김치찌개", Restaurant.Cuisine.KOREAN, Restaurant.PriceRange.FROM_10000_TO_15000, List.of("r001","r002")),
                new MenuFood("된장찌개", Restaurant.Cuisine.KOREAN, Restaurant.PriceRange.FROM_10000_TO_15000, List.of("r001","r002")),
                new MenuFood("추어탕", Restaurant.Cuisine.KOREAN, Restaurant.PriceRange.FROM_10000_TO_15000, List.of("r001","r002")),
                new MenuFood("감자탕", Restaurant.Cuisine.KOREAN, Restaurant.PriceRange.FROM_10000_TO_15000, List.of("r001","r002")),
                new MenuFood("초밥세트", Restaurant.Cuisine.JAPANESE, Restaurant.PriceRange.OVER_15000, List.of("r005")),
                new MenuFood("파스타", Restaurant.Cuisine.WESTERN, Restaurant.PriceRange.FROM_10000_TO_15000, List.of("r004"))
        );
    }

    private static List<Restaurant> seedRestaurants() {
        return List.of(
                new Restaurant("r001","진수정","", Restaurant.Cuisine.KOREAN, Restaurant.PriceRange.FROM_10000_TO_15000),
                new Restaurant("r002","백반천국","", Restaurant.Cuisine.KOREAN, Restaurant.PriceRange.UNDER_10000),
                new FranchiseRestaurant("r005","스시오 판교점","", Restaurant.Cuisine.JAPANESE, Restaurant.PriceRange.OVER_15000, "스시오","PG-02"),
                new Restaurant("r004","파스타팩토리 정문점","", Restaurant.Cuisine.WESTERN, Restaurant.PriceRange.FROM_10000_TO_15000)
        );
    }

    private static List<MenuCafe> seedMenuCafe() {
        return new ArrayList<>(List.of(
                new MenuCafe("크로와상", Cafe.CafeCat.BAKERY, Cafe.PriceRange.UNDER_10000, List.of("c001","c002")),
                new MenuCafe("소금빵", Cafe.CafeCat.BAKERY, Cafe.PriceRange.UNDER_10000, List.of("c001", "c002")),
                new MenuCafe("와플", Cafe.CafeCat.BAKERY, Cafe.PriceRange.FROM_10000_TO_15000, List.of("c001")),
                new MenuCafe("치즈케이크", Cafe.CafeCat.DESSERT, Cafe.PriceRange.UNDER_10000, List.of("c003", "c004")),
                new MenuCafe("초코케이크", Cafe.CafeCat.DESSERT, Cafe.PriceRange.FROM_10000_TO_15000, List.of("c003", "c004")),
                new MenuCafe("아메리카노", Cafe.CafeCat.COFFEE, Cafe.PriceRange.UNDER_10000, List.of("c005")),
                new MenuCafe("캬라멜마끼야또", Cafe.CafeCat.COFFEE, Cafe.PriceRange.FROM_10000_TO_15000, List.of("c005", "c006"))
        ));
    }

    private static List<Cafe> seedCafe() {
        return new ArrayList<>(List.of(
                new Cafe("c001","만드레이크","", Cafe.CafeCat.BAKERY, Cafe.PriceRange.FROM_10000_TO_15000),
                new Cafe("c002","제과제발","", Cafe.CafeCat.BAKERY, Cafe.PriceRange.UNDER_10000),
                new Cafe("c003","루돌프코는 빨개요","", Cafe.CafeCat.DESSERT, Cafe.PriceRange.FROM_10000_TO_15000),
                new Cafe("c004","루돌프엉덩이는?","", Cafe.CafeCat.DESSERT, Cafe.PriceRange.FROM_10000_TO_15000),
                new Cafe("c005","사약카페","", Cafe.CafeCat.COFFEE, Cafe.PriceRange.UNDER_10000),
                new Cafe("c006","이상한 나라의 앨리스","", Cafe.CafeCat.COFFEE, Cafe.PriceRange.FROM_10000_TO_15000)
        ));
    }

    public static void main(String[] args){
        System.out.println("----------------------------------------------");
        System.out.println();
        System.out.println("안녕하세요 당신의 소중한 식사/카페 시간!");
        System.out.println("메뉴를 고르기 망설여진다면 제가 추천해 드릴게요");
        System.out.println();
        System.out.println("----------------------------------------------");

        Scanner sc = new Scanner(System.in);
        boolean isFood = askIsFoodOrCafe(sc);


        if(isFood){
            var cuisine = askCuisine(sc);
            var price = askFoodPrice(sc);

            // 사용자의 선택에 따른 메뉴들 씨드 데이터에서 찾기 (2개 이상의 조건이기에 .filter() 사용) -> 후보군이니 candidate로 하고 list 화 하기
            List<MenuFood> candidates = seedMenuFoods().stream()
                    .filter(m -> m.getCuisine() == cuisine && m.getPrice() == price)
                    .toList();

            // 필터 결과가 비어있으면 조건에 맞는 음식 없다는 문구 추가
            if(candidates.isEmpty()){
                System.out.println("조건에 맞는 음식이 없어요.");
                return;
            }

            // 동시성 제어 스피너 돌리기
            AtomicBoolean stop = new AtomicBoolean(false);
            // 스피너 클래스 가져와 (스레드 종료 신호, 프레임 전환 속도, SharedPrinter 객체)
            Thread spinner = new Spinner(stop, 100, PRINTER);
            spinner.start();
            // 메인 쓰레드 정지 (try catch), 스피너 쓰레드는 동작
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {}
            // 스피너 쓰레드 돌 때동안 메뉴 추천하기 Random()로 158줄에 있는 menufood list 화한 candidates에서 랜덤 돌리기
            MenuFood chosen = candidates.get(new Random().nextInt(candidates.size()));
            // 추첨했으면 스피너 정지
            stop.set(true);
            // 스피너 쓰레드가 끝날 때까지 메인 쓰레드 대기
            try {
                spinner.join();
            } catch (InterruptedException e) {}

            System.out.println("오늘의 추천 메뉴: " + chosen.getName());
            // 씨드 데이터(식당) 에서 chosen된 메뉴의 태그가 포함되어 있는 식당 리스트 뽑고 -> 리스트화
            List<Restaurant> available = seedRestaurants().stream()
                    .filter(r -> chosen.getRestaurantIds().contains(r.getId()))
                    .toList();

            // 리스트화된 식당 보여주기
            for(Restaurant r : available){
                System.out.println(" - " + r.getName());
            }
        }
        // 카페 메뉴인 경우
        else{
            Cafe.CafeCat cat = askCafeCategory(sc);
            Cafe.PriceRange price = askCafePrice(sc);

            List<MenuCafe> candidates = seedMenuCafe().stream()
                    .filter(m -> m.getCafeCat() == cat && m.getPriceRange() == price)
                    .toList();


            // 필터 결과가 비어있으면 조건에 맞는 음식 없다는 문구 추가
            if(candidates.isEmpty()){
                System.out.println("조건에 맞는 음식이 없어요.");
                return;
            }

            // 동시성 제어 스피너 돌리기
            AtomicBoolean stop = new AtomicBoolean(false);
            // 스피너 클래스 가져와 (스레드 종료 신호, 프레임 전환 속도, SharedPrinter 객체)
            Thread spinner = new Spinner(stop, 100, PRINTER);
            spinner.start();
            // 메인 쓰레드 정지 (try catch), 스피너 쓰레드는 동작
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {}
            // 스피너 쓰레드 돌 때동안 메뉴 추천하기 Random()로 158줄에 있는 menufood list 화한 candidates에서 랜덤 돌리기
            MenuCafe chosen = candidates.get(new Random().nextInt(candidates.size()));
            // 추첨했으면 스피너 정지
            stop.set(true);
            // 스피너 쓰레드가 끝날 때까지 메인 쓰레드 대기
            try {
                spinner.join();
            } catch (InterruptedException e) {}

            System.out.println("오늘의 추천 메뉴: " + chosen.getName());
            // 씨드 데이터(식당) 에서 chosen된 메뉴의 태그가 포함되어 있는 식당 리스트 뽑고 -> 리스트화
            List<Cafe> available = seedCafe().stream()
                    .filter(r -> chosen.getCafeIds().contains(r.getId()))
                    .toList();

            // 리스트화된 식당 보여주기
            for(Cafe r : available){
                System.out.println(" - " + r.getName());
            }
        }
    }
}
