package com.crossfit.util;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class UtilsTest {
    @Test
    public void given_a_file_path_when_loadResource_is_called_then_the_file_content_is_returned() {
        String fileContent = Utils.loadResource("new_valid_proposed_for_time_request.json");

        assertThat(fileContent, containsString("exercises"));
    }

}
