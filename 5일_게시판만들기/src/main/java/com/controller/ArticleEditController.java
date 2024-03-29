package com.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.exception.CustomException;
import com.service.ArticleService;
import com.vo.ArticleFileVO;
import com.vo.ArticleVO;
import com.vo.UserVO;

public class ArticleEditController implements Controller {
    
    public static Logger logger = LogManager.getLogger(ArticleEditController.class);
    
    /**
     * 게시글을 등록하는 메서드 (유저 정보 얻기 위한 세션 사용)
     * 
     * 예외 처리
     * - 세션이 존재하지 않는 경우 체크
     * - 등록된 객체가 UserVO의 인스턴스가 아닐 경우 체크
     * - 요청이 들어온 값이 잘못된 키를 가지고 있는 경우 체크
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        logger.debug("ArticleEditController 진입");
        
        HttpSession session = req.getSession();
 
        UserVO user = (UserVO) session.getAttribute("user");
        
        // 세션이 존재하지 않는 경우 리다이렉트
        if (session == null || user == null) {
            throw new CustomException("다시 로그인 해주세요" , HttpServletResponse.SC_BAD_REQUEST, "login.jsp");
        }
        
        logger.debug("user " + user.getId() + " 세션 받아오기 완료");
        
        String title = null;
        String content = null;
        String pk = null;
        byte[] fileBytes = null;
        
        ArticleVO articleVo = new ArticleVO();


        List<ArticleFileVO> articleFilesVo = new ArrayList<>();
        
        // multipart 헤더 맞는지 확인
        String contentType = req.getContentType();
        if (contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
            Collection<Part> parts = req.getParts();
            
            for (Part part : parts) {
                

                
                // 파일인 경우 
                if (part.getHeader("Content-Disposition").contains("filename=")) {
                    Part file = req.getPart(part.getName());
                    fileBytes = readPart(file);
                    
                    ArticleFileVO articleFileVo = ArticleFileVO.getNewInstanceWithUUID();
                    articleFileVo.setExternalArticle(articleVo);
                    
                    articleFileVo.setTitle(part.getName());
                    articleFileVo.setFile(fileBytes);
                    
                    articleFilesVo.add(articleFileVo);
                    
                // 파일이 아닌 경우 (텍스트인 경우)
                } else {
                  String formValue = req.getParameter(part.getName());  
                  switch(part.getName()) {
                      case "title":
                          title = formValue;
                          break;
                      
                      case "content":
                          content = formValue;
                          break;
                          
                      case "pk":
                          pk = formValue;
                          break;
                          
                      default:
                          throw new CustomException("잘못된 요청입니다", HttpServletResponse.SC_BAD_REQUEST, "post.jsp");
                  }
                }
               
            } //end of for
            
            // 게시글 오브젝트 설정
            articleVo.setTitle(title);
            articleVo.setContent(content);
            articleVo.setUUID(pk);
            
            // 양방향 설정
            articleVo.setExternalUser(user);
            articleVo.setExternalFiles(articleFilesVo);
            
        } //end of if
        
        
        ArticleService articleService = ArticleService.getInstance();
        articleService.editArticle(articleVo);
    
        req.setAttribute("path", "index.jsp");
        
        logger.debug("ArticleRegisterController 완료");
    }
    
    
    /**
     * blob에 등록하기 위해 전송받은 part를 바이트 스트림으로 바꾸는 메서드
     * @param part
     * @return byte[]
     * @throws IOException
     */
    private byte[] readPart(Part part) throws IOException {
        InputStream inputStream = part.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        byte[] buffer = new byte[1024];
        int bytesRead;
     
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        
        return outputStream.toByteArray();
    }

}
