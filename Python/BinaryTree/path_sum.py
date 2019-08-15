# -*- coding: utf-8 -*-
# =============================================================================
# Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up all the values along the path equals the given sum.
# 
# Note: A leaf is a node with no children.
# 
# Example:
# 
# Given the below binary tree and sum = 22,
# 
#       5
#      / \
#     4   8
#    /   / \
#   11  13  4
#  /  \      \
# 7    2      1
# return true, as there exist a root-to-leaf path 5->4->11->2 which sum is 22.
# =============================================================================

import draw_tree as dt

def hasPathSum(root, target):
    if not root:
        return False
    stack = [(root, root.val)]
    while stack:
        item = stack.pop()
        node, total = item
        # root to LEAF, not intermediate nodes 
        if not node.left and not node.right and total == target:
            return True
# =============================================================================
#         if total > target: # wrong for negative numbers
#             continue 
# =============================================================================
        if node.left:
            temp = total + node.left.val
            stack.append((node.left, temp))
        if node.right:
            temp = total + node.right.val
            stack.append((node.right, temp))
    return False

def hasPathSum1(root, target):
    """
    For clean up purpose
    """
    stack = [(root, 0)]
    while stack:
        cur, value = stack.pop()
        if cur:
            value += cur.val
            if cur.left:
                stack.append((cur.left, value))
            if cur.right:
                stack.append((cur.right, value))
            if not (cur.left or cur.right):
                if value == target:
                    return True
    return False

def hasPathSum_iteratively(root, target):
# =============================================================================
#  ### CONCISE code below
#     if not root:
#         return False
#     if root.left is None and root.right is None and root.val == target:
#         return True
#     # left OR right
#     # A + B == TOTAL => A = TOTAL - B
#     return hasPathSum_iteratively(root.left, target-root.val) \
#             or hasPathSum_iteratively(root.right, target - root.val)
# =============================================================================
    return helper(root, target, 0)

def helper(node, target, total):
    if node is None:
        return False
    
    total += node.val  
    if node.left is None and node.right is None and total == target:
        return True 
    left = helper(node.right, target, total)
    right = helper(node.left, target, total)
    return left or right
    # return left or right
    # return helper(node.right, target, total + node.val) or helper(node.left, target, total + node.val)
    

if __name__ == '__main__':
    # root = dt.deserialize('[2,1,3,0,7,9,1,2,null,1,0,null,null,8,8,null,null,null,null,7]')
    # root = dt.deserialize('[1,2,2,3,4,4,3]')
    # root = dt.deserialize('[5,4,8,11,null,13,4,7,2,null,null,null,1]')
    root = dt.deserialize('[-2,null,-3]')
    res = hasPathSum_iteratively(root, -5)
    print(res)       
    