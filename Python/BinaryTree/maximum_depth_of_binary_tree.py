# -*- coding: utf-8 -*-
# =============================================================================
# Given a binary tree, find its maximum depth.
# 
# The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
# 
# Note: A leaf is a node with no children.
# 
# Example:
# 
# Given binary tree [3,9,20,null,null,15,7],
# 
#     3
#    / \
#   9  20
#     /  \
#    15   7
# return its depth = 3.
# 
# =============================================================================

#Time O(n)
#Space O(max(h)) h is the nodes of a specific level
import draw_tree as dt
def maxDepth(root):
    if root is None:
        return 0
    current = [root]
    level = 0
    while current:
        level += 1
# =============================================================================
#         next_level = []
#         for node in current:
#             if node.left:
#                 next_level.append(node.left)
#             if node.right:
#                 next_level.append(node.right)
#         current = next_level
# =============================================================================
        current = [child for node in current for child in (node.left, node.right) if child]
    return level

# Time O(n)
# Space O(h) h is the height of the binary tree
def maxDepth_recursively(root):
    if not root:
        return 0
# =============================================================================
#     max_left = maxDepth_recursively (root.left) + 1
#     max_right = maxDepth_recursively(root.right) + 1
#     return max(max_left, max_right)
# =============================================================================
    else:
        return max(maxDepth_recursively(root.left), maxDepth_recursively(root.right)) + 1

if __name__ == '__main__':
    # root = dt.deserialize('[2,1,3,0,7,9,1,2,null,1,0,null,null,8,8,null,null,null,null,7]')
    root = dt.deserialize('[3,9,20,null,null,15,7]')
    res = maxDepth_recursively(root)
    print(res)
            

