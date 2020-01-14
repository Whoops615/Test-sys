package by.htp.ts.bean;

public class RequestInfo {
	
	private String url;
	private String message;
	
	public RequestInfo() {
		
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "RequestInfo [url=" + url + ", message=" + message + "]";
	}

}
