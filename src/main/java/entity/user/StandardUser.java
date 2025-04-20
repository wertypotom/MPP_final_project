package entity.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("User")
public class StandardUser extends User {

    public StandardUser() {
        super();
    }

    public StandardUser(int userId, String name, String email) {
        super(userId, name, email);
    }
}