package com.jiangdg.poidemos.utils;

import android.os.Environment;
import android.webkit.WebView;

import com.jiangdg.poidemos.bean.WordBean;
import com.jiangdg.poidemos.bean.WordCharRunBean;
import com.jiangdg.poidemos.bean.WordParagraphBean;
import com.jiangdg.poidemos.bean.WordTableBean;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取doc或docx文件
 * <p>
 * Created by jiangdongguo on 2017/10/27.
 */

public class WordReadUtil {
    public static final String htmlPath = Environment.getExternalStorageDirectory().getAbsoluteFile() + File.separator + "123.html";
    private static final String SUFFIX_DOC = ".doc";
    private static final String SUFFIX_DICX = ".docx";
    private int presentPicture = 0;
    private FileOutputStream output;
    private List<Picture> picList;


    public WordBean readDocDocument(String wordPath) {
        if (wordPath.endsWith(SUFFIX_DOC)) {
            return readDoc(wordPath);
        } else if (wordPath.endsWith(SUFFIX_DICX)) {
            return readDocx(wordPath);
        }
        return null;
    }

    public WordBean readDoc2Html(String wordPath) {
        WordBean wordBean = null;
        try {
            output = new FileOutputStream(htmlPath);
            // html文件开始标签
            output.write(Contants.htmlBegin.getBytes());
            // 解析doc或docx文件
            if (wordPath.endsWith(SUFFIX_DOC)) {
                wordBean = readDoc(wordPath);
            } else if (wordPath.endsWith(SUFFIX_DICX)) {
                wordBean = readDocx(wordPath);
            }
            // html文件结束标签
            output.write(Contants.htmlEnd.getBytes());
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordBean;
    }

    private WordBean readDoc(String path) {
        WordBean wordBean = new WordBean();
        try {
            // 实例化HWPFDocument
            FileInputStream fis = new FileInputStream(path);
            POIFSFileSystem poiFs = new POIFSFileSystem(fis);
            HWPFDocument hwpfDoc = new HWPFDocument(poiFs);
            // 整个文档为一个range，读取当中的所有图片和段落
            // 解析每一个段落中，具有相同属性的文本
            picList = hwpfDoc.getPicturesTable().getAllPictures();
            Range docRange = hwpfDoc.getRange();
            TableIterator tableIterator = new TableIterator(docRange);
            int numParagraph = docRange.numParagraphs();
            List<WordParagraphBean> paragraphList = new ArrayList<>();

            for (int i = 0; i < numParagraph; i++) {
                WordParagraphBean paragraphBean = new WordParagraphBean();
                Paragraph paragraph = docRange.getParagraph(i);
                // 判断是否为表格
                if (!paragraph.isInTable()) {
                    if(output != null){
                        output.write(Contants.lineBegin.getBytes());
                    }
                    List<WordCharRunBean> charRuns = readParagraphContext(paragraph);
                    paragraphBean.setCharList(charRuns);
                    paragraphList.add(paragraphBean);
                    if(output != null){
                        output.write(Contants.lineEnd.getBytes());
                    }
                } else {
                    int temp = i;
                    if(tableIterator.hasNext()){
                        // 按行解析一个表格
                        WordTableBean bean = new WordTableBean();

                        Table table = tableIterator.next();
                        if(output != null) {
                            output.write(Contants.tableBegin.getBytes());
                        }
                        int rows = table.numRows();
                        for(int r=0; r<rows; r++){
                            if(output != null) {
                                output.write(Contants.rowBegin.getBytes());
                            }
                            // 读取表格的一行
                            TableRow row = table.getRow(r);
                            int rowNumParagraphs = row.numParagraphs();
                            int colsNumParagraphs = 0;
                            int cols = row.numCells();
                            // 读取表格中一行的所有列,即遍历单元格
                            for (int c=0; c<cols ;c++){
                                if(output != null) {
                                    output.write(Contants.columnBegin.getBytes());
                                }
                                // 读取一个单元格
                                TableCell cell = row.getCell(c);
                                int max = temp + cell.numParagraphs();
                                colsNumParagraphs = colsNumParagraphs + cell.numParagraphs();
                                for (int cp = temp; cp < max; cp++) {
                                    Paragraph p1 = docRange.getParagraph(cp);
                                    if(output != null) {
                                        output.write(Contants.lineBegin.getBytes());
                                    }
                                    readParagraphContext(p1);
                                    if(output != null) {
                                        output.write(Contants.lineEnd.getBytes());
                                    }

                                    temp++;
                                }
                                if(output != null) {
                                    output.write(Contants.columnEnd.getBytes());
                                }
                            }
                            int max1 = temp + rowNumParagraphs;
                            for (int m = temp + colsNumParagraphs; m < max1; m++) {
                                temp++;
                            }
                            output.write(Contants.rowEnd.getBytes());
                        }
                        output.write(Contants.tableEnd.getBytes());
                    }
                    i = temp;
                }
            }
            wordBean.setParagraphList(paragraphList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordBean;
    }

    private WordBean readDocx(String path) {
        WordBean wordBean = new WordBean();
        return wordBean;
    }

    /**
     * 读取某一个段落的所有内容
     */
    private List<WordCharRunBean> readParagraphContext(Paragraph paragraph) {
        List<WordCharRunBean> charRunList = new ArrayList<>();
        int numCharRun = paragraph.numCharacterRuns();
        for (int i = 0; i < numCharRun; i++) {
            WordCharRunBean charBean = new WordCharRunBean();
            charBean.setWhichRunOfPara(i);
            // 获取一段相同属性内容
            // 判断是图片，还是文本
            CharacterRun run = paragraph.getCharacterRun(i);
            try {
                if (run.getPicOffset() == 0 || run.getPicOffset() >= 1000) {
                    if (presentPicture < picList.size()) {
                        byte[] picBytes = picList.get(presentPicture).getContent();
                        charBean.setPicBytes(picBytes);
                        if (output != null) {
                            String picPath = FileUtil.createFile("html", FileUtil.getFileName(htmlPath) + presentPicture + ".jpg");
                            FileUtil.writePicture(picPath, picBytes);
                            String imageString = String.format(Contants.imgBegin, picPath);
                            output.write(imageString.getBytes());

                        }
                        presentPicture++;
                    }
                } else {
                    String text = run.text();
                    // 判断是否有空格
                    if (text.length() >= 2 && numCharRun < 2) {
                        charBean.setText(text);
                        if (output != null) {
                            output.write(text.getBytes());
                        }
                    } else {
                        charBean.setText(text);         // 文本内容
                        charBean.setTextFrontSize(run.getFontSize());  // 文本字体大小
                        charBean.setTextFrontName(run.getFontName());  // 文本字体名称
                        charBean.setTextColor(run.getColor());     // 文本颜色
                        charBean.setTextHlightedColor(run.getHighlightedColor());  // 文本高亮颜色
                        charBean.setBold(run.isBold());     // 粗体
                        charBean.setItalic(run.isItalic()); // 倾斜
                        charBean.setData(run.isData());     // 是否为数据
                        charBean.setHighlighted(run.isHighlighted());// 高亮

                        if(output != null) {
                            String fontSizeBegin = String.format(Contants.fontSizeTag,getFontSize(run.getFontSize()));
                            String fontColorBegin = String.format(Contants.fontColorTag,getFontColor(run.getColor()));
                            // 字体风格开始标签
                            boolean isBold = run.isBold();
                            boolean isItalic = run.isItalic();
                            output.write(fontColorBegin.getBytes());
                            output.write(fontSizeBegin.getBytes());
                            if(isBold) {
                                output.write(Contants.boldBegin.getBytes());
                            }
                            if(isItalic) {
                                output.write(Contants.italicBegin.getBytes());
                            }
                            // 写入文本
                            output.write(run.text().getBytes());
                            // 字体风格结束标签
                            if(isBold) {
                                output.write(Contants.boldEnd.getBytes());
                            }
                            if(isItalic) {
                                output.write(Contants.italicEnd.getBytes());
                            }
                            output.write(Contants.fontEnd.getBytes());
                            output.write(Contants.fontEnd.getBytes());
                        }
                    }
                }
                charRunList.add(charBean);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return charRunList;
    }

    /**
     * 字体大小
     * */
    private int getFontSize(int sizeType) {
        if(sizeType >= 1 && sizeType <= 8){
            return 1;
        } else if(sizeType >= 9 && sizeType <= 11){
            return 2;
        }else if(sizeType >= 12 && sizeType <= 14){
            return 3;
        }else if(sizeType >= 15 && sizeType <= 19){
            return 4;
        }else if(sizeType >= 20 && sizeType <= 29){
            return 5;
        }else if(sizeType >= 30 && sizeType <= 39){
            return 6;
        }else if(sizeType >= 40){
            return 7;
        }else{
            return 3;
        }
    }

    /**
     * 字体颜色
     * */
    private String getFontColor(int colorType) {
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
