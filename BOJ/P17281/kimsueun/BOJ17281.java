/*

1번 순서는 4번 타자로 결정되었기 때문에 나머지 2~9번 선수들의 순서만 결정해주면 됨.
-> 순열을 이용하여 각 이닝의 선수 순서를 결정할 것임.

9명의 순서를 모두 정했다면 정해진 순서에 의해서 게임을 진행시킴.
게임을 해당 선수의 결과인 0~4를 각 케이스를 나눠줌.
이렇게 매 순열의 순서마다 게임을 진행시키고 얻을 수 있는 최대 점수를 구함.

 */

import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n,maxScore=Integer.MIN_VALUE;
    static int[][] result; //각 이닝에 각 선수가 내는 결과
    static int[] turn; //이닝의 순서, 한 번 정해진 순서는 매 이닝마다 동일하게 적용됨
                        //turn[a]=b : a번 타자는 b번 선수가 됨
    static boolean[] visited; //순열 구할 때 필요한 방문 배열

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());

        result = new int[n+1][10]; //이닝과 사람수 1-indexed!!
        turn = new int[10]; //해당 이닝에서 정해진 순서
        visited = new boolean[10]; //순열을 구할 때 필요한 방문체크배열

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= 9; j++) {
                result[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        //1번 선수는 4번 타자로 정해져 있음
        visited[4] = true;
        turn[4]=1; //4번 타자는 1번 선수

        //1. 선수둘의 순서를 순열을 이용해서 정해줌
        perm(2); //2번 선수부터 순서 정해줘야 함

        System.out.println(maxScore);
    }

    private static void perm(int idx) {
        if(idx==10){
            //2. 10명의 순서를 다 정했다면 게임을 진행함
            play();
            return;
        }

        //순열 구해주기
        for (int i = 1; i <= 9; i++) { //i: 치는 순서
            if(visited[i]) continue; //이미 방문했다면 선택하지 않음

            visited[i]=true;
            turn[i]=idx; //i번째 순서에 idx번 선수를 넣음!!! *헷갈리니 조심*
            perm(idx+1);
            visited[i]=false;
        }
    }

    private static void play() {
        //3. 해당 순서의 타자가 공을 침.
        //해당 순서를 이용하여 모든 이닝에서의 결과 점수를 구함.

        int score = 0; //이번 순서를 통한 경기의 점수
        int cntPlay=1; //선수의 순서는 이닝마다 바뀌는 것이 아니고 계속 이어짐, 1번 타자부터 시작

        for (int i = 1; i <= n; i++) { //n번 이닝이 있음
            //새로운 이닝이 시작되면 초기화해줌
            int[] ru = new int[4]; //몇 루에 누구 있는지 (1,2,3루만 있으면 됨), 1-indexed
            int out=0; //현재 이닝에서 아웃 횟수

            while(out<3){
                int curPlayer = turn[cntPlay];
                int cur = result[i][curPlayer];

                if(cur==1){
                    //안타인 경우
                    //루에 있는 모든 선수가 1루씩 진루함

                    //홈으로 들어온 선수가 있다면
                    if(ru[3]!=0) score++;
                    
                    //선수를 1칸씩 밀어줌
                    ru[3] = ru[2];
                    ru[2] = ru[1];
                    ru[1] = curPlayer;

                }
                else if(cur==2){
                    //2루타인 경우
                    if(ru[2]!=0) score++;
                    if(ru[3]!=0) score++;

                    //선수를 2칸씩 밀어줌
                    ru[3] = ru[1];
                    ru[2] = curPlayer;
                    ru[1] = 0; //1루는 비게 됨
                }
                else if(cur==3){
                    if(ru[1]!=0) score++;
                    if(ru[2]!=0) score++;
                    if(ru[3]!=0) score++;

                    //선수를 3칸씩 밀어줌
                    ru[3] = curPlayer;
                    ru[2] = 0; //2루는 비게 됨
                    ru[1] = 0; //1루는 비게 됨
                }
                else if(cur==4){
                    if(ru[1]!=0) score++;
                    if(ru[2]!=0) score++;
                    if(ru[3]!=0) score++;

                    //현재 플레이어는 무조건 홈으로 들어오므로 점수 +1
                    score++;

                    //모든 도루가 빔
                    ru[3] = 0;
                    ru[2] = 0;
                    ru[1] = 0;
                }
                else{ //cur==0
                    //선수들은 아무것도 하지 않음

                    //out이 하나 증가함
                    out++;
                }
                cntPlay++; //다음 선수가 나오기 위해 순서 +1

                // 10번 타자가 되면 다시 1번 타자로 되돌림
                if(cntPlay>=10) cntPlay=1;
            }
        }

        //매 조합 중 가장 최대 점수를 구해줌
        maxScore = Math.max(maxScore, score);
    }
}