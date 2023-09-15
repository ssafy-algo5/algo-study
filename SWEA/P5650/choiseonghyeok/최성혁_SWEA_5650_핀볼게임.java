package Study;

import java.util.Scanner;

public class 최성혁_SWEA_5650_핀볼게임 {

	/*
	 * 조건대로 구현했다.
	 * 재귀식으로 짰더니 stackvoverflow
	 * 
	 *  
	 */

	static int N;
	static int[][] board;
	static pair firstCor;
	static int max = 0;
	//0:상 , 1:하, 2:좌. 3:우
	static int dir;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static pair[][] wormholes; 
//	static void Move(int x,int y,int firstCnt,int sum) {
//		/*
//		 * 1: 하->우, 좌->상 , 나머지 반대로 튕김
//		 * 2: 상->우, 좌->하, 나머지 반대로 튕김
//		 * 3: 상->좌, 우->하, 나머지 반대로 튕김
//		 * 4: 우 ->상, 하->좌, 나머지 반대로 튕김
//		 * 5: 전방향 다튕김
//		 * 
//		 */
//		
//		//처음 좌표와 같으면(처음입력은 1, 다음에 또만나면2
//		if(firstCor.x==x&&firstCor.y==y) {
//			firstCnt++;
//		}
//		//현재 좌표가 무슨칸인지 검사
//		//블랙홀에 갔거나 처음자리로 다시 돌아갔을경우
//		if(board[x][y]==-1||firstCnt==2) {
//			max=Math.max(max, sum);
//			return;
//		}
//		
//		if(board[x][y]==1) {
//			sum++;
//			if(dir==1) {//하
//				dir=3;
//			}
//			else if(dir==2) {//좌
//				dir=0;
//			}
//			else {//상이나 우일때
//				if(dir==0) {
//					dir=1;
//				}
//				else if(dir==3) {
//					dir=2;
//				}
//			}
//		}
//		else if(board[x][y]==2) {
//			sum++;
//			if(dir==0) { //상
//				dir=3;
//			}
//
//			else if(dir==2) {//좌
//				dir=1;
//			}
//			else {
//				if(dir==1) {//하일때
//					dir=0;
//				}
//				else if(dir==3) {//우일때
//					dir=2;
//				}
//			}
//		}
//		else if(board[x][y]==3) {
//			sum++;
//			if(dir==0) { //상
//				dir=2;
//			}
//
//			else if(dir==3) {//우
//				dir=1;
//			}
//			else {
//				if(dir==1) {//하일때
//					dir=0;
//				}
//				else if(dir==2) {//좌일때
//					dir=3;
//				}
//			}
//		}
//		else if(board[x][y]==4) {
//			sum++;
//			if(dir==2) {//좌
//				dir=0;
//			}
//			else if(dir==1) {//하
//				dir=2;
//			}
//			else {
//				if(dir==0) {//상일때
//					dir=1;
//				}
//				else if(dir==3) {//우일때
//					dir=2;
//				}
//			}
//		}
//		else if(board[x][y]==5) { //전부반대로
//			sum++;
//			if(dir==0) {
//				dir=1;
//			}
//			else if(dir==1) {
//				dir=0;
//			}
//			else if(dir==2) {
//				dir=3;
//			}
//			else if(dir==3) {
//				dir=2;
//			}
//		}
//		//위에까진 블록처리
//		
//		//웜홀 이동
//		else if(board[x][y]==6||board[x][y]==7||board[x][y]==8||board[x][y]==9||board[x][y]==10) {
//			if(x==wormholes[board[x][y]][0].x&&y==wormholes[board[x][y]][0].y) {
//				x=wormholes[board[x][y]][1].x;
//				y=wormholes[board[x][y]][1].y;
//			}
//			else if(x==wormholes[board[x][y]][1].x&&y==wormholes[board[x][y]][1].y) {
//				x=wormholes[board[x][y]][0].x;
//				y=wormholes[board[x][y]][0].y;
//			}
//		}
//
//		
//		
//		int nextX = x + dx[dir];
//		int nextY = y + dy[dir];
//
//		//경계체크
//		if(0<=nextX&&nextX<N&&0<=nextY&&nextY<N) {
//			Move(nextX,nextY,firstCnt,sum);
//		}
//		//경계를 벗어난다는 것은 반대로 방향바꿔서 이동한다는뜻
//		else if(nextX<0) {
//			dir=1;
//			sum++;
//			Move(x,y,firstCnt, sum);
//		}
//		else if(N<=nextX) {
//			dir=0;
//			sum++;
//			Move(x,y,firstCnt, sum);
//		}
//		else if(nextY<0) {
//			dir=3;
//			sum++;
//			Move(x,y,firstCnt, sum);
//		}
//		else if(N<=nextY) {
//			dir=2;
//			sum++;
//			Move(x,y,firstCnt, sum);
//		}
//		
//	}
	
	
	
	static class pair{
		int x;
		int y;
		public pair(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int T =sc.nextInt();
		for(int tc=1;tc<=T;tc++) {
			N = sc.nextInt();
			board = new int[N][N];
			wormholes = new pair[11][2];
			for(int i=0;i<N;i++) {
				for (int j = 0; j < N; j++) {
					board[i][j] = sc.nextInt();
					if(board[i][j]==6||board[i][j]==7||board[i][j]==8||board[i][j]==9||board[i][j]==10) {
						if(wormholes[board[i][j]][0]==null) { //해당 웜홀을 처음만난거면(짝이없는상태면)

							wormholes[board[i][j]][0]=new pair(i,j);
						}
						else {//2번째 웜홀에 입력
							wormholes[board[i][j]][1]=new pair(i,j);

						}
					}
				}
			}

			for(int i=0;i<N;i++) {
				for (int j = 0; j < N; j++) {
					if (board[i][j] == 0) {
						for (int k = 0; k < 4; k++) {
							dir = k;
							firstCor = new pair(i, j);
							// Move(i, j, 0, 0);
							int x = i;
							int y = j;
							int firstCnt = 0;
							int sum = 0;
							while (true) {
								/*
								 * 1: 하->우, 좌->상 , 나머지 반대로 튕김 
								 * 2: 상->우, 좌->하, 나머지 반대로 튕김 
								 * 3: 상->좌, 우->하, 나머지 반대로 튕김 
								 * 4: 좌 ->상, 하->좌, 나머지 반대로 튕김 5: 전방향 다튕김
								 * 
								 */
								//System.out.println(sum);
								// 처음 좌표와 같으면(처음입력은 1, 다음에 또만나면2
								if (firstCor.x == x && firstCor.y == y) {
									firstCnt++;
								}
								// 현재 좌표가 무슨칸인지 검사
								// 블랙홀에 갔거나 처음자리로 다시 돌아갔을경우
								if (board[x][y] == -1 || firstCnt == 2) {
									max = Math.max(max, sum);
									break;
								}

								if (board[x][y] == 1) {
									sum++;
									if (dir == 1) {// 하
										dir = 3;
									} else if (dir == 2) {// 좌
										dir = 0;
									} else {// 상이나 우일때
										if (dir == 0) {
											dir = 1;
										} else if (dir == 3) {
											dir = 2;
										}
									}
								} else if (board[x][y] == 2) {
									sum++;
									if (dir == 0) { // 상
										dir = 3;
									}

									else if (dir == 2) {// 좌
										dir = 1;
									} else {
										if (dir == 1) {// 하일때
											dir = 0;
										} else if (dir == 3) {// 우일때
											dir = 2;
										}
									}
								} else if (board[x][y] == 3) {
									sum++;
									if (dir == 0) { // 상
										dir = 2;
									}

									else if (dir == 3) {// 우
										dir = 1;
									} else {
										if (dir == 1) {// 하일때
											dir = 0;
										} else if (dir == 2) {// 좌일때
											dir = 3;
										}
									}
								} else if (board[x][y] == 4) {
									sum++;
									if (dir == 3) {// 우
										dir = 0;
									} else if (dir == 1) {// 하
										dir = 2;
									} else {
										if (dir == 0) {// 상일때
											dir = 1;
										} else if (dir == 2) {// 좌일때
											dir = 3;
										}
									}
								} else if (board[x][y] == 5) { // 전부반대로
									sum++;
									
									if (dir == 0) {
										dir = 1;
									} else if (dir == 1) {
										dir = 0;
									} else if (dir == 2) {
										dir = 3;
									} else if (dir == 3) {
										dir = 2;
									}
								}
								// 위에까진 블록처리

								// 웜홀 이동
								else if (board[x][y] == 6 || board[x][y] == 7 || board[x][y] == 8 || board[x][y] == 9
										|| board[x][y] == 10) {
									if (x == wormholes[board[x][y]][0].x && y == wormholes[board[x][y]][0].y) {
										int tempx = wormholes[board[x][y]][1].x;
										int tempy = wormholes[board[x][y]][1].y;
										x=tempx;
										y=tempy;
									} else if (x == wormholes[board[x][y]][1].x && y == wormholes[board[x][y]][1].y) {
										int tempx = wormholes[board[x][y]][0].x;
										int tempy = wormholes[board[x][y]][0].y;
										x=tempx;
										y=tempy;
									}
								}

								int nextX = x + dx[dir];
								int nextY = y + dy[dir];

								// 경계체크
								if (0 <= nextX && nextX < N && 0 <= nextY && nextY < N) {
									x = nextX;
									y = nextY;
									continue;
								}
								// 경계를 벗어난다는 것은 반대로 방향바꿔서 이동한다는뜻
								else if (nextX < 0) {
									dir = 1;
									sum++;
									continue;
								} else if (N <= nextX) {
									dir = 0;
									sum++;
									continue;
								} else if (nextY < 0) {
									dir = 3;
									sum++;
									continue;
								} else if (N <= nextY) {
									dir = 2;
									sum++;
									continue;
								}
							}
						}
					}
				}
			}
			System.out.println("#"+tc+" "+max);
			max=0;
		}
	}

}