package threadtest;

public class Thread1 extends Thread{

	private int ticket = 10;
	private String name;
	public Thread1(String name){
		this.name = name;
	}
	
	public void run(){
		for(int i = 0; i < 500; i++){
			if(this.ticket > 0){
				System.out.println(this.name + "卖票---->" + (this.ticket--));
			}
		}
	}
	
	public static void main(String[] args) {
		Thread1 th1 = new Thread1("1号窗口");
		Thread1 th2 = new Thread1("2号窗口");
		Thread1 th3 = new Thread1("3号窗口");
		th1.start();
		th2.start();
		th3.start();
	}
	
}

