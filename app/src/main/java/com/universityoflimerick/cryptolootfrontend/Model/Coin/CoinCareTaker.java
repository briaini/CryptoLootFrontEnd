package com.universityoflimerick.cryptolootfrontend.Model.Coin;

import java.util.ArrayList;
import java.util.List;

public class CoinCareTaker {
    private List<CoinMemento> mementoList = new ArrayList<CoinMemento>();

    public void add(CoinMemento state){
        mementoList.add(state);
    }

    public CoinMemento get(int index){
        CoinMemento temp = mementoList.get(index);
        mementoList.remove(index);
        return temp;
    }

   public CoinMemento getLast(){
   	int index = mementoList.size();
   	if(index < 0) index=0;
   	return mementoList.get(index-1);
   }

    public int getListSize(){
        return this.mementoList.size();
    }
}
