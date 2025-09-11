package com.example.menu;

import com.example.menu.model.*;
import com.example.menu.model.Restaurant.Cuisine;
import com.example.menu.model.Restaurant.PriceRange;
import com.example.menu.model.Cafe.CafeCat;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Test {

    private static final SharedPrinter PRINTER = new SharedPrinter();

    // 타자기 효과
    private static void typeWriter(String s, long delay) {
        for (char c : s.toCharArray()) { PRINTER.print(String.valueOf(c)); try { Thread.sleep(delay); } catch (InterruptedException ignored) {} }
        PRINTER.println("");
    }

    // 스피너(콘솔 락 사용)
    static class Spinner extends Thread {
        private final AtomicBoolean stop; private final long interval; private final SharedPrinter p;
        private static final char[] FRAMES = {'|','/','-','\\'};
        Spinner(AtomicBoolean stop, long interval, SharedPrinter p) { (true); this.stop=stop; this.interval=interval; this.p=p; }
        @Override public void run() {
            int i=0; while (!stop.get()) { p.print("\r추천을 준비 중입니다... " + FRAMES[i++ % FRAMES.length] + " ♪"); try { Thread.sleep(interval); } catch (InterruptedException ignored) {} }
            p.print("\r" + " ".repeat(50) + "\r");
        }
    }

    // 입력 유틸
    private static int askInt(Scanner sc, String prompt, int min, int max) {
        while (true) {
            PRINTER.print(prompt);
            String s = sc.nextLine().trim();
            try {
                int n = Integer.parseInt(s);
                if (n >= min && n <= max) return n;
            } catch (NumberFormatException ignored) {}
            PRINTER.println("잘못된 입력입니다. " + min + " ~ " + max + " 사이 숫자를 입력해 주세요.");
        }
    }

    // --- 선택 분기 ---
    private static boolean askIsFoodOrCafe(Scanner sc) {
        typeWriter("무엇을 찾고 계신가요?", 35);
        PRINTER.println("1. 식사(레스토랑)");
        PRINTER.println("2. 카페");
        int n = askInt(sc, "번호를 입력해 주세요: ", 1, 2);
        return n == 1; // true=식사, false=카페
    }

    private static Cuisine askCuisine(Scanner sc) {
        typeWriter("어떤 종류의 식사를 원하시나요?", 35);
        PRINTER.println("1. 한식  2. 중식  3. 일식  4. 양식");
        PRINTER.println("5. 아시안  6. 분식/햄버거  7. 샐러드/샌드위치  8. 디저트");
        int n = askInt(sc, "번호를 입력해 주세요: ", 1, 8);
        return switch (n) {
            case 1 -> Cuisine.KOREAN;
            case 2 -> Cuisine.CHINESE;
            case 3 -> Cuisine.JAPANESE;
            case 4 -> Cuisine.WESTERN;
            case 5 -> Cuisine.ASIAN;
            case 6 -> Cuisine.SNACK_BURGER;
            case 7 -> Cuisine.SALAD_SANDWICH;
            case 8 -> Cuisine.DESSERT;
            default -> Cuisine.KOREAN;
        };
    }

    private static PriceRange askFoodPrice(Scanner sc) {
        typeWriter("예산을 선택해 주세요!", 35);
        PRINTER.println("1. 10,000원 이하");
        PRINTER.println("2. 10,000 ~ 15,000원");
        PRINTER.println("3. 15,000원 이상");
        int n = askInt(sc, "번호를 입력해 주세요: ", 1, 3);
        return switch (n) {
            case 1 -> PriceRange.UNDER_10000;
            case 2 -> PriceRange.FROM_10000_TO_15000;
            case 3 -> PriceRange.OVER_15000;
            default -> PriceRange.FROM_10000_TO_15000;
        };
    }

    private static CafeCat askCafeCategory(Scanner sc) {
        typeWriter("카페 카테고리를 선택해 주세요!", 35);
        PRINTER.println("1. 베이커리  2. 디저트  3. 커피  4. 티");
        int n = askInt(sc, "번호를 입력해 주세요: ", 1, 4);
        return switch (n) {
            case 1 -> CafeCat.BAKERY;
            case 2 -> CafeCat.DESSERT;
            case 3 -> CafeCat.COFFEE;
            case 4 -> CafeCat.TEA;
            default -> CafeCat.COFFEE;
        };
    }

    private static Cafe.PriceRange askCafePrice(Scanner sc) {
        typeWriter("예산을 선택해 주세요!", 35);
        PRINTER.println("1. 10,000원 이하");
        PRINTER.println("2. 10,000 ~ 15,000원");
        PRINTER.println("3. 15,000원 이상");
        int n = askInt(sc, "번호를 입력해 주세요: ", 1, 3);
        return switch (n) {
            case 1 -> Cafe.PriceRange.UNDER_10000;
            case 2 -> Cafe.PriceRange.FROM_10000_TO_15000;
            case 3 -> Cafe.PriceRange.OVER_15000;
            default -> Cafe.PriceRange.FROM_10000_TO_15000;
        };
    }

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

    private static <T> T pickRandom(List<T> list) { return list.get(new Random().nextInt(list.size())); }

    // --- 메인 ---
    public static void main(String[] args) {
        typeWriter("-------------------------------------------------------", 15);

        typeWriter("안녕하세요 당신의 소중한 식사/카페 시간!", 35);
        typeWriter("메뉴를 고르기 망설여진다면 제가 추천해 드릴게요 :)", 35);
        typeWriter("-------------------------------------------------------", 15);
        PRINTER.println("");

        Scanner sc = new Scanner(System.in);
        boolean isFood = askIsFoodOrCafe(sc);

        if (isFood) {
            Cuisine cuisine = askCuisine(sc);
            PriceRange price = askFoodPrice(sc);

            List<MenuFood> candidates = seedMenuFoods().stream()
                    .filter(m -> m.getCuisine() == cuisine && m.getPrice() == price)
                    .toList();

            if (candidates.isEmpty()) { typeWriter("조건에 맞는 식사 메뉴가 없어요. 다시 시도해 주세요!", 35); return; }

            AtomicBoolean stop = new AtomicBoolean(false);
            Thread spinner = new Spinner(stop, 180, PRINTER);
            typeWriter("선택하신 조건에 맞춰 추천을 준비하고 있어요. 5초만 기다려 주세요!", 35);
            spinner.start();
            try { Thread.sleep(5000); } catch (InterruptedException ignored) {}
            MenuFood chosen = pickRandom(candidates);
            stop.set(true);
            try { spinner.join(); } catch (InterruptedException ignored) {}

            typeWriter("👉 오늘의 추천 메뉴: " + chosen.getName(), 35);

            List<Restaurant> restaurants = seedRestaurants();
            List<Restaurant> list = restaurants.stream()
                    .filter(r -> chosen.getRestaurantIds().contains(r.getId()))
                    .toList();

            if (list.isEmpty()) typeWriter("이 메뉴를 판매하는 식당 정보를 찾을 수 없습니다.", 35);
            else {
                typeWriter("👉 이 메뉴를 맛볼 수 있는 식당들:", 35);
                for (Restaurant r : list) typeWriter(" - " + r.getName(), 20);
            }

        } else {
            CafeCat cat = askCafeCategory(sc);
            Cafe.PriceRange price = askCafePrice(sc);

            List<MenuCafe> candidates = seedMenuCafes().stream()
                    .filter(m -> m.getCategory() == cat && m.getPrice() == price)
                    .toList();

            if (candidates.isEmpty()) { typeWriter("조건에 맞는 카페 메뉴가 없어요. 다시 시도해 주세요!", 35); return; }

            AtomicBoolean stop = new AtomicBoolean(false);
            Thread spinner = new Spinner(stop, 180, PRINTER);
            typeWriter("선택하신 조건에 맞춰 추천을 준비하고 있어요. 5초만 기다려 주세요!", 35);
            spinner.start();
            try { Thread.sleep(5000); } catch (InterruptedException ignored) {}
            MenuCafe chosen = pickRandom(candidates);
            stop.set(true);
            try { spinner.join(); } catch (InterruptedException ignored) {}

            typeWriter("👉 오늘의 추천 메뉴(카페): " + chosen.getName(), 35);

            List<Cafe> cafes = seedCafes();
            List<Cafe> list = cafes.stream()
                    .filter(c -> chosen.getCafeIds().contains(c.getId()))
                    .toList();

            if (list.isEmpty()) typeWriter("이 메뉴를 판매하는 카페 정보를 찾을 수 없습니다.", 35);
            else {
                typeWriter("👉 이 메뉴를 즐길 수 있는 카페들:", 35);
                for (Cafe c : list) typeWriter(" - " + c.getName(), 20);
            }
        }
    }
}
