package io.reactivesw.exception.handler;

import io.reactivesw.exception.AlreadyExistException;
import io.reactivesw.exception.AuthFailedException;
import io.reactivesw.exception.AuthInfoMissingException;
import io.reactivesw.exception.ConflictException;
import io.reactivesw.exception.CreateResourceFailed;
import io.reactivesw.exception.ImmutableException;
import io.reactivesw.exception.NotExistException;
import io.reactivesw.exception.ParametersException;
import io.reactivesw.exception.PasswordErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionHandler {

  /**
   * logger.
   */
  Logger LOG = LoggerFactory.getLogger(ExceptionHandler.class);

  /**
   * exception EXCEPTION_MAP.
   */
  private Map<Class<?>, HttpStatus> EXCEPTION_MAP = new ConcurrentHashMap<Class<?>,
      HttpStatus>() {
    {
      this.put(AlreadyExistException.class, HttpStatus.CONFLICT);
      this.put(AuthFailedException.class, HttpStatus.UNAUTHORIZED);
      this.put(AuthInfoMissingException.class, HttpStatus.BAD_REQUEST);
      this.put(ConflictException.class, HttpStatus.CONFLICT);
      this.put(CreateResourceFailed.class, HttpStatus.INTERNAL_SERVER_ERROR);
      this.put(ImmutableException.class, HttpStatus.FORBIDDEN);
      this.put(NotExistException.class, HttpStatus.NOT_FOUND);
      this.put(ParametersException.class, HttpStatus.BAD_REQUEST);
      this.put(PasswordErrorException.class, HttpStatus.UNAUTHORIZED);
    }
  };

  /**
   * exception that do not log.
   */
  private ArrayList<Class> OMITTED_EXCEPTIONS = new ArrayList<>();

  {
    OMITTED_EXCEPTIONS.add(AlreadyExistException.class);
    OMITTED_EXCEPTIONS.add(AuthFailedException.class);
    OMITTED_EXCEPTIONS.add(AuthInfoMissingException.class);
    OMITTED_EXCEPTIONS.add(ConflictException.class);
    OMITTED_EXCEPTIONS.add(CreateResourceFailed.class);
    OMITTED_EXCEPTIONS.add(ImmutableException.class);
    OMITTED_EXCEPTIONS.add(NotExistException.class);
    OMITTED_EXCEPTIONS.add(ParametersException.class);
    OMITTED_EXCEPTIONS.add(PasswordErrorException.class);
  }

  /**
   * set the error info that need to send back to the client.
   *
   * @param request  HttpServletRequest
   * @param response HttpServletResponse
   * @param obj      Object
   * @param ex       Exception
   * @return the input exception
   */
  public Exception setResponse(HttpServletRequest request, HttpServletResponse response,
                               Object obj,
                               Exception ex) {
    // get the status
    HttpStatus status = EXCEPTION_MAP.get(ex.getClass());
    if (status == null) {
      //if this is an unexpected exception, set the code to internal server error.
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    logException(request, response, obj, status, ex);

    response.setStatus(status.value());
    response.addHeader("ErrorMessage", ex.getMessage());
    //use the application_json_utf8_value
    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    return ex;
  }

  /**
   * log the detail of the exception.
   * only log these exceptions that does not excepted.
   *
   * @param ex exception
   */
  private void logException(HttpServletRequest request, HttpServletResponse response,
                            Object obj, HttpStatus status, Exception ex) {
    boolean shouldLog = !OMITTED_EXCEPTIONS.contains(ex.getClass());
    if (shouldLog) {
      // only log those ones that are real failures
      LOG.error("request {}, response {}, obj {}, status {}", request, response, obj, status, ex);
    }
  }
}
