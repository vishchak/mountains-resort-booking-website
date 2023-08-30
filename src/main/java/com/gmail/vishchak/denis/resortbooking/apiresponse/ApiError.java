package com.gmail.vishchak.denis.resortbooking.apiresponse;

import java.time.LocalDateTime;

public record ApiError(String path,
                       String message,
                       int statusCode,
                       LocalDateTime localDateTime
) {
}
