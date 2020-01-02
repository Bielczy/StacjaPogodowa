package com.example.stacjapogodowa;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class DataForRealm extends RealmObject {

    public static final String CHART_LABEL = "CHART_LABEL";
    public static final String VALUE_X = "xValue";
    public static final String VALUE_Y = "yValue";

    @PrimaryKey
    @Required
    public String chartLabel;
    public Float x = 0f;
    public Float y = 0f;




}
