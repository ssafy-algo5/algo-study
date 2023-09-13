class Solution {
    int answer = 0; //answer ������ �ִ� ���� ���� ������ �����Դϴ�. �ʱⰪ�� 0���� �����˴ϴ�.
//    info �迭�� �� ���(�� �Ǵ� ����)�� ������ ��Ÿ����, edges �迭�� �׷����� ���� ������ ��Ÿ���ϴ�.
    public int solution(int[] info, int[][] edges) {
        dfs(0,new boolean[info.length],0,0,info, edges);
        return answer;
    }
    // �� �Լ��� ���� �켱 Ž���� �����Ͽ� �׷����� Ž���ϰ� �ִ� ���� ���� ����մϴ�.
//    idx: ���� ����� �ε���
//    visited: �湮�� ��带 �����ϱ� ���� �迭
//    sheep: ���� ���� ��
//    wolf: ���� ������ ��
//    info: ��庰 �� �Ǵ� ������ ���� �迭
//    edges: �׷����� ���� ���� �迭
    private void dfs(int idx, boolean[] visited, int sheep, int wolf, int[] info, int[][] edges){
        visited[idx] = true; //���� ��带 �湮������ ǥ���մϴ�.
        //  ���� ��尡 ���� ��� ���� ���� ������Ű�� �ִ� ���� ���� answer�� ������Ʈ�մϴ�. ��尡 ������ ��� ������ ���� ������ŵ�ϴ�.
        if(info[idx] == 0){
            sheep++;
            answer = Math.max(sheep, answer);
        }else{
            wolf++;
        }
        // ���� ���� ������ �� ������ ��� �ش� ��θ� Ž������ �ʰ� �����մϴ�.
        if (sheep <= wolf) return;
        // ���� ���� �迭 edges�� ��ȸ�ϸ� ����� ��带 ã���ϴ�.
        //  ���� ���� ���(edge[0])�� �̹� �湮�Ǿ���, ����� ���(edge[1])�� ���� �湮���� �ʾҴٸ� ���ο� �湮 �迭 newVisited�� �����Ͽ� �ش� ��带 �湮�ϰ� ��������� dfs �Լ��� ȣ���մϴ�.
        for(int[] edge: edges){
            if(visited[edge[0]] && !visited[edge[1]]){
                boolean[] newVisited = visited.clone();
                dfs(edge[1], newVisited, sheep, wolf, info,edges);
            }
        }
    }
}