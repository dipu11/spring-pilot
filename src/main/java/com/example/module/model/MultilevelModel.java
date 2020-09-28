package com.example.module.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

/**
 * Created by DIPU on 8/20/20
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MultilevelModel {

    private String id;
    private String name;
    private String region;
    private String division;
    private String district;
    private String upozilla;
    private String voterArea;

    @JsonProperty("migrateFrom")
    private void unpackNested(Map<String,Object> elements)
    {
        Map<String, String> divisionMap=(Map<String, String>)elements.get("division");
        Map<String, String> districtMap=(Map<String, String>)elements.get("district");
        Map<String, String> upoMap=(Map<String, String>)elements.get("upozilla");

        this.division=divisionMap.get("lookupId");
        this.district=districtMap.get("lookupId");
    }

}
