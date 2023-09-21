import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 충격은 세로로 전달되기에 세로를 검색한다
 * 한줄의 세로에 최소로 연결된 A or B 만 존재하면 된다 !
 * 아예 사용을 안할때 부터 1~W 만큼의 약품을 조합으로 구해보면서 -> 정답을 구해본다 !
 *
 * 조합으로 해당 행을 하나로 다 바꿔보는 작업 (A 한번 B 한번 !)
 * 원본 배열을 말단에서 복사해서 쓴다
 *
 * 조합을 0 ~ W 개 만큼을 뽑아보는데 1개를 뽑는 모든 경우를 보고 2로 넘어가야 하기에 파워셋 부적절
 * 하지만 적은 약품을 사용한 조합을 이용해서 더 많은 약품들은 백트레킹 시킨다면 ? -> 가능하다 !
 * 재귀는 결국 문제에 나온 것을 그대로 구현한다 ! -> A 약품 쓸때 B 약품쓸때 !
 *
 * DFS로 조합을 뽑아준다 ! ->
 * 약품을 주입 시켜주는 메소드 제작 !
 * 체크 메소드를 만들어서 가능한지 체크 !
 *
 * 재귀를 어떻게 처리 할지도 문제에 핵심이였다 a b 바꾸지 않는것을 어떻게 구분할지도 중요했다
 * 푼 방식을 기억해놓기 !
 */
public class Solution {
    static int D, W, K, answer;
    static int map[][], copy[][], sel[];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            answer = Integer.MAX_VALUE;
            st = new StringTokenizer(br.readLine());
            D = Integer.parseInt(st.nextToken()); // 세로
            W = Integer.parseInt(st.nextToken()); // 가로
            K = Integer.parseInt(st.nextToken()); // 세로에 연속해서 나와야 하는 갯수 (a, b)

            map = new int[D][W];
            copy = new int[D][W];
            sel = new int[D]; // -> 0 or 1 or -1 로 구분해서 -1 경우에는 원본 그대로 사용 !

            for (int i = 0; i < D; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < W; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            // 없어도 될때
            DFS(0, 0);
            System.out.println("#" + tc + " " + answer);

        }

    }

    private static void DFS(int cnt, int times) {
        // TODO Auto-generated method stub
        // 가지치기
        if (times >= answer)
            return;

        // basis part
        if (cnt == D) {
            for (int i = 0; i < D; i++) {
                copy[i] = map[i].clone();
            }

            change();

            if (check()) { // 최소횟수 갱신 해보기
                //print(copy);
                answer = Math.min(answer, times);
            }

            return;
        }

        // inductive part
        sel[cnt] = -1;// 원본 배열 사용
        DFS(cnt + 1, times); // 약품 주입을 하지 않는다
        sel[cnt] = 0; // A
        DFS(cnt + 1, times + 1);
        sel[cnt] = 1; // B
        DFS(cnt + 1, times + 1);

    }

    private static void change() {
        // TODO Auto-generated method stub
        for (int i = 0; i < D; i++) {

            switch (sel[i]) {

                case 0:
                    for (int j = 0; j < W; j++) {
                        copy[i][j] = 0;
                    }
                    break;

                case 1:
                    for (int j = 0; j < W; j++) {
                        copy[i][j] = 1;
                    }
                    break;

                default:
                    break;
            }

        }
    }

    private static boolean check() {
        // TODO Auto-generated method stub
        for (int i = 0; i < W; i++) {
            int aCnt = 0;
            int bCnt = 0;
            boolean flag = false;

            for (int j = 0; j < D; j++) {
                if (0 == copy[j][i]) {
                    aCnt++;
                    bCnt = 0;
                } else {
                    aCnt = 0;
                    bCnt++;
                }
                // 가능하다면 여기서 다 걸리기 때문에
                if (aCnt == K || bCnt == K) {
                    flag = true;
                    break; //
                }
                // 여기서 걸리지 않아야 한다 !
            }
            if (!flag) {
                return false;
            }
        }

        return true;
    }

    private static void print(int[][] map) {
        // TODO Auto-generated method stub
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}
