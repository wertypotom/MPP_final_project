package entity.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Admin")
public class Admin extends User {

    public Admin() {
        super();
    }

    public Admin(int userId, String name, String email) {
        super(userId, name, email);
    }
}