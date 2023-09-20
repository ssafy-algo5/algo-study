import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;
/*
 * 이 문제는 단계를 시간으로 생각하면 안된다 ! 왜냐? 소용돌이를 건너면 해당 시간을 먼저 앞서가는 애들이 생기기 때문이다 그렇기에 큐에 각
 * 숫자를 더해주며 기록해야한다 ! 최소 비용이기 때문에 횟수는 상관이 없다 !
 **/
public class Solution {
    static int dx[] = { -1, 0, 1, 0 };
    static int dy[] = { 0, 1, 0, -1 };
    static int map[][];
    static boolean ch[][];
    static int start_x, start_y, end_x, end_y, N;

    static class Point {
        int x, y, time;

        public Point(int x, int y, int time) {
            this.x = x;
            this.y = y;
            this.time = time;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine());

            map = new int[N][N];
            ch = new boolean[N][N];

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            st = new StringTokenizer(br.readLine());
            start_x = Integer.parseInt(st.nextToken());
            start_y = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            end_x = Integer.parseInt(st.nextToken());
            end_y = Integer.parseInt(st.nextToken());

            // 0 그냥 길
            // 1 장애물 ( 지나갈수 없다 )
            // 2 소용돌이

            // print(dis);

            int answer = BFS();
            if (!ch[end_x][end_y]) {
                answer = -1;
            }
            System.out.println("#" + tc + " " + answer);

        }

    }

    private static int BFS() {
        // TODO Auto-generated method stub
        int answer = 0;

        Queue<Point> Q = new ArrayDeque<>();
        Q.add(new Point(start_x, start_y, 0));
        ch[start_x][start_y] = true;

        while (!Q.isEmpty()) {

            Point p = Q.poll();
            int x = p.x;
            int y = p.y;
            int time = p.time;
            if (x == end_x && y == end_y) {
                answer = time;
                break;
            }

            for (int j = 0; j < 4; j++) {
                int nx = x + dx[j];
                int ny = y + dy[j];

                if (nx >= N || nx < 0 || ny >= N || ny < 0 || map[nx][ny] == 1 || ch[nx][ny])
                    continue;

                // 2초 5초 8초 에는 소용돌이가 없는 상태이다 !
                if (map[nx][ny] == 0) {
                    Q.add(new Point(nx, ny, time + 1));
                    ch[nx][ny] = true;
                }
                // 소용돌이가 있는 자리에 시간이 소용돌이가 존재한다면 2초 기다렸다가 가야한다
                else if (map[nx][ny] == 2) {
                    if((time % 3) == 2) {
                        Q.add(new Point(nx, ny, time + 1));
                        ch[nx][ny] = true;

                    }else {
                        Q.add(new Point(x, y, time + 1));
                    }
                }
            }
        }

        return answer;

    }
}


