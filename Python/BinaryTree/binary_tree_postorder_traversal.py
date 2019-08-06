# -*- coding: utf-8 -*-
# =============================================================================
# Given a binary tree, return the postorder traversal of its nodes' values.
# 
# Example:
# 
# Input: [1,null,2,3]
#    1
#     \
#      2
#     /
#    3
# 
# Output: [3,2,1]
# Follow up: Recursive solution is trivial, could you do it iteratively?
# =============================================================================

import draw_tree as dt
import binarytree_node as tn

# recursive solution
def postorderTraversal(root) -> list:
    if not root:
        return []
    res = []
    return helper(root, res)

def helper(node, res):
    if not node:
        return []
    helper(node.left, res)
    helper(node.right, res)
    res.append(node.val)
    return res

# stack solution
def stackSol(root) -> list:
    if not root:
        return []
    res, stack = [], [(root, False)]
    while stack:
        node, visited = stack.pop()
        if not node:
            continue 
        if visited:
            res.append(node.val)
        else:
            stack.append((node, True))
            stack.append((node.right, False))
            stack.append((node.left, False))
    return res

def stackSol2(root) -> list:
    if not root:
        return []
    res, stack = [], [root]
    while stack:
        curr = stack.pop()
        res = [curr.val] + res # reversing, so, root before right, right before left
        if curr.left:
            stack.append(curr.left)
        if curr.right:
            stack.append(curr.right)
        
            
    return res

# reference: https://www.jianshu.com/p/277086887276
#           https://www.cnblogs.com/AnnieKim/archive/2013/06/15/MorrisTraversal.html
# Time: O(n)
# Space: O(1)
def morrisSolution(root) -> list:
    if not root:
        return []
    res = []
    curr = root
    dummy = tn.TreeNode(0) # new TreeNode
    dummy.left = root # direct its left child to root
    res, curr = [], dummy # set dummy node as curr node
    while curr:
        if curr.left is None:
            curr = curr.right
        else:
            # find predecessor
            # pre = findPredecessor(curr)
            pre = curr.left
            if pre:
                while pre.right and pre.right != curr:
                    pre = pre.right
            if pre.right is None: # first time visited
                pre.right = curr
                curr = curr.left
            else: # second time
                # res.extend(reverseRightSubTree(curr.left, pre))
                # output the nodes from curr.left to predecessor node reversely
                res += reverseNodes(curr.left, pre)
                pre.right = None
                curr = curr.right # trace backforward
    return res

def reverseNodes(frm, to):
    """
    Output the nodes from curr.left to predecessor node reversely
    """
    rest = []
    curr = frm
    while curr is not to:
        rest.append(curr.val)
        curr = curr.right
    rest.append(to.val)
    rest.reverse()
    return rest                
         
def findPredecessor(node):
    pre = node
    if pre.left:
        pre = pre.left
        while pre.right and pre.right != node:
            pre = pre.right
    return pre

if __name__ == '__main__':
    root = dt.deserialize('[2,1,3,0,7,9,1,2,null,1,0,null,null,8,8,null,null,null,null,7]')
    # root = dt.deserialize('[1,null,2,3]')
    res = morrisSolution(root)
    for i in res:
        print(i)