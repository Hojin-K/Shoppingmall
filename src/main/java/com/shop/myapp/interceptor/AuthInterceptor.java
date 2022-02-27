package com.shop.myapp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.shop.myapp.dto.MemberSession;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info(this.getClass().toString());
		// 1. handler 종류 확인
				// 우리가 관심 있는 것은 Controller에 있는 메서드이므로 HandlerMethod 타입인지 체크
				if(!(handler instanceof HandlerMethod)) {
					// return true이면  Controller에 있는 메서드가 아니므로, 그대로 컨트롤러로 진행
					return true;
				}

				// 2.형 변환
				HandlerMethod handlerMethod = (HandlerMethod)handler;
				
				// 3. @Auth 받아오기
				Auth classAuth = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Auth.class);
				Auth methodAuth = handlerMethod.getMethodAnnotation(Auth.class);
				Auth auth;

				if(classAuth == null) {
					auth = methodAuth;
				}else {
					if(methodAuth != null) {
						auth = methodAuth;
					}else {
						auth = classAuth;
					}
				}

				System.out.println("auth----->"+auth);
				
				// 4. method에 @Auth가 없는 경우, 즉 인증이 필요 없는 요청
				if( auth == null ) {
					return true;
				}
				
				// 5. @Auth가 있는 경우이므로, 세션이 있는지 체크
				HttpSession session = request.getSession();
				if( session == null ) {
					// 로그인 화면으로 이동
					response.sendRedirect(request.getContextPath() + "/members/login");
					return false;
				}
				
				// 6. 세션이 존재하면 유효한 유저인지 확인
				MemberSession authUser = (MemberSession)session.getAttribute("member");
				if ( authUser == null ) {
					response.sendRedirect(request.getContextPath() + "/members/login");
					return false;
				}

				// 7. admin일 경우
		System.out.println("어노테이션이 있을경우");
		String role = auth.role().toString();
		if( "ADMIN".equals(role) ) {
			System.out.println("어노테이션이 amdin 인 경우");
			// admin임을 알 수 있는 조건을 작성한다.
			// ex) 서비스의 id가 root이면 admin이다.
			if(!authUser.getMemberLevel().toString().contains("ADMIN")){// admin이 아니므로 return false
				System.out.println("유저 권한이 admin이 아닌 경우");
				response.sendRedirect(request.getContextPath());
				return false;
			}
		}else if( "SELLER".equals(role) ) {
			System.out.println("어노테이션이 amdin 인 경우");
			// admin임을 알 수 있는 조건을 작성한다.
			// ex) 서비스의 id가 root이면 admin이다.
			if(!authUser.getMemberLevel().toString().contains("SELLER")){// seller가 아니므로 return false
				System.out.println("유저 권한이 seller가 아닌 경우");
				response.sendRedirect(request.getContextPath());
				return false;
			}
		}else if( "USER".equals(role) ) {
			System.out.println("어노테이션이 amdin 인 경우");
			// admin임을 알 수 있는 조건을 작성한다.
			// ex) 서비스의 id가 root이면 admin이다.
			if(!authUser.getMemberLevel().toString().contains("USER")){// user가 아니므로 return false
				System.out.println("유저 권한이 user가 아닌 경우");
				response.sendRedirect(request.getContextPath());
				return false;
			}
		}

		// 8. 접근허가, 즉 메서드를 실행하도록 함
				return true;
	}

	
	
}
