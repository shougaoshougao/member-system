package cn.bmaster.member.service;

import cn.bmaster.member.web.entity.request.user.CreateUserRequest;
import cn.bmaster.member.web.entity.request.user.EditUserRequest;
import cn.bmaster.member.dao.UserRepository;
import cn.bmaster.member.entity.system.User;
import com.renaissance.core.CoreConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public User load(Long id) {
        return userRepository.findById(id).orElseThrow(CoreConst::idNotFoundException);
    }

    @Transactional(readOnly = true)
    public User findByUserName(String username) {
        return userRepository.findByUserNameAndDeleted(username, false);
    }

    public User editUser(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> findByIds(List<Long> creatorIds) {
        return userRepository.findAllByIdIn(creatorIds);
    }

    @Transactional(readOnly = true)
    public User findByMobile(String mobile) {
        return userRepository.findTopByMobileAndDeleted(mobile, false);
    }

    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAllByDeleted(false, pageable);
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAllByDeleted(false);
    }

    public void createUser(CreateUserRequest createUserRequest, Long operatorId) {
        User user = userRepository.save(createUserRequest.toUser().initialize(operatorId));
    }

    @Transactional(readOnly = true)
    public User findTopByUserName(String userName) {
        return userRepository.findTopByUserNameAndDeleted(userName, false);
    }

    public User checkByUserNameAndId(String userName, Long userId) {
        return userRepository.findTopByUserNameAndIdNotAndDeleted(userName, userId, false);
    }

    public User checkByMobileAndId(String mobile, Long userId) {
        return userRepository.findTopByMobileAndIdNotAndDeleted(mobile, userId, false);
    }
}
