package com.jspPractice.calculator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Stack;

public class PostfixCalculator {
	
	private ArrayList<String> calculatorDataList;
	private Stack<String> postfixConverterStack;
	private boolean isInfloatNumber = false;
	
	
	public PostfixCalculator(ArrayList<String> calculatorDataList) {
		this.calculatorDataList = calculatorDataList;
	}
	
	public String calculatePostfix() {
		Stack<String> stack = new Stack<String>();
		ArrayList<String> postfixDataList = convertToPostfix(stack);
		//사용자가 입력한 숫자가 정수만 포함됬을때
		if(isInfloatNumber == false) {
			for(String i : postfixDataList) {
				int n = 0;
				try {
					n = Integer.parseInt(i);
				} catch (Exception e) {
					//연산자일때
					int y = Integer.parseInt(stack.pop());
					System.out.println(y);
					int x = Integer.parseInt(stack.pop());
					System.out.println(x);
					if(i.equals("+")) {
						stack.add(Integer.toString(x+y));
					}
					else if(i.equals("-")) {
						stack.add(Integer.toString(x-y));
					}
					else if(i.equals("×")) {
						stack.add(Integer.toString(x*y));
					}
					else if(i.equals("÷")) {
						stack.add(Integer.toString(x/y));
					}
					continue;
				}
				//숫자일때
				stack.push(i);
			}
			return stack.pop();
		}
		//사용자가 입력한 숫자가 실수가(소수점을 가진 숫자) 포함됬을때
		else {
			for(String i : postfixDataList) {
				float n = 0;
				try {
					n = Float.parseFloat(i);
				} catch (Exception e) {
					//연산자일때
					float y =  Float.parseFloat(stack.pop());
					float x = Float.parseFloat(stack.pop());
					if(i.equals("+")) {
						stack.add(Float.toString(x+y));
					}
					else if(i.equals("-")) {
						stack.add(Float.toString(x-y));
					}
					else if(i.equals("×")) {
						stack.add(Float.toString(x*y));
					}
					else if(i.equals("÷")) {
						stack.add(Float.toString(x/y));
					}
					continue;
				}
				//숫자일때
				stack.push(i);
			}
			float num  = Float.parseFloat(stack.pop());
			DecimalFormat df = new DecimalFormat("#.#####");
			return df.format(num);
		}
	}
	
	
	
	
	
	//list를 후위 표기식으로 만들어주는 메소드
	private ArrayList<String> convertToPostfix(Stack<String> stack) {
		String number = "";
		ArrayList<String> postfixDataList = new ArrayList<String>();
		//후위 표기식으로 만들어주기
		for(String i : calculatorDataList) {
			if(i.equals("+") || i.equals("-")) {
				postfixDataList.add(number);
				number  = "";
				if(stack.isEmpty() == true) {
					stack.push(i);
				}
				else {
					while(stack.isEmpty() == false) {
						postfixDataList.add(stack.pop());
					}
					stack.add(i);
				}
			}
			else if(i.equals("×") || i.equals("÷")) {
				if(i.equals("÷")) {
					isInfloatNumber = true;
				}
				postfixDataList.add(number);
				number  = "";
				if(stack.isEmpty() == true) {
					stack.push(i);
				}
				else {
					while(stack.isEmpty() == false) {
						String a = stack.peek();
						//현재 연산자보다 스택안에 연산자가 우선 순위 낳으므로 그냥 현재 연산자를 스택에 넣는다.
						if(a.equals("-") || a.equals("+")) {
							stack.add(i);
							break;
						}
						else {
							postfixDataList.add(stack.pop());
						}
					}
					if(stack.isEmpty() == true) {
						stack.add(i);
					}
				}
			}
			else {
				if(i.equals(".")) {
					isInfloatNumber = true;
				}
				number += i;
			}
		}
		//마지막 숫자는 안넣어지므로 넣어준다.
		stack.push(number);
		//남는 스택 다 털어서 post에 넣어준다.
		while(stack.isEmpty() == false) {
			postfixDataList.add(stack.pop());
		}
		//잘 바꾸어주었는지 테스트용 출력 장치
		for(String i : postfixDataList) {
			System.out.print(i+" ");
		}
		System.out.println();
		return postfixDataList;
	}
	
	
	
	
	
	
	
	

}
