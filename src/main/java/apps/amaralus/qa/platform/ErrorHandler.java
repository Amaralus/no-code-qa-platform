package apps.amaralus.qa.platform;

import apps.amaralus.qa.platform.exception.EntityNotFoundException;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object captureValidationException(MethodArgumentNotValidException e) {
        return new ErrorResponseException(HttpStatus.BAD_REQUEST, ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage()), e);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public Object captureEntityNotFoundException(EntityNotFoundException e) {
        return new ErrorResponseException(HttpStatus.NOT_FOUND, ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage()), e);
    }

    private String buildErrorMessage(UUID errorId, Throwable t) {
        return "errorId=" + errorId + " cause: " + Throwables.getRootCause(t).getMessage();
    }
}