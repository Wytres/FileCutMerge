package fileCutMerge;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileCutMerge {
	
	//分析命令行参数，决定是分割文件还是合并文件
	public static void main(String[] args) {
		FileCutMerge tool = new FileCutMerge();
		//判断命令行参数的数量
		if((args == null) || (args.length != 2)) {
			tool.help();
		}
		else if(args[0].startsWith("-c")) {
			//被分割文件的文件名，从当前目录下开始
			File f1 = new File(args[1]);
			if(!f1.exists()) {
				System.out.println("指定的文件不存在");
			}
			//从命令行参数获得分割后文件的大小
			int fileSize = Integer.parseInt(args[0].substring(2));
			try {
				tool.cut(f1, fileSize);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		else if(args[0].equals("-m")) {
			//被合并的小文件名的前缀
			String  preFixname = args[1];
			File f = new File(".");
			//列出当前目录下所需合并的文件，使用了一个文件过滤器
			File [] names = f.listFiles(new MyFilesFilter(preFixname));
			try {
				tool.merge(names);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		else {
			
		}
	}

	public void help() {
		System.out.println("错误的命令行格式，正确的是：\n FileCutMerge-c Filename \n 或者 \n FileCutMerge-m filenameprefix");
	}
	
	//
	public void cut(File file, int size) throws Exception{
		System.out.println("开始分割文件...");
		//获得被分割文件的父目录，将被分割后的文件放到该目录下
		File parent = file.getParentFile();
		long fileLength = file.length();
		//获得被分割后小文件的数目
		int filenum = (int)(fileLength / size);
		if(fileLength % size != 0) {
			filenum += 1;
		}
		String[] smallfilenames = new String[filenum];
		//创建文件输入流读取被分割的文件
		FileInputStream fin = new FileInputStream(file);
		//构造一个字节数组，每次读取一个字节的数据
		byte [] buf = new byte[size];
		for(int i=0; i<filenum; i++) {
			//构造分割后的文件名
			File outfile = new File(parent, file.getName()+"-"+i);
			//构造文件输出流
			FileOutputStream fout = new FileOutputStream(outfile);
			//读取数据
			int count = fin.read(buf);
			//输出数据
			fout.write(buf, 0, count);
			fout.close();
			smallfilenames[i] = outfile.getName();
		}
		fin.close();
		//输出分割后的文件名
		System.out.println("分割后的文件如下：");
		for(int i=0; i<smallfilenames.length; i++) {
			System.out.println(smallfilenames[i]);
		}
		System.out.println("分割完成");
	}
	
	//合并文件
	public void merge(File[] files) throws Exception{
		System.out.println("开始合并文件...");
		//获得目标文件名， 来源于被分割文件
		String smallfilename = files[0].getName();
		int pos = smallfilename.indexOf("-");
		String tagetfilename = "new-"+smallfilename.substring(0,pos);
		System.out.println("合并后的文件名为："+tagetfilename);
		File outFile = new File(files[0].getParentFile(), tagetfilename);
		FileOutputStream fout = new FileOutputStream(outFile);
		//合并文件内容，输出到目标文件
		for(int i=0; i<files.length; i++) {
			FileInputStream fin = new FileInputStream(files[i]);
			int b;
			while((b=fin.read()) != -1) {
				fout.write(b);
			}
			fin.close();
		}
		fout.close();
		System.out.println("合并完成");
	}

}
