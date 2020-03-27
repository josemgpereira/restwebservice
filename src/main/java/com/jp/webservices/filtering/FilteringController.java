package com.jp.webservices.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class FilteringController {

    @GetMapping("/filtering")
    public MappingJacksonValue retrieveSomeBean() {
        SomeBean someBean = new SomeBean("value1", "value2", "value3");
        //SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field2");
        //FilterProvider filters = new SimpleFilterProvider().addFilter("someBeanFilter", filter);
        //MappingJacksonValue mapping = new MappingJacksonValue(someBean);
        //mapping.setFilters(filters);
        FilterProvider filters = createFilters(Arrays.asList("field1", "field2"));
        MappingJacksonValue mapping = new MappingJacksonValue(someBean);
        mapping.setFilters(filters);
        return mapping;
    }

    @GetMapping("/filtering-list")
    public MappingJacksonValue retrieveListOfSomeBeans() {
        List<SomeBean> list = Arrays.asList(new SomeBean("value1", "value2", "value3"), new SomeBean("value12", "value22", "value32"));
        FilterProvider filters = createFilters(Arrays.asList("field2", "field3"));
        MappingJacksonValue mapping = new MappingJacksonValue(list);
        mapping.setFilters(filters);
        return mapping;
    }

    public FilterProvider createFilters(List<String> fields) {
        Set<String> properties = new HashSet<>(fields);
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept(properties);
        FilterProvider filters = new SimpleFilterProvider().addFilter("someBeanFilter", filter);
        return filters;
    }
}