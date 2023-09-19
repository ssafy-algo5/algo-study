/*
    구현방법:
    BFS를 도는데, 외부공기를 flood fill 해주었습니다.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static int[][] map;
    static ArrayList<Integer[]> list = new ArrayList<>();
    static LinkedList<Integer[]> tmp = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];

        for (int r = 0; r < N; r++) {
            st = new StringTokenizer(in.readLine());
            for (int c = 0; c < M; c++) {
                map[r][c] = Integer.parseInt(st.nextToken());
                if (map[r][c] == 1) list.add(new Integer[] {r,c});
            }
        }

        tmp.add(new Integer[] {0, 0});

        int cnt = 0;
        while(!list.isEmpty()) {
            cnt++;
            bfs();
            go();
        }
        System.out.println(cnt);
    }

    private static void go() {
        for (int i = 0; i < list.size(); i++) {
            Integer[] p = list.get(i);

            int cnt = 0;
            for (int j = 0; j < 4; j++) {
                int nr = p[0] + dr[j];
                int nc = p[1] + dc[j];
                if (map[nr][nc] == 3) {
                    cnt++;
                }
            }
            if(cnt >= 2) {
                tmp.add(new Integer[] {p[0], p[1]});
                list.remove(i--);
            }
        }
    }

    static int[] dr = {1, -1, 0, 0};
    static int[] dc = {0, 0, 1, -1};
    private static void bfs() {
        while(!tmp.isEmpty()) {
            Integer[] element = tmp.poll();
            int r = element[0];
            int c = element[1];
            map[r][c] = 3;

            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                if (nr < 0 || nc < 0 || nr >= N || nc >= M || map[nr][nc] != 0) continue;
                map[nr][nc] = 3;
                tmp.add(new Integer[] {nr, nc});
            }
        }
    }

    private static void print() {
        for (int[] y : map) {
            for (int x : y) {
                System.out.print(x+" ");
            } System.out.println();
        } System.out.println();
    }
}

