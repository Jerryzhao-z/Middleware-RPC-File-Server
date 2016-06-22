package app;

import fr.insa.tc.RTC.fileService;
import fr.insa.tc.RTC.RemoteData;
import fr.insa.tc.RTC.UpdateData;
import java.nio.ByteBuffer;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import org.apache.thrift.TException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class Client {

	fileService.Client client;

	//connection 
 	public void call(String server) throws TException
 	{
	    TTransport transport = new TSocket(server, 4100); 
	    transport.open();
	    TProtocol protocol = new TBinaryProtocol(transport); 
	    client = new fileService.Client(protocol);
  	}

  	public void getListFile() throws TException
  	{
  		client.getListFile().stream().forEach((Name) -> System.out.println(Name));
  	}

  	public void getFile(String FileName) throws TException, IOException
  	{
  		RemoteData DataFile = client.getFile(FileName);
                if(DataFile == null)
                    System.out.println("nothing");
                if(!DataFile.FileExiste)
                {
                    System.out.println("don't existe");
                }
                System.out.println(new String(DataFile.getFile()));
  		File f = new File("D:\\user\\4TC\\MID\\TP\\TP1\\clientRepertoire\\"+FileName);
  		if(f.exists()) f = new File("D:\\user\\4TC\\MID\\TP\\TP1\\clientRepertoire\\copy_"+FileName);
  		OutputStream stream = new FileOutputStream(f);
  		stream.write(DataFile.getFile());
  	}

	public void updateFile(String FileName) throws TException, FileNotFoundException, IOException
	{
		//read byte file
                System.out.println("D:\\user\\4TC\\MID\\TP\\TP1\\clientRepertoire\\"+FileName);
  		File f = new File("D:\\user\\4TC\\MID\\TP\\TP1\\clientRepertoire\\"+FileName);
  		InputStream stream = new FileInputStream(f);
  		byte[] fileContent = new byte[(int)f.length()];
  		stream.read(fileContent);
  		//construction of UpdateDate
  		ByteBuffer fileContentInBuffer = ByteBuffer.wrap(fileContent);
                //System.out.println(new String(fileContentInBuffer.array(), "ASCII"));
  		UpdateData DataFile = new UpdateData(FileName, fileContentInBuffer);
  		//request
  		boolean res = client.updateFile(DataFile);
		String comments = (res)?FileName+" has been updated":"Failed";
		System.out.println(comments);
	}

	public void deleteFile(String FileName) throws TException
	{
		boolean res = client.deleteFile(FileName);
		String comments = (res)?FileName+" has been deleted":"Failed";
		System.out.println(comments);
	}

}