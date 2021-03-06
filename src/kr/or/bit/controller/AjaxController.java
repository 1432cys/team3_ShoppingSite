package kr.or.bit.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.ActionAjax;
import kr.or.bit.action.ActionAjaxData;
import kr.or.bit.service.c_Add_inquiry_Answer;

import kr.or.bit.service_ajax.IdCheckAjaxService;
import kr.or.bit.service_ajax.PurchaseAjaxService;
import kr.or.bit.service_ajax.SignInAjaxService;
import kr.or.bit.service_ajax.c_ConfirmPwdService;
import kr.or.bit.service_ajax.c_EditMemberAjaxService;



@WebServlet(
		name = "AjaxController",
		urlPatterns = "*.ajax",
		loadOnStartup = 1
		)
public class AjaxController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private HashMap<String, ActionAjax> actionList = null;
       
    public AjaxController() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void init(ServletConfig config) throws ServletException {
		System.out.print("*.ajax Servlet ->");
		actionList = new HashMap<String, ActionAjax>();
		
		actionList.put("/idCheck.ajax", new IdCheckAjaxService());
		actionList.put("/signIn.ajax", new SignInAjaxService());
		actionList.put("/purchase.ajax", new PurchaseAjaxService());
		actionList.put("/editMember.ajax", new c_EditMemberAjaxService());
		actionList.put("/confirmPwd.ajax", new c_ConfirmPwdService());
		actionList.put("/inqAnswer.ajax" , new c_Add_inquiry_Answer());

		
		System.out.println(" initialized");
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String urlCommand = (String)request.getAttribute("urlCommand");
		
		PrintWriter out = response.getWriter();
		ActionAjax actionAjax = actionList.get(urlCommand);
		if(actionAjax != null) {
			ActionAjaxData ajaxData = actionAjax.execute(request, response);
			response.setContentType(ajaxData.getContentType());
			out.print(ajaxData.getData());
		} else {
			// wrong request
			response.setStatus(404);
		}
		
		out.close();
	}

}
