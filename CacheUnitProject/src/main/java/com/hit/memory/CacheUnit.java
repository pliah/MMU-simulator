package com.hit.memory;
import com.hit.algorithm.IAlgoCache;
import com.hit.dm.DataModel;

/**
 * class CacheUnit stands for manipulating the ram data
 * @param <T> the type of the data content
 */

public class CacheUnit<T>{
    private IAlgoCache<Long,DataModel<T>> algo;
    public CacheUnit(com.hit.algorithm.IAlgoCache<java.lang.Long,DataModel<T>> algo){
         this.algo=algo;
    }

    /**
     * getDataModels functions work's is to return data from memory due to id's
     * @param ids - the list of ids of the data to return
     * @return data from memory which its ids the function got
     */
    public DataModel<T>[] getDataModels(java.lang.Long[] ids){
        DataModel<T>[] dataModels = new DataModel[(ids.length)];
        int index=0;
        for (Long id: ids){
            dataModels[index]=algo.getElement(id);
            index++;
        }
        return dataModels;
    }
    /**
     * putDataModels functions work's is to put data in the memory
     * @param dataModels - the list of the data to put in memory
     * @return data from memory which the cache algorithm replaced with the new data
     */
    public DataModel<T>[] putDataModels(DataModel<T>[] dataModels){
        DataModel<T>[] replacedData = new DataModel[(dataModels.length)];
        int index=0;
        for (DataModel<T> data:dataModels){
            replacedData[index]=algo.putElement(data.getDataModelId(),data);
            index++;
        }
        return replacedData;
    }

    /**
     *   removes elements according to theirs ids from the cache
     * @param ids of the removed elements
     */
    public void removeDataModels(java.lang.Long[] ids) {
        for (Long id : ids) {
            algo.removeElement(id);
        }
    }
    public DataModel<T>[] getAll(){
        DataModel<T>[] allTheCache= algo.getAll().toArray(new DataModel[algo.getAll(). size()]);
        return allTheCache;
    }


}
