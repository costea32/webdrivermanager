/*
 * (C) Copyright 2021 Boni Garcia (http://bonigarcia.github.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package io.github.bonigarcia.wdm.test.generic;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.condition.OS.LINUX;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Test with generic manager.
 *
 * @author Boni Garcia
 * @since 5.0.0
 */
@EnabledOnOs(LINUX)
class GenericCreateTest {

    final Logger log = getLogger(lookup().lookupClass());

    WebDriver driver;

    WebDriverManager wdm;

    @AfterEach
    void teardown() {
        wdm.quit();
        System.clearProperty("wdm.defaultBrowser");
    }

    @ParameterizedTest
    @ValueSource(strings = { "chrome", "firefox" })
    void test(String defaultBrowser) {
        System.setProperty("wdm.defaultBrowser", defaultBrowser);
        wdm = WebDriverManager.getInstance();
        driver = wdm.create();

        String sutUrl = "https://bonigarcia.org/webdrivermanager";
        driver.get(sutUrl);
        String title = driver.getTitle();
        log.debug("The title of {} is {}", sutUrl, title);

        Wait<WebDriver> wait = new WebDriverWait(driver,
                Duration.ofSeconds(30));
        wait.until(d -> d.getTitle().contains("WebDriverManager"));
    }

}
