package pl.swapmed.service;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CurrentUser extends User {
    private final pl.swapmed.model.User user;
    public CurrentUser(String username, String password,
                       Collection<? extends GrantedAuthority> authorities,
                       pl.swapmed.model.User user) {
        super(username, password, authorities);
        this.user = user;
    }

    public pl.swapmed.model.User getUser() {
        return user;
    }
}
