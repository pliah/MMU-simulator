package com.hit.memory;

import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dm.DataModel;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class CacheUnitTest {
    @Test
    public void cacheUnitTest() {
        LRUAlgoCacheImpl<Long, DataModel<String>> lru = new LRUAlgoCacheImpl<Long,DataModel<String>>(4);
        CacheUnit<String> cache =new CacheUnit<>(lru);
        DataModel<String>[] dataModelsArray=new DataModel[4];
        dataModelsArray[0]= new DataModel<String>(1L,"data1");
        dataModelsArray[1]= new DataModel<String>(2L,"data2");
        dataModelsArray[2]= new DataModel<String>(3L,"data3");
        dataModelsArray[3]= new DataModel<String>(4L,"data4");
        cache.putDataModels(dataModelsArray);
        Long[] ids=new Long[3];
        ids[0]=1L;
        ids[1]=2L;
        ids[2]=3L;
        DataModel<String>[] returnedData = cache.getDataModels(ids);
        DataModel<String>[] partOfDataModel = Arrays.copyOfRange(dataModelsArray,0,3);
        Assert.assertArrayEquals( partOfDataModel,returnedData);
        DataModel[] newDataModelsArray=new DataModel[1];
        newDataModelsArray[0] = new DataModel<String>(5L,"data5");
        Assert.assertEquals(dataModelsArray[3],cache.putDataModels(newDataModelsArray)[0]);
    }
}
