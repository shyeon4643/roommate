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
import com.roommate.roommate.user.dto.response.AccountTokenInfoDto;
import com.roommate.roommate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static com.roommate.roommate.exception.ExceptionCode.*;

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
        if(userRepository.existsByUid(request.getUid())) {
            throw new CustomException(null,DUPLICATE_ID);
        }
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(null,DUPLICATE_EMAIL);
        }
            User user = User.builder()
                    .name(request.getName())
                    .birth(request.getBirth())
                    .uid(request.getUid())
                    .nickname(request.getNickname())
                    .phoneNum(request.getPhoneNum())
                    .mbti(request.getMbti())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .email(request.getEmail())
                    .gender(request.getGender())
                    .build();

            userRepository.save(user);
            return user;

        }


    /**
     * 회원 가입
     * 500(SERVER_ERROR)
     */
    @Transactional
    public User kakaoJoin(SignUpRequestDto request){
        if(userRepository.existsByUid(request.getUid())) {
            throw new CustomException(null,DUPLICATE_ID);
        }
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(null,DUPLICATE_EMAIL);
        }
        User user = User.builder()
                .name(request.getName())
                .birth(request.getBirth())
                .uid(request.getUid())
                .nickname(request.getNickname())
                .phoneNum(request.getPhoneNum())
                .mbti(request.getMbti())
                .email(request.getEmail())
                .gender(request.getGender())
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
        try {
            User user = userRepository.findByUidAndIsDeletedIsFalse(signInRequestDto.getUid());

            if (!passwordEncoder.matches(signInRequestDto.getPassword(), user.getPassword())) {
                throw new CustomException(null,INCORRECT_PASSWORD_REQUIRED);
            }

            return user;
        }catch(RuntimeException e){
            e.printStackTrace();
            throw new CustomException(e,SERVER_ERROR);
        }
    }

    @Transactional
    public User detailRoommate(DetailRoommateRequestDto detailRoommateRequestDto, Long id){
        try {
            User user = findById(id);
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
            throw new CustomException(e,SERVER_ERROR);
        }
    }

    @Transactional
    public User updateDetailRoommate(DetailRoommateRequestDto detailRoommateRequestDto, Long id){
        User user = findById(id);
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
        throw new CustomException(e,SERVER_ERROR);
    }
}

    @Transactional
    public void updateEmail(User user, String email){
        try{
            user.updateEmail(email);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(e,SERVER_ERROR);
        }
    }

    @Transactional
    public void updateNickname(User user, String nickname){
        try{
            user.updateNickname(nickname);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(e,SERVER_ERROR);
        }
    }

    @Transactional
    public void deleteUser(User user){
        try{
            user.delete();
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(e,SERVER_ERROR);
        }
    }

    public User findById(Long id){
        return userRepository.findById(id).get();
    }

    @Transactional
    public AccountTokenInfoDto createToken(User user){
        String accessToken = jwtTokenProvider.generateAccessToken(user.getId(),Role.ROLE_USER.toString());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId(),Role.ROLE_USER.toString());

        user.setToken(refreshToken);
        userRepository.save(user);
        return new AccountTokenInfoDto(accessToken, refreshToken);
    }

    public AccountTokenInfoDto refreshToken(User user, String refreshToken){
        String accessToken = jwtTokenProvider.generateAccessToken(user.getId(),Role.ROLE_USER.toString());
        return new AccountTokenInfoDto(accessToken, refreshToken);
    }
}
