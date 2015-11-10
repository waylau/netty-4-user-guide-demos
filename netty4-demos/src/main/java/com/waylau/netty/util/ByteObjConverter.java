/**
 * 
 */
package com.waylau.netty.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 说明：
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月9日 
 */
public class ByteObjConverter {

	public static Object ByteToObject(byte[] bytes) {  
        Object obj = null;  
        ByteArrayInputStream bi = new ByteArrayInputStream(bytes);  
        ObjectInputStream oi = null;  
        try {  
            oi = new ObjectInputStream(bi);  
            obj = oi.readObject();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                bi.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
            try {  
                oi.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return obj;  
    }  
  
    public static byte[] ObjectToByte(Object obj) {  
        byte[] bytes = null;  
        ByteArrayOutputStream bo = new ByteArrayOutputStream();  
        ObjectOutputStream oo = null;  
        try {  
            oo = new ObjectOutputStream(bo);  
            oo.writeObject(obj);  
            bytes = bo.toByteArray();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                bo.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
            try {  
                oo.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return (bytes);  
    }  
}
