package com.global;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.UUID;

// UUID 버전 1
public class UUIDFactory {

    private static UUIDFactory instance = null;
    
    private static long lastTime = 0L;
    private static long clockSequence = 0L;
    private static final long node = initializeNode();
    
    private UUIDFactory() {}
    
    private static UUIDFactory getInstance() {
        if(instance == null) {
            synchronized(UUIDFactory.class) {
                instance = new UUIDFactory();
            }
        }
        return instance;
    }
    
    public static synchronized UUID generateUUID() {
        long currentTime = System.currentTimeMillis();
        if (currentTime != lastTime) {
            lastTime = currentTime;
            clockSequence = new SecureRandom().nextLong() & 0x3FFF; // 오버플로우 방지
        } else {
            clockSequence = (clockSequence + 1) & 0x3FFF;
        }

        // 컴퓨터 시스템은 1970/1/1을 기준으로 하는 UNIX 시스템을 따르기에 UUID가 요구하는 기준 1582/10/15을 따르기 위해 더해줘야 함
        long time = (currentTime * 10000) + 0x01B21DD213814000L;

        /*
         * 0x1000 
         *    - UUID 버전 1 사용
         * 
         * time << 32 
         *    - 시간 값을 32비트 왼쪽으로 이동
         * 
         * time & 0xFFFF00000000L
         *    - 시간의 중간 부분을 추출해서 16비트 오른쪽으로 밀기
         *    - time의 상위 48~63비트만을 선택
         *    
         * clockSequence & 0x3F000
         *    - 14번째부터 19번째 비트까지 선택
         *    
         * clockSequence & 0x3FFF
         *    - 하위 14비트만 선택
         */
        long mostSigBits = (time << 32) | ((time & 0xFFFF00000000L) >> 16) | 0x1000 | ((clockSequence & 0x3F000) >> 48);
        long leastSigBits = (clockSequence & 0x3FFF) << 48 | node;

        return new UUID(mostSigBits, leastSigBits);
    }

    // 서버의 mac 추소를 node로 사용
    private static long initializeNode() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                
                if (networkInterface != null && networkInterface.getHardwareAddress() != null) {
                    // 6바이트 길이 mac 주소 가져오기
                    byte[] mac = networkInterface.getHardwareAddress();
                    
                    long node = 0;
                    for (int i = 0; i < Math.min(mac.length, 6); i++) {
                        /*
                         * mac[i] & 0xFF
                         *    - 바이트를 부호 없는 정수로 변환
                         *    - & 0xFF 사용하여 0에서 255까지 정수로 해석
                         *    
                         * << (mac.length - i - 1) * 8
                         *    - mac의 첫번째 바이트는 제일 앞에 두번째 바이트는 두번째에 배치되어야 하기에 밀어주기 
                         *    - 첫번째 바이트는 40비트 왼쪽으로 두번째 바이트는 32비트 왼쪽으로 시프트 됨
                         */
                        node |= ((long) mac[i] & 0xFF) << (mac.length - i - 1) * 8;
                    }
                    return node;
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException("Error obtaining MAC address", e);
        }
        
        // mac 조회할 수 없는 경우 임의의 long 던짐 (48비트)
        return new SecureRandom().nextLong() & 0xFFFFFFFFFFFFL;
    }

    public static void main(String[] args) {
        UUID uuid1 = generateUUID();
        System.out.println("Generated UUID Version 1: " + uuid1.toString());
    }
}
