package fileCutMerge;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileCutMerge {
	
	//���������в����������Ƿָ��ļ����Ǻϲ��ļ�
	public static void main(String[] args) {
		FileCutMerge tool = new FileCutMerge();
		//�ж������в���������
		if((args == null) || (args.length != 2)) {
			tool.help();
		}
		else if(args[0].startsWith("-c")) {
			//���ָ��ļ����ļ������ӵ�ǰĿ¼�¿�ʼ
			File f1 = new File(args[1]);
			if(!f1.exists()) {
				System.out.println("ָ�����ļ�������");
			}
			//�������в�����÷ָ���ļ��Ĵ�С
			int fileSize = Integer.parseInt(args[0].substring(2));
			try {
				tool.cut(f1, fileSize);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		else if(args[0].equals("-m")) {
			//���ϲ���С�ļ�����ǰ׺
			String  preFixname = args[1];
			File f = new File(".");
			//�г���ǰĿ¼������ϲ����ļ���ʹ����һ���ļ�������
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
		System.out.println("����������и�ʽ����ȷ���ǣ�\n FileCutMerge-c Filename \n ���� \n FileCutMerge-m filenameprefix");
	}
	
	//
	public void cut(File file, int size) throws Exception{
		System.out.println("��ʼ�ָ��ļ�...");
		//��ñ��ָ��ļ��ĸ�Ŀ¼�������ָ����ļ��ŵ���Ŀ¼��
		File parent = file.getParentFile();
		long fileLength = file.length();
		//��ñ��ָ��С�ļ�����Ŀ
		int filenum = (int)(fileLength / size);
		if(fileLength % size != 0) {
			filenum += 1;
		}
		String[] smallfilenames = new String[filenum];
		//�����ļ���������ȡ���ָ���ļ�
		FileInputStream fin = new FileInputStream(file);
		//����һ���ֽ����飬ÿ�ζ�ȡһ���ֽڵ�����
		byte [] buf = new byte[size];
		for(int i=0; i<filenum; i++) {
			//����ָ����ļ���
			File outfile = new File(parent, file.getName()+"-"+i);
			//�����ļ������
			FileOutputStream fout = new FileOutputStream(outfile);
			//��ȡ����
			int count = fin.read(buf);
			//�������
			fout.write(buf, 0, count);
			fout.close();
			smallfilenames[i] = outfile.getName();
		}
		fin.close();
		//����ָ����ļ���
		System.out.println("�ָ����ļ����£�");
		for(int i=0; i<smallfilenames.length; i++) {
			System.out.println(smallfilenames[i]);
		}
		System.out.println("�ָ����");
	}
	
	//�ϲ��ļ�
	public void merge(File[] files) throws Exception{
		System.out.println("��ʼ�ϲ��ļ�...");
		//���Ŀ���ļ����� ��Դ�ڱ��ָ��ļ�
		String smallfilename = files[0].getName();
		int pos = smallfilename.indexOf("-");
		String tagetfilename = "new-"+smallfilename.substring(0,pos);
		System.out.println("�ϲ�����ļ���Ϊ��"+tagetfilename);
		File outFile = new File(files[0].getParentFile(), tagetfilename);
		FileOutputStream fout = new FileOutputStream(outFile);
		//�ϲ��ļ����ݣ������Ŀ���ļ�
		for(int i=0; i<files.length; i++) {
			FileInputStream fin = new FileInputStream(files[i]);
			int b;
			while((b=fin.read()) != -1) {
				fout.write(b);
			}
			fin.close();
		}
		fout.close();
		System.out.println("�ϲ����");
	}

}
