package com.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.ResponseData;

public interface Controller {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException;
}
