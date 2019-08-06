# -*- coding: utf-8 -*-
# =============================================================================
# Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).
# 
# For example, this binary tree [1,2,2,3,4,4,3] is symmetric:
# 
#     1
#    / \
#   2   2
#  / \ / \
# 3  4 4  3
#  
# 
# But the following [1,2,2,null,3,null,3] is not:
# 
#     1
#    / \
#   2   2
#    \   \
#    3    3
#  
# 
# Note:
# Bonus points if you could solve it both recursively and iteratively.
# =============================================================================
import draw_tree as dt
    
# Time: O(n)
# Space: O(h)
def isSymmetric(root):
    if not root:
        return True
    stack = []
    stack.append(root.left)
    stack.append(root.right)
    while stack:
        p, q = stack.pop(), stack.pop()
        if p is None and q is None:
            continue
        if p is None or q is None or p.val != q.val:
            return False
        stack.append(p.left)
        stack.append(q.right)
        
        stack.append(p.right)
        stack.append(q.left)
        
    return True

# Recursive Solution
def isSymmetric_recursively(root):
    if not root:
        return True
    return symmetric(root.left, root.right)

# Time: O(n)
# Space: O(h)
def symmetric(left, right):
    if left is None and right is None:
        return True
    if left is None or right is None or left.val != right.val:
        return False
    return symmetric(left.left, right.right) and symmetric(left.right, right.left)

if __name__ == '__main__':
    # root = dt.deserialize('[2,1,3,0,7,9,1,2,null,1,0,null,null,8,8,null,null,null,null,7]')
    root = dt.deserialize('[1,2,2,3,4,4,3]')
    # root = dt.deserialize('[1,2,3]')
    res = isSymmetric(root)
    print(res)