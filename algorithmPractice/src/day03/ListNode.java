package day03;

 public class ListNode {
     int val;
     ListNode next;
     ListNode() {}
     ListNode(int val) { this.val = val; }
     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 }
class Solution {
    public ListNode deleteNode(ListNode head, int val) {
        // 处理头节点为 null 的情况 
        if (head == null) return null;
        
        // 删除所有连续值为 val 的头节点 
        while (head != null && head.val  == val) {
            return head.next; 
        }
         
        ListNode pre = head;
        ListNode cur = head.next; 
        
        // 遍历剩余节点 
        while (cur != null) {
            if (cur.val  == val) {
                pre.next  = cur.next;  // 删除当前节点 
                cur = pre.next;       // cur 更新为下一个待检查节点 
            } else {
                pre = cur;          // pre 跟随 
                cur = cur.next;      // cur 正常后移 
            }
        }
        return head;
    }
}