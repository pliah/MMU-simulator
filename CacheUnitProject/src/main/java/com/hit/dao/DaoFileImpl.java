package com.hit.dao;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hit.dm.DataModel;
import com.google.gson.Gson;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * This class stands for manipulating the I/O of the file system
 * */
public class DaoFileImpl<T> implements IDao<java.lang.Long, DataModel<T>> {
    private int capacity;
    private java.lang.String filePath;
    private Map<Long, DataModel<T>> fileSystemMap;

    public DaoFileImpl(String filePath, int capacity) {
        this.capacity = capacity;
        this.filePath = filePath;
        this.fileSystemMap = new HashMap<Long, DataModel<T>>(capacity);
    }

    public DaoFileImpl(java.lang.String filePath) {
        this.filePath = filePath;
        this.fileSystemMap = new HashMap<Long, DataModel<T>>();
    }
    /**
     * function save's work is to  write stuff to disk.
     * it checks you don't put null entity to memory,
     * and that there is no such an id's data yet.
     * @params - entity = the data we want to save to the hardisk
     * */
    @Override
    public void save(DataModel<T> entity) {
        read();
        if (null != entity) {
            if (fileSystemMap != null) {

                if (isIdExists((HashMap) fileSystemMap, entity.getDataModelId())) {
                    fileSystemMap.remove(entity.getDataModelId());
                }
            }
            else {
                fileSystemMap = new HashMap<>();
            }
            this.fileSystemMap.put(entity.getDataModelId(), entity);
            updateMemory();
        }
    }

    /**
     * function delete's work is to  write stuff to disk.
     * @params - entity = the data we want to delete from the hardisk
     * */

    @Override
    public void delete(DataModel<T> entity) {
        read();
        this.fileSystemMap.remove(entity.getDataModelId());
        updateMemory();
    }

    /**
     * function find's work is to  write stuff to disk.
     * it reads the whole data to hashmap, delete the right one and run over the file
     * @params - id = the data's id we want to find in the hardisk
     * @return the DataModel data that found
     * */

    @Override
    public DataModel<T> find(java.lang.Long id) {
        read();
        if (fileSystemMap.get(id) != null) {
            DataModel<T> findData = new DataModel<T>(id, (fileSystemMap.get(id)).getContent());
            return findData;
        }
        return null;
    }
    /**
     * isIdExists function is an helper that check's if certain id exists already in disk
     * @params - fileSystemMap: the hash map with the disk content'
     *           id: the one we want to know if exist or not
     * @return boolean with false value if it is not exists, true if it is.
     * */
    private boolean isIdExists(HashMap fileSystemMap, Long id) {
        return (fileSystemMap.containsKey(id.toString()));
    }

    /**
     * read function is an helper that reads data from the file to hashmap
     * */

    private void read() {
        Gson gson = new Gson();
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("src/main/resources/" + this.filePath));
            String line = ReadBigStringIn(reader);
            Type type = new TypeToken<Map<Long, DataModel<T>>>() {
            }.getType();
            this.fileSystemMap = gson.fromJson(line, type);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  ReadBigStringIn function is an helper that create string of file's data.
     * @params - buffIn: the BufferedReader of the file.
     * @return String of data.
     * */
    private String ReadBigStringIn(BufferedReader buffIn) throws IOException {
        StringBuilder everything = new StringBuilder();
            String line;
            while ((line = buffIn.readLine()) != null) {
                everything.append(line);
            }
            return everything.toString();
    }

    /**
     *  updateMemory function is an helper that put the updated data into the disk.
     * */
    private void updateMemory() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(this.fileSystemMap);
        try {
            Writer writer = Files.newBufferedWriter(Paths.get("src/main/resources/" + this.filePath));
            writer.write(jsonString);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
