package com.universityoflimerick.cryptolootfrontend.dillon;

import java.util.ArrayList;
import java.util.List;

public class coinCareTaker {
    private List<coinMemento> mementoList = new ArrayList<coinMemento>();

    public void add(coinMemento state){
        mementoList.add(state);
    }

    public coinMemento get(int index){
        coinMemento temp = mementoList.get(index);
        mementoList.remove(index);
        return temp;
    }

   public coinMemento getLast(){
   	int index = mementoList.size();
   	return mementoList.get(index-1);
   }
   public int getListSize(){
        return this.mementoList.size();
   }
}
