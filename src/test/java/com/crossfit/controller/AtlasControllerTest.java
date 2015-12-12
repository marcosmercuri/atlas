package com.crossfit.controller;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.nio.charset.Charset;

import com.crossfit.AtlasApplication;
import com.crossfit.util.Utils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

@RunWith (SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration (classes = AtlasApplication.class)
@WebAppConfiguration
public class AtlasControllerTest {
    private MockMvc mockMvc;
    private MediaType jsonContentType;
    private MediaType xmlContentType;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp () {
        jsonContentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
              MediaType.APPLICATION_JSON.getSubtype(),
              Charset.forName("utf8"));
        xmlContentType = new MediaType(MediaType.APPLICATION_XML.getType(),
              MediaType.APPLICATION_XML.getSubtype(),
              Charset.forName("utf8"));
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void test_successful_new_proposed_workout () throws Exception {
        mockMvc.perform(
              post("/proposedWorkouts")
                    .content(createValidForTimeRequest())
                    .contentType(jsonContentType)
        )
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.id", is(not(""))));
    }

    private String createValidForTimeRequest () {
        return Utils.loadResource("new_valid_proposed_for_time_request.json");
    }

    @Test
    public void test_invalid_content_type_in_request_new_proposed_workout () throws Exception {
        ResultActions result = mockMvc.perform(
              post("/proposedWorkouts")
                    .content(createValidForTimeRequest())
                    .contentType(xmlContentType)
        );
        result.andDo(print());
        result.andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType());
    }
}
