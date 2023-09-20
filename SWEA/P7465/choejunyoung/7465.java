import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

/*
 * Union&Find
 */
public class Solution {
    static int N, M, parents[];

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for (int i = 1; i <= T; i++) {

            st = new StringTokenizer(br.readLine());
            // 노드의 갯수
            N = Integer.parseInt(st.nextToken());
            // 간선의 갯수
            M = Integer.parseInt(st.nextToken());

            // 노드 생성 (Union&Find 에서는 필요가 없다 ! )
//			ArrayList<ArrayList<Integer>> arr = new ArrayList<ArrayList<Integer>>();
//
//			for (int j = 0; j <= N; j++) {
//				arr.add(new ArrayList<Integer>());
//			}

            // 초기 세
            parents = new int[N+1];
            for (int j = 1; j <= N; j++) {
                parents[j] = j;
            }

            // 간선 주입
            for (int j = 0; j < M; j++) {
                st = new StringTokenizer(br.readLine());
                int A = Integer.parseInt(st.nextToken());
                int B = Integer.parseInt(st.nextToken());

                Union(A,B);
            }
            int answer = 0;

            for (int j = 1; j <= N; j++) {
                if(j == parents[j]) {
                    answer++;
                }
            }

            System.out.println("#" + i + " " + answer);



        }
    }

    private static void Union(int a, int b) {
        int aRoot = Find(a);
        int bRoot = Find(b);

        parents[bRoot] = aRoot;

    }

    private static int Find(int a) {
        // TODO Auto-generated method stub
        if(a == parents[a]) return a;
        else return parents[a] = Find(parents[a]);
    }
}
