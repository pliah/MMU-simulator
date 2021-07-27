package com.hit.dao;

import com.hit.dm.DataModel;
import org.junit.Assert;
import org.junit.Test;
import java.util.HashMap;

public class DaoFileImplTest {
    @Test
    public void daoFileImplTest(){
        DataModel<String> entity1 = new DataModel<String>(3L,"firstEntity");
        DataModel<String> entity2 = new DataModel<String>(4L,"secondEntity");
        DataModel<String> entity3 = new DataModel<String>(5L,"thirdEntity");
        DaoFileImpl<String> hardDisk = new DaoFileImpl<>("datasource.txt");
        HashMap<Long,String> fileSystemMap=new HashMap<>();
        hardDisk.save(entity1);
        hardDisk.save(entity2);
        Assert.assertEquals( null,hardDisk.find(5L));
        hardDisk.save(entity3);
        Assert.assertEquals( "thirdEntity",hardDisk.find(5L).getContent());
        entity1.setContent("changedObject");
        hardDisk.save(entity1);
        Assert.assertNotEquals("firstEntity",hardDisk.find(3L).getContent());
        Assert.assertEquals("changedObject",hardDisk.find(3L).getContent());
        Assert.assertEquals(null, hardDisk.find(7L));
        hardDisk.delete(entity1);
        Assert.assertEquals(null,hardDisk.find(3L));
    }
}
