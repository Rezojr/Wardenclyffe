package com.wardenclyffe.wardenclyffe.common.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException(Long id, String message) {
        super(message + id);
    }

}
