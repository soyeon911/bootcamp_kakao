// monitor lock(Synchronized 사용)
// 이 객체(SharedPrinter)를 모니터 락으로 사용하자
// 한 번에 하나의 쓰레드만 통과

public class SharedPrinter {
    // synchronized 메서드 사용 -> 같은 객체에 대해 한 번에 하나의 스레드만 접근 가능함
    public synchronized void print(String s){
        System.out.println(s);
        System.out.flush();     // 버퍼를 비워서 즉시 출력하게 함
    }
    public synchronized void println(String s){
        System.out.println(s);
        System.out.flush();
    }
}