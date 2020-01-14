package by.htp.ts.controller.constant;

public final class Transition {

	public static final String PAGE_ERROR = "/WEB-INF/jsp/error.jsp";
	public static final String PAGE_INDEX = "index.jsp";
	public static final String PAGE_TEACHER = "/WEB-INF/jsp/teacher/teacher.jsp";
	public static final String PAGE_REGISTRATION = "/WEB-INF/jsp/registration.jsp";
	public static final String PAGE_AUTHORIZATION = "/WEB-INF/jsp/authorization.jsp";
	public static final String PAGE_STUDENT = "/WEB-INF/jsp/student/student.jsp";
	public static final String PAGE_CREATE_TEST = "/WEB-INF/jsp/test/createTest.jsp";
	public static final String PAGE_CREATE_QUESTION = "/WEB-INF/jsp/test/createQuestion.jsp";
	public static final String PAGE_APPOINTMENT_TEST = "/WEB-INF/jsp/teacher/appointmentTest.jsp";
	public static final String PAGE_VIEW_APPOINTMENT_TEST = "/WEB-INF/jsp/student/viewAppointmentTest.jsp";
	public static final String PAGE_START_TEST = "/WEB-INF/jsp/student/startPassTest.jsp";
	
	public static final String PAGE_QUESTION_RADIO = "/WEB-INF/jsp/student/questionRadio.jsp";
	public static final String PAGE_QUESTION_BOX = "/WEB-INF/jsp/student/questionCheckBox.jsp";
	
	
	public static final String REDIRECT_MESSAGE = "&message=";
	
	public static final String REDIRECT_AUTHORIZATION = "Controller?command=go_to_authorization_page";
	public static final String REDIRECT_ERROR = "Controller?command=go_to_error_page";
	public static final String REDIRECT_TEACHER = "Controller?command=go_to_teacher_page";
	public static final String REDIRECT_STUDENT = "Controller?command=go_to_student_page";
	public static final String REDIRECT_INDEX = "Controller?command=go_to_index_page";
	public static final String REDIRECT_CREATE_QUESTION = "Controller?command=go_to_create_question_page";
	public static final String REDIRECT_APPOINTMENT_TEST = "Controller?command=go_to_appointment_test_page";

	

	private Transition() {

	}

}
