package com.arity.a6;

import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
public class JmxProcessor {

    private final JmeterPropertyLoader jmeterPropertyLoader;

    @Autowired
    public JmxProcessor(JmeterPropertyLoader jmeterPropertyLoader) {
        this.jmeterPropertyLoader = jmeterPropertyLoader;
    }

    @RequestMapping(
            value = "/jmx", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void process() {

        StandardJMeterEngine jmeter = new StandardJMeterEngine();

        JMeterUtils.loadJMeterProperties(jmeterPropertyLoader.getJmeterProperties());
        JMeterUtils.setJMeterHome(jmeterPropertyLoader.getJmeterHome());
        JMeterUtils.initLogging();// you can comment this line out to see extra log messages of i.e. DEBUG level
        JMeterUtils.initLocale();

        HashTree testPlanTree = null;

        try {
            // Initialise JMeter SaveService via saveservice.properties
            SaveService.loadProperties();

            // Load existing .jmx Test Plan
            testPlanTree = SaveService.loadTree(new File(jmeterPropertyLoader.getJmeterJmx()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Run JMeter Test
        jmeter.configure(testPlanTree);
        jmeter.run();
    }
}