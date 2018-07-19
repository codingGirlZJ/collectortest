package threadtest;

public class Thread2 implements Runnable{

	private int ticket = 10;
	private String name;

	@Override
	public void run(){
		for(int i = 0; i < 500; i++){
			if(this.ticket > 0){
				System.out.println(Thread.currentThread().getName() + "卖票---->" + (this.ticket--));
			}
		}
	}
	
	public static void main(String[] args) {
		//设计三个线程
		Thread2 th = new Thread2();
		Thread th1 = new Thread(th,"1号窗口");
		Thread th2 = new Thread(th,"2号窗口");
		Thread th3 = new Thread(th,"3号窗口");
		th1.start();
		th2.start();
		th3.start();
	}
}
