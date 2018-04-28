package com.hui10.app.common.utils;

import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UnionLottoPoolGeneratorUtil {
	
	private static LinkedList<String> redCombine = null;
	
	private static Lock lock = new ReentrantLock();
	
	public static LinkedList<String> getLottoRed() {
		if (null != redCombine) {
			return redCombine;
		}
		redCombine =  new LinkedList<>();
		return redCombine;
	}
	
	public static void generateLottoRed(){
		try {
			lock.lock();
			if (null == redCombine) {
				redCombine = new LinkedList<>();
				String[] reds = new String[]{
						"01","02","03","04","05","06","07","08","09","10","11","12","13",
						"14","15","16","17","18","19","20","21","22","23","24","25","26",
						"27","28","29","30","31","32","33"
				};
				combineLottoRed(0,"",reds,6);
			}else {
				return;
			}
		} finally {
			lock.unlock();
		}
	}

    private static void combineLottoRed(int i, String str, String[] reds,int n) {
        if(n==0){
        		redCombine.add(str.substring(0, str.length()-1));
            return;
        }
        if(i==reds.length){
            return;
        }
        combineLottoRed(i+1,str+reds[i]+",",reds,n-1);
        combineLottoRed(i+1,str,reds,n);
    }
}
