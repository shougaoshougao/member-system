package cn.bmaster.member.dao;

import cn.bmaster.member.entity.system.User;
import com.renaissance.core.jpa.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRepository extends BaseRepository<User, Long> {

    public User findByUserNameAndDeleted(String userName, boolean deleted);

    List<User> findAllByIdIn(List<Long> creatorIds);

    User findTopByMobileAndDeleted(String mobile, boolean deleted);

    List<User> findAllByDeleted(boolean deleted);

    Page<User> findAllByDeleted(boolean deleted, Pageable pageable);

    User findTopByUserNameAndDeleted(String userName, boolean deleted);

    User findTopByUserNameAndIdNotAndDeleted(String userName, Long userId, boolean deleted);

    User findTopByMobileAndIdNotAndDeleted(String mobile, Long userId, boolean deleted);
}
