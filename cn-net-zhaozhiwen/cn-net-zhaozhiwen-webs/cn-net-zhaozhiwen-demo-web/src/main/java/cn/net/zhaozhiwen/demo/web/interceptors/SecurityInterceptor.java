package cn.net.zhaozhiwen.demo.web.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SecurityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
	    // intercept
	   /* HttpSession session = request.getSession();
	    if (session.getAttribute("user") == null) {
	    	System.out.println("preHandle--false");
	        return false;
	    } else {
	    	System.out.println("preHandle--true");
	        return true;
	    }*/
    	System.out.println("preHandle--true");
    	return true;
    }
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		System.out.println("postHandle");
		
	}
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		System.out.println("afterCompletion");
		
	}


}