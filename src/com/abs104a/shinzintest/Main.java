package com.abs104a.shinzintest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Main {
	
	//商品データ用配列
	private static int[] stockArray;
	//キャンペーン価格用配列
	private static int[] priceArray;
	
    public static void main(String args[] ) throws Exception  {
    	
    	//データを読み込む
        readData();
        //商品データを値段の昇順にソートする
        sort(stockArray);
        
        //開催日ごとの組み合わせを出力
        for(int price : priceArray){
        	//2分木探索
        	//暫定結果を格納する変数
        	int tmp = 0;
        	for(int i = 0;i < stockArray.length - 1;i++){
        		//対となる結果を検索
        		int searchResult = bun_search(stockArray,price - stockArray[i],i + 1,stockArray.length - 1);
        		//合計金額を計算
        		int result = stockArray[searchResult] + stockArray[i];
        		
        		//合計金額が超過した場合はそれ以降は結果が出ないため枝切り
        		if(result > price){
        			break;
        		}
        		//結果が設定金額と同じになった場合はそれ以上の金額はあり得ないため枝切り
        		else if(result == price){
        			tmp = result;
        			break;
        		}
        		//結果が現在の暫定数値を上回る場合は値を更新
        		else if(result > tmp){
        			tmp = result;
        		}
        	}
        	//結果を出力
        	System.out.println(tmp);
        }
    }
    
    /**
     * データを読み込むメソッド
     * @throws Exception
     */
    public static void readData() throws Exception{
    	//データを読む
    	FileInputStream inFile = new FileInputStream("test.txt"); 
    	BufferedReader br = new BufferedReader(new InputStreamReader(inFile));
    	//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        int space = line.indexOf(" ");
        int N = Integer.parseInt(line.substring(0, space));
        int M = Integer.parseInt(line.substring(space + 1));
        
        //商品
    	stockArray = new int[N];
    	//キャンペーン価格
    	priceArray = new int[M];
        
        for (int i = 0; i < N; i++) {
            String readLine = br.readLine();
            stockArray[i] = Integer.parseInt(readLine);
        }
        
        for (int i = 0; i < M; i++) {
            String readLine = br.readLine();
            priceArray[i] = Integer.parseInt(readLine);
        }
        br.close();
    }
    
////////////////////////////////////////////////////////////////////////////////////////////
//
//						ここからソートプログラム
//	
////////////////////////////////////////////////////////////////////////////////////////////
    
	private static int pivot(int[] a,int i,int j){
		int k= i + 1;
		while(k <= j && a[i] == a[k]) k++;
		if(k > j) return -1;
		if(a[i] >= a[k]) return i;
		return k;
	}

	private static int partition(int[] a,int i,int j,int x){
		int l = i,r = j;
		// 衝突するまで
		while(l <= r){
			// 軸要素以上のデータを探す
			while(l <= j && a[l] < x)  l++;

			// 軸要素未満のデータを探す
			while(r >= i && a[r] >= x) r--;

			if(l > r) break;
			int t = a[l];
			a[l] = a[r];
			a[r] = t;
			l++; r--;
		}
		return l;
	}

	/**
	 * クイックソート（再帰用）
	 * 配列aの、a[i]からa[j]を並べ替える
	 */
	private static void quickSort(int[] a,int i,int j){
		if(i == j) return;
		int p = pivot(a,i,j);
		if(p != -1){
			int k = partition(a,i,j,a[p]);
			quickSort(a,i,k - 1);
			quickSort(a,k,j);
		}
	}


	/**
	 * Quicksortを行う
	 * @param a
	 */
	public static void sort(int[] a){
		quickSort(a,0,a.length - 1);
	}
	
////////////////////////////////////////////////////////////////////////////////////////////
//
//ここから探索プログラム
//
////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 二分探索を行う
	 * @param target
	 * @param targetArray
	 * @return
	 */
	public static int getSearch(int target,final int[] targetArray){
		return bun_search(targetArray, target, 0, targetArray.length - 1);
	}
	
	/**
	 * 二分探索（再帰用）
	 * @param targetArray
	 * @param target
	 * @param first
	 * @param last
	 * @return
	 */
	public static int bun_search(final int[] targetArray,final int target,int first,int last){
		
		//両者の差がなくなったら終了
		if(last - first <= 1){
			return first;
		}
		
		//中心点を取る
		int tmp = (int) (first + last) >> 1;
		//結果保管用
		int result;
		
		if(targetArray[tmp] > target){
			//中心点よりターゲットが小さい位置にあるとき
			result = bun_search(targetArray,target,first,tmp);
		}else{
			//中心点よりターゲットが大きい位置にあるとき
			result = bun_search(targetArray,target,tmp,last);
		}
		
		return result;
	}
}
