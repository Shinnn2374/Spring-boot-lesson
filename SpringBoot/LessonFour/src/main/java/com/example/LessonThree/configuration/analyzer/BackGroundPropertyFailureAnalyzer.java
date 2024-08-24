package com.example.LessonThree.configuration.analyzer;

import com.example.LessonThree.Exception.BackGroundTaskPropertiesException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

import java.text.MessageFormat;

public class BackGroundPropertyFailureAnalyzer extends AbstractFailureAnalyzer<BackGroundTaskPropertiesException>
{
    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, BackGroundTaskPropertiesException cause) {
        return new FailureAnalysis(MessageFormat.format("Exception when try to set property: {}",cause.getMessage()),
                "set-application-properties", cause );
    }
}
