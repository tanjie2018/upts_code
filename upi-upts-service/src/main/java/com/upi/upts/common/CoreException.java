package com.upi.upts.common;

public class CoreException extends UiisException {

	private static final long serialVersionUID = 5045188995783034780L;

	private boolean a;

	public CoreException(String paramString1, Object[] paramArrayOfObject, String paramString2,
			Throwable paramThrowable) {
		super(paramString1, paramArrayOfObject, paramString2, paramThrowable);
	}

	public CoreException(String paramString1, Object[] paramArrayOfObject, String paramString2) {
		super(paramString1, paramArrayOfObject, paramString2);
	}

	public CoreException(String paramString, Object[] paramArrayOfObject, Throwable paramThrowable) {
		super(paramString, paramArrayOfObject, paramThrowable);
	}

	public CoreException(String paramString, Object[] paramArrayOfObject) {
		super(paramString, paramArrayOfObject);
	}

	public CoreException(String paramString1, String paramString2, Throwable paramThrowable) {
		super(paramString1, paramString2, paramThrowable);
	}

	public CoreException(String paramString1, String paramString2) {
		super(paramString1, paramString2);
	}

	public CoreException(String paramString, Throwable paramThrowable) {
		super(paramString, paramThrowable);
	}

	public void setWrapped(boolean paramBoolean) {
		this.a = paramBoolean;
	}

	public boolean isWrapped() {
		return this.a;
	}

	public CoreException(String paramString) {
		super(paramString);
	}
}
