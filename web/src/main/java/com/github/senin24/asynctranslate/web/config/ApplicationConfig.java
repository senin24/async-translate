package com.github.senin24.asynctranslate.web.config;

import com.github.senin24.asynctranslate.service.config.TranslateServiceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Application config.
 *
 * @author Pavel Senin
 */
@Configuration
@Import({TranslateServiceConfig.class})
@ComponentScan(basePackages = "com.github.senin24.asynctranslate")
public class ApplicationConfig {}
