package cellstore.db;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * 
 * A user
 * 
 * @author Johan Henriksson
 *
 */
public class CellStoreUser
	{
	public int id;
	public String name;
	public String username;
	public String email;
	
	public transient String password;
	public transient String salt;
	
	
	public static String hashpass(String pass, String salt)
		{
		String defaultHashMethod="SHA-256";//  "MD5";//"SHA-512"; //"SHA-256", "MD5"
    try
      {
      String toHash=pass+salt;

      MessageDigest digest = MessageDigest.getInstance(defaultHashMethod);

      byte[] arr=toHash.getBytes("UTF-8");
      digest.update(arr, 0, arr.length);

      byte[] hash = digest.digest();
      BigInteger bigInt = new BigInteger(1, hash);
      return bigInt.toString(16);
      }
    catch (Exception e)
    	{
    	throw new RuntimeException(e.getMessage());
    	}
		}
	
	

	public void setPass(String pass)
		{
		salt=""+Math.random();
		pass=hashpass(pass, salt);		
		}
	
	}
