import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.StringTokenizer;
// 조합을 직접 만들어서 체크하는 과정
// 만든 sel 배열에서 하나씩 꺼내는 과정
// 숫자 배열에서 하나씩 더해가는 과정 모두 생략이 가능하다 !
public class Solution{
    static int N,numbers[], min, max, sum, sub, mul, div;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for (int i = 1; i <= T; i++) {
            min = Integer.MAX_VALUE;
            max = Integer.MIN_VALUE;

            N = Integer.parseInt(br.readLine());

            st = new StringTokenizer(br.readLine());
            // + - * /
            sum = Integer.parseInt(st.nextToken());
            sub = Integer.parseInt(st.nextToken());
            mul = Integer.parseInt(st.nextToken());
            div = Integer.parseInt(st.nextToken());


            st = new StringTokenizer(br.readLine());
            numbers = new int[N];
            for (int j = 0; j < N; j++) {
                numbers[j] = Integer.parseInt(st.nextToken());
            }

            DFS(numbers[0],0);

            System.out.println("#" + i + " " + (max-min));


        }
    }
    private static void DFS(int result, int cnt) {
        // TODO Auto-generated method stub

        if(cnt == N-1) {
            min = Math.min(min, result);
            max = Math.max(max, result);

            return;
        }
        // 이 로직이 완탐이라는걸 인지해야 한다 !
        if(sum > 0) {
            sum--;
            DFS(result + numbers[cnt+1], cnt+1);
            sum++;
        }
        if(sub > 0) {
            sub--;
            DFS(result - numbers[cnt+1], cnt+1);
            sub++;
        }
        if(mul > 0) {
            mul--;
            DFS(result * numbers[cnt+1], cnt+1);
            mul++;
        }
        if(div > 0) {
            div--;
            DFS(result / numbers[cnt+1], cnt+1);
            div++;
        }

    }
}
