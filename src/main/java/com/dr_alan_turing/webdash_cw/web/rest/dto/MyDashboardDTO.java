package com.dr_alan_turing.webdash_cw.web.rest.dto;

/**
 * Created by Dr-Alan-Turing on 03/08/2016.
 */
public class MyDashboardDTO {

    private String options;

    private String data1;

    private String data2;

    public MyDashboardDTO(String options, String data1, String data2) {
        setOptions(options);
        setData1(data1);
        setData2(data2);
    }

    public String getOptions() {
        return options;
    }

    public String getData1() {
        return data1;
    }

    public String getData2() {
        return data2;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

}
