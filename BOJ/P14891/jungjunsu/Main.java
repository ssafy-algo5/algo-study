/*
    구현방법:
    저는 배열에 12시 방향의 극부터 순서대로 입력해서 다루었습니다.
    4개의 톱니바퀴가 어떻게 회전하는지 계산하여 v배열에 담아두고,
    위의 극 배열을 수정하는 로직을 짜서 시뮬레이션을 구현했습니다.
 */

import java.io.*;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        long Ans = 0;

        // 톱니바퀴들
        int[][] tools = new int[4][8];
        // 회전 방향 저장하는 배열
        // 0 : 정지, 1 : 시계, -1 : 반시계
        int[] v = new int[4];

        for (int i = 0; i < 4; i++) {
            String input = in.readLine();

            for (int d = 0; d < 8; d++) {
                tools[i][d] = input.charAt(d) - '0';
            }
        }

        int K = Integer.parseInt(in.readLine()); // 회전횟수 : 1 ≤ K ≤ 100
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(in.readLine());
            int N = Integer.parseInt(st.nextToken()) - 1; // 회전 시킬 톱니바퀴 번호
            int V = Integer.parseInt(st.nextToken()); // 회전 방향

            // N번째 톱니바퀴의 회전방향 : V
            v[N] = V;

            // 회전 방향 계산
            // tmp 인접한(좌 or 우) 톱니바퀴의 회전 방향 저장
            int tmp = V;
            for (int k = N - 1; k >= 0; k--) {
                // 같은 극 이라면
                if (tools[k][2] == tools[k+1][6]) {
                    v[k] = 0;
                    tmp = v[k];
                }
                // 다른 극 이라면
                else {
                    if (tmp == 0) {
                        v[k] = 0;
                        continue;
                    }
                    v[k] = tmp == -1 ? 1 : -1;
                    tmp = v[k];
                }
            }
            tmp = V;
            for (int k = N + 1; k < 4; k++) {
                // 같은 극 이라면
                if (tools[k][6] == tools[k-1][2]) {
                    v[k] = 0;
                    tmp = v[k];
                }
                // 다른 극 이라면
                else {
                    if (tmp == 0) {
                        v[k] = 0;
                        continue;
                    }
                    v[k] = tmp == -1 ? 1 : -1;
                    tmp = v[k];
                }
            }

            // 회전 진행
            for (int k = 0; k < 4; k++) {
                if (v[k] == 0) continue;
                // 1 : 시계방향, -1 : 반시계 방향
                if (v[k] == 1) {
                    int last = tools[k][7];
                    for (int s = 7; s >= 1; s--) {
                        tools[k][s] = tools[k][s-1];
                    }
                    tools[k][0] = last;
                } else {
                    int first = tools[k][0];
                    for (int s = 0; s <= 6; s++) {
                        tools[k][s] = tools[k][s+1];
                    }
                    tools[k][7] = first;
                }
            }

        }

        // 점수계산
        for (int i = 0; i < 4; i++) {
            Ans += tools[i][0] == 1 ? Math.pow(2, i) : 0;
        }
        bw.write(Ans+"");
        bw.flush();

    }

}

