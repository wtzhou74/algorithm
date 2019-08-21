# -*- coding: utf-8 -*-
# =============================================================================
# Given inorder and postorder traversal of a tree, construct the binary tree.
# 
# Note:
# You may assume that duplicates do not exist in the tree.
# 
# For example, given
# 
# inorder = [9,3,15,20,7]
# postorder = [9,15,7,20,3]
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

def buildTree(inorder, postorder):
    if not postorder or not inorder:
        return None
    lookup = {} # for looking up specific value from inorder list
    for i, num in enumerate(inorder):
        lookup[num] = i
    return buildTree(lookup, inorder, postorder, len(postorder), 0, len(inorder))
    
def buildTreeRecu(lookup, inorder, postorder, post_end, in_start, in_end):
    if in_start == in_end:
        return None
    i = lookup[postorder[post_end - 1]] # looking for the position of CURRENT ROOT
    node = bn.TreeNode(postorder[post_end - 1])
    # partition inorder list into left subtree and right subtree
    node.left = buildTree(lookup, inorder, postorder, post_end - 1 - (in_end - i - 1), in_start, i)
    node.right = buildTree(lookup, inorder, postorder, post_end - 1, i + 1, in_end)
    
    return node

# Time O(n)
# Space O(n)
def sol2(inorder, postorder):
    rev_postorder= postorder[::-1] # when recursion below, first RIGHT, then LEFT !!!!!!!
    post_iterator = iter(rev_postorder)
    lookup = {num:i for i, num in enumerate(inorder)}
    return helper(0, len(inorder) - 1, lookup, post_iterator)
    
def helper(start, end, lookup, post_iterator):
    if start > end:
        return None
    root_val = next(post_iterator)
    root = bn.TreeNode(root_val)
    idx = lookup[root_val]
    root.right = helper(idx + 1, end, lookup, post_iterator)
    root.left = helper(start, idx - 1, lookup, post_iterator)
    return root

if __name__ == '__main__':
    root = sol2([9,3,15,20,7], [9,15,7,20,3])
    dt.drawtree(root)