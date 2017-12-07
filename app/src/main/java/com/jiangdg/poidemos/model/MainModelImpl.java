package com.jiangdg.poidemos.model;

import android.util.Xml;

import com.jiangdg.poidemos.bean.word.WordCharRunBean;
import com.jiangdg.poidemos.bean.word.WordReferenceBean;
import com.jiangdg.poidemos.utils.Contants;
import com.jiangdg.poidemos.utils.FileUtil;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * M层，从word文档提取参考文献数据业务处理逻辑
 * <p>
 * Created by jiangdongguo on 2017/11/2.
 */

public class MainModelImpl implements IMainModel {

    private boolean isEnter = false;

    @Override
    public List<WordReferenceBean> getWordReference(String wordPath) {
        if (wordPath.endsWith("doc")) {
            return readDoc(wordPath);
        } else if (wordPath.endsWith("docx")) {
            return null;
        } else {
            return null;
        }
    }

    @Override
    public void getAllCharRuns(String wordPath, OnParseResultListener listener) {
        if (listener == null)
            return;
        listener.onParseResult(readDocx(wordPath));
    }

    /**
     * 使用POI解析dox格式文档
     */
    private List<WordReferenceBean> readDoc(String docPath) {
        List<WordReferenceBean> refBeanList = new ArrayList<>();

        return refBeanList;
    }

    /**
     *  使用POI解析dox格式文档
     * */
//    private List<WordReferenceBean> readDocx(String docxPath){
//        List<WordReferenceBean> refBeanList = new ArrayList<>();
//        try {
//            ZipFile docxFile = new ZipFile(new File(docxPath));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return refBeanList;
//    }

    /**
     * 使用POI解析dox格式文档
     * 返回所有具有相同属性文本的段
     */
    private List<WordCharRunBean> readDocx(String docxPath) {
        List<WordCharRunBean> charRunList = new ArrayList<>();
        try {
            ZipFile docxFile = new ZipFile(new File(docxPath));
            ZipEntry sharedStringXML = docxFile.getEntry("word/document.xml");
            InputStream inputStream = docxFile.getInputStream(sharedStringXML);
            XmlPullParser xmlParser = Xml.newPullParser();
            xmlParser.setInput(inputStream, "utf-8");
            int event_type = xmlParser.getEventType();

            while (event_type != XmlPullParser.END_DOCUMENT) {
                WordCharRunBean runBean = null;
                switch (event_type) {
                    case XmlPullParser.START_TAG: // 开始标签
                        String tagBegin = xmlParser.getName();

//                        if (isEnter && tagBegin.equalsIgnoreCase("jc")) { // 判断对齐方式
//                            String align = xmlParser.getAttributeValue(0);
//                            if (align.equals("center")) {
//                                runBean.setCenter(true);
//                            }
//                            if (align.equals("right")) {
//                                runBean.setRight(true);
//                            }
//                        }
//                        if (isEnter && tagBegin.equalsIgnoreCase("color")) { // 判断文字颜色
//                            String color = xmlParser.getAttributeValue(0);
//                            runBean.setTextColor(color);
//                        }
//                        if (isEnter && tagBegin.equalsIgnoreCase("sz")) { // 判断文字大小
//                            int size = getSize(Integer.valueOf(xmlParser.getAttributeValue(0)));
//                            runBean.setTextSize(size);
//                        }
//                        if (isEnter && tagBegin.equalsIgnoreCase("b")) { // 检测到加粗
//                            runBean.setBold(true);
//                        }
//                        if (isEnter && tagBegin.equalsIgnoreCase("u")) { // 检测到下划线
//                            runBean.setUnderline(true);
//                        }
//                        if (isEnter && tagBegin.equalsIgnoreCase("i")) { // 检测到斜体
//                            runBean.setItalic(true);
//                        }
                        // 检测到文本
//                        if (tagBegin.equalsIgnoreCase("t")) {
//                            runBean = new WordCharRunBean();
//                            String text = xmlParser.nextText();
//                            if (isEnter) {
//                                runBean.setText(text);
//                            }
//                            // 整个文档中，根据"参考文献"标志找到参考文献部分
//                            if ("参考文献：".equals(text)) {
//                                isEnter = true;
//                            }
//                        }
                        break;
                    // 结束标签
                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        break;
                }
                if (isEnter && runBean != null) {
                    charRunList.add(runBean);
                }
                // 解析下一个节点
                event_type = xmlParser.next();
            }
            // 结束获取
            isEnter = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return charRunList;
    }

    private int getSize(int sizeType) {
        if (sizeType >= 1 && sizeType <= 8) {
            return 1;
        } else if (sizeType >= 9 && sizeType <= 11) {
            return 2;
        } else if (sizeType >= 12 && sizeType <= 14) {
            return 3;
        } else if (sizeType >= 15 && sizeType <= 19) {
            return 4;
        } else if (sizeType >= 20 && sizeType <= 29) {
            return 5;
        } else if (sizeType >= 30 && sizeType <= 39) {
            return 6;
        } else if (sizeType >= 40) {
            return 7;
        } else {
            return 3;
        }
    }
}