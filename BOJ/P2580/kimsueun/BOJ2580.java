import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int[][] arr = new int[9][9];
    static ArrayList<int[]> list;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();

        list = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 9; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
                
              //빈칸이면 arraylist에 넣어주기
                if(arr[i][j]==0) list.add(new int[] {i, j});
            }
        }
        recursive(0);
    }

    private static void recursive(int cnt) {
        if(cnt==list.size()) {
            //출력하기
            for(int[] a : arr){
                for(int b : a){
                    sb.append(b+" ");
                }
                sb.append('\n');
            }

            System.out.println(sb);
            System.exit(0); //return 대신 아예 프로그램 종료
        }

        //cnt는 채워야 빈 칸의 인덱스임
        int x = list.get(cnt)[0];
        int y = list.get(cnt)[1];

        boolean[] check = new boolean[10];
        for (int k = 0; k < 9; k++) {
            //arr[i][k] : i번째 줄에 있는 칸의 값
            check[arr[x][k]] = true;
            check[arr[k][y]] = true;
        }


        //해당 3x3 한 칸 살펴보기
        int r = (x / 3) * 3;
        int c = (y / 3) * 3;
        for (int k = r; k < r + 3; k++) {
            for (int l = c; l < c + 3; l++) {
                check[arr[k][l]] = true;
            }
        }

        for (int i = 1; i <= 9; i++) {
            //해당 좌표에 1~9 중 수 중 넣을 수 있는 수라면 그대로 진행함
            if(!check[i]){
                arr[x][y] = i;
                recursive(cnt+1);
                arr[x][y] = 0;
            }
        }
    }

}
