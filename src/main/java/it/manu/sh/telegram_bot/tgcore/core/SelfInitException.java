package it.manu.sh.telegram_bot.tgcore.core;

import java.lang.reflect.Field;

@SuppressWarnings("serial") 
public class SelfInitException extends RuntimeException {

	SelfInitException(String msg) {
		super(msg);
	}

	SelfInitException(Class<?> clazz, Field field) {
		super("Missing required field from class: "+clazz.getName()+(field == null ? "" : " field: "+field.getName()));
	}

	SelfInitException(Exception e) {
		super(e.getCause()+"\n"+e.getStackTrace());
	}

}
