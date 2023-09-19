/*
    구현방법:
    ok 배열에 3x3 영역, 가로세로에 가능한 숫자를 true로 두고 백트래킹 하였습니다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    static final int N = 9;
    static int[][] map;
    static ArrayList<Integer[]> list = new ArrayList<>();
    static boolean endCheck;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        map = new int[N][N];

        for (int r = 0; r < N; r++) {
            st = new StringTokenizer(in.readLine());
            for (int c = 0; c < N; c++) {
                map[r][c] = Integer.parseInt(st.nextToken());
                if (map[r][c] == 0) list.add(new Integer[] {r, c});
            }
        }
        fillMap(0);
    }

    private static void fillMap(int index) {
        if (endCheck) return;

        if (index == list.size()) {
            endCheck = true;
            print();
        } else {
            Integer[] pos = list.get(index);
            // 가능한 숫자를 담는 배열
            boolean[] ok = new boolean[10];

            for (int i = 1; i < 10; i++) {
                ok[i] = check(pos[0], pos[1], i);
            }
            for (int i = 1; i < 10; i++) {
                if (!ok[i]) continue;
                map[pos[0]][pos[1]] = i;
                fillMap(index + 1);
                map[pos[0]][pos[1]] = 0;
            }
        }
    }

    private static boolean check(int r, int c, int num) {
        // 가로 세로 검사
        for (int i = 0; i < N; i++) {
            if (map[r][i] == num) return false;
            if (map[i][c] == num) return false;
        }
        // 영역 검사
        int sr = r/3 * 3;
        int sc = c/3 * 3;
        for (int ar = sr; ar < sr+3; ar++) {
            for (int ac = sc; ac < sc+3; ac++) {
                if (map[ar][ac] == num) return false;
            }
        }
        return true;
    }
    private static void print() {
        for (int[] y : map) {
            for (int x : y) {
                System.out.print(x+" ");
            } System.out.println();
        }
    }
}

