package com.company.sample.autoconfigure.base;

import com.company.sample.base.BaseConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import({BaseConfiguration.class})
public class BaseAutoConfiguration {
}

