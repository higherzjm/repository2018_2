package com.advanced.concurrencyclass.type_volatile.example2;

/**
 * 看不出来
 */
public class Volatile20180904 {
	public static  int value;//更新数据只有程序结束之后才会刷新到主存
	public static volatile boolean missedIt;//更新数据马上就会刷新到主存

	public Volatile20180904() {
		value = 10;
		missedIt = true;
	}
 
	public static void run() {

		while ( value < 20 ) {
			System.out.println("value");
			while ( missedIt ) {
				System.out.println("missedIt");
			}
		}
	}

	public static void main(String[] args) {
		try {
			new Thread(new Thread1()).start();
			new Thread(new Thread2()).start();
			Volatile20180904.run();
		} catch ( Exception x ) {
			System.err.println("one of the sleeps was interrupted");
		}
	}
}

class Thread1 implements  Runnable{

	@Override
	public void run() {
		Volatile20180904.value=50;
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Thread2 implements  Runnable{

	@Override
	public void run() {
		Volatile20180904.missedIt=false;
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
