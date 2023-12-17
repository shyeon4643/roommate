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
import com.roommate.roommate.user.dto.response.UserInfoResponseDto;
import com.roommate.roommate.user.dto.response.UserLoginResponseDto;
import com.roommate.roommate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.roommate.roommate.exception.ExceptionCode.*;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


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


    @Transactional
    public UserLoginResponseDto login(SignInRequestDto signInRequestDto){
        try {
            User user = userRepository.findByUidAndIsDeletedIsFalse(signInRequestDto.getUid());

            if (!passwordEncoder.matches(signInRequestDto.getPassword(), user.getPassword())) {
                throw new CustomException(null,INCORRECT_PASSWORD_REQUIRED);
            }

            return new UserLoginResponseDto(user, createToken(user));
        }catch(RuntimeException e){
            e.printStackTrace();
            throw new CustomException(e,SERVER_ERROR);
        }
    }

    @Transactional
    public AccountTokenInfoDto createToken(User user){
        String accessToken = jwtTokenProvider.generateAccessToken(user.getId(),Role.ROLE_USER.toString());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId(),Role.ROLE_USER.toString());

        user.setToken(refreshToken);
        userRepository.save(user);
        return new AccountTokenInfoDto(accessToken);
    }

    @Transactional
    public UserInfoResponseDto writeDetailRoommate(DetailRoommateRequestDto detailRoommateRequestDto, Long id){
        try {
            User user = userRepository.findById(id).get();

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
            return new UserInfoResponseDto(user);
        }catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(e,SERVER_ERROR);
        }
    }

    @Transactional
    public UserInfoResponseDto updateDetailRoommate(DetailRoommateRequestDto detailRoommateRequestDto, Long id){
        User user = userRepository.findById(id).get();

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

        return new UserInfoResponseDto(user);
    }

    @Transactional
    public UserInfoResponseDto updateUserInformation(Long id, String password, String nickname, String Email){
        try{
            User user = userRepository.findById(id).get();
            user.updatePassword(passwordEncoder.encode(password));
            user.updateNickname(nickname);
            user.updateEmail(Email);
            userRepository.save(user);
            return new UserInfoResponseDto(user);
        }catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(e,SERVER_ERROR);
        }
    }


    @Transactional
    public void deleteUser(Long id){
        try{
            User user = findById(id);
            user.delete();
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(e,SERVER_ERROR);
        }
    }

    public UserInfoResponseDto myPage(Long id){
        User user = userRepository.findById(id).get();
        return new UserInfoResponseDto(user);
    }

    public User findById(Long id){
        return userRepository.findById(id).get();
    }

}
