package com.example.chris.coshare.SampleData;

/**
 * Created by chris on 12/12/2017.
 */

public class BackEndFactory {

    public BackEndFactory() {}

    public data getBackend(String dataType){
        if (dataType=="users"){
            return new BackendUser();
        }
        else if(dataType=="locations"){
            return new BackendLocations();
        }
        else return null;
    }
}
