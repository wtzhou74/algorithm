# -*- coding: utf-8 -*-
# =============================================================================
# Given a binary tree, return the level order traversal of its nodes' values. (ie, from left to right, level by level).
# 
# For example:
# Given binary tree [3,9,20,null,null,15,7],
#     3
#    / \
#   9  20
#     /  \
#    15   7
# return its level order traversal as:
# [
#   [3],
#   [9,20],
#   [15,7]
# ]
# =============================================================================
import draw_tree as dt

def levelOrder(root):
    if not root:
        return []
    res = []
    current = [root] # traverse each level
    while current:
        level, nextLevel = [], []
        for node in current:
            level.append(node.val)
            if node.left is not None:
                nextLevel.append(node.left)
            if node.right is not None:
                nextLevel.append(node.right)
        res.append(level)
        current = nextLevel 
        
    return res

def levelOrder2(root):
    if not root:
        return []
    visited = []
    level = [root]
    while level:
        visited.append([node.val for node in level]) # expend() will unpack nested list
        level = [child for node in level for child in (node.left, node.right) if child]
    return visited

if __name__ == '__main__':
    root = dt.deserialize('[2,1,3,0,7,9,1,2,null,1,0,null,null,8,8,null,null,null,null,7]')
    res = levelOrder2(root)
    for i in res:
        print(i)
            
            