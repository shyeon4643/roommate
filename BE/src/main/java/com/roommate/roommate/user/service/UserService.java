package com.roommate.roommate.user.service;

import com.roommate.roommate.config.security.JwtTokenProvider;
import com.roommate.roommate.exception.CustomException;
import com.roommate.roommate.post.domain.PostArea;
import com.roommate.roommate.post.domain.PostCategory;
import com.roommate.roommate.user.domain.DetailRoommate;
import com.roommate.roommate.user.domain.Role;
import com.roommate.roommate.user.domain.User;
import com.roommate.roommate.user.domain.enums.Gender;
import com.roommate.roommate.user.domain.enums.LifeCycle;
import com.roommate.roommate.user.domain.enums.Pet;
import com.roommate.roommate.user.domain.enums.Smoking;
import com.roommate.roommate.user.dto.request.DetailRoommateRequestDto;
import com.roommate.roommate.user.dto.request.SignInRequestDto;
import com.roommate.roommate.user.dto.request.SignUpRequestDto;
import com.roommate.roommate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.roommate.roommate.exception.ExceptionCode.SERVER_ERROR;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원 가입
     * 500(SERVER_ERROR)
     */
    @Transactional
    public User join(SignUpRequestDto request){
        User user = User.builder()
                .name(request.getName())
                .birth(request.getBirth())
                .uid(request.getUid())
                .nickname(request.getNickname())
                .phoneNum(request.getPhoneNum())
                .mbti(request.getMbti())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .build();

        userRepository.save(user);
        return user;
    }

    /**
     * 회원 로그인
     * 500(SERVER_ERROR)
     */
    @Transactional
    public User login(SignInRequestDto signInRequestDto){
        User user = userRepository.findByUidAndIsDeletedIsFalse(signInRequestDto.getUid()).get();
        if(!passwordEncoder.matches(signInRequestDto.getPassword(),user.getPassword())){
            throw new RuntimeException();
        }
        String token = jwtTokenProvider.createToken(String.valueOf(user.getUid()), Role.convertEnum(user.getRole()));
        user.setToken(token);
        log.info("[Login Token] token : {} ",token);
        return user;

    }

    @Transactional
    public User detailRoommate(DetailRoommateRequestDto detailRoommateRequestDto, String uid){
        try {
            User user = userRepository.findByUid(uid);
            DetailRoommate detailRoommate = DetailRoommate.builder()
                    .pet(Pet.valueOf(detailRoommateRequestDto.getPet()))
                    .wishRoommate(detailRoommateRequestDto.getWishRoommate())
                    .area(PostArea.valueOf(detailRoommateRequestDto.getArea()))
                    .category(PostCategory.valueOf(detailRoommateRequestDto.getCategory()))
                    .fee(detailRoommateRequestDto.getFee())
                    .roommateGender(Gender.valueOf(detailRoommateRequestDto.getGender()))
                    .lifeCycle(LifeCycle.valueOf(detailRoommateRequestDto.getLifeCycle()))
                    .smoking(Smoking.valueOf(detailRoommateRequestDto.getSmoking()))
                    .build();
            user.setDetailRoommate(detailRoommate);
            userRepository.save(user);
            return user;
        }catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(SERVER_ERROR);
        }
    }

    @Transactional
    public User updateDetailRoommate(DetailRoommateRequestDto detailRoommateRequestDto, String uid){
        User user = findByUid(uid);
        user.getDetailRoommate().update(
                LifeCycle.valueOf(detailRoommateRequestDto.getLifeCycle()),
                Smoking.valueOf(detailRoommateRequestDto.getSmoking()),
                Gender.valueOf(detailRoommateRequestDto.getGender()),
                Pet.valueOf(detailRoommateRequestDto.getPet()),
                detailRoommateRequestDto.getFee(),
                detailRoommateRequestDto.getWishRoommate(),
                PostCategory.valueOf(detailRoommateRequestDto.getCategory()),
                PostArea.valueOf(detailRoommateRequestDto.getArea())
        );
        userRepository.save(user);
        return user;
    }

    @Transactional
    public void updatePassword(User user, String password){
        try{
        user.updatePassword(passwordEncoder.encode(password));
    } catch (RuntimeException e) {
        e.printStackTrace();
        throw new CustomException(SERVER_ERROR);
    }
}

    @Transactional
    public void updateEmail(User user, String email){
        try{
            user.updateEmail(email);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(SERVER_ERROR);
        }
    }

    @Transactional
    public void updateNickname(User user, String nickname){
        try{
            user.updateNickname(nickname);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(SERVER_ERROR);
        }
    }

    @Transactional
    public void deleteUser(User user){
        try{
            user.delete();
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(SERVER_ERROR);
        }
    }

    public User findById(Long id){
        return userRepository.findById(id).get();
    }

    public User findByUid(String uid){
        return userRepository.findByUid(uid);
    }
}
