package com.example.menu;

import com.example.menu.model.Menu;
import com.example.menu.model.Restaurant;
import com.example.menu.model.Restaurant.Cuisine;
import com.example.menu.model.Restaurant.PriceRange;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class AStage {

    // 콘솔 출력 임계구역 보호용 (락 적용)
    private static final SharedPrinter PRINTER = new SharedPrinter();

    // ===== 타자기 효과 =====
    private static void typeWriter(String message, long delayMillis) {
        for (char c : message.toCharArray()) {
            PRINTER.print(String.valueOf(c));
            try { Thread.sleep(delayMillis); } catch (InterruptedException ignored) {}
        }
        PRINTER.println("");
    }

    // ===== 스피너 스레드 (락 사용) =====
    static class SpinnerSound extends Thread {
        private static final char[] FRAMES = {'|','/','-','\\'};
        private final AtomicBoolean stop;
        private final long intervalMillis;
        private final SharedPrinter printer;

        SpinnerSound(AtomicBoolean stop, long intervalMillis, SharedPrinter printer) {
            setDaemon(true);
            this.stop = stop;
            this.intervalMillis = intervalMillis;
            this.printer = printer;
        }

        @Override public void run() {
            int i = 0;
            while (!stop.get()) {
                printer.print("\r추천을 준비 중입니다... " + FRAMES[i % FRAMES.length] + " ♪");
                i++;
                try { Thread.sleep(intervalMillis); } catch (InterruptedException ignored) {}
            }
            // 라인 정리도 임계구역 내에서
            printer.print("\r" + " ".repeat(40) + "\r");
        }
    }

    // ===== 유틸 =====
    private static int askInt(Scanner sc, String prompt, int min, int max) {
        while (true) {
            PRINTER.print(prompt);
            String s = sc.nextLine().trim();
            try {
                int n = Integer.parseInt(s);
                if (n >= min && n <= max) return n;
            } catch (NumberFormatException ignored) {}
            PRINTER.println(String.format("잘못된 입력입니다. %d ~ %d 사이의 숫자를 입력해 주세요.", min, max));
        }
    }

    private static Cuisine askCuisine(Scanner sc) {
        System.out.println();
        typeWriter("어떤 종류의 식사를 원하시나요?", 40);
        System.out.println();
        PRINTER.println("1. 한식");
        PRINTER.println("2. 중식");
        PRINTER.println("3. 일식");
        PRINTER.println("4. 양식");
        PRINTER.println("5. 아시안");
        PRINTER.println("6. 분식/햄버거");
        PRINTER.println("7. 샐러드");
        PRINTER.println("8. 디저트");
        System.out.println();
        System.out.println();
        int n = askInt(sc, "번호를 입력해주세요 : ", 1, 8);
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

    private static PriceRange askPrice(Scanner sc) {
        typeWriter("-------------------------------------------------------", 20);
        System.out.println();
        typeWriter("식사를 하기 위해 설정하신 예산이 궁금해요!", 40);
        System.out.println();
        PRINTER.println("1. 10,000원 이하 가성비 식사");
        System.out.println();
        PRINTER.println("2. 10,000 ~ 15,000원 사이의 적당한 식사");
        System.out.println();
        PRINTER.println("3. 15,000원 이상 맛꿀마 식사");
        System.out.println();
        System.out.println();
        int n = askInt(sc, "번호를 입력해주세요 : ", 1, 3);
        return switch (n) {
            case 1 -> PriceRange.UNDER_10000;
            case 2 -> PriceRange.FROM_10000_TO_15000;
            case 3 -> PriceRange.OVER_15000;
            default -> PriceRange.FROM_10000_TO_15000;
        };
    }

    // ===== 샘플 데이터 =====
    private static List<Restaurant> seedRestaurants() {
        return new ArrayList<>(List.of(
                new Restaurant("r001", "진수정", Restaurant.Cuisine.KOREAN, Restaurant.PriceRange.FROM_10000_TO_15000),
                new Restaurant("r002", "백반천국", Restaurant.Cuisine.KOREAN, Restaurant.PriceRange.UNDER_10000),
                new Restaurant("r003", "김밥나라", Restaurant.Cuisine.SNACK_BURGER, Restaurant.PriceRange.UNDER_10000),
                new Restaurant("r004", "맘스터치", Restaurant.Cuisine.SNACK_BURGER, Restaurant.PriceRange.UNDER_10000),
                new Restaurant("r005", "파스타팩토리 정문점", Restaurant.Cuisine.WESTERN, Restaurant.PriceRange.FROM_10000_TO_15000),
                new Restaurant("r006", "스시오", Restaurant.Cuisine.JAPANESE, Restaurant.PriceRange.OVER_15000),
                new Restaurant("r007", "우동야", Restaurant.Cuisine.JAPANESE, Restaurant.PriceRange.FROM_10000_TO_15000),
                new Restaurant("r008", "마라천국", Restaurant.Cuisine.CHINESE, Restaurant.PriceRange.FROM_10000_TO_15000),
                new Restaurant("r009", "짜장명가", Restaurant.Cuisine.CHINESE, Restaurant.PriceRange.UNDER_10000),
                new Restaurant("r010", "조이포", Restaurant.Cuisine.ASIAN, Restaurant.PriceRange.FROM_10000_TO_15000),
                new Restaurant("r011", "반미스토어", Restaurant.Cuisine.SALAD_SANDWICH, Restaurant.PriceRange.FROM_10000_TO_15000),
                new Restaurant("r012", "샐러디", Restaurant.Cuisine.SALAD_SANDWICH, Restaurant.PriceRange.FROM_10000_TO_15000),
                new Restaurant("r013", "브런치카페", Restaurant.Cuisine.WESTERN, Restaurant.PriceRange.OVER_15000),
                new Restaurant("r014", "스위트하우스", Restaurant.Cuisine.DESSERT, Restaurant.PriceRange.UNDER_10000),
                new Restaurant("r015", "황제갈비", Restaurant.Cuisine.KOREAN, Restaurant.PriceRange.OVER_15000),
                new Restaurant("r016", "규카츠정 판교점", Restaurant.Cuisine.JAPANESE, Restaurant.PriceRange.OVER_15000),
                new Restaurant("r017", "청년다방", Restaurant.Cuisine.SNACK_BURGER, Restaurant.PriceRange.FROM_10000_TO_15000),
                new Restaurant("r018", "서호돈가스", Restaurant.Cuisine.JAPANESE, Restaurant.PriceRange.OVER_15000),
                new Restaurant("r019", "감성쪽갈비 판교점", Restaurant.Cuisine.KOREAN, Restaurant.PriceRange.OVER_15000),
                new Restaurant("r020", "라무진 판교점", Restaurant.Cuisine.CHINESE, Restaurant.PriceRange.OVER_15000)
        ));
    }

    private static List<Menu> seedMenus() {
        return new ArrayList<>(List.of(
                new Menu("김치찌개", Cuisine.KOREAN, PriceRange.FROM_10000_TO_15000, List.of("r001","r002")),
                new Menu("된장찌개", Cuisine.KOREAN, PriceRange.UNDER_10000, List.of("r002")),
                new Menu("초밥세트", Cuisine.JAPANESE, PriceRange.OVER_15000, List.of("r006","r007")),
                new Menu("파스타", Cuisine.WESTERN, PriceRange.FROM_10000_TO_15000, List.of("r005","r013")),
                new Menu("마라탕", Cuisine.CHINESE, PriceRange.FROM_10000_TO_15000, List.of("r008")),
                new Menu("짜장면", Cuisine.CHINESE, PriceRange.UNDER_10000, List.of("r009")),
                new Menu("쌀국수", Cuisine.ASIAN, PriceRange.FROM_10000_TO_15000, List.of("r010")),
                new Menu("샐러드볼", Cuisine.SALAD_SANDWICH, PriceRange.FROM_10000_TO_15000, List.of("r011","r012")),
                new Menu("케이크", Cuisine.DESSERT, PriceRange.UNDER_10000, List.of("r014")),
                new Menu("고기", Cuisine.KOREAN, PriceRange.OVER_15000, List.of("r015", "r019", "r020")),
                new Menu("규카츠", Cuisine.JAPANESE, PriceRange.OVER_15000, List.of("r016")),
                new Menu("떡볶이", Cuisine.SNACK_BURGER, PriceRange.FROM_10000_TO_15000, List.of("r017")),
                new Menu("돈까스", Cuisine.JAPANESE, PriceRange.OVER_15000, List.of("r016", "r018"))
        ));
    }


    private static Menu pickRandomMenu(List<Menu> menus) {
        return menus.get(new Random().nextInt(menus.size()));
    }

    public static void main(String[] args) {
        typeWriter("-------------------------------------------------------", 20);
        System.out.println();
        System.out.println();
        typeWriter("안녕하세요 당신의 소중한 식사 시간 !", 40);
        System.out.println();
        typeWriter("메뉴를 고르지 못해 망설이고 있다면 제가 추천해드릴게요 !", 40);
        System.out.println();
        System.out.println();
        typeWriter("-------------------------------------------------------", 20);
        PRINTER.println("");

        Scanner sc = new Scanner(System.in);
        Cuisine cuisine = askCuisine(sc);
        PriceRange price = askPrice(sc);

        List<Menu> filteredMenus = seedMenus().stream()
                .filter(m -> m.getCuisine() == cuisine && m.getPrice() == price)
                .toList();

        if (filteredMenus.isEmpty()) {
            typeWriter("조건에 맞는 메뉴가 없어요. 다시 시도해 주세요!", 40);
            return;
        }

        // 스피너 돌리면서 5초 생각
        AtomicBoolean stop = new AtomicBoolean(false);
        Thread spinner = new SpinnerSound(stop, 200, PRINTER);
        System.out.println();
        typeWriter("선택하신 조건에 맞춰 추천을 준비하고 있어요. 5초만 기다려 주세요!", 40);
        spinner.start();
        try { Thread.sleep(5000); } catch (InterruptedException ignored) {}
        Menu chosenMenu = pickRandomMenu(filteredMenus);
        stop.set(true);
        try { spinner.join(); } catch (InterruptedException ignored) {}

        // 메뉴 출력
        typeWriter("👉 오늘의 추천 메뉴: " + chosenMenu.getName(), 40);

        // 이 메뉴를 파는 식당들 여러 개 출력
        List<Restaurant> available = seedRestaurants().stream()
                .filter(r -> chosenMenu.getRestaurantIds().contains(r.getId()))
                .toList();

        if (available.isEmpty()) {
            System.out.println();
            typeWriter("이 메뉴를 판매하는 식당 정보를 찾을 수 없습니다.", 40);
        } else {
            System.out.println();
            typeWriter("👉 이 메뉴를 맛볼 수 있는 식당들:", 40);
            for (Restaurant r : available) {
                typeWriter(" - " + r.getName(), 20);
            }
        }
    }
}
