package com.hit.server;

/**
 * Request class defines the shape and functionality of a memmory request
 * @param <T>
 */
public class Request<T> extends java.lang.Object implements java.io.Serializable {

    private java.util.Map<java.lang.String,java.lang.String> headers=null;
    private T body;
    public Request(java.util.Map<java.lang.String,java.lang.String> headers, T body){
        this.headers=headers;
        this.body=body;
    }

    /**
     * @return - the headers of the request
     */
    public java.util.Map<java.lang.String,java.lang.String> getHeaders(){
        return  this.headers;
    }

    /**
     * sets the headers of the request
     * @param headers
     */
    public void setHeaders(java.util.Map<java.lang.String,java.lang.String> headers){
        this.headers=headers;
    }

    /**
     * @return - the body of the request
     */
    public T getBody(){
        return body;
    }

    /**
     * sets the body of the request
     * @param body
     */
    public void setBody(T body){
        this.body=body;
    }

    /**
     * @return he sring shape of a requst
     */
    @Override
    public String toString() {
        return "Request{" +
                "headers=" + headers +
                ", body=" + body +
                '}';
    }
}
