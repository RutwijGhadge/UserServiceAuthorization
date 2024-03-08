package com.UserService.demo.Services;

import com.UserService.demo.Model.Session;
import com.UserService.demo.Model.SessionStatus;
import com.UserService.demo.Model.User;
import com.UserService.demo.Repository.SessionRepo;
import com.UserService.demo.Repository.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SessionRepo sessionRepo;

    @Autowired
    private SecretKey secret;

    public User signUp(String email, String password){
       Optional<User> userOptional= userRepo.findByEmail(email);
       if(userOptional.isEmpty()){//if the user is not there
           //we should create User
            User user=new User();
            user.setEmail(email);
           // user.setPassword(password);
           //now storing the Encrypted Password in the DB
            user.setPassword(bCryptPasswordEncoder.encode(password));
            User savedUser=userRepo.save(user);
            return savedUser;
       }

       return userOptional.get();//return the already present user
    }

    public Pair<User,MultiValueMap<String,String>> login(String email,String password){
        Optional<User> userOptional=userRepo.findByEmail(email);
        if(userOptional.isEmpty()){//if the user is not present (Checked by emailID)
            return null;
        }

        User user=userOptional.get();
       /* if(!user.getPassword().equals(password)){//user is present but the password NOT matches
            return null;
        }*/

        //Matching the encrypted password and the password user has sent(Both encrypted)s
        if(!bCryptPasswordEncoder.matches(password,user.getPassword()))
            return null;

        //Token Generation
       /* String Message = "{\n" +
                  "   \"email\": \"anurag@scaler.com\",\n" +
                  "   \"roles\": [\n" +
                  "      \"instructor\",\n" +
                  "      \"buddy\"\n" +
                  "   ],\n" +
                  "   \"expirationDate\": \"2ndApril2024\"\n" +
              "}";*/

        //byte[]Content=Message.getBytes(StandardCharsets.UTF_8);

        // String token= Jwts.builder().content(Content).compact();//Generation of token


        // headers.add(HttpHeaders.SET_COOKIE,token);//setting the cookie value to token
        //String token= Jwts.builder().content(Content).signWith(secret).compact();

        //Bean named SecretKey is created in SpringSecurity Config file
//        MacAlgorithm macAlgorithm=Jwts.SIG.HS256;
//        SecretKey secret=macAlgorithm.key().build();
        //signing token with the secret to avoid the Tampering with Token


        Map<String,Object>jwtData=new HashMap<>();
        jwtData.put("email",user.getEmail());
        jwtData.put("roles",user.getRoleList());
        long getTimeInMilis=System.currentTimeMillis();
        jwtData.put("expiryTime",new Date(getTimeInMilis+10000000));
        jwtData.put("createdAT",new Date(getTimeInMilis));

        String token= Jwts.builder().claims(jwtData).signWith(secret).compact();
        MultiValueMap<String,String>headers=new LinkedMultiValueMap();
        headers.add(HttpHeaders.SET_COOKIE,token);//setting the cookie value to token

        Session session=new Session();
        session.setUser(user);
        session.setToken(token);
        session.setSessionStatus(SessionStatus.Active);
        session.setExpiryAt(new Date(getTimeInMilis+10000));
        sessionRepo.save(session);


        return new Pair<User,MultiValueMap<String,String>>(user,headers);
        //returning user along with token
    }

    public Boolean validate(String token,Long userId){
            Optional<Session>optionalSession=sessionRepo.findByTokenAndUser_Id(token,userId);

            if(optionalSession.isEmpty()){
                System.out.println("No user or Token found");
                return false;
            }

            Session session= optionalSession.get();//particular session returned
            String tokens=session.getToken();

           //parsing : processing / fetching the info.
        JwtParser parser=Jwts.parser().verifyWith(secret).build();//fetching the secret
        Claims claims=parser.parseSignedClaims(tokens).getPayload();//obtain the payload
        System.out.println(claims);

        long nowInMillis=System.currentTimeMillis();
        long tokenExpiry=(Long)claims.get("expiryTime");
        //in Map key for ExpiryTime: "expiryTime"

        if(nowInMillis>tokenExpiry){
            System.out.println("Token is Expired");
            System.out.println("Time:"+nowInMillis);
            System.out.println("expiryTime:"+ tokenExpiry);
            return false;
        }

        Optional<User>optionalUser=userRepo.findById(userId);
        if(optionalUser.isEmpty()){
            return false;
        }

        String email=optionalUser.get().getEmail();
        if(!email.equals(claims.get("email"))) { //key for storing email: "email"
            System.out.println(email);
            System.out.println(claims.get("email"));
            System.out.println("email Not Matching");
            return false;
        }

     return true;
    }

}
