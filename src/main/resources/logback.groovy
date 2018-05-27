import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import java.nio.charset.Charset
import static ch.qos.logback.classic.Level.INFO

def LOG_HOME = "./logs"

appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n"
        charset = Charset.forName("utf8")
    }
}

appender("FILE", RollingFileAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} [%-5level] [%logger{50}] - %msg%n"
        charset = Charset.forName("utf8")
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${LOG_HOME}/galaxy-message.log.%d{yyyy-MM-dd}.log"
        maxHistory = 10
    }
    triggeringPolicy(SizeBasedTriggeringPolicy) {
        maxFileSize = "10MB"
    }
}

logger("com.bjfl.galaxymessage.netty", INFO, ["FILE", "STDOUT"], false)

logger("com.bjfl.galaxymessage.mqtt", INFO, ["FILE", "STDOUT"], false)

logger("com.bjfl.galaxymessage.util", ERROR, ["FILE", "STDOUT"], false)

logger("com.bjfl.galaxymessage.messages", INFO, ["FILE", "STDOUT"], false)

root(INFO, ["STDOUT"])