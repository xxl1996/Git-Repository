package com.ok.test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.locks.ReentrantLock;

public class Intest {

	/**
	 * @param args
	 */
		public static void main(String args[]) {
			Queue<String> queue=new LinkedList<String>();
	        //添加几个元素
	        queue.offer("a");
	        queue.offer("b");
	        queue.offer("c");
	        queue.offer("d");
	        queue.offer("e");
	        queue.add("1");
	        queue.add("2");
	        queue.add("3");
	        queue.add("4");
	        queue.add("5");
	        queue.add("6");
	        System.out.println("队列中的元素是:"+queue);
	        //弹出元素
	        queue.poll();
	        queue.poll();
	        System.out.println("队列中的元素是:"+queue);
	        //查看队列中首个元素，并不移除
	        String peek=queue.peek();
	        System.out.println("查看队列中首个元素，并不移除:"+peek);
	        System.out.println("队列中的元素是:"+queue);
		   }    

}
