package com.global;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Encrypt {
    public static String encode(String password) {
        try {
            // SHA-256 해시 알고리즘을 사용하여 MessageDigest 객체 생성
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 비밀번호 문자열을 바이트 배열로 변환하여 해시 함수에 전달
            byte[] hashBytes = digest.digest(password.getBytes());

            // 해시된 바이트 배열을 Base64로 인코딩하여 문자열로 반환
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            // 알고리즘을 찾을 수 없는 경우 예외 처리
            e.printStackTrace();
            return null;
        }
    }
    
    // 사용자가 제공한 비밀번호와 저장된 해시된 비밀번호가 일치하는지 확인하는 메서드
    public static boolean authenticate(String providedPassword, String storedHashedPassword) {
        String hashedProvidedPassword = encode(providedPassword);

        // 해시된 비밀번호가 null이거나 저장된 해시된 비밀번호와 다른 경우
        return hashedProvidedPassword != null && hashedProvidedPassword.equals(storedHashedPassword);
    }

}
