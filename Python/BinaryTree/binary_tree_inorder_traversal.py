# -*- coding: utf-8 -*-

# recursive solution

import draw_tree as dt

def inorderTraversal(root) -> list:
    if not root:
        return []
    res = []
    helper(root, res)
        
    return res

def helper(root, res):
    if not root:
        return []
    helper(root.left, res)
    res.append(root.val)
    helper(root.right, res)
    
def stackSol(root):
    if not root:
        return []
    stack, res = [(root, False)], []
    curr = root
    while stack:
        curr, visited = stack.pop()
        if curr:           
            if visited:
                res.append(curr.val)
            else:
                stack.append((curr.right, False))
                stack.append((curr, True))
                stack.append((curr.left, False))
    return res

def stackSol2(root):
    if not root:
        return []
    stack, res = [root], []
    curr = root
    while stack:
        # Based on inorder traversal theory
        # push left node to stack till the left child is null
        while curr and curr.left:
            curr = curr.left
            stack.append(curr)
        temp_node = stack.pop()
        res.append(temp_node.val)
        # start traversing curr's right subtree
        curr = temp_node.right
        if curr:
            stack.append(curr)
    return res

def morriesSol(root):
    if not root:
        return []
    res = []
    curr = root
    while curr:
        if curr.left is None:
            res.append(curr.val)
            curr = curr.right # trace back
        else: 
            # find predecessor node
            pre = findPredecessor(curr)
            if pre.right is None:
                pre.right = curr
                # move next layer
                curr = curr.left
            else:
                pre.right = None # pre.right point to itself, set None
                res.append(curr.val)
                curr = curr.right
    return res

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
    # root = dt.deserialize('[1,2,3]')
    res = stackSol2(root)
    for i in res:
        print(i)