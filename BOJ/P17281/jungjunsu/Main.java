/*
 * 구현방법
 * 1번 타자를 고정한 상태에서 permutation을 전부 돌려서 시뮬레이션 하였습니다.
 */

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static final int eightFactorial = 40320;
    static int[][] permutation;
    static int[] sel;
    static int pIdx = 0;

    public static void main(String[] args) throws IOException {
        //System.setIn(Main.class.getResourceAsStream("./input/야구"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int Ans = Integer.MIN_VALUE;
        int N = Integer.parseInt(br.readLine());
        int[][] input = new int[N][9];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 9; j++) {
                input[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        sel = new int[9];
        permutation = new int[eightFactorial][9];

        permutation(0);
        pIdx = -1;
        Deque<Integer> Q = new ArrayDeque<>();
        while (eightFactorial - pIdx++ > 1) {

            int start = 0;
            int sum = 0;

            for (int n = 0; n < N; n++) {
                int outCount = 0;
                int s = start;
                Q.clear();
                while (true) {
                    if (input[n][permutation[pIdx][s%9]] == 0) {
                        outCount++;
                        if (outCount == 3) {
                            start = (s + 1) % 9;
                            break;
                        }
                        s++;
                        continue;
                    }

                    int anta = input[n][permutation[pIdx][s%9]];
                    Q.addFirst(anta);
                    int size = Q.size();
                    for (int i = 0; i < size; i++) {
                        int num = Q.pollFirst();
                        if (i == 0 && num < 4) {Q.addLast(num); continue;}
                        if (num + anta >= 4) {
                            sum++;
                            continue;
                        }
                        Q.addLast(num + anta);
                    }
                    s++;
                }

            }

            Ans = Math.max(Ans, sum);
        }
        bw.write(Ans+""); bw.flush();
    }

    static boolean[] v = new boolean[9];
    private static void permutation(int k) {
        if (k == 3) {sel[k] = 0; permutation(k + 1);}
        if (k == 9) {
            for (int i = 0; i < 9; i++) permutation[pIdx][i] = sel[i];
            pIdx++;
            if (pIdx == eightFactorial) pIdx--;
            return;
        }

        for (int i = 1; i < 9; i++) {
            if (v[i]) continue;
            v[i] = true;
            sel[k] = i;
            permutation(k + 1);
            if (pIdx == eightFactorial) return;
            v[i] = false;
        }
    }


}