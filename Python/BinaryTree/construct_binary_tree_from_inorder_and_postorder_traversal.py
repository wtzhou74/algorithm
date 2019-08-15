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