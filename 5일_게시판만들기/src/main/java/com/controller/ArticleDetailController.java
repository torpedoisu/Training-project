package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.exception.CustomException;
import com.global.HttpUtil;
import com.service.ArticleService;
import com.vo.ArticleFileVO;
import com.vo.ArticleVO;

public class ArticleDetailController implements Controller{

    /**
     * 게시글의 상세 내용을 반환하는 메서드
     * 
     * 예외 처리
     * - 파일이 첨부되지 않은 경우 확인
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
     // 게시물 ID 파라미터 가져오기
        String articlePk = req.getParameter("pk");
        
        ArticleService articleService = ArticleService.getInstance();
        ArticleVO articleVo = articleService.getArticle(articlePk);

        if (articleVo == null) {
            throw new CustomException("삭제된 게시글입니다", HttpServletResponse.SC_BAD_REQUEST, "index.jsp");
        }
        
        JSONObject responseObject = new JSONObject();
        responseObject.put("pk", articlePk);
        responseObject.put("title", articleVo.getTitle());
        responseObject.put("content", articleVo.getContent());
        responseObject.put("user", articleVo.getExternalUser().getId());
        
        JSONObject fileObject = new JSONObject();
        List<ArticleFileVO> articleFiles = articleVo.getExternalFiles();
        // 파일(blob)을 Base64로 인코딩하여 JSON에 추가
        if ((articleFiles != null) && (articleFiles.size() != 0)) {
            int index = 0;
            for (ArticleFileVO articleFile : articleFiles) {
                String base64EncodedFile = this.convertBlobToBase64(articleFile.getFile());
                
                
                
                System.out.println("디코딩 전 base64 - " + base64EncodedFile);
                String s = new String(Base64.getDecoder().decode(base64EncodedFile));
                System.out.println("디코딩 후 base64 - " + s);
                
                
                
                fileObject.put("file" + index++, base64EncodedFile);
            }
        }    
        responseObject.put("file", fileObject);
        
        PrintWriter out = res.getWriter();
        out.write(responseObject.toString());
        
        req.setAttribute("path", "article.jsp");
        req.setAttribute("PrintWriter", out);
    }
    
    private String convertBlobToBase64(byte[] blobAsBytes) throws IOException {
        return Base64.getEncoder().encodeToString(blobAsBytes);
    }
    
}
