class Solution {
    int answer = 0; //answer 변수는 최대 양의 수를 저장할 변수입니다. 초기값은 0으로 설정됩니다.
    //    info 배열은 각 노드(양 또는 늑대)의 정보를 나타내며, edges 배열은 그래프의 간선 정보를 나타냅니다.
    public int solution(int[] info, int[][] edges) {
        dfs(0,new boolean[info.length],0,0,info, edges);
        return answer;
    }
    // 이 함수는 깊이 우선 탐색을 수행하여 그래프를 탐색하고 최대 양의 수를 계산합니다.
//    idx: 현재 노드의 인덱스
//    visited: 방문한 노드를 추적하기 위한 배열
//    sheep: 현재 양의 수
//    wolf: 현재 늑대의 수
//    info: 노드별 양 또는 늑대의 정보 배열
//    edges: 그래프의 간선 정보 배열
    private void dfs(int idx, boolean[] visited, int sheep, int wolf, int[] info, int[][] edges){
        visited[idx] = true; //현재 노드를 방문했음을 표시합니다.
        //  현재 노드가 양인 경우 양의 수를 증가시키고 최대 양의 수인 answer를 업데이트합니다. 노드가 늑대인 경우 늑대의 수를 증가시킵니다.
        if(info[idx] == 0){
            sheep++;
            answer = Math.max(sheep, answer);
        }else{
            wolf++;
        }
        // 양의 수가 늑대의 수 이하일 경우 해당 경로를 탐색하지 않고 종료합니다.
        if (sheep <= wolf) return;
        // 간선 정보 배열 edges를 순회하며 연결된 노드를 찾습니다.
        //  만약 현재 노드(edge[0])가 이미 방문되었고, 연결된 노드(edge[1])가 아직 방문되지 않았다면 새로운 방문 배열 newVisited를 복제하여 해당 노드를 방문하고 재귀적으로 dfs 함수를 호출합니다.
        for(int[] edge: edges){
            if(visited[edge[0]] && !visited[edge[1]]){
                boolean[] newVisited = visited.clone();
                dfs(edge[1], newVisited, sheep, wolf, info,edges);
            }
        }
    }
}