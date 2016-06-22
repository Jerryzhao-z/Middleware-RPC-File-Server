namespace java fr.insa.tc.RTC

struct RemoteData {
	1: bool FileExiste;
	2: optional string FileName;
	3: optional binary File;
}

struct UpdateData {
	1: string FileName;
	2: binary File;
}

service fileService {
	list<string> getListFile();
	RemoteData getFile(1: string FileName);
	bool updateFile(1: UpdateData File);
	bool deleteFile(1: string FileName);
}
