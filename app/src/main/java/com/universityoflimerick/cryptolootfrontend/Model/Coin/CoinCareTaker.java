package com.universityoflimerick.cryptolootfrontend.Model.Coin;

import java.util.ArrayList;
import java.util.List;

public class CoinCareTaker {
    /**this class is a part of the Memento pattern, used to restore the state of object(s).
     * a instance of CoinCaretaker is used to keep track of multiple mementos (states)
     * Whenever a new state is created it will be added to the Caretaker object
     *
     * @param mementoList is a list that holds all our Coin states
     *  */
    private List<CoinMemento> mementoList = new ArrayList<CoinMemento>();

    /**add function is used to add a memento onto our mementoList */
    public void add(CoinMemento state){
        mementoList.add(state);
    }

    /**get(index) and getLast() are used to used to retrieve mementos from the list */
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
        return mementoList.size();
   }
}
