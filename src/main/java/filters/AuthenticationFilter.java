package filters;

import ninja.*;

import static ninja.SecureFilter.USERNAME;

/**
 * Created by dillon on 2017/01/22.
 * AuthenticationFilter - prevents unauthenticated users from viewing the index page
 */
public class AuthenticationFilter implements Filter {
    @Override
    public Result filter(FilterChain chain, Context context) {
        if (context.getSession() == null
                || context.getSession().get(USERNAME) == null) {

            return Results.redirect("/login");
//            return Results.forbidden().html().template("/views/system/403forbidden.ftl.html");
        } else {
            return chain.next(context);
        }
    }
}
