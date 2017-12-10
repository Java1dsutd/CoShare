package com.example.chris.coshare.SampleData;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FastSampleDataProvider {

    public static List<FastDataModel> dataItemList;
    public static Map<String, FastDataModel> dataItemMap;

    static {
        dataItemList = new ArrayList<>();
        dataItemMap = new HashMap<>();

        addItem(new FastDataModel("Gh73d2_ag4", "Ang Mo Kio", "blahblah",
                "North", "amk.png"));

        addItem(new FastDataModel("x5g3d29d6s", "Bishan", "blahblah",
                "North", "bsh.png"));

        addItem(new FastDataModel("53bfy5n0ry", "Caldecott Hill", "blahblah ",
                "Central", "cdct.png"));

        addItem(new FastDataModel("x5g3345d6s", "Rochor", "blahblah",
                "Central", "rcr.png"));

        addItem(new FastDataModel("y5ged22d6s", "Suntec", "blahblah",
                "Central", "stc.png"));

        addItem(new FastDataModel("y8g3dfy96s", "Tampines", "blahblah",
                "East", "tmp.png"));

    }

    private static void addItem(FastDataModel item){
        dataItemList.add(item);
        dataItemMap.put(item.getTableID(), item);
    }

}
