package test.tools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;


public class DelFileSpace {
    private  List<String> getTheFileData(String fileName) throws IOException {
        String oneRowData="";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(fileName),"GBK"));
        List<String> strAllRowAllDataList = new ArrayList<String>();

        while ((oneRowData = bufferedReader.readLine()) != null) {
            if(StringUtils.isBlank(oneRowData))
                continue;
            
            strAllRowAllDataList.add(oneRowData);

        }

        bufferedReader.close();
        return strAllRowAllDataList;

    }
   
    private   void writeDate2File(List<String> tmpAllData,String fileName) throws IOException{
        if(fileName==null)
            return;
        FileOutputStream fos = new FileOutputStream( fileName );
        Writer writer = new OutputStreamWriter(fos, "UTF-8" );
        StringBuffer fileinfo=new StringBuffer();
        for(String tmpstr:tmpAllData){
            if(tmpstr!=""){
                fileinfo.append(tmpstr);
                fileinfo.append("\r\n");
            }
        }
        writer.write(fileinfo.toString());
        writer.flush();
    }
    public static void main(String[] args) {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        DelFileSpace delspace=new DelFileSpace();
        String[] paths=new String[2];
        String flag="y";
        try{
        	while(flag.equals("y")){
        		//G:\workspaces2\lampsSell\WebRoot\login.jsp
	        	System.out.println("请输入要删除空白行的文件全路径：");
	            paths[0]=br.readLine();
	            
	            //G:\workspaces2\lampsSellHtml\head\.html
	            System.out.println("请输入删除空白行后的文件全路径：");
	            paths[1]=br.readLine();
	            
	            delspace.writeDate2File(delspace.getTheFileData(paths[0].replace('/', '\\')), paths[1].replace('/', '\\'));
	            System.out.println("是否继续？[y/n]");
	            flag=br.readLine();
        	}
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

}