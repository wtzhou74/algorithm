# -*- coding: utf-8 -*-
# =============================================================================
# Given a binary tree, return the preorder traversal of its nodes' values.
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
# Output: [1,2,3]
# Follow up: Recursive solution is trivial, could you do it iteratively?
# =============================================================================

# definition for a binary tree node
# class TreeNode:
#   def __init__(self, x):
#       self.val = x
#       self.left = None
#       self.right = None

import draw_tree as dt

# recursive solution
# Time: O(n)
# Space: O(h) h is the height of tree
def preorderTraversal(root) -> list:
    res = []
    helper(root, res)
    
    return res

def helper(node, res):
    if not node:
        return []
    res.append(node.val)
    helper(node.left, res)
    helper(node.right, res)
    
    return res

# iterative solution (Morries Traversal Solution)
# Time: O(n)
# Space: O(1)
def iterSol(root) -> list:
    """
    :type root: TreeNode
    :rtype list
    """
    if not root:
        return []
    res = []
    curr = root
    while curr:
        if curr.left is None:
            # left child is null
            res.append(curr.val)
            # move to right subtree for preorder
            curr = curr.right
        else:
            # preorder predecessor is defined as the rightmost node in curr's left subtree
            pred = curr.left
            # pred.right cannot lead to curr, 
            while pred.right and pred.right != curr:
                pred = pred.right
            # on our first visit, predecessor's right child is guaranteed to be null
            # since predecessor by definition is the RIGHTMOST node on curr's left subtree
            if pred.right is None:
                res.append(curr.val)
                # making predecessor's right child to a link leading back to curr
                # this link will act as our way to travel back to curr after we have visited pred
                pred.right = curr
                curr = curr.left
            else:
                # on our second visit to curr, since we have already established the
                # the link pred.right == curr previously
                # pred.right != null now
                # if a link exist, it means we have already added curr.val to res and
                # that we have fully visit curr's left subtree before we delete the link
                # to restore the tree and traverse to curr's right subtree
                pred.right = None
                curr = curr.right
    return res

# Time: O(n)
# Space: O(1)
# reference: https://www.jianshu.com/p/484f587c967c
def morrisTraversalSol(root) -> list:
    res = []
    if not root:
        return []
    # starting from root
    curr = root
    while curr:
        if curr.left is None: # does not have left child, so, does not need to do Morris traversal (leaf nodes)
            res.append(curr.val)
            curr = curr.right # trace back, the right child was added for Morries traversal
        else:
            pre = findPredecessor(curr)
            # first round traversion => add right child for second round traversion
            if pre.right is None: # the pre.right must be null, or the pre is the node itself since curr does not have right child
                # when trace back, pre.right is not null, and pre.right was added on the first round, so we need to set it to null
                res.append(curr.val) # add root node of current (sub)tree
                # link the pre's right to current node if its right node is null
                pre.right = curr # change the tree structure
                curr = curr.left # prepare the next traversal cycle
            else: # second round traversion
                # right is not null, now right lead to curr, we need change it to its original structure
                pre.right = None # change the updated tree to original one
                curr = curr.right # trace back or move the right tree
    return res
                

def findPredecessor(node):
    pre = node
    # check its direct left child's rightmost child
    if pre.left:
        pre = pre.left # if left child does not have right child, then this node is its pre node
        while pre.right and pre.right != node: # when trace back, pre.right == node will be found, at that time, right child is not needed
            pre = pre.right
    return pre

# Stack solution
# Time: O(n)
# Space: O(h)
def stackSol(root) -> list:
    if not root:
        return []
    res, stack = [], [(root, False)]
    while stack:
        root, is_visited = stack.pop()
        if root is None:
            continue
        if is_visited:
            res.append(root.val)
        else:
            stack.append((root.right, False))
            stack.append((root.left, False))
            stack.append(root, True)
    return res
            

# Time: O(n)
# Space: O(h)
def stackSol2(root) -> list:
    if not root:
        return []
    res, stack = [], [root]
# =============================================================================
#     while stack:
#         curr = stack.pop()
#         res.append(curr.val)
#         # for LIFO
#         if curr.right:
#             stack.append(curr.right)
#         if curr.left:
#             stack.append(curr.left)
# =============================================================================
    # optimization => only push right child to stack
    curr = root
    while stack:
        if curr:
            res.append(curr.val)
            if curr.right:
                stack.append(curr.right)
            curr = curr.left # visit its left child
        else: # left child is null, pop right child out to visit
            curr = stack.pop();
            
    return res
    

if __name__ == '__main__':
    root = dt.deserialize('[2,1,3,0,7,9,1,2,null,1,0,null,null,8,8,null,null,null,null,7]')
    # root = dt.deserialize('[3,1,2]')
    res = stackSol2(root)
    for i in res:
        print(i)
        
            
    