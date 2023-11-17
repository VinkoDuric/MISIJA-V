package com.misijav.flipmemo.exception;

import java.util.Date;

public record ApiError(
        Date timestamp,
        int status,
        String error,
        String path
) { }