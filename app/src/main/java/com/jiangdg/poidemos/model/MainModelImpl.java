package com.jiangdg.poidemos.model;

import android.text.SpannableString;
import android.util.Log;
import android.util.Xml;

import com.jiangdg.poidemos.bean.word.ParseResultBean;
import com.jiangdg.poidemos.bean.word.WordCharRunBean;
import com.jiangdg.poidemos.bean.word.WordReferenceBean;
import com.jiangdg.poidemos.utils.Contants;
import com.jiangdg.poidemos.utils.FileUtil;
import com.jiangdg.poidemos.utils.SpannableStringUtil;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
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
    private SpannableStringUtil mSpannaleUtil;
    private String color;
    private float size;

    public MainModelImpl() {
        mSpannaleUtil = SpannableStringUtil.getInstance();
    }

    @Override
    public void getWordReference(String wordPath, OnParseResultListener listener) {
        if (listener == null)
            return;
        if (wordPath.endsWith("doc")) {
            listener.onParseResult(readDOC(wordPath));
        } else if (wordPath.endsWith("docx")) {
            listener.onParseResult(readDOCX(wordPath));
        }
    }

    private ParseResultBean readDOC(String docPath) {
        boolean isEnter = false;
        ParseResultBean resultBean = new ParseResultBean();;
        try {
            FileInputStream in = new FileInputStream(docPath);
            POIFSFileSystem pfs = new POIFSFileSystem(in);
            HWPFDocument hwpf = new HWPFDocument(pfs);
            Range range = hwpf.getRange();

            List<SpannableString> spannableList = new ArrayList<>();
            int numParagraphs = range.numParagraphs();// 得到页面所有的段落数
            for (int i = 0; i < numParagraphs; i++) { // 遍历段落数
                // 得到文档中的每一个段落
                Paragraph p = range.getParagraph(i);
                // 读取每一段落中具有相同的属性的部分
                int pnumCharacterRuns = p.numCharacterRuns();
                for (int j = 0; j < pnumCharacterRuns; j++) {
                    CharacterRun run = p.getCharacterRun(j);
                    // 文本正文
                    String text = run.text();
                    if(isEnter) {
                        Log.d("dddd","text = "+text);
                        mSpannaleUtil.setSpanString(text);
                    }
                    if ("参考文献：".equals(text)) {
                        isEnter = true;
                    }

                    // 文本的字体大小
                    if(isEnter) {
                        mSpannaleUtil.setFrontSize(run.getFontSize(),true);
                        Log.d("dddd","run.getFontSize() = "+run.getFontSize());
                    }
                    // 文本字体颜色
                    if(isEnter) {
                        mSpannaleUtil.setFrontColor(getColor(run.getColor()));
                    }
                    // 粗体
                    if (isEnter && run.isBold()) {
                        mSpannaleUtil.setBold();
                    }
                    // 斜体
                    if (isEnter && run.isItalic()) {
                        mSpannaleUtil.setItalic();
                    }
                    // 删除线
                    if(isEnter && run.isMarkedDeleted()) {
                        mSpannaleUtil.setDeleteLine();
                    }
                    if (isEnter) {
                        spannableList.add(mSpannaleUtil.getSpanString());
                        resultBean.setSpanStrings(spannableList);
                    }
                }
            }
            isEnter = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultBean;
    }

    private ParseResultBean readDOCX(String docxPath) {
        ParseResultBean resultBean = new ParseResultBean();
        boolean isEnter = false;
        try {
            ZipFile docxFile = new ZipFile(new File(docxPath));
            ZipEntry sharedStringXML = docxFile.getEntry("word/document.xml");
            InputStream inputStream = docxFile.getInputStream(sharedStringXML);
            XmlPullParser xmlParser = Xml.newPullParser();
            xmlParser.setInput(inputStream, "utf-8");
            boolean isTable = false; // 表格
            boolean isSize = false; // 文字大小
            boolean isColor = false; // 文字颜色
            boolean isCenter = false; // 居中对齐
            boolean isRight = false; // 靠右对齐
            boolean isItalic = false; // 斜体
            boolean isUnderline = false; // 下划线
            boolean isBold = false; // 加粗
            boolean isRegion = false; // 在那个区域中
            int event_type = xmlParser.getEventType();
            List<SpannableString> spannableList = new ArrayList<>();

            while (event_type != XmlPullParser.END_DOCUMENT) {
                switch (event_type) {
                    case XmlPullParser.START_TAG: // 开始标签
                        String tagBegin = xmlParser.getName();
                        if (isEnter) {
                            Log.i("ddddd", tagBegin);
                        }
                        if (isEnter && tagBegin.equalsIgnoreCase("r")) {
                            isRegion = true;
                        }
                        if (isEnter && tagBegin.equalsIgnoreCase("jc")) { // 判断对齐方式
                            String align = xmlParser.getAttributeValue(0);
                            if (align.equals("center")) {
                                isCenter = true;
                            }
                            if (align.equals("right")) {
                                isRight = true;
                            }
                        }
                        if (isEnter && tagBegin.equalsIgnoreCase("color")) { // 判断文字颜色
                            color = xmlParser.getAttributeValue(0);
                            Log.i("ddddd", "color--->" + color);
                            isColor = true;
                        }
                        if (isEnter && tagBegin.equalsIgnoreCase("sz")) { // 判断文字大小
                            if (isRegion == true) {
//                                size = Integer.valueOf(xmlParser.getAttributeValue(0));
                                size = getSize(Integer.valueOf(xmlParser.getAttributeValue(0)));
                                Log.i("ddddd", "size--->" + Integer.valueOf(xmlParser.getAttributeValue(0)));
                                isSize = true;
                            }
                        }
                        if (isEnter && tagBegin.equalsIgnoreCase("b")) { // 检测到加粗
                            isBold = true;
                        }
                        if (isEnter && tagBegin.equalsIgnoreCase("u")) { // 检测到下划线
                            isUnderline = true;
                        }
                        if (isEnter && tagBegin.equalsIgnoreCase("i")) { // 检测到斜体
                            isItalic = true;
                        }
                        // 检测到文本
                        if (tagBegin.equalsIgnoreCase("t")) {
                            String text = xmlParser.nextText();
                            if (isEnter) {
                                Log.i("ddddd", text);
                                mSpannaleUtil.setSpanString(text);
                            }

                            if ("参考文献：".equals(text)) {
                                isEnter = true;
                            }
                            if (isEnter && isBold == true) { // 加粗
                                mSpannaleUtil.setBold();
                                isBold = false;
                            }
                            if (isEnter && isUnderline == true) { // 检测到下划线
                                mSpannaleUtil.setUnderLine();
                                isUnderline = false;
                            }
                            if (isEnter && isItalic == true) { // 检测到斜体
                                mSpannaleUtil.setItalic();
                                isItalic = false;
                            }
                            if (isEnter && isColor) {        // 颜色
                                mSpannaleUtil.setFrontColor(color);
                                isColor = false;
                            }
                            if (isEnter && isSize) {         // 字体大小
                                mSpannaleUtil.setFrontSize(size, false);
                                isSize = false;
                            }
                            if (isEnter) {
                                spannableList.add(mSpannaleUtil.getSpanString());
                                resultBean.setSpanStrings(spannableList);
                            }
                        }

                        break;
                    // 结束标签
                    case XmlPullParser.END_TAG:
                        String tagEnd = xmlParser.getName();
                        if (isEnter && tagEnd.equalsIgnoreCase("p")) { // 输入段落结束标签</p>，如果在表格中就无视
                            if (isTable == false) {
                                if (isCenter == true) { // 输入居中结束标签</center>
                                    isCenter = false;
                                }
                            }
                        }
                        if (isEnter && tagEnd.equalsIgnoreCase("r")) {
                            isRegion = false;
                        }
                        break;
                    default:
                        break;
                }
                event_type = xmlParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultBean;
    }

    private float getSize(int sizeType) {
        // 相对值
        if (sizeType >= 32) {
            return (float) 1.4;
        } else if (sizeType == 30) {
            return (float) 1.3;
        } else if (sizeType == 28) {
            return (float) 1.2;
        } else if (sizeType == 24) {
            return (float) 1.1;
        } else if (sizeType == 18) {
            return (float) 0.9;
        } else if (sizeType == 15) {
            return (float) 0.8;
        } else if (sizeType == 13) {
            return (float) 0.7;
        } else if (sizeType == 11) {
            return (float) 0.6;
        } else {
            return (float) 0.5;
        }
    }

    private String getColor(int colorType) {
        if (colorType == 1) {
            return "#000000";
        } else if (colorType == 2) {
            return "#0000FF";
        } else if (colorType == 3 || colorType == 4) {
            return "#00FF00";
        } else if (colorType == 5 || colorType == 6) {
            return "#FF0000";
        } else if (colorType == 7) {
            return "#FFFF00";
        } else if (colorType == 8) {
            return "#FFFFFF";
        } else if (colorType == 9 || colorType == 15) {
            return "#CCCCCC";
        } else if (colorType == 10 || colorType == 11) {
            return "#00FF00";
        } else if (colorType == 12 || colorType == 16) {
            return "#080808";
        } else if (colorType == 13 || colorType == 14) {
            return "#FFFF00";
        } else {
            return "#000000";
        }
    }
}