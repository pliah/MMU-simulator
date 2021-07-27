package com.hit.view;
import java.beans.PropertyChangeSupport;

/**
 * CacheUnitView class stands for creating and manipulating the UI
 */
public class CacheUnitView {
    public final static String FALSE = "false";
    public final static String TRUE = "true";
    private PropertyChangeSupport support;
    private MyFrame myFrame;

    public CacheUnitView(){
        support = new PropertyChangeSupport(this);
    }
    public void start(){
         myFrame= new MyFrame(support);
    }

    public void addPropertyChangeListener(java.beans.PropertyChangeListener pcl){
        support.addPropertyChangeListener(pcl);
    }
    public void removePropertyChangeListener(java.beans.PropertyChangeListener pcl){
        support.removePropertyChangeListener(pcl);
    }

    /**
     * updateUIData function changes the message and the picture on the frame
     * accordingly to the server's response
     * @param t - the server response
     */
    public <T> void updateUIData(T t){
        String image= "";
        if (t.toString().equals(TRUE)){
            image ="src/main/resources/icons/V.png";
            myFrame.setUIData("your request has finished successfully\n\n please choose your next request ", image);
            return;
        }
        else if (t.toString().equals(FALSE)){
            image ="src/main/resources/icons/x.png";
            myFrame.setUIData("failed to load your request\n\n please try again", image);
            return;
        }
        else if (t.toString().contains("[")){
           String[] res = ((String) t).split(",");
           String response="Your Requested Data:\n\n";
           for(String memObj:res){
               if(memObj.contains("[")||memObj.contains("{")){
                 String newObject=(memObj.replace("[",""));
                 newObject= newObject.replace("null","object didn't found\n\n");
                 response+=(newObject.replace("{",""));
               }
              else if(memObj.contains("}")||memObj.contains("]")){
                   String newObject=(memObj.replace("]",""));
                   newObject=(newObject.replace("}","\n"));
                   response+=newObject.replace("null","object didn't found\n\n");
               }
           }
            myFrame.setUIData(response,"src/main/resources/icons/file.png");
        }
        else {
            String[] res = ((String) t).split(" ");
            myFrame.setUIData("Statistics Details:\n\n\nAlgorithm: " + res[1] + "\n\nCapacity: " + res[0] +
                    "\n\nTotal Numbers Of Requests: " + res[2] +
                    "\n\nTotal Number Of DataModel Swaps(From Cache To Disk): " + res[4] +
                    "\n\nTotal Number Of DataModels(GET/DELETE/UPDATE Requests): " + res[3],"src/main/resources/icons/statisticIcon.png");
            return;
        }
    }
}
