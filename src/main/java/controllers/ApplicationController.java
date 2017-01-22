package controllers;

import filters.AuthenticationFilter;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.SecureFilter;

import com.google.inject.Singleton;

@Singleton
public class ApplicationController {
    @FilterWith(AuthenticationFilter.class)
    public Result index() {
        Result result = Results.html();

        result.render("page", "index");

        return result;
    }
}
