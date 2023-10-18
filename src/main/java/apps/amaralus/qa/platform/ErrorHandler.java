package apps.amaralus.qa.platform;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerErrorException;

import java.util.UUID;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    public ErrorResponse captureException(Exception e) {
        var errorId = UUID.randomUUID();
        log.error("Exception has occurred, errorId={}", errorId, e);
        return new ServerErrorException(buildErrorMessage(errorId, e), e);
    }

    @ExceptionHandler(ErrorResponseException.class)
    public ErrorResponse captureErrorResponseException(ErrorResponseException e) {
        return e;
    }

    private String buildErrorMessage(UUID errorId, Throwable t) {
        return "errorId=" + errorId + " cause: " + Throwables.getRootCause(t).getMessage();
    }
}