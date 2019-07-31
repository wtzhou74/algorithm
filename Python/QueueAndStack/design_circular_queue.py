# -*- coding: utf-8 -*-
# =============================================================================
# Design your implementation of the circular queue. The circular queue is a linear data structure in which the operations are performed based on FIFO (First In First Out) principle 
# and the last position is connected back to the first position to make a circle. It is also called "Ring Buffer".
# 
# One of the benefits of the circular queue is that we can make use of the spaces in front of the queue. 
# In a normal queue, once the queue becomes full, we cannot insert the next element even if there is a space in front of the queue. But using the circular queue, we can use the space to store new values.
# 
# Your implementation should support following operations:
# 
# MyCircularQueue(k): Constructor, set the size of the queue to be k.
# Front: Get the front item from the queue. If the queue is empty, return -1.
# Rear: Get the last item from the queue. If the queue is empty, return -1.
# enQueue(value): Insert an element into the circular queue. Return true if the operation is successful.
# deQueue(): Delete an element from the circular queue. Return true if the operation is successful.
# isEmpty(): Checks whether the circular queue is empty or not.
# isFull(): Checks whether the circular queue is full or not.
#  
# 
# Example:
# 
# MyCircularQueue circularQueue = new MyCircularQueue(3); // set the size to be 3
# circularQueue.enQueue(1);  // return true
# circularQueue.enQueue(2);  // return true
# circularQueue.enQueue(3);  // return true
# circularQueue.enQueue(4);  // return false, the queue is full
# circularQueue.Rear();  // return 3
# circularQueue.isFull();  // return true
# circularQueue.deQueue();  // return true
# circularQueue.enQueue(4);  // return true
# circularQueue.Rear();  // return 4
#  
# Note:
# 
# All values will be in the range of [0, 1000].
# The number of operations will be in the range of [1, 1000].
# Please do not use the built-in Queue library.
# =============================================================================

# create queue using LIST
class MyCircularQueue:

    def __init__(self, k: int):
        """
        Initialize your data structure here. Set the size of the queue to be k.
        """
        self.__start = 0
        self.__size = 0
        self.__buffer = [0] * k # list
        

    def enQueue(self, value: int) -> bool:
        """
        Insert an element into the circular queue. Return true if the operation is successful.
        """
        # check full before inserting
        if self.isFull():
            return False
        self.__buffer[(self.__start + self.__size) % len(self.__buffer)] = value # RING
        self.__size += 1
        
        return True

    def deQueue(self) -> bool:
        """
        Delete an element from the circular queue. Return true if the operation is successful.
        """
        # check empty before delete
        if self.isEmpty():
            return False
        self.__start = (self.__start + 1) % len(self.__buffer) # delete from start
        self.__size -= 1
        
        return True
        

    def Front(self) -> int:
        """
        Get the front item from the queue.
        """
        return -1 if self.isEmpty() else self.__buffer[self.__start]

    def Rear(self) -> int:
        """
        Get the last item from the queue.
        """
        # -1 for index
        return -1 if self.isEmpty() else self.__buffer[(self.__start + self.__size - 1) % len(self.__buffer)]

    def isEmpty(self) -> bool:
        """
        Checks whether the circular queue is empty or not.
        """
        return self.__size == 0
        

    def isFull(self) -> bool:
        """
        Checks whether the circular queue is full or not.
        """
        return self.__size == len(self.__buffer)
    

q = MyCircularQueue(3)
print(q.enQueue(1))
print(q.enQueue(2))
print(q.enQueue(3))
print(q.enQueue(4))
print(q.Rear())
print(q.isFull())
print(q.deQueue())
print(q.enQueue(4))
print(q.Rear())