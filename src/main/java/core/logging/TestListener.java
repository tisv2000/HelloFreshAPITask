package core.logging;

import io.qameta.allure.Attachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestListener extends TestListenerAdapter {
    @Override
    public void onTestStart(ITestResult iTestResult) {
        super.onTestStart(iTestResult);
        getLogger(iTestResult).info("STARTED: {}", iTestResult.getName());
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        super.onTestSuccess(iTestResult);
        getLogger(iTestResult).info("Finished successfully: {}", iTestResult.getName());
        finalizeWithTestLog();
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        super.onTestSkipped(iTestResult);
        getLogger(iTestResult).warn("Skipped: {}", iTestResult.getName());
        finalizeWithTestLog();
    }

    @Override
    public void onTestFailure(ITestResult testResult) {
        super.onTestFailure(testResult);
        getLogger(testResult).error("FAILED: {} WITH: {}", testResult.getName(), testResult.getThrowable().toString());
        finalizeWithTestLog();
    }

    private Logger getLogger(ITestResult iTestResult) {
        return LoggerFactory.getLogger(iTestResult.getTestClass().getName());
    }

    private void finalizeWithTestLog() {
        if (ThreadLocalLogAppender.isCurrentTreadLogPresented()) {
            attachLog();
            ThreadLocalLogAppender.clearCurrentThreadLog();
        }
    }

    @Attachment(value = "Log", type = "text/plain", fileExtension = ".txt")
    private byte[] attachLog() {
        return ThreadLocalLogAppender.getCurrentThreadLogAsBytes();
    }
}
