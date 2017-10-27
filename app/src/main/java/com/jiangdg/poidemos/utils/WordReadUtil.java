package com.jiangdg.poidemos.utils;

import com.jiangdg.poidemos.bean.WordBean;
import com.jiangdg.poidemos.bean.WordCharRunBean;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** 读取doc或docx文件
 *
 * Created by jiangdongguo on 2017/10/27.
 */

public class WordReadUtil {
    private static final String SUFFIX_DOC = ".doc";
    private static final String SUFFIX_DICX = ".docx";
    private int presentPicture = 0;


    public WordBean readDocDocument(String wordPath){
        if(wordPath.endsWith(SUFFIX_DOC)) {
            return readDoc(wordPath);
        }else if(wordPath.endsWith(SUFFIX_DICX)){
            return readDocx(wordPath);
        }
        return null;
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
            List<Picture> picList = hwpfDoc.getPicturesTable().getAllPictures();
            Range docRange = hwpfDoc.getRange();
            TableIterator tableIterator = new TableIterator(docRange);
            int numParagraph = docRange.numParagraphs();
            for (int i=0 ;i<numParagraph; i++) {
                Paragraph paragraph = docRange.getParagraph(i);
                // 判断一个段落是表格还是文字图片
                if(! paragraph.isInTable()){
                    wordBean.setCharList(readParagraphContext(i,paragraph,picList));
                } else {
                    List<WordTableBean> tableList = new ArrayList<>();
                    int temp = numParagraph;
                    if(tableIterator.hasNext()){
                        // 按行解析一个表格
                        WordTableBean bean = new WordTableBean();
                        Table table = tableIterator.next();
                        int rows = table.numRows();
                        for(int r=0; r<rows; r++){
                            // 读取表格的一行
                            TableRow row = table.getRow(r);
                            int cols = row.numCells();
                            int rowNumParagraphs = row.numParagraphs();
                            int colsNumParagraphs = 0;
                            // 读取表格中一行的所有列
                            for (int c=0; c<cols ;c++){
                                TableCell cell = row.getCell(c);
                                int max = temp + cell.numParagraphs();
                                colsNumParagraphs = colsNumParagraphs + cell.numParagraphs();
                                for(int cp = temp; cp<max;cp++){
                                    Paragraph p1 = docRange.getParagraph(cp);
                                    readParagraphContext(cp,p1,null);
                                    temp++;
                                }
                            }
                            int max1 = temp+rowNumParagraphs;
                            for(int m=temp+colsNumParagraphs;m<max1; m++){
                                temp++;
                            }
                        }
                        tableList.add(bean);
                        i = temp;
                    }
                }
            }
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
     * */
    private List<WordCharRunBean> readParagraphContext(int numParagraph,Paragraph paragraph,List<Picture> pictures) {
        List<WordCharRunBean> charRunList = new ArrayList<>();
        int numCharRun = paragraph.numCharacterRuns();
        for (int i=0; i<numCharRun; i++) {
            WordCharRunBean charBean = new WordCharRunBean();
            charBean.setWhichParagraph(numParagraph);
            charBean.setWhichRunOfPara(i);
            // 获取一段相同属性内容
            // 判断是图片，还是文本
            CharacterRun run = paragraph.getCharacterRun(i);
            if(run.getPicOffset()==0 || run.getPicOffset() >=1000){
                if(presentPicture < pictures.size()) {
                    byte[] picBytes = pictures.get(presentPicture).getContent();
                    charBean.setPicBytes(picBytes);
                    presentPicture++;
                }
            }else {
                String text = run.text();
                // 判断是否有空格
                if(text.length()>=2 && numCharRun<2) {
                    charBean.setSpaceBytes(text.getBytes());
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
                }
            }
            charRunList.add(charBean);
        }
        return charRunList;
    }
}
