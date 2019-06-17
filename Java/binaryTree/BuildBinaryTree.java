package binaryTree;

public class BuildBinaryTree {

	public void buildBinaryTree ()
	{
		TreeNode[] node = new TreeNode[10];
		for(int i = 0; i < 10; i++)
		{
			node[i] = new TreeNode(i);
		}
		for (int i = 0; i < 10; i++)
		{
			if (2 * i + 1 < 10)
				node[i].left = node[i*2 + 1];
			if (2 * i + 2 < 10)
				node[i].right = node[i*2 + 2];
		}
	}
}
