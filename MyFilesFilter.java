package fileCutMerge;

import java.io.File;
import java.io.FileFilter;
//import java.io.FilenameFilter;

class MyFilesFilter implements FileFilter{
	String prefix = "";
	
	public MyFilesFilter(String preFixname) {
		this.prefix = preFixname;
	}

	@Override
	public boolean accept(File f) {
		if(f.getName().length() > prefix.length()
				&& f.getName().startsWith(prefix)) {
			return true;
		}
		return false;
	}

}
