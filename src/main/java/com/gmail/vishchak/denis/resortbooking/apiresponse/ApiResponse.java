package com.gmail.vishchak.denis.resortbooking.apiresponse;

public record ApiResponse<T>(boolean success,
                             T data,
                             String message
) {
}
