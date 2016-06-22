package app;

import fr.insa.tc.RTC.fileService;
import fr.insa.tc.RTC.RemoteData;
import fr.insa.tc.RTC.UpdateData;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.HashMap; 
import java.lang.Exception;
import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.server.TThreadPoolServer.Args;

import java.util.ArrayList;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;

public class Server
{
        public static String location = "D:\\user\\4TC\\MID\\TP\\TP1\\serverRepertoire\\";
	public static HashMap<String, byte[]> FileDataBase = new HashMap<String, byte[]>();

	private static class requestHandler implements fileService.Iface
	{
            @Override
            public List<String> getListFile() throws TException
            {
                    return new ArrayList(FileDataBase.keySet());
            }
            
            @Override
	    public RemoteData getFile(String FileName) throws TException
	    {
                
	    	byte[] file = FileDataBase.get(FileName);
	    	boolean fileexiste = (file == null)?false:true;
	    	if(!fileexiste){
                        System.out.println("don't existe");
	    		return new RemoteData(fileexiste);
                }
                else{
                        System.out.println("existe");
	    		return new RemoteData(fileexiste).setFileName(FileName).setFile(file);
                }
	    }
            
            @Override
	    public boolean updateFile(UpdateData File) throws TException
	    {
                System.out.println("###update File###");
                //System.out.println(new String(File.getFile()));
	    	FileDataBase.put(File.getFileName(), File.getFile());
	    	boolean resValue = (FileDataBase.containsKey(File.getFileName()))?true:false;
	    	return resValue;
	    }
            @Override
	    public boolean deleteFile(String FileName) throws TException
	    {
	    	FileDataBase.remove(FileName);
	    	boolean resValue = (FileDataBase.containsKey(FileName))?false:true;
	    	return resValue;
	    }
	}

	public void start()
	{
		try
		{
			fileService.Processor<requestHandler> processor = new fileService.Processor<>(new requestHandler());                 
                        TServerTransport transport = new TServerSocket(4100);
                        TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(transport).processor(processor));
                        server.serve();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}