package com.project.semipermbackend.store.dto;

import lombok.Getter;

public interface StoreSaveCreationDto {

    @Getter
    class Request {
        private String placeId;
    }

    class Response {

    }
}
