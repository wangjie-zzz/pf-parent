package com.pf.test.leetcode;

/**
 * @ClassName : Q1
 * @Description : 删除链表中倒数第n个节点, 并返回删除节点后的整个链表
 * @Author : wangjie
 * @Date: 2021/7/15-18:53
 */
public class Q1 {
    static class ListNode {
        int data;
        ListNode next;
        public ListNode(int data) {
            this.data = data;
        }
    }
    public static ListNode removeNode(ListNode list, int n) {
        ListNode tmp = new ListNode(-1);
        tmp.next = list;
        
        ListNode fast = tmp;
        ListNode slow = tmp;
        for(int i=0; i< n+1; i++) {
            fast = fast.next;
        }
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return tmp.next;
    }
    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode5 = new ListNode(5);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        Q1.removeNode(listNode1, 2);
    }
}
