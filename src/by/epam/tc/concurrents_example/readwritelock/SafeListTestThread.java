package by.epam.tc.concurrents_example.readwritelock;

public class SafeListTestThread extends Thread{
	private ThreadSafeArrayList<String> list;
	
	public SafeListTestThread(ThreadSafeArrayList<String> list){
		this.list = list;
	}

	@Override
    public void run() {
//		for(int i = 0; i < list.getLenth(); i++){
//			list.get(i);
//		}
		
		list.set(this.getName());
	}
}
