# -*- coding: utf-8 -*-
# =============================================================================
# Given preorder and inorder traversal of a tree, construct the binary tree.
# 
# Note:
# You may assume that duplicates do not exist in the tree.
# 
# For example, given
# 
# preorder = [3,9,20,15,7]
# inorder = [9,3,15,20,7]
# Return the following binary tree:
# 
#     3
#    / \
#   9  20
#     /  \
#    15   7
# =============================================================================

import binarytree_node as bn
import draw_tree as dt

# Time O(n)
# Space O(n)
def buildTree(preorder, inorder):
    # for index reason
# =============================================================================
#     lookup = {}
#     for i, v in enumerate(inorder):
#         lookup[v] = i
# =============================================================================
    lookup = {v:i for i, v in enumerate(inorder)}
    return constructTree(lookup, preorder, inorder, 0, 0, len(inorder)) # len instead of len - 1 !!!

def constructTree(lookup, preorder, inorder, subrootInd, instart, inend):
    if instart == inend:
        return None
    i = lookup[preorder[subrootInd]] # find the index of current root within inorder
    node = bn.TreeNode(preorder[subrootInd])
    node.left = constructTree(lookup, preorder, inorder, subrootInd + 1, instart, i)
    node.right = constructTree(lookup, preorder, inorder, subrootInd + i - instart + 1 , i + 1, inend)
    
    return node

# Time O(n)
# Space O(n)
def sol2(preorder, inorder):
    inorder_lookup = {num:i for i, num in enumerate(inorder)}
    preorder_iterator = iter(preorder)
    return helper(0, len(inorder) - 1, inorder_lookup, preorder_iterator)
    
def helper(start, end, inorder_lookup, preorder_iterator):
    if start > end:
        return None
    root_val = next(preorder_iterator)
    root = bn.TreeNode(root_val)
    idx = inorder_lookup[root_val] # find the index/cut point/root, in comparison of subrootidx above
    root.left = helper(start, idx - 1, inorder_lookup, preorder_iterator)
    root.right = helper(idx + 1, end, inorder_lookup, preorder_iterator)
    return root
    
if __name__ == '__main__':
    root = sol2([3,9,20,15,7], [9,3,15,20,7])
    dt.drawtree(root)
    print("done")
    