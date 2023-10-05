/*
	구현방법:
	영향을 중복해서 받을때 최대 충전량을 얻는 부분을 잘 처리하면 되는 시뮬레이션 문제였던것 같습니다.
 */
import java.io.*;
import java.util.*;
public class Solution {

    public static void main(String[] args) throws IOException {
        //System.setIn(new FileInputStream("./input/무선 충전"));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        int[] dr = {0, -1, 0, 1, 0};
        int[] dc = {0, 0, 1, 0, -1};

        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine());
            int ANS = 0;
            int M = Integer.parseInt(st.nextToken());
            int A = Integer.parseInt(st.nextToken());
            // A = 배터리 종류, 0~3 index : 0-1 = 위치(Y,X), 2 = 충전범위(C), 3 = 처리량(P)
            int[][] BC = new int[A][4];
            int[] moveA = new int[M + 1];
            int[] moveB = new int[M + 1];
            int AX = 0;
            int AY = 0;
            int BX = 9;
            int BY = 9;

            // A, B의 이동 정보 저장
            moveA[0] = 0;
            moveB[0] = 0;
            for (int ab = 0; ab < 2; ab++) {
                st = new StringTokenizer(br.readLine());
                for (int m = 1; m <= M; m++) {
                    if (ab == 0) {moveA[m] = Integer.parseInt(st.nextToken()); continue;}
                    moveB[m] = Integer.parseInt(st.nextToken());
                }
            }
            //System.out.println(Arrays.toString(moveA));
            //System.out.println(Arrays.toString(moveB));
            //System.out.println();
            // BC 정보 저장
            for (int a = 0; a < A; a++) {
                st = new StringTokenizer(br.readLine());
                // BC의 좌표
                BC[a][1] = Integer.parseInt(st.nextToken()) - 1;
                BC[a][0] = Integer.parseInt(st.nextToken()) - 1;
                BC[a][2] = Integer.parseInt(st.nextToken());
                BC[a][3] = Integer.parseInt(st.nextToken());
            }

            for (int move = 0; move < M + 1; move++) {
                int[] effectA = new int[A];
                int[] effectB = new int[A];
                AY += dr[moveA[move]];
                AX += dc[moveA[move]];
                BY += dr[moveB[move]];
                BX += dc[moveB[move]];

                //System.out.println(ANS);
                //System.out.println("AY: "+AY+" AX: "+AX+" || BY: "+BY+" BX: "+BX);
                // A와 B가 이동했을때, 영향을 받는 BC를 effectA/B 배열에 저장
                //System.out.print("A EFFECT : ");
                for (int eCheck = 0; eCheck < A; eCheck++) {
                    int dist = Math.abs(BC[eCheck][0] - AY) + Math.abs(BC[eCheck][1] - AX);
                    if (dist <= BC[eCheck][2]) {effectA[eCheck] = BC[eCheck][3]; /*System.out.print(eCheck +" ");*/}
                }
                //System.out.print("// B EFFECT : ");
                for (int eCheck = 0; eCheck < A; eCheck++) {
                    int dist = Math.abs(BC[eCheck][0] - BY) + Math.abs(BC[eCheck][1] - BX);
                    if (dist <= BC[eCheck][2]) {effectB[eCheck] = BC[eCheck][3]; /*System.out.print(eCheck +" ");*/}
                }
                //System.out.println();
                //System.out.println();
                int maxA = 0;
                int maxB = 0;
                int idxA = 0;
                int idxB = 0;
                for (int a = 0; a < A; a++) {
                    if (maxA < effectA[a]) {
                        maxA = effectA[a];
                        idxA = a;
                    }
                }
                for (int b = 0; b < A; b++) {
                    if (maxB < effectB[b]) {
                        maxB = effectB[b];
                        idxB = b;
                    }
                }

                if (idxA != idxB) {ANS += maxA + maxB; continue;}
                if (maxA != maxB && idxA == idxB) {ANS += maxA + maxB; continue;}
                int max1 = maxA;
                int max2 = 0;
                effectA[idxA] = 0;
                effectB[idxB] = 0;
                maxA = 0;
                maxB = 0;
                for (int a = 0; a < A; a++) {
                    maxA = Math.max(maxA, effectA[a]);
                }
                for (int b = 0; b < A; b++) {
                    maxB = Math.max(maxB, effectB[b]);
                }
                if (maxA >= maxB) max2 = maxA;
                if (maxB > maxA) max2 = maxB;
                if (maxA == 0 && maxB == 0) max2 = 0;
                ANS += max1 + max2;

            }
            bw.write("#"+tc+" "+ANS+"\n");

        }
        bw.flush();

    }


}
