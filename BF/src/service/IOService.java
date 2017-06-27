//服务器IOService的Stub，内容相同
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
public interface IOService extends Remote{
	public boolean writeFile(String file, String userId, String fileName)throws RemoteException;

	public String readFile(String userId, String fileName)throws RemoteException;

	public String readFileList(String userId)throws RemoteException;

	public void clickonSave(String currentFilename, String currentUsername, String file, String currentFiletype);

	public String readhistoryFile(String currentUsername, String currentFilename, String currentFiletype,
			String version);

	public boolean newFile(String fn, String ft, String un);

	public boolean deleteFile(String fn, String un, String ft) throws RemoteException;

}
