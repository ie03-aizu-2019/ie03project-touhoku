package classes;

import java.util.Vector;

public class DijkstraNode {
	// このノードから伸びるエッジの情報
	Vector<Integer> edges_to; // 各エッジの接続先のノード番号
	Vector<Integer> edges_cost; // 各エッジのコスト

	// ダイクストラ法のためのデータ
	boolean done; // 確定ノードか否か
	int cost; // このノードへの現時点で判明している最小コスト

	public DijkstraNode () {
		edges_to = new Vector<Integer>();
		edges_cost = new Vector<Integer>();
		done = false;
		cost = Integer.MAX_VALUE;
	}
}
