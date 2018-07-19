package collectortest.collectortest_temp;

public class MyThread_implements_Runnable extends Thread implements Runnable{
	
	int count = 1,number;
	
	public MyThread_implements_Runnable(int num){
		number = num;
		System.out.println("创建线程" + number);
	}

	@Override
	public void run() {
		while(true){
			System.out.println("线程" + number + "：计数" + count);
			if(++count == 6){
				return;
			}
		}
	}
	
	public static void main(String args[]){
		for(int i = 0; i < 5; i++){
			new Thread(new MyThread_implements_Runnable(i+1)).start();
		}
	}

}
