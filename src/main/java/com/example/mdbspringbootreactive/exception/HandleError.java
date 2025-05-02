package com.example.mdbspringbootreactive.exception;

import com.example.mdbspringbootreactive.model.Rating;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class HandleError {
    private List<Rating> ratings = Collections.emptyList();
    private String errorMessage;

    public HandleError(List<Rating> ratings, String errorMessage) {
        this.ratings = ratings;
        this.errorMessage = errorMessage;
    }
}
