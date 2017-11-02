package com.jiangdg.poidemos.model;

import com.jiangdg.poidemos.bean.word.WordReferenceBean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipFile;

/** M层，从word文档提取参考文献数据业务处理逻辑
 *
 * Created by jiangdongguo on 2017/11/2.
 */

public class MainModelImpl implements IMainModel {

    @Override
    public List<WordReferenceBean> getWordReference(String wordPath) {
       if(wordPath.endsWith("doc")) {
           return readDoc(wordPath);
       }else if(wordPath.endsWith("docx")) {
           return readDocx(wordPath);
       }else {
           return null;
       }
    }

    /**
     *  使用POI解析dox格式文档
     * */
    private List<WordReferenceBean> readDoc(String docPath){
        List<WordReferenceBean> refBeanList = new ArrayList<>();

        return refBeanList;
    }

    /**
     *  使用POI解析dox格式文档
     * */
    private List<WordReferenceBean> readDocx(String docxPath){
        List<WordReferenceBean> refBeanList = new ArrayList<>();
        try {
            ZipFile docxFile = new ZipFile(new File(docxPath));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return refBeanList;
    }
}
