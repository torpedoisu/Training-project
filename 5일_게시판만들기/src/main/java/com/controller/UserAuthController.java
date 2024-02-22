package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.exception.CustomException;
import com.global.HttpUtil;
import com.global.ResponseData;
import com.global.Status;
import com.service.UserService;
import com.vo.UserVO;

public class UserAuthController implements Controller{

    public static Logger logger = LogManager.getLogger(UserLogoutController.class);
    
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        logger.debug("UserAuthController 진입");
        
        UserService userService = UserService.getInstance();
        
        PrintWriter out = res.getWriter();
        
        HttpSession session = req.getSession();
        
        // 세션을 가져올 수 없는 경우
        if(session == null) {
            throw new CustomException("세션을 생성할 수 없습니다", HttpServletResponse.SC_CONFLICT, "/index.jsp");
        
        // 세션이 정상적으로 불러와진 경우
        } else {
            Object obj = session.getAttribute("user");
            
            // 유저 객체 제대로 불러와졌다면 아이디와 비밀번호 일치하는지 검증
            if (obj instanceof UserVO) {
                UserVO user = (UserVO) obj;
                
                String userPk = user.getPk();
                String userId = user.getId();
                String userPwd = user.getPwd();
                
                boolean isValid = userService.checkUserIsValidInSession(userPk, userId, userPwd);
                
                // 유저 정보가 db와 일치하는 경우
                if (isValid) {
                    res.setStatus(HttpServletResponse.SC_OK);
                    out.print(new ResponseData(Status.SUCCESS));
                
                // 유저 정보가 db와 일치하지 않는 경우
                } else {
                    //TODO:어디로 갈지?
                    throw new CustomException("잘못된 아이디이거나 잘못된 비밀번호 입니다", HttpServletResponse.SC_BAD_REQUEST, "/index.jsp");
                }
                
            // 유저 객체가 제대로 불러와지지 않는 경우
            } else {
                // TODO: 어디로 갈지?
                throw new CustomException("유저 정보가 손상되었습니다", HttpServletResponse.SC_BAD_REQUEST, "/index.jsp");
            }    
        }

        out.flush();
        out.close();
        return;
    }

}
