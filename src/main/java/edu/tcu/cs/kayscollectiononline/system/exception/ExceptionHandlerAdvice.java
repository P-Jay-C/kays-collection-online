package edu.tcu.cs.kayscollectiononline.system.exception;

import edu.tcu.cs.kayscollectiononline.artifact.ArtifactNotFoundException;
import edu.tcu.cs.kayscollectiononline.system.Result;
import edu.tcu.cs.kayscollectiononline.system.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(ArtifactNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Result handleArtifactNotFoundException(ArtifactNotFoundException ex){
        return  new Result(false, StatusCode.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    Result handleValidationException(MethodArgumentNotValidException ex){
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        Map<String,String> map = new HashMap<>(errors.size());

        errors.forEach((error) -> {
            String key = ((FieldError) error).getField();
            String val = error.getDefaultMessage();
            map.put(key,val);
        });

        return new Result(false, StatusCode.INVALID_ARGUMENT, "Provided arguments are invalid, see data for details.", map);
    }

}
