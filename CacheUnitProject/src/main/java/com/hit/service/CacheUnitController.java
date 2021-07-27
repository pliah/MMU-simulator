package com.hit.service;

import com.hit.dm.DataModel;

/**
 * CacheUnitController class connecting between the Services and the server
 * @param <T>
 */
public class CacheUnitController<T> extends java.lang.Object {
    private CacheUnitService<T> cacheUnitService;
    public CacheUnitController(){
        cacheUnitService=new CacheUnitService<T>();
    }

    /**
     * update calls the same function of the service
     * @param dataModels - the dataModels to update the cache with
     * @return - in success: true, in failure: false
     */
    public boolean update(DataModel<T>[] dataModels){
        return  cacheUnitService.update(dataModels);
    }

    /**
     * delete calls the same function of the service
     * @param dataModels - the dataModels to delete
     * @return - in success: true, in failure: false
     */
    public boolean delete(DataModel<T>[] dataModels){
        return cacheUnitService.delete(dataModels);
    }

    /**
     * get calls the same function of the service
     * @param dataModels - contains the ids of the dataModels to get
     * @return - the full data models that has been gotten
     */
    public DataModel<T>[] get(DataModel<T>[] dataModels){
        return cacheUnitService.get(dataModels);
    }

    /**
     * shutdown calls the same function of the service
     */
    public void  shutdown(){
        cacheUnitService.shutdown();
    }

    /**
     * getStatistic calls the same function of the service
     * @return
     */
    public String getStatistic(){
        return cacheUnitService.getStatistic();
    }
}
