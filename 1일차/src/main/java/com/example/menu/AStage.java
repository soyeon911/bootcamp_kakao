package com.example.menu;

import com.example.menu.model.Restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class AStage {

    // 스피너 + 효과음(터미널에 따라 벨이 안날 수도 있음)
    static class SpinnerSound extends Thread {
        private static final char[] FRAMES = {'|', '/', '-', '\\'};
        private final AtomicBoolean stop;
        private final long intervalMillis;

        SpinnerSound(AtomicBoolean stop, long intervalMillis) {
            setDaemon(true);
            this.stop = stop;
            this.intervalMillis = intervalMillis;
        }

        @Override
        public void run() {
            int i = 0;
            while (!stop.get()) {
                System.out.print("\r생각 중... " + FRAMES[i % FRAMES.length] + " ♪");
                System.out.print("\007"); // 콘솔 벨 (일부 터미널에서만 소리남)
                System.out.flush();
                i++;
                try {
                    Thread.sleep(intervalMillis);
                } catch (InterruptedException ignored) {}
            }
            // 라인 정리
            System.out.print("\r" + " ".repeat(30) + "\r");
        }
    }

    private static Restaurant pickOne(List<Restaurant> candidates) {
        if (candidates.isEmpty()) return null;
        return candidates.get(new Random().nextInt(candidates.size()));
    }

    public static void main(String[] args) {
        // (필요하면 여기서 한식/거리/가격 필터를 적용한 결과 리스트를 넣으면 된다)
        List<Restaurant> candidates = new ArrayList<>(List.of(
                new Restaurant("r001", "진수정"),
                new Restaurant("r014", "스시오"),
                new Restaurant("r020", "맘스터치"),
                new Restaurant("r030", "파스타팩토리 정문점")
        ));

        AtomicBoolean stop = new AtomicBoolean(false);
        Thread spinner = new SpinnerSound(stop, 200);

        System.out.println("메뉴를 추천하고 있어요. 5초만 기다려 주세요!");
        spinner.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {}

        Restaurant choice = pickOne(candidates);

        stop.set();
        try {
            spinner.join();
        } catch (InterruptedException ignored) {}

        if (choice != null) {
            System.out.println("👉 오늘의 추천: " + choice.getName());
        } else {
            System.out.println("조건에 맞는 후보가 없어요.");
        }
    }
}
