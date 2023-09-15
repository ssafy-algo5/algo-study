 import java.io.*;
import java.util.*;

/*
NEW SAMPLE

input
8 9
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 1 1 0 0 0 1 1 0
0 1 0 1 1 1 0 1 0
0 1 0 0 1 0 0 1 0
0 1 0 1 1 1 0 1 0
0 1 1 0 0 0 1 1 0
0 0 0 0 0 0 0 0 0

output
3
 */
public class Main {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int n,m,cnt,time;
    static boolean[][] cheese, isOutside, visited;

    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        //초기화
        cheese = new boolean[n][m]; //치즈냐 공기냐

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                if(Integer.parseInt(st.nextToken())==1) {
                    cheese[i][j] = true;
                    cnt++; //치즈 개수 세기
                }
                else cheese[i][j] = false;
            }
        }

        while(true){
            if(cnt<=0) break; //치즈가 다 녹았다면 끝

            time++; //시간 1 증가

            //매번 bfs를 돌릴 때마다 새로 갱신해야 함
            visited = new boolean[n][m];
            isOutside = new boolean[n][m]; //밖이냐 안이냐

            //bfs를 돌려서 현재 남아있는 치즈를 이용하여 새롭게 밖인 좌표가 어디인지 체크함
            bfs();

//            System.out.println("*isOutside");
//            //isOutside가 잘 되는지 확인용 코드
//            for (int i = 0; i < n; i++) {
//                for (int j = 0; j < m; j++) {
//                    if(isOutside[i][j])
//                        System.out.print("1 ");
//                    else System.out.print("0 ");
//                }
//                System.out.println();
//            }

            //cheese가 true이면서 isOutside가 true인 애들을 대상으로 4분면을 살펴봄
            //4분면을 봤는데 isOutside이면서 cheese가 아닌 좌표가 2개 이상인지 체크
            //2개 이상이라면 cheese를 false로 해주고 cnt로 감소시킴
            check();

//            System.out.println("cheese");
//            for (int i = 0; i < n; i++) {
//                for (int j = 0; j < m; j++) {
//                    if(cheese[i][j])
//                        System.out.print("1 ");
//                    else System.out.print("0 ");
//                }
//                System.out.println();
//            }
        }
        System.out.println(time);
    }

    private static void check() {
        ArrayList<int[]> list = new ArrayList<>(); //삭제될 치즈를 저장함

        //치즈이면서 밖인 애들을 찾음
        //만약 시간초과가 난다면 치즈리스트를 만들어서 nxm을 보지 않도록 짜기
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int num=0; //밖이면서 치즈가 아닌 좌표 개수 셀 변수

                if(cheese[i][j] && isOutside[i][j]){
                    //치즈이면서 밖인 애들의 4방을 봄
                    for (int dir = 0; dir < 4; dir++) {
                        int nx = i + dx[dir];
                        int ny = j + dy[dir];

                        if(OOB(nx,ny)) continue; //범위를 벗어났으면 끝
                        if(isOutside[nx][ny]&&!cheese[nx][ny]) { //밖이면서 치즈인 경우
                            num++;
                        }
                    }
                }

                //맞닿아있는 밖의 공기가 2개 이상이라면 치즈는 녹음
                if(num>=2){
                    cnt-=1; //현재 좌표의 치즈 개수 빼줌
                    list.add(new int[] {i,j}); //현재 치즈가 녹음, 녹여야 하는 치즈는 바로 녹이면 안됨
                }
            }
        }

        //녹여야 하는 치즈인지 아닌지를 모두 구분짓고 나서
        //녹여야 하는 치즈 한번에 삭제함
        for(int[] cur : list){
            cheese[cur[0]][cur[1]] = false;
        }
    }

    private static void bfs() {
        //(0,0)부터 시작해서 bfs를 돌림
        Queue<int[]> q = new ArrayDeque<>();

        isOutside[0][0]=true;
        visited[0][0]=true;
        q.add(new int[] {0,0});

        while(!q.isEmpty()){
            int[] cur = q.poll();
            int curX = cur[0];
            int curY = cur[1];

            for(int dir=0; dir<4; dir++){
                int nx = curX + dx[dir];
                int ny = curY + dy[dir];

                //범위를 벗어났거나 이미 방문한 점이면 탐색 멈춤
                if(OOB(nx,ny)) continue;
                if(visited[nx][ny]) continue;

                isOutside[nx][ny] = true; //밖과 연결된 곳이므로 밖이라고 표시함

                visited[nx][ny] = true;
                //밖과 닿아있는 치즈를 찾는 것이기 때문에 치즈가 아닐 시에만 큐에 넣어서
                //치즈가 아니면서 밖인 좌표의 4방향을 찾아봄
                if(!cheese[nx][ny]){
                    q.add(new int[] {nx,ny});
                }
            }
        }
    }

    private static boolean OOB(int nx, int ny) {
        return nx<0||nx>=n||ny<0||ny>=m;
    }
}

