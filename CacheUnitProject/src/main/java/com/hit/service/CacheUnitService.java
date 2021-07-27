package com.hit.service;

import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dm.DataModel;
import com.hit.memory.CacheUnit;
import java.util.Arrays;
import java.util.List;

/**
 * CacheUnitService class is in charge of providing all of the services of the fileSystem
 * @param <T>
 */
public class CacheUnitService<T> extends java.lang.Object{
    private final Integer capacity=3;
    private CacheUnit<T> cacheUnit;
    private DaoFileImpl<T> hardDisk;
    private IAlgoCache<Long,DataModel<T>> algorithm;
    private int totalNumOfRequest;
    private int totalNumOfDataModels;
    private int totalNumOfDataModelsSwap;

    public CacheUnitService(){
        this.hardDisk=new DaoFileImpl<T>("datasource.txt");
        this.algorithm=new LRUAlgoCacheImpl<>(capacity);
        cacheUnit=new CacheUnit<>(algorithm);
    }

    /**
     * update function calls the cacheUnit.putDataModels and also searches for each data id if it exist already
     * to know if the totalNumOfDataModels has been changedor not.
     * then it upddates it and the totalNumOfDataModelsSwap.
     * @param dataModels - the dataModels to put/update
     * @return - true in success, false in failure
     */
    public boolean update(DataModel<T>[] dataModels){
        totalNumOfRequest++;
        try {
            if(dataModels!=null) {
                DataModel<T>[] allTheCache=cacheUnit.getAll();
                int index=0;
                Long[] allTheCacheIds= new Long[allTheCache.length];
                for(DataModel<T> cacheData:allTheCache){
                    allTheCacheIds[index]=cacheData.getDataModelId();
                    index++;
                }
                List<Long> cacheListIds = Arrays.asList(allTheCacheIds);
                DataModel<T>[] replacedData = cacheUnit.putDataModels(dataModels);
                for(DataModel<T> dataId:dataModels){
                    if(!cacheListIds.contains(dataId.getDataModelId())){
                        totalNumOfDataModels++;
                    }
                }
                for (DataModel<T> data : replacedData) {
                    if(data!=null){
                        totalNumOfDataModelsSwap++;
                    }
                }
            }
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * delete function deletes dataModels from the cache' and updates the totalNumOfDataModels
     * if the dataModelId to delete actually was in cache before
     * @param dataModels - the dataModels to delete
     * @return - true in success, false in failure
     */
    public boolean delete(DataModel<T>[] dataModels){
        totalNumOfRequest++;
        Long[] dataModelsId=new Long[dataModels.length];
        int index=0;
        try {
            DataModel<T>[] allTheCache=cacheUnit.getAll();
            List<DataModel<T>> cacheList = Arrays.asList(allTheCache);
            for (DataModel<T> data : dataModels) {
                dataModelsId[index] = data.getDataModelId();
                if(cacheList.contains(data)){
                    totalNumOfDataModels--;
                }
                index++;
            }
            cacheUnit.removeDataModels(dataModelsId);
            return true;
        }
        catch (Exception e) {
            return  false;
        }
    }

    /**
     * get function dends back dataModels due to ids
     * @param dataModels - the dataModels with the ids to get
     * @return - the dataModels with the data of the ids it gets
     */
    public DataModel<T>[] get(DataModel<T>[] dataModels){
        totalNumOfRequest++;
        Long[] dataModelsId=new Long[dataModels.length];
        int index=0;
        for(DataModel<T> data:dataModels){
            dataModelsId[index]=data.getDataModelId();
            index++;
        }
        dataModels=cacheUnit.getDataModels(dataModelsId);
        return dataModels;
    }

    /**
     *  shutdown function write all of the cache into the hard disk
     */
    public void shutdown(){
        DataModel<T>[] cacheData=cacheUnit.getAll();
        for(DataModel<T> data:cacheData) {
            hardDisk.save(data);
        };
    }

    /**
     *  getStatistic function - return the statistics of the system
     * @return  - the string of the statistics
     */
    public String getStatistic(){
        return  capacity + " " + "LRU" + " " +totalNumOfRequest +
                " " + totalNumOfDataModels +" " + totalNumOfDataModelsSwap;
    }
}
