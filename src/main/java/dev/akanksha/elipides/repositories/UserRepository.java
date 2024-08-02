package dev.akanksha.elipides.repositories;


import dev.akanksha.elipides.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query("UPDATE User u SET u.firstName = :firstName WHERE u.username = :username")
    @Modifying
    void updateFirstName(@Param("firstName") String firstName, @Param("username") String username);

    @Query("UPDATE User u SET u.lastName = :lastName WHERE u.username = :username")
    @Modifying
    void updateLastName(@Param("lastName") String lastName, @Param("username") String username);

    @Query("UPDATE User u SET u.status = :status WHERE u.username = :username")
    @Modifying
    void updateStatus(@Param("status") String status, @Param("username") String username);

    @Query("UPDATE User u SET u.password = :password WHERE u.username = :username")
    @Modifying
    void updatePassword(@Param("password") String password, @Param("username") String username);

    @Query("UPDATE User u SET u.email = :email WHERE u.username = :username")
    @Modifying(clearAutomatically = true)
    void updateEmail(@Param("email") String email, @Param("username") String username);
}
