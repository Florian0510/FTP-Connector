package org.apache.commons.net.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;

public class FTP {
	
	/**
	 * @author Florian
	 */
	
	
	
	/**
	 * @param isConnected; Saves the Boolean if the FTP-Client is Connected to the FTP-Server.
	 */
	private static boolean isConnected = false;
	
	/**
	 * @param FTPClient; Saves the Local FTPClient Class to use it in all Methods of the class.
	 */
	private static FTPClient FTPClient;

	
	
	/**
	 * @param  Host, Port, User, Password; Overwrite the Login and Connect data from FTP.
	 * @throws IOException
	 * @method Connect(); Connect to the FTP-Server.
	 */
	public static void Connect(String Host, int Port, String User, String Password) throws IOException {
		if (!isConnected) {
			FTPClient = new FTPClient();
			FTPClient.connect(Host, Port);
	
			if (FTPClient.login(User, Password)) {
				isConnected = true;
			} else {
				isConnected = false;
				return;
			}
	
			FTPClient.enterLocalPassiveMode();
			FTPClient.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
		}
	}

	
	
	/**
	 * @method isConnected(); Look if the FTP-Client is already Connected to an FTP-Server.
	 * @return FTP.isConnected;
	 */
	public static boolean isConnected() {
		return isConnected;
	}

	
	
	/**
	 * @method Close(); Close the Connection between FTP-Client and FTP-Server if both are Connected to each other.
	 * @throws IOException
	 */
	public static void Close() throws IOException {
		if (FTPClient != null) {
			FTPClient.logout();
			FTPClient.disconnect();
			isConnected = false;
		}
	}

	
	
	/**
	 * @param  FTPDirectory, LocalFilePath, LocalFileName
	 * @throws IOException
	 * @method StoreFile(); Upload a Local File to the Connected FTP-Server.
	 * @return FTP.FTPClient.storeFile(LocalFileName, new FileInputStream(new File(LocalFilePath)));
	 */
	public static boolean StoreFile(String FTPDirectory, String LocalFilePath, String LocalFileName) throws IOException {
		FTPClient.makeDirectory(FTPDirectory);
		FTPClient.changeWorkingDirectory(FTPDirectory);

		return FTPClient.storeFile(LocalFileName, new FileInputStream(new File(LocalFilePath)));
	}

	
}