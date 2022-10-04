package com.jspPractice.calculator;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Stack;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/calculator")
public class CalculatorServlet extends HttpServlet {

	protected static final int NUMBER_ATTRIBUTE = 1;
	protected static final int OPERATOR_ATTRIBUTE = 0;
	
	//post요청을 했을땐 특정 계산을 함.
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		HttpSession httpSession = req.getSession();
		int control = -1;
		//사용자의 요청이 number로 넘어왔을떄
		if(req.getParameter("number") != null) {
			control = NUMBER_ATTRIBUTE;
		}
		//사용자의 요청이 operator가 넘어왔을때
		else {
			control = OPERATOR_ATTRIBUTE;
		}
		//사용자가 처음 값을 입력하여 session의 calc_data가 존재하지 않을때 
		if(httpSession.getAttribute("calculatorData") == null) {
			if(control == NUMBER_ATTRIBUTE) {
				ArrayList<String> list = new ArrayList<String>();
				list.add((String) req.getParameter("number"));
				httpSession.setAttribute("calculatorData", list);
				System.out.println((String)req.getParameter("number"));
				resp.sendRedirect("calculator");
			}
			else if(control == OPERATOR_ATTRIBUTE) {
				if(req.getParameter("operator").equals(".")) {
					ArrayList<String> list = new ArrayList<String>();
					list.add("0");
					list.add(".");
				}
				else {
					resp.sendRedirect("calculator");
				}
			}
		}
		
		//사용자가 두 번째 값을 입력한 이후부터 session의 calc_data가 존재할 
		else {
			ArrayList<String> calculatorDataList  = (ArrayList<String>) httpSession.getAttribute("calculatorData");
			if(control == NUMBER_ATTRIBUTE) {
				calculatorDataList.add((String) req.getParameter("number"));
				System.out.println((String)req.getParameter("number"));
				resp.sendRedirect("calculator");
			}
			else if(control == OPERATOR_ATTRIBUTE){
				String operator = (String) req.getParameter("operator");
				//계산
				if(operator.equals("=")){
					String number = "";
					boolean float_number = false;
					ArrayList<String> post = new ArrayList<String>();
					Stack<String> stack = new Stack<String>();
					//후위 표기식 계산 객체 생성
					PostfixCalculator postfixCalculator = new PostfixCalculator(calculatorDataList);
					//계산
					String result = postfixCalculator.calculatePostfix();
					calculatorDataList.clear();
					calculatorDataList.add(result);
				}
				//초기화
				else if(operator.equals("CE") || operator.equals("C")){
					httpSession.setAttribute("calculatorData", null);
				}
				//맨뒤 지우기
				else if(operator.equals("BS")) {
					if(calculatorDataList.size() > 0) {
						calculatorDataList.remove(calculatorDataList.size() - 1);
					}
				}
				else {
					calculatorDataList.add(operator);
				}
				resp.sendRedirect("calculator");
			}
		}
	}
	
	//get으로 요청하면 html 문서를 동적으로 반환해줌.
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		String result = "";
		HttpSession httpSession = req.getSession();
		if(httpSession.getAttribute("calculatorData") == null) {
			result = "0";
		}
		else {
			ArrayList<String> list = (ArrayList<String>) httpSession.getAttribute("calculatorData");
			for(String i : list) {
				result += i;
			}
		}
		resp.getWriter().printf("<!DOCTYPE html>\r\n"
				+ "<html lang=\"kr\">\r\n"
				+ "<head>\r\n"
				+ "	<meta charset=\"UTF-8\">\r\n"
				+ "	<title>계산기</title>\r\n"
				+ "	<style>\r\n"
				+ "		*{\r\n"
				+ "			box-sizing: border-box;\r\n"
				+ "		}\r\n"
				+ "		table{\r\n"
				+ "			border-collapse: collapse;\r\n"
				+ "		}\r\n"
				+ "		tr{\r\n"
				+ "			width: 320px;\r\n"
				+ "		}\r\n"
				+ "		td{\r\n"
				+ "			margin: 3px;\r\n"
				+ "			display: inline-block;\r\n"
				+ "			width: 80px;\r\n"
				+ "			height: 80px;\r\n"
				+ "		}\r\n"
				+ "		tr:first-child>td{\r\n"
				+ "            width: 320px;\r\n"
				+ "		}\r\n"
				+ "		input{\r\n"
				+ "			font-size: 1.5rem;\r\n"
				+ "			border-radius: 10px;\r\n"
				+ "			border: 1px solid lightgrey;\r\n"
				+ "			width: 80px;\r\n"
				+ "			height: 80px;\r\n"
				+ "			background-color: white;\r\n"
				+ "		}\r\n"
				+ "		p{\r\n"
				+ "			font-size: 2rem;\r\n"
				+ "			text-align: right;\r\n"
				+ "		}\r\n"
				+ "		input:active{\r\n"
				+ "			background-color: rgba(247, 247, 247, 1)\r\n"
				+ "		}\r\n"
				+ "	</style>\r\n"
				+ "</head>\r\n"
				+ "<body>\r\n"
				+ "<form  method=\"post\">\r\n"
				+ "  <fieldset>\r\n"
				+ "	  <legend>계산기</legend>\r\n"
				+ "	  <table>\r\n"
				+ "		  <tr>\r\n"
				//%s 부분이 계산된값에 따라 동적으로 달라짐
				+ "			  <td colspan=\"4\"><p>%s</p></td>\r\n"
				+ "		  </tr>\r\n"
				+ "		  <tr>\r\n"
				+ "			  <td><input type=\"submit\" name=\"operator\" value=\"CE\"></td>\r\n"
				+ "			  <td><input type=\"submit\"  name=\"operator\"  value=\"C\"></td>\r\n"
				+ "			  <td><input type=\"submit\"  name=\"operator\"  value=\"BS\"></td>\r\n"
				+ "			  <td><input type=\"submit\"  name=\"operator\"  value=\"÷\"></td>\r\n"
				+ "		  </tr>\r\n"
				+ "		  <tr>\r\n"
				+ "			  <td><input type=\"submit\"  name=\"number\"  value=\"7\"></td>\r\n"
				+ "			  <td><input type=\"submit\"  name=\"number\"  value=\"8\"></td>\r\n"
				+ "			  <td><input type=\"submit\"  name=\"number\"  value=\"9\"></td>\r\n"
				+ "			  <td><input type=\"submit\"  name=\"operator\"  value=\"×\"></td>\r\n"
				+ "		  </tr>\r\n"
				+ "		  <tr>\r\n"
				+ "			  <td><input type=\"submit\"  name=\"number\"  value=\"4\"></td>\r\n"
				+ "			  <td><input type=\"submit\"  name=\"number\"  value=\"5\"></td>\r\n"
				+ "			  <td><input type=\"submit\"  name=\"number\"  value=\"6\"></td>\r\n"
				+ "			  <td><input type=\"submit\"  name=\"operator\"  value=\"-\"></td>\r\n"
				+ "		  </tr>\r\n"
				+ "		  <tr>\r\n"
				+ "			  <td><input type=\"submit\"  name=\"number\"  value=\"1\"></td>\r\n"
				+ "			  <td><input type=\"submit\"  name=\"number\"  value=\"2\"></td>\r\n"
				+ "			  <td><input type=\"submit\"  name=\"number\"  value=\"3\"></td>\r\n"
				+ "			  <td><input type=\"submit\"  name=\"operator\"  value=\"+\"></td>\r\n"
				+ "		  </tr>\r\n"
				+ "		  <tr>\r\n"
				+ "			  <td></td>\r\n"
				+ "			  <td><input type=\"submit\"  name=\"number\"  value=\"0\"></td>\r\n"
				+ "			  <td><input type=\"submit\"  name=\"operator\"  value=\".\"></td>\r\n"
				+ "			  <td><input type=\"submit\"  name=\"operator\"  value=\"=\"></td>\r\n"
				+ "		  </tr>\r\n"
				+ "	  </table>\r\n"
				+ "\r\n"
				+ "  </fieldset>\r\n"
				+ "	<script>\r\n"
				+ "\r\n"
				+ "	</script>\r\n"
				+ "</form>\r\n"
				+ "</body>\r\n"
				+ "</html>", result);
	}
	
	
}
