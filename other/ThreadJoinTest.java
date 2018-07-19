package other;

public class ThreadJoinTest {

	public static void main(String[] args){
		Thread t = new Thread(new ThreadImp());
		t.start();
		try{
			t.join(1000);//join方法不加参数会一直等到线程t结束，而加了参数后，最多等待指定时间
			if(t.isAlive())
				System.out.println("t has not finished");
			else
				System.out.println("t has finished");
			System.out.println("joinFinish");
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}

class ThreadImp implements Runnable{

	public void run() {
		try{
			System.out.println("Begin ThreadImp");
			Thread.sleep(5000);
			System.out.println("End ThreadImp");
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}
