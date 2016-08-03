package com.dr_alan_turing.webdash_cw.web.rest.dto;

import com.dr_alan_turing.webdash_cw.domain.Dashboard;

/**
 * Created by Dr-Alan-Turing on 03/08/2016.
 */
public class MyDashboardDTO {


    private String options;

    private String data1;

    private String data2;

    /**
     * Empty constructor
     */
    public MyDashboardDTO() {}

    /**
     * Full constructor
     *
     * @param options
     * @param data1
     * @param data2
     */
    public MyDashboardDTO(String options, String data1, String data2) {
        this.options = options;
        this.data1 = data1;
        this.data2 = data2;
    }

    /**
     * Constructor that copies attributes from related domain entity
     *
     * @param dashboard
     */
    public MyDashboardDTO(Dashboard dashboard) {
        this(dashboard.getOptions(), dashboard.getData1(), dashboard.getData2());
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
