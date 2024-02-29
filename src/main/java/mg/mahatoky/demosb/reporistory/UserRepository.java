package mg.mahatoky.demosb.reporistory;

import mg.mahatoky.demosb.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author mtk_ext
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
