package com.upi.upts.common;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;

public class UiisException extends Exception implements MessageSourceResolvable {
	
	private static final long serialVersionUID = -5743433775803742800L;

	private final DefaultMessageSourceResolvable a;
	private boolean b;

	public boolean isAlreadyLogged() {
		return this.b;
	}

	public StackTraceElement[] getStackTrace() {
		this.b = true;
		StackTraceElement[] arrayOfStackTraceElement = super.getStackTrace();
		return arrayOfStackTraceElement;
	}

	public UiisException(String paramString) {
		super(a(paramString, null));
		this.a = new DefaultMessageSourceResolvable(paramString);
	}

	public UiisException(String paramString, Object[] paramArrayOfObject) {
		super(a(paramString, null));
		this.a = new DefaultMessageSourceResolvable(new String[] { paramString }, paramArrayOfObject);
	}

	public UiisException(String paramString, Throwable paramThrowable) {
		super(a(paramString, null), paramThrowable);
		this.a = new DefaultMessageSourceResolvable(new String[] { paramString }, null, paramThrowable.getMessage());
	}

	public UiisException(String paramString, Object[] paramArrayOfObject, Throwable paramThrowable) {
		super(a(paramString, null), paramThrowable);
		this.a = new DefaultMessageSourceResolvable(new String[] { paramString }, paramArrayOfObject,
				paramThrowable.getMessage());
	}

	public UiisException(String paramString1, String paramString2) {
		super(a(paramString1, paramString2));
		this.a = new DefaultMessageSourceResolvable(new String[] { paramString1 }, null, paramString2);
	}

	public UiisException(String paramString1, Object[] paramArrayOfObject, String paramString2) {
		super(a(paramString1, paramString2));
		this.a = new DefaultMessageSourceResolvable(new String[] { paramString1 }, paramArrayOfObject, paramString2);
	}

	public UiisException(String paramString1, String paramString2, Throwable paramThrowable) {
		super(a(paramString1, paramString2), paramThrowable);
		this.a = new DefaultMessageSourceResolvable(new String[] { paramString1 },
				new Object[] { paramThrowable.getClass().getName() }, paramString2);
	}

	public UiisException(String paramString1, Object[] paramArrayOfObject, String paramString2,
			Throwable paramThrowable) {
		super(a(paramString1, paramString2), paramThrowable);
		this.a = new DefaultMessageSourceResolvable(new String[] { paramString1 }, paramArrayOfObject, paramString2);
	}

	public String getCode() {
		return this.a.getCode();
	}

	public String[] getCodes() {
		return this.a.getCodes();
	}

	public Object[] getArguments() {
		return this.a.getArguments();
	}

	public String getDefaultMessage() {
		return this.a.getDefaultMessage();
	}

	static String a(String paramString1, String paramString2) {
		if (paramString1 == null) {
			return paramString2;
		}
		if (paramString2 == null) {
			return "[" + paramString1 + "]";
		}
		return "[" + paramString1 + "] " + paramString2;
	}
}
