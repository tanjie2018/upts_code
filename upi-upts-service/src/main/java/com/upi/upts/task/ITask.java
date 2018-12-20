/**
 * 
 */
package com.upi.upts.task;

import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 * 所有Task的需实现该接口
 * @author tanj_5
 *
 */
public interface ITask extends Callable<Object>, Serializable {
	
}
