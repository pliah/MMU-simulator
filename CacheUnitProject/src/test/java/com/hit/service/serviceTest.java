package com.hit.service;

import com.hit.dm.DataModel;
import com.hit.server.Request;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
public class serviceTest {
    @Test
    public void cacheUnitService() {
        Map<String,String> headers=new HashMap<>();
        headers.put("action","UPDATE");
        DataModel<String>[] dataModelsArray=new DataModel[2];
        dataModelsArray[0]= new DataModel<String>(1L,"data1");
        dataModelsArray[1]= new DataModel<String>(2L,"data2");

        DataModel<String>[] dataModelsDeleted=new DataModel[2];
        dataModelsDeleted[0]= null;
       dataModelsDeleted[1]= null;

        CacheUnitService<String> cacheUnitService=new CacheUnitService<String>();
        Assert.assertEquals(true,cacheUnitService.update(dataModelsArray));
        Assert.assertArrayEquals(dataModelsArray,cacheUnitService.get(dataModelsArray));
        Assert.assertEquals(true,cacheUnitService.delete(dataModelsArray));
        cacheUnitService.get(dataModelsArray);
        Assert.assertArrayEquals(dataModelsDeleted,cacheUnitService.get(dataModelsArray));
        Assert.assertEquals(true,cacheUnitService.update(dataModelsArray));
        cacheUnitService.shutdown();
        System.out.println(cacheUnitService.getStatistic());
    }
}
