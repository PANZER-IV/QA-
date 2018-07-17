package utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PythonUtils {

	public static String prepare_true_qfile;
	public static String prepare_true_afile;
	public static String p_t_savepath;
	public static String predict1_savepath;
	public static String predict2_savepath;
	public static String predict_q;

	public static void main(String[] args) {
		// ���崫�����
		prepare_true_qfile = "d:/java/chatWeb/test/question.txt";
		prepare_true_afile = "d:/java/chatWeb/test/answer_label.txt";
		p_t_savepath = "d:/java/chatWeb/test";
		predict1_savepath = "d:/java/chatWeb/test";
		predict2_savepath = "d:/java/chatWeb/test";
		predict_q = "Ӧ�ó���������Ⱥ����";
		String cmd1 = "python D:\\pycham\\prepare_process\\Src\\prepare_true.py" + " " + prepare_true_qfile + " "
				+ prepare_true_afile + " " + p_t_savepath;
		String cmd2 = "python D:\\pycham\\prepare_process\\Src\\predict1.py" + " " + predict1_savepath;
		String cmd3 = "python D:\\pycham\\prepare_process\\Src\\predict2.py" + " " + predict2_savepath + " "
				+ predict_q;
	    prepare(cmd1);
		prepare(cmd2);
		// predict(cmd3);
		// ����python�ű���������

	}

	public static int prepare(String cmdStr) {

		InputStream is2 = null;
		BufferedReader br2 = null;
		try {
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec(cmdStr);

			final InputStream is1 = pr.getInputStream();
			new Thread(new Runnable() {
				public void run() {
					BufferedReader br = new BufferedReader(new InputStreamReader(is1));
					try {
						while (br.readLine() != null)
							;
					} catch (Exception e) {

						e.printStackTrace();
					} finally {
						try {
							br.close();
							is1.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}).start(); // �����������߳������p.getInputStream()�Ļ�����
			is2 = pr.getErrorStream();
			br2 = new BufferedReader(new InputStreamReader(is2));
			String line = null;
			while ((line = br2.readLine()) != null) {
				System.out.println(line);
			}
			int exitVal = pr.waitFor();
			if(exitVal!=0)
				return 0;
			System.out.println("Exited with error code " + exitVal);

		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		} finally {
			try {
				br2.close();
				is2.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return 1;
	}

	public static String predict(String cmdStr) {

		// ���建��������������������������Ϣ�����
		byte[] buffer = new byte[10240];
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		ByteArrayOutputStream outerrStream = new ByteArrayOutputStream();

		Process proc;

		try {
			System.out.print("start:");
			proc = Runtime.getRuntime().exec(cmdStr);
			InputStream errStream = proc.getErrorStream();
			InputStream stream = proc.getInputStream();

			// ����ȡ��д��
			int len = -1;
			while ((len = errStream.read(buffer)) != -1) {
				outerrStream.write(buffer, 0, len);
			}
			while ((len = stream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			System.out.println("!!!!");
			proc.waitFor();// �ȴ�����ִ�����

			// ��ӡ����Ϣ
			System.out.println(outStream.toString());
		    System.out.println(outerrStream.toString());

			String result = outStream.toString();
			//if (proc.waitFor() == 0) {
			    System.out.println(result);
				return result;
			//}

			// �����յ�������ת��ΪĿ������
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "false";
	}
}