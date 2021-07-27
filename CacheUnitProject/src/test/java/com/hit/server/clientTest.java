package com.hit.server;

import com.google.gson.Gson;
import com.hit.dm.DataModel;
import org.junit.Assert;
import org.junit.Test;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class clientTest {

    @Test
    public void updateClientTest(){
        Map<String, String> headerReq = new HashMap<>();
        headerReq.put("action", "UPDATE");

        DataModel<String>[] dataModelsArray = new DataModel[2];
        dataModelsArray[0] = new DataModel<String>(1L, "data11");
        dataModelsArray[1] = new DataModel<String>(2L, "data22");

        Request<DataModel<String>[]> req = new Request<>(headerReq, dataModelsArray);
        Assert.assertEquals(headerReq ,req.getHeaders());
        DataOutputStream output;
        DataInputStream input;
        try {
            Gson gson = new Gson();
            Socket myServer = new Socket("127.0.0.1", 12345);
            StringBuilder sb = new StringBuilder();
            output = new DataOutputStream(myServer.getOutputStream());
            output.writeUTF(gson.toJson(req));
            output.flush();
            input = new DataInputStream(new BufferedInputStream(myServer.getInputStream()));
            String content = "";
            do {
                content = input.readUTF();
                sb.append(content);
            } while (input.available() != 0);
            content = sb.toString();
            Boolean response=false;
            response = new Gson().fromJson(content, response.getClass());
            Assert.assertTrue(response);
            output.close();
            input.close();
            myServer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getClientTest(){//you must Execute this function after updateClientTest function
        try {
            Map<String, String> headerReq = new HashMap<>();
            headerReq.put("action", "GET");
            
            DataModel<String>[] dataModelsArray = new DataModel[2];
            dataModelsArray[0] = new DataModel<String>(1L, "data11");
            dataModelsArray[1] = new DataModel<String>(2L, "data22");
            
            Request<DataModel<String>[]> req = new Request<>(headerReq, dataModelsArray);
            DataOutputStream output;
            DataInputStream input;
            Gson gson = new Gson();
            Socket myServer = new Socket("127.0.0.1", 12345);
            StringBuilder sb = new StringBuilder();
            input = new DataInputStream(new BufferedInputStream(myServer.getInputStream()));
            output = new DataOutputStream(myServer.getOutputStream());
            output.writeUTF(gson.toJson(req));
            output.flush();

            String content = "";

            do {
                content = input.readUTF();
                sb.append(content);
            } while (input.available() != 0);

            content = sb.toString();
            Type requestType = new TypeToken<DataModel<String>[]>() {}.getType();
            DataModel<String>[] response = new Gson().fromJson(content, requestType);
            System.out.println(response);
            Assert.assertArrayEquals(response,dataModelsArray);
            output.close();
            input.close();
            myServer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void deleteClientTest() {

        Map<String, String> headerReq = new HashMap<>();
        headerReq.put("action", "DELETE");

        DataModel<String>[] dataModelsArray = new DataModel[2];
        dataModelsArray[0] = new DataModel<String>(1L, "data11");
        dataModelsArray[1] = new DataModel<String>(2L, "data22");


        Request<DataModel<String>[]> req = new Request<>(headerReq, dataModelsArray);
        Assert.assertEquals(headerReq ,req.getHeaders());
        DataOutputStream output;
        DataInputStream input;
        Gson gson = new Gson();
        try {
            Socket myServer = new Socket("127.0.0.1", 12345);
            output = new DataOutputStream(myServer.getOutputStream());
            output.writeUTF(gson.toJson(req));
            output.flush();

            input = new DataInputStream(new BufferedInputStream(myServer.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String content = "";
            do {
                content = input.readUTF();
                sb.append(content);
            } while (input.available() != 0);
            content = sb.toString();
            Boolean response = false;
            response = new Gson().fromJson(content, response.getClass());
            System.out.println("message from server: " + response);
            Assert.assertTrue(response);
            output.close();
            input.close();
            myServer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
