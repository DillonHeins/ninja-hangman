package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.session.Session;
import services.AuthenticationService;

import javax.persistence.NoResultException;

/**
 * Created by dillon on 2017/01/22.
 * AuthenticationController
 */
@Singleton
public class AuthenticationController {
    @Inject
    AuthenticationService authenticationService;

    public Result register() {
        Result result = Results.html();

        result.render("page", "register");

        return result;
    }

    public Result registerPost(@Param("username") String username,
                               @Param("password") String password,
                               Context context) {
        boolean register = authenticationService.register(username, password);

        return Results.redirect("/");
    }

    public Result login(Context context) {
        Result result = Results.html();

        result.render("page", "login");

        return result;
    }

    public Result loginPost(@Param("username") String username,
                            @Param("password") String password,
                            Context context) {
        try {
            authenticationService.login(username, password);

            Session session = context.getSession();
            session.put("username", username);

            context.getFlashScope().success("login.loginSuccessful");

            return Results.redirect("/");
        } catch (NoResultException noResultException) {
            context.getFlashScope().put("username", username);
            context.getFlashScope().error("login.errorLogin");

            return Results.redirect("/login");
        }
    }

    public Result logout(Context context) {
        context.getSession().clear();
        context.getFlashScope().success("logout.logoutSuccessful");

        return Results.redirect("/");
    }
}
