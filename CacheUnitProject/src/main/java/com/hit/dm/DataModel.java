package com.hit.dm;
import java.io.Serializable;

/**
 * DataModel class stands for declaring the structure of data units that will be manipulated in the disk
 * @param <T> the type of the data content
 * the function are getters' setters and overridden functions.
 */
public class DataModel<T> implements Serializable{
    private java.lang.Long id;
    private T content;

    public DataModel(java.lang.Long id, T content){
        this.id=id;
        this.content=content;
    }
    @Override
    public boolean equals(java.lang.Object obj) {
        DataModel<T> objCast = (DataModel<T>) obj;
        return this.content.equals(objCast.getContent()) && this.id.equals( objCast.getDataModelId()) ;
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "id=" + id +
                ", content=" + content +
                '}';
    }
    public Long getDataModelId(){
        return  this.id;
    }
    public void setDataModelId(java.lang.Long id){
        this.id=id;
    }
    public T getContent(){
        return  this.content;
    }
    public void setContent(T content){
        this.content=content;
    }
}
