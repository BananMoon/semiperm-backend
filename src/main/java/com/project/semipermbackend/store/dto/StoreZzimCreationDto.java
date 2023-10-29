package com.project.semipermbackend.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public interface StoreZzimCreationDto {
    @Setter
    @Getter
    @NoArgsConstructor
    class Request {
        @NotBlank
        String placeId;
    }
    @Setter
    @Getter
    @AllArgsConstructor
    class Response {
        Long memberZzimStoreId;
    }
}
