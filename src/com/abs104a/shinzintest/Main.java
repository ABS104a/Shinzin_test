package com.abs104a.shinzintest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Main {
	
	//���i�f�[�^�p�z��
	private static int[] stockArray;
	//�L�����y�[�����i�p�z��
	private static int[] priceArray;
	
    public static void main(String args[] ) throws Exception  {
    	
    	//�f�[�^��ǂݍ���
        readData();
        //���i�f�[�^��l�i�̏����Ƀ\�[�g����
        sort(stockArray);
        
        //�J�Ó����Ƃ̑g�ݍ��킹���o��
        for(int price : priceArray){
        	//2���ؒT��
        	//�b�茋�ʂ��i�[����ϐ�
        	int tmp = 0;
        	for(int i = 0;i < stockArray.length - 1;i++){
        		//�΂ƂȂ錋�ʂ�����
        		int searchResult = bun_search(stockArray,price - stockArray[i],i + 1,stockArray.length - 1);
        		//���v���z���v�Z
        		int result = stockArray[searchResult] + stockArray[i];
        		
        		//���v���z�����߂����ꍇ�͂���ȍ~�͌��ʂ��o�Ȃ����ߎ}�؂�
        		if(result > price){
        			break;
        		}
        		//���ʂ��ݒ���z�Ɠ����ɂȂ����ꍇ�͂���ȏ�̋��z�͂��蓾�Ȃ����ߎ}�؂�
        		else if(result == price){
        			tmp = result;
        			break;
        		}
        		//���ʂ����݂̎b�萔�l������ꍇ�͒l���X�V
        		else if(result > tmp){
        			tmp = result;
        		}
        	}
        	//���ʂ��o��
        	System.out.println(tmp);
        }
    }
    
    /**
     * �f�[�^��ǂݍ��ރ��\�b�h
     * @throws Exception
     */
    public static void readData() throws Exception{
    	//�f�[�^��ǂ�
    	FileInputStream inFile = new FileInputStream("test.txt"); 
    	BufferedReader br = new BufferedReader(new InputStreamReader(inFile));
    	//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        int space = line.indexOf(" ");
        int N = Integer.parseInt(line.substring(0, space));
        int M = Integer.parseInt(line.substring(space + 1));
        
        //���i
    	stockArray = new int[N];
    	//�L�����y�[�����i
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
//						��������\�[�g�v���O����
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
		// �Փ˂���܂�
		while(l <= r){
			// ���v�f�ȏ�̃f�[�^��T��
			while(l <= j && a[l] < x)  l++;

			// ���v�f�����̃f�[�^��T��
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
	 * �N�C�b�N�\�[�g�i�ċA�p�j
	 * �z��a�́Aa[i]����a[j]����בւ���
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
	 * Quicksort���s��
	 * @param a
	 */
	public static void sort(int[] a){
		quickSort(a,0,a.length - 1);
	}
	
////////////////////////////////////////////////////////////////////////////////////////////
//
//��������T���v���O����
//
////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * �񕪒T�����s��
	 * @param target
	 * @param targetArray
	 * @return
	 */
	public static int getSearch(int target,final int[] targetArray){
		return bun_search(targetArray, target, 0, targetArray.length - 1);
	}
	
	/**
	 * �񕪒T���i�ċA�p�j
	 * @param targetArray
	 * @param target
	 * @param first
	 * @param last
	 * @return
	 */
	public static int bun_search(final int[] targetArray,final int target,int first,int last){
		
		//���҂̍����Ȃ��Ȃ�����I��
		if(last - first <= 1){
			return first;
		}
		
		//���S�_�����
		int tmp = (int) (first + last) >> 1;
		//���ʕۊǗp
		int result;
		
		if(targetArray[tmp] > target){
			//���S�_���^�[�Q�b�g���������ʒu�ɂ���Ƃ�
			result = bun_search(targetArray,target,first,tmp);
		}else{
			//���S�_���^�[�Q�b�g���傫���ʒu�ɂ���Ƃ�
			result = bun_search(targetArray,target,tmp,last);
		}
		
		return result;
	}
}
